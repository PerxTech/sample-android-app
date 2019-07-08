package com.example.digisample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Base64;

public class CongratsActivity extends AppCompatActivity {
    public static final String PAYLOAD = "payload";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_congrats);

        TextView raw = findViewById(R.id.base64Txt);
        TextView json = findViewById(R.id.json);

        Intent intent = getIntent();
        String message = intent.getStringExtra(CongratsActivity.PAYLOAD);
        if (message == null) {
            return;
        }
        raw.setText(message);

        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.O) {
            json.setVisibility(View.GONE);
            return;
        }

       byte[] decoded = Base64.getDecoder().decode(message);
       json.setText(new String(decoded));
    }
}
