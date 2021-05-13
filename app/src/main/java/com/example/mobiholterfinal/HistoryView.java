package com.example.mobiholterfinal;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.mobiholterfinal.adapter.ViewPagerAdapter;
import com.example.mobiholterfinal.adapter.ViewPagerAdapterHistory;
import com.google.android.material.tabs.TabLayout;

public class HistoryView extends AppCompatActivity {
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_view);
        mTabLayout=  findViewById(R.id.tab_layout);
        mViewPager = findViewById(R.id.view_pager_history);

        ViewPagerAdapterHistory viewPagerAdapterHistory = new ViewPagerAdapterHistory(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mViewPager.setAdapter(viewPagerAdapterHistory);

        mTabLayout.setupWithViewPager(mViewPager);

    }
}