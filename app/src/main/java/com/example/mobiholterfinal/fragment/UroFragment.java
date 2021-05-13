package com.example.mobiholterfinal.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.mobiholterfinal.R;
import com.example.mobiholterfinal.model.UroData;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;


public class UroFragment extends Fragment {

    int i ;

    public UroFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_uro_his, container, false);
        // Inflate the layout for this fragment

        readEcgData();

        LineChart lineChart= v.findViewById(R.id.uro_line);

        LineDataSet lineDataSet = new LineDataSet(lineChartDataSet(), "ml");

        ArrayList<ILineDataSet> iLineDataSets = new ArrayList<>();
        iLineDataSets.add(lineDataSet);
        LineData lineData = new LineData(iLineDataSets);
        lineChart.setData(lineData);
        lineChart.setVisibleXRangeMaximum(100000);
        lineChart.moveViewToX(lineDataSet.getEntryCount());
        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(true);
        lineDataSet.setColor(Color.GREEN);
        lineDataSet.setDrawCircles(false);
        lineDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        lineDataSet.setLineWidth(2f);
        return v;
    }

    private ArrayList<Entry> lineChartDataSet(){
        ArrayList<Entry> dataSet = new ArrayList();


        for (i=0; i<timeSamples.size(); i++){
            float xValues= Float.parseFloat(timeSamples.get(i));
            float yValues= Float.parseFloat(weightSamples.get(i));
            dataSet.add(new Entry(xValues, yValues));
            //Log.d(TAG, String.valueOf(xValues) + String.valueOf(yValues));

        }

        return dataSet;

    }

    private List<String> timeSamples = new ArrayList<>();
    private List<String> weightSamples = new ArrayList<>();
    private List<UroData> dataSamples = new ArrayList<>();
    public void readEcgData(){

        InputStream inputStream= getResources().openRawResource(R.raw.urodata);
        BufferedReader reader= new BufferedReader(
                new InputStreamReader(inputStream, Charset.forName("UTF-8"))
        );

        String line = "";
        try {

            reader.readLine();
            timeSamples.clear();
            weightSamples.clear();
            while ( (line = reader.readLine())!= null){
                String[] tokens = line.split(",");
                // Read the data

                UroData dataModel = new UroData();

                if (tokens[0].matches("#")== true   ){

                    dataModel.setTitle(Float.parseFloat(tokens[1]));
                    String title_display = tokens[1]   ;
                    Log.d("title_display", title_display);
                } else {

                    dataModel.setTitle(0);
                }
                if ((tokens[0].matches("#") == false) &&(tokens[0].length() > 0)){
                    dataModel.setTime(Float.parseFloat(tokens[0]));
                    timeSamples.add(String.valueOf(dataModel.getTime()));
                } else{
                    dataModel.setTime(0);
                }
                if (tokens.length >= 2 && tokens[1].length() <= 4 ){
                    dataModel.setWeight(Float.parseFloat(tokens[1]));
                } else{
                    dataModel.setWeight(0);
                }

                dataSamples.add(dataModel);
                weightSamples.add(String.valueOf(dataModel.getWeight()) );

                //Log.d(TAG, "Data stored: " + dataModel);
                //Log.d(TAG, "TIME SAMPLES: " + timeSamples);
                // Log.d(TAG, "WEIGHT SAMPLES: "+ weightSamples    );







            }
        } catch (IOException e) {
            Log.wtf("My activity", "Error reading"+ line,e);
            e.printStackTrace();
        }


    }


}