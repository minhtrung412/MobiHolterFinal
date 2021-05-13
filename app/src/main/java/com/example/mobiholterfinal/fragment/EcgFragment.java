package com.example.mobiholterfinal.fragment;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobiholterfinal.R;
import com.example.mobiholterfinal.adapter.CustomAdapter;
import com.example.mobiholterfinal.data.DBManager;
import com.example.mobiholterfinal.model.EcgDataUpload;
import com.example.mobiholterfinal.model.UserData;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class EcgFragment extends Fragment {
    //bluetooth
    static final int STATE_LISTENING = 1;
    static final int STATE_CONNECTING = 2;
    static final int STATE_CONNECTED = 3;
    static final int STATE_CONNECTION_FAILED = 4;
    static final int STATE_MESSAGE_RECEIVED = 5;
    BluetoothAdapter bluetoothAdapter;
    BluetoothDevice[] btArray;
    private ConnectedThread mConnectedThread;
    private static final String TAG = "bluetooth2";
    private StringBuilder recDataString = new StringBuilder();
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    ImageView listDevices;
    private LineChart[] mCharts = new LineChart[3];
    private Thread thread;

    public static String data_channel1_string, channel1_append, data_channel2_string, channel2_append, data_channel3_string, channel3_append,time_stamp;
    ArrayList<String> ecg_data_string;
    StringBuilder ecgap1, ecgap2, ecgap3,time_stamp_builder;

    private List<UserData> userDataList;
    DBManager dbManager;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    DatabaseReference ecg_ref;
    Date date;
    String userID, name, formattedDate;
    TextView timerText, dateText,status;
    String[] tokens;
    Timer timer;
    TimerTask timerTask, addDataTask, uploadDataTask, hrTask;
    Double timeText = 0.0;
    int one_hour = 3600000;
    ListView listView;
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    Handler handler;
    Float data0,data1,data2,yValues_0, yValues_1, yValues_2;
    Calendar calendar;
    private ArrayList<String> xValueList = new ArrayList<>();
    private ArrayList<String> timeStamp = new ArrayList<>();

    private int ecg_id = 0;
    private int sampling_rate = 100;
    TextView heart_rate_txt, hr_status;
    Integer heart_rate;



    private int i;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_ecg, container, false);
       // update = v.findViewById(R.id.btnUpdate);
        fAuth = FirebaseAuth.getInstance();
        listDevices= v.findViewById(R.id.listDevices);
        fStore = FirebaseFirestore.getInstance();
        dbManager = new DBManager(getContext());
        timer = new Timer();
        date = new Date();
        heart_rate_txt = v.findViewById(R.id.heart_rate);
        hr_status = v.findViewById(R.id.hr_status);



        userID= fAuth.getCurrentUser().getUid();
        ecg_ref = FirebaseDatabase.getInstance().getReference().child(userID);


        DocumentReference documentReference = fStore.collection("users").document(userID);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable DocumentSnapshot documentSnapshot, @javax.annotation.Nullable FirebaseFirestoreException e) {
               name = documentSnapshot.getString("Name");
            }
        });

        dbManager = new DBManager(getContext());
        userDataList= dbManager.getAllUserData();
        mCharts[0] = (LineChart) v.findViewById(R.id.lineChart0);
        mCharts[1] = (LineChart) v.findViewById(R.id.lineChart1);
        mCharts[2] = (LineChart) v.findViewById(R.id.lineChart2);
        timerText = v.findViewById(R.id.timerText);
        dateText = v.findViewById(R.id.dateText);

        status= v.findViewById(R.id.status);

//        addData();
//        uploadData();

        ecg_data_string = new ArrayList<>();
        ecgap1 = new StringBuilder();
        ecgap2 = new StringBuilder();
        ecgap3 = new StringBuilder();
        time_stamp_builder= new StringBuilder();


        for (int i = 0; i < 3; i++) {


            mCharts[i].setDragEnabled(true);
            mCharts[i].setScaleEnabled(true);
            mCharts[i].setDrawGridBackground(false);
            mCharts[i].setPinchZoom(true);
            mCharts[i].getDescription().setEnabled(false);
            //enable scaling and dragging
            Legend l = mCharts[i].getLegend();
            l.setForm(Legend.LegendForm.LINE);
            l.setTextColor(Color.RED);
            l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
            l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
            l.setOrientation(Legend.LegendOrientation.VERTICAL);
            l.setDrawInside(true);
            XAxis xl = mCharts[i].getXAxis();

            xl.setDrawGridLines(false);
            xl.setAvoidFirstLastClipping(true);
            xl.setEnabled(true);

            YAxis leftAxis = mCharts[i].getAxisLeft();
            leftAxis.setDrawGridLines(true);
            leftAxis.setGridColor(Color.WHITE);
            YAxis rightAxis = mCharts[i].getAxisRight();
            rightAxis.setEnabled(false);
            LineData data = new LineData();

            mCharts[i].setData(data);
        }

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();       // get Bluetooth adapter
        checkBTState();
        implementListeners();

        handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {

                switch (msg.what) {
                    case STATE_LISTENING:
                        status.setText("Listening");
                        break;
                    case STATE_CONNECTING:
                        status.setText("Connecting");
                        status.setTextColor(getResources().getColor(R.color.blue));
                        break;
                    case STATE_CONNECTED:
                        status.setText("Connected");
                        status.setTextColor(getResources().getColor(R.color.green));
                        startTimer();
                        addData();
                        uploadData();
                        String pattern = "hh:mm:ss "   + "  yyyy-MM-dd";
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                        String date = simpleDateFormat.format(new Date());
                        dateText.setText(date);

                        break;
                    case STATE_CONNECTION_FAILED:
                        status.setText("Connection Failed");
                        break;
                    case STATE_MESSAGE_RECEIVED:
                        String readMessage = (String) msg.obj;                                                                // msg.arg1 = bytes from connect thread
                        recDataString.append(readMessage);                                      //keep appending to string until ~
                        int endOfLineIndex = recDataString.indexOf("~");
                        Log.d("endOf", String.valueOf(endOfLineIndex));// determine the end-of-line
                        if (endOfLineIndex >= 0) {                                           // make sure there data before ~
                            String dataInPrint = recDataString.substring(0, endOfLineIndex);
                            Log.d("dataInPrint", dataInPrint);
                            if (dataInPrint.contains("#"))                             //if it starts with # we know it is what we are looking for
                            {
                                tokens= dataInPrint.split(",");

                                String sub = tokens[0].replace("#","");
                                data0 = Float.parseFloat(sub);
                                heart_rate = Integer.parseInt(tokens[1]);

                            };
                            recDataString.delete(0, recDataString.length());
                            addEntry0();
                            addEntry1();
                            addEntry2();
                            setHeart_rate_txt();


                        }

                        break;
                }
            }

        };

        return v;
    }

    public void createBTselect(){
        dialogBuilder = new AlertDialog.Builder(getContext());
        final View btPopupView = getLayoutInflater().inflate(R.layout.bt_popup, null);
        listView = (ListView) btPopupView.findViewById(R.id.listView);
        Button esc_btn= (Button)btPopupView.findViewById(R.id.exit);
        dialogBuilder.setView(btPopupView);
        dialog = dialogBuilder.create();
        dialog.show();

        esc_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ClientClass clientClass = new ClientClass(btArray[i]);
                clientClass.start();

                status.setText("Connecting");
            }
        });

    }

    private void implementListeners() {

        listDevices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createBTselect();
                Set<BluetoothDevice> bt = bluetoothAdapter.getBondedDevices();
                String[] strings = new String[bt.size()];
                btArray = new BluetoothDevice[bt.size()];
                int index = 0;

                if (bt.size() > 0) {
                    for (BluetoothDevice device : bt) {
                        btArray[index] = device;
                        strings[index] = device.getName();
                        index++;
                    }
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, strings);
                    listView.setAdapter(arrayAdapter);
                }
            }
        });


    }

    private class ClientClass extends Thread {
        private BluetoothDevice device;
        private BluetoothSocket socket;

        public ClientClass(BluetoothDevice device1) {
            device = device1;

            try {
                socket = device.createRfcommSocketToServiceRecord(MY_UUID);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        public void run() {
            try {
                socket.connect();
                Message message = Message.obtain();
                message.what = STATE_CONNECTED;
                handler.sendMessage(message);

                mConnectedThread = new ConnectedThread(socket);
                mConnectedThread.start();

            } catch (IOException e) {
                e.printStackTrace();
                Message message = Message.obtain();
                message.what = STATE_CONNECTION_FAILED;
                handler.sendMessage(message);
            }
        }
    }

    //


    private void checkBTState() {
        // Check for Bluetooth support and then check to make sure it is turned on
        // Emulator doesn't support Bluetooth and will return null
        if (bluetoothAdapter == null) {
            errorExit("Bluetooth not support");
        } else {
            if (bluetoothAdapter.isEnabled()) {
                Log.d(TAG, "...Bluetooth ON...");
            } else {
                //Prompt user to turn on Bluetooth
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, 1);
            }
        }
    }

    private void errorExit(String message) {
        Toast.makeText(getActivity().getBaseContext(), "Fatal Error" + " - " + message, Toast.LENGTH_LONG).show();
        getActivity().finish();
    }

    private class ConnectedThread extends Thread {
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectedThread(BluetoothSocket socket) {
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            // Get the input and output streams, using temp objects because
            // member streams are final
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run() {
            byte[] buffer = new byte[256];  // buffer store for the stream
            int bytes; // bytes returned from read()

            // Keep listening to the InputStream until an exception occurs
            while (true) {
                try {

                    bytes = mmInStream.read(buffer);            //read bytes from input buffer
                    String readMessage = new String(buffer, 0, bytes);
                    // Send the obtained bytes to the UI Activity via handler
                    handler.obtainMessage(STATE_MESSAGE_RECEIVED, bytes, -1, readMessage).sendToTarget();

                } catch (IOException e) {
                    break;
                }
            }
        }

        public void write(String message) {
            Log.d(TAG, "...Data to send: " + message + "...");
            byte[] msgBuffer = message.getBytes();
            try {
                mmOutStream.write(msgBuffer);
            } catch (IOException e) {
                Log.d(TAG, "...Error data send: " + e.getMessage() + "...");
            }
        }
    }

    private void startTimer(){
        timerTask= new TimerTask() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run()
                    {
                        timeText++;
                        timerText.setText((getTimerText()));
                    }
                });
            }

        };
        timer.scheduleAtFixedRate(timerTask,0,1000);
    }

    private void addData(){
        addDataTask = new TimerTask() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        UserData userData= createNewUserData();
                        if(userData != null){
                            dbManager.addUserData(userData);
                            ecgap1.delete(0,ecgap1.length());
                            ecgap2.delete(0,ecgap2.length());
                            ecgap3.delete(0,ecgap3.length());
                            time_stamp_builder.delete(0,time_stamp_builder.length());

                        }
                    }
                });
            }
        };
        timer.schedule(addDataTask,10000,20000);

    }

    private void uploadData(){
        uploadDataTask = new TimerTask() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        add_ecg_data();

                    }
                });
            }
        };
        timer.schedule(uploadDataTask,10000,20000);

    }

    private  void setHeart_rate_txt(){
        hrTask = new TimerTask() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(tokens[1] != null){
                            if(heart_rate >= 50 && heart_rate <=100 ) {
                                heart_rate_txt.setText(tokens[1] + " BPM");
                                heart_rate_txt.setTextColor(Color.GREEN);
                                hr_status.setText("Normal heart rate");
                                hr_status.setTextColor(Color.GREEN);


                            }
                            if(heart_rate >100 ) {
                                heart_rate_txt.setText(tokens[1] + " BPM");
                                heart_rate_txt.setTextColor(Color.RED);
                                hr_status.setText("High heart rate");
                                hr_status.setTextColor(Color.RED);
                            }
                            if(heart_rate < 50 ) {
                                heart_rate_txt.setText(tokens[1] + " BPM");
                                heart_rate_txt.setTextColor(Color.BLUE);
                                hr_status.setText("Lơw heart rate");
                                hr_status.setTextColor(Color.BLUE);
                            }

                        }
                    }
                });
            }
        };
        timer.schedule(hrTask, 1000,1000);

    }

    private String getTimerText()   {
        int rounded = (int)  Math.round(timeText);
        int seconds = ((rounded %86400) %3600) %60;
        int minutes = ((rounded %86400) %3600) /60;
        int hours = ((rounded %86400) /3600);
        return formatTime(seconds,minutes,hours);
    }

    private String formatTime(int seconds, int minutes, int hours){
        return String.format("%02d",hours) + ":" + String.format("%02d",minutes) + ":" + String.format("%02d",seconds);
    }

    private UserData createNewUserData(){

        String pattern = "hh:mm:ss " + "  yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat_store = new SimpleDateFormat(pattern);
        String time = simpleDateFormat_store.format(new Date());

        UserData userData = new UserData (name,time,channel1_append,channel2_append,channel3_append,time_stamp,null);
        return userData;
    }

    private LineDataSet createSet0() {
        LineDataSet set0 = new LineDataSet(null, " Chanel 1");
        set0.setDrawCircles(false);
        set0.setDrawValues(false);
        set0.setAxisDependency(YAxis.AxisDependency.LEFT);
        set0.setColor(Color.GREEN);
        set0.setLineWidth(2f);
        return set0;


    }

    private LineDataSet createSet1() {
        LineDataSet set1 = new LineDataSet(null, "Chanel 2");
        set1.setDrawCircles(false);
        set1.setDrawValues(false);
        set1.setAxisDependency(YAxis.AxisDependency.LEFT);
        set1.setColor(Color.RED);
        set1.setLineWidth(2f);

        return set1;


    }

    private LineDataSet createSet2() {
        LineDataSet set2 = new LineDataSet(null, "Chanel 3");
        set2.setDrawCircles(false);
        set2.setDrawValues(false);
        set2.setAxisDependency(YAxis.AxisDependency.LEFT);
        set2.setColor(Color.BLUE);
        set2.setLineWidth(2f);

        return set2;


    }

    public void addEntry0() {
//        data_channel1 = (float) ((Math.random() * 40) + 30f);
//        data_channel1_string = String.valueOf(data_channel1);
//        ecgap1.append(data_channel1_string + ",");
//        channel1_append = ecgap1.toString();
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        formattedDate = df.format(new Date());
        time_stamp_builder.append(formattedDate+",");
        time_stamp = time_stamp_builder.toString();
        timeStamp.add(formattedDate);
        LineData data = mCharts[0].getData();
        if (data != null) {
            ILineDataSet set0 = data.getDataSetByIndex(0);

            if (set0 == null) {
                set0 = createSet0();
                data.addDataSet(set0);

            }
            if(data0 != null) {
                int xValues = set0.getEntryCount();
                xValueList.add(String.valueOf(xValues));
                //Log.d("data_channel_receive1: ", String.valueOf(data_channel_1));
                data.addEntry(new Entry(xValues, data0), 0);
                data_channel1_string = String.valueOf(data0);
                ecgap1.append(data_channel1_string + ",");
                channel1_append = ecgap1.toString();
                XAxis xl = mCharts[0].getXAxis();
                xl.setDrawGridLines(true);
                xl.setGranularityEnabled(true);
                xl.setGranularity(2f);
                xl.setValueFormatter(new IndexAxisValueFormatter(timeStamp));
                data.notifyDataChanged();
                mCharts[0].notifyDataSetChanged();
                mCharts[0].setVisibleXRangeMaximum(500);
                mCharts[0].moveViewToX(data.getEntryCount());
            }



        }
    }




    public void addEntry1() {
//        data_channel2 = (float) ((Math.random() * 40) + 30f);


        LineData data = mCharts[1].getData();

        if (data != null) {
            ILineDataSet set1 = data.getDataSetByIndex(0);

            if (set1 == null) {
                set1 = createSet1();
                data.addDataSet(set1);

            }
            if(data0!= null) {
                // Log.d("data_channel_receive2: ", String.valueOf(data_channel_2));

                data.addEntry(new Entry(set1.getEntryCount(), data0), 0);
                data.notifyDataChanged();
                XAxis x2 = mCharts[1].getXAxis();
                x2.setEnabled(false);
                mCharts[1].notifyDataSetChanged();
                mCharts[1].setVisibleXRangeMaximum(500);
                mCharts[1].moveViewToX(data.getEntryCount());;
                data_channel2_string = String.valueOf(data0);
                ecgap2.append(data_channel2_string + ",");
                channel2_append = ecgap2.toString();
            }

        }
    }

    public void addEntry2() {
//        data_channel3_string = String.valueOf(data_channel3);
//        ecgap3.append(data_channel3_string + ",");
//        channel3_append = ecgap3.toString();
        LineData data = mCharts[2].getData();
        if (data != null) {

            ILineDataSet set2 = data.getDataSetByIndex(0);

            if (set2 == null) {
                set2 = createSet2();
                data.addDataSet(set2);

            }

            if (data0 != null) {

                data.addEntry(new Entry(set2.getEntryCount(), data0), 0);
                data.notifyDataChanged();

                XAxis x2 = mCharts[2].getXAxis();
                x2.setEnabled(false);

                mCharts[2].notifyDataSetChanged();
                mCharts[2].setVisibleXRangeMaximum(500);
                mCharts[2].moveViewToX(data.getEntryCount());
                data_channel3_string = String.valueOf(data0);
                ecgap3.append(data_channel3_string + ",");
                channel3_append = ecgap3.toString();
            }
        }
    }

    private void add_ecg_data(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        String time_stamp = sdf.format(new Date());
        ecg_id++;
        EcgDataUpload ecg_data = new EcgDataUpload(channel1_append, channel2_append, channel3_append,ecg_id,time_stamp,userID,sampling_rate);
        ecg_ref.push().setValue(ecg_data);
    }




}