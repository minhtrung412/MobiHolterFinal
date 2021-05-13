package com.example.mobiholterfinal.onboardingfragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.mobiholterfinal.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OnboardingFragment2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OnboardingFragment2 extends Fragment {

    public OnboardingFragment2() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_onboarding2, container, false);
    }
}