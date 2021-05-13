package com.example.mobiholterfinal.onboardingfragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.strictmode.WebViewMethodCalledOnWrongThreadViolation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.mobiholterfinal.LoginActivity;
import com.example.mobiholterfinal.R;


public class OnboardingFragment3 extends Fragment {


    public OnboardingFragment3() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_onboarding3, container, false);

        Button goBtn = v.findViewById(R.id.goBtn);
        goBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getActivity(), LoginActivity.class);
                getActivity().startActivity(intent);
            }
        });
        // Inflate the layout for this fragment
        return v;
    }
}