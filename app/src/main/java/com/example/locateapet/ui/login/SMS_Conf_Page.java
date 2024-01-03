package com.example.locateapet.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.locateapet.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

public class SMS_Conf_Page extends AppCompatActivity {

    FirebaseAuth pAuth;
    public static final String PHONE = "Empty";
    Button send_data = findViewById(R.id.confirm_button);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_conf_page);

        send_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //login logic here
            }
        });
    }
}