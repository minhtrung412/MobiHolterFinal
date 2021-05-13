package com.example.mobiholterfinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    Button loginBtn;
    EditText login_email, login_pass;
    FirebaseAuth fAuth;
    ProgressBar progressBar2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginBtn = findViewById(R.id.loginBtn);
        login_email = findViewById(R.id.loginID);
        login_pass  = findViewById(R.id.loginPass);
        progressBar2= findViewById(R.id.progressBar);
        fAuth = FirebaseAuth.getInstance();

        if(fAuth.getCurrentUser() != null)
        {
            Intent intent = new Intent(this,MainScreen.class);
            startActivity(intent);
            finish();
        }
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = login_email.getText().toString().trim();
                String password = login_pass.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    login_email.setError("Email is required");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    login_pass.setError("Password is required");
                    return;
                }

                if (password.length() < 8) {
                    login_pass.setError("Password must be at least 8 characters");
                    return;
                }
                progressBar2.setVisibility(View.VISIBLE);
                // check user

                fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Logged in successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent (LoginActivity.this, MainScreen.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(LoginActivity.this, "Error !" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar2.setVisibility(View.GONE);
                        }
                    }
                });

            }
        });


    }
}