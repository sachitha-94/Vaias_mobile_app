package com.vaias.ucsc.vaias.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.vaias.ucsc.vaias.R;

import java.security.PrivateKey;

public class SendMassageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_massage);
        sendMsgBtn=(Button)findViewById(R.id.sendBtn);

        sendMsgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }

    Button sendMsgBtn;
}
