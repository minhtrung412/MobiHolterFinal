package com.example.mobiholterfinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.mobiholterfinal.adapter.ViewPagerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainScreen extends AppCompatActivity {
    private BottomNavigationView mNavigationView;
    private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        mNavigationView = findViewById(R.id.bottom_nav);
        mNavigationView.setItemIconTintList(null);
        viewPager = findViewById(R.id.view_pager);
        setUpViewPager();
        mNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.main_screen:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.ecg_record:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.history:
                        viewPager.setCurrentItem(2);
                        break;

                }
                return true;
            }
        });

    }

    private void setUpViewPager(){
   //     ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        viewPager.setAdapter(viewPagerAdapter);
//        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                switch (position){
//                    case 0:
//                        mNavigationView.getMenu().findItem(R.id.action_home).setChecked(true);
//                        break;
//                    case 1:
//                        mNavigationView.getMenu().findItem(R.id.action_favor).setChecked(true);
//                        break;
//                    case 2:
//                        mNavigationView.getMenu().findItem(R.id.action_my_page).setChecked(true);
//                        break;
//                }
//
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });

    }
}