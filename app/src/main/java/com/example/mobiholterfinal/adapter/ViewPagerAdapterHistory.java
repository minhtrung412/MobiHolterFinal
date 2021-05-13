package com.example.mobiholterfinal.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.mobiholterfinal.fragment.EcgFragmentHistory;
import com.example.mobiholterfinal.fragment.UroFragment;

public class ViewPagerAdapterHistory extends FragmentStatePagerAdapter {
    public ViewPagerAdapterHistory(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 1:
                return new UroFragment();
            case 0:
            default:
                return new EcgFragmentHistory();


        }

    }

    @Override
    public int getCount() {

        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title= "";
        switch (position)
        {
            case 0:
                title = "ECG";
                break;
            case 1:
                title = "URO RECORD";
                break;

        }

        return title;
    }
}

