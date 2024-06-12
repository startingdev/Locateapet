package com.example.locateapet.ui.login;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.locateapet.MainActivity;
import com.example.locateapet.R;
import com.example.locateapet.ui.login.LoginViewModel;
import com.example.locateapet.ui.login.LoginViewModelFactory;
import com.example.locateapet.databinding.ActivityLoginBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginActivity extends AppCompatActivity {

    public EditText phone_number;
    Button send_phone;
    public String phone;

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (authStateListener != null) {
            mAuth.removeAuthStateListener(authStateListener);
        }
    }

    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    FirebaseAuth.AuthStateListener authStateListener = new FirebaseAuth.AuthStateListener() {
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
            if (firebaseUser != null) {
                Toast.makeText(LoginActivity.this, "Вход совершен успешно!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_login);

        phone_number = findViewById(R.id.phone_num);
        send_phone = findViewById(R.id.login);

        send_phone.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Send_data();
            }
        });

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            // Пользователь уже аутентифицирован, переходим к MainActivity
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }



    public void Send_data(){

        phone = phone_number.getText().toString().trim();

        if (phone != null){
            if ((phone.charAt(0) != '+') && (phone.length() != 12)){
                Toast.makeText(LoginActivity.this, "Неверный формат телефона", Toast.LENGTH_LONG).show();
            }else{
                Intent i = new Intent(LoginActivity.this, SMS_Conf_Page.class);

                i.putExtra("mobile", phone);

                startActivity(i);
            }
        }else{
            Toast.makeText(LoginActivity.this, "Error", Toast.LENGTH_LONG).show();
        }
    }
}