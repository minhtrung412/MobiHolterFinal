package com.example.mobiholterfinal.fragment;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobiholterfinal.R;
import com.example.mobiholterfinal.adapter.CustomAdapter;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class HistoryFragment extends Fragment {
    RecyclerView recyclerView;
    CustomAdapter customAdapter;
    DBManager dbManager;
    List<UserData> userDataList;
    CardView cardView;
    UserData userData;
    FloatingActionButton deleteBtn;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_history, container, false);

        recyclerView= v.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        cardView= v.findViewById(R.id.cardView);
        deleteBtn = v.findViewById(R.id.float_delete_btn);
        dbManager = new DBManager(this.getContext());
        userDataList = dbManager.getAllUserData();
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbManager.removeAll();
                setAdapter();
            }
        });

        // updateListData();
        setAdapter();


        return v;

    }

    private void setAdapter(){
        LayoutAnimationController layoutAnimationController= AnimationUtils.loadLayoutAnimation(getContext(), R.anim.layout_animation_right_to_left);
        recyclerView.setLayoutAnimation(layoutAnimationController);
        CustomAdapter customAdapter = new CustomAdapter(getContext(), dbManager.getAllUserData());
        recyclerView.setAdapter(customAdapter);

    }

}