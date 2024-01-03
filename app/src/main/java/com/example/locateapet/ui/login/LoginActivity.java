package com.example.locateapet.ui.login;

import android.app.Activity;

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

import com.example.locateapet.R;
import com.example.locateapet.ui.login.LoginViewModel;
import com.example.locateapet.ui.login.LoginViewModelFactory;
import com.example.locateapet.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private EditText phone_number;
    Button send_phone;
    private String phone;

    @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_login);

        phone_number = findViewById(R.id.phone_num);
        send_phone = findViewById(R.id.login);

        send_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Send_data();
            }
        });
    }



    public void Send_data(){

        phone = phone_number.getText().toString().trim();
        Intent i = new Intent(LoginActivity.this, SMS_Conf_Page.class);

        i.putExtra(SMS_Conf_Page.PHONE, phone);

        startActivity(i);
    }
}