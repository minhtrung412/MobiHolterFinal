package com.example.mobiholterfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button access_btn;
    private static final String KEY_FIRST_INSTALL = "KEY_FIRST_INSTALL";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final MySharedPreferences mySharedPreferences= new MySharedPreferences(this);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(mySharedPreferences.getBooleanValue(KEY_FIRST_INSTALL)){
                    //Main Screen
                    startActivity(LoginActivity.class);

                }else {
                    //Onboarding Screen

                    startActivity(OnboardingActivity.class);
                    mySharedPreferences.putBooleanValue(KEY_FIRST_INSTALL,true);


                }

            }
        }, 3000);

    }
    private void startActivity(Class<?> cls){
        Intent intent = new Intent(this,cls);
        startActivity(intent);
        finish();
    }
}