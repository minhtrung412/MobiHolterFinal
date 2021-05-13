package com.example.mobiholterfinal.onboardingfragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mobiholterfinal.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OnboardingFragment1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OnboardingFragment1 extends Fragment {



    public OnboardingFragment1() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_onboarding1, container, false);
    }
}