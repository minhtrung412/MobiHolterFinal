package com.example.mobiholterfinal.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.mobiholterfinal.fragment.EcgFragment;
import com.example.mobiholterfinal.fragment.HistoryFragment;
import com.example.mobiholterfinal.fragment.HomeFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new HomeFragment();
            case 1:
                return new EcgFragment();
            case 2:
                return new HistoryFragment();
            default:
                return new HomeFragment();

        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
