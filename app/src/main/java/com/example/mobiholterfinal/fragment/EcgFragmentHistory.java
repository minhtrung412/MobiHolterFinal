package com.example.mobiholterfinal.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.mobiholterfinal.R;
import com.example.mobiholterfinal.data.DBManager;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class EcgFragmentHistory extends Fragment {

    DBManager dbManager;
    UserData userData;
    private LineChart[] charts = new LineChart[3];
    private int i;
    private List<String> dataList_1 = new ArrayList<>();
    private List<String> dataList_2 = new ArrayList<>();
    private List<String> dataList_3 = new ArrayList<>();
    private List<String> dataList_4 = new ArrayList<>();

    public EcgFragmentHistory() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_ecg_his,container,false);


        String data_get_1 = getActivity().getIntent().getStringExtra("Data1");
        String data_get_2 = getActivity().getIntent().getStringExtra("Data2");
        String data_get_3 = getActivity().getIntent().getStringExtra("Data3");
        String data_get_time = getActivity().getIntent().getStringExtra("DataTime");



        String[] tokens_1 = data_get_1.split(",");
        //Log.d("token_ 1", String.valueOf(tokens_1[0]));
        String[] tokens_2 = data_get_2.split(",");
        String[] tokens_3 = data_get_3.split(",");
        String[] tokens_4 = data_get_time.split(",");

        dataList_1.addAll(Arrays.asList(tokens_1));
        dataList_2.addAll(Arrays.asList(tokens_2));
        dataList_3.addAll(Arrays.asList(tokens_3));
        dataList_4.addAll(Arrays.asList(tokens_4));


        charts[0]= v.findViewById(R.id.lineChart_his1);
        charts[1]= v.findViewById(R.id.lineChart_his2);
        charts[2]= v.findViewById(R.id.lineChart_his3);


        LineDataSet lineDataSet1 = new LineDataSet(lineChartDataSet1(), "Channel 1");
        LineDataSet lineDataSet2 = new LineDataSet(lineChartDataSet2(), "Channel 2");
        LineDataSet lineDataSet3 = new LineDataSet(lineChartDataSet3(), "Channel 3");


        ArrayList<ILineDataSet> iLineDataSets1 = new ArrayList<>();
        iLineDataSets1.add(lineDataSet1);
        LineData lineData1 = new LineData(iLineDataSets1);

        ArrayList<ILineDataSet> iLineDataSets2 = new ArrayList<>();
        iLineDataSets2.add(lineDataSet2);
        LineData lineData2 = new LineData(iLineDataSets2);

        ArrayList<ILineDataSet> iLineDataSets3 = new ArrayList<>();
        iLineDataSets3.add(lineDataSet3);
        LineData lineData3 = new LineData(iLineDataSets3);


        charts[0].setData(lineData1);
        charts[0].getDescription().setEnabled(false);
        charts[0].setDrawGridBackground(true);
        charts[1].setData(lineData2);
        charts[1].getDescription().setEnabled(false);
        charts[1].setDrawGridBackground(true);
        charts[2].setData(lineData3);
        charts[2].getDescription().setEnabled(false);
        charts[2].setDrawGridBackground(true);




        // enable scaling and dragging
        Legend l0= charts[0].getLegend();
        l0.setForm(Legend.LegendForm.LINE);
        l0.setTextColor(Color.GREEN);
        l0.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l0.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l0.setOrientation(Legend.LegendOrientation.VERTICAL);
        l0.setDrawInside(true);

        Legend l1 = charts[1].getLegend();
        l1.setForm(Legend.LegendForm.LINE);
        l1.setTextColor(Color.RED);
        l1.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l1.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l1.setOrientation(Legend.LegendOrientation.VERTICAL);
        l1.setDrawInside(true);

        Legend l2 = charts[2].getLegend();
        l2.setForm(Legend.LegendForm.LINE);
        l2.setTextColor(Color.BLUE);
        l2.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l2.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l2.setOrientation(Legend.LegendOrientation.VERTICAL);
        l2.setDrawInside(true);



        YAxis leftAxis1 = charts[0].getAxisLeft();
        leftAxis1.setEnabled(true);
        leftAxis1.setAxisMinimum(-600);
        leftAxis1.setAxisMaximum(600);
        YAxis rightAxis1 = charts[0].getAxisRight();
        rightAxis1.setEnabled(false);

        YAxis leftAxis2 = charts[1].getAxisLeft();
        leftAxis2.setEnabled(true);
        leftAxis2.setAxisMinimum(-600);
        leftAxis2.setAxisMaximum(600);
        YAxis rightAxis2 = charts[1].getAxisRight();
        rightAxis2.setEnabled(false);

        YAxis leftAxis3 = charts[2].getAxisLeft();
        leftAxis3.setEnabled(true);
        leftAxis3.setAxisMinimum(-600);
        leftAxis3.setAxisMaximum(600);
        YAxis rightAxis3 = charts[2].getAxisRight();
        rightAxis3.setEnabled(false);

        lineDataSet1.setColor(Color.GREEN);
        lineDataSet1.setDrawCircles(false);
        lineDataSet1.setDrawValues(false);
        lineDataSet1.setLineWidth(2f);

        lineDataSet2.setColor(Color.RED);
        lineDataSet2.setDrawCircles(false);
        lineDataSet2.setDrawValues(false);
        lineDataSet2.setLineWidth(2f);

        lineDataSet3.setColor(Color.BLUE);
        lineDataSet3.setDrawCircles(false);
        lineDataSet3.setDrawValues(false);
        lineDataSet3.setLineWidth(2f);

        charts[0].setVisibleXRangeMaximum(1000);
        charts[1].setVisibleXRangeMaximum(1000);
        charts[2].setVisibleXRangeMaximum(500);

        return v;

    }

    private ArrayList<Entry> lineChartDataSet1(){
        ArrayList<Entry> dataSet1 = new ArrayList();

        for (i=0; i<dataList_1.size(); i++){
            float xValues = Float.parseFloat(String.valueOf(i));
            float yValues= Float.parseFloat(dataList_1.get(i));
            dataSet1.add(new Entry(xValues, yValues));
            XAxis xl = charts[0].getXAxis();
            xl.setDrawGridLines(true);
            xl.setGranularityEnabled(true);
            xl.setGranularity(2f);
            xl.setValueFormatter(new IndexAxisValueFormatter(dataList_4));
            //Log.d("Chart Data: ", String.valueOf(yValues));

        }

        return dataSet1;

    }

    private ArrayList<Entry> lineChartDataSet2(){
        ArrayList<Entry> dataSet2 = new ArrayList();

        for (i=0; i<dataList_2.size(); i++){
            float xValues = Float.parseFloat(String.valueOf(i));
            float yValues= Float.parseFloat(dataList_2.get(i));
            dataSet2.add(new Entry(xValues, yValues));
            //Log.d("Chart Data: ", String.valueOf(xValues) + String.valueOf(yValues));

        }

        return dataSet2;

    }

    private ArrayList<Entry> lineChartDataSet3(){
        ArrayList<Entry> dataSet3 = new ArrayList();

        for (i=0; i<dataList_3.size(); i++) {
            float xValues = Float.parseFloat(String.valueOf(i));
            float yValues = Float.parseFloat(dataList_3.get(i));
            dataSet3.add(new Entry(xValues, yValues));
        }
        return dataSet3;

    }

}
