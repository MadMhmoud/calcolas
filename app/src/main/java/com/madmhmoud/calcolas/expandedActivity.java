package com.madmhmoud.calcolas;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;


public class expandedActivity extends AppCompatActivity {

    TextView result2;

    boolean isResult = false;

    Button dig0;
    Button dig1;
    Button dig2;
    Button dig3;
    Button dig4;
    Button dig5;
    Button dig6;
    Button dig7;
    Button dig8;
    Button dig9;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_expanded);


        result2 = findViewById(R.id.result2);

        dig0 = findViewById(R.id.dig0);
        dig1 = findViewById(R.id.dig1);
        dig2 = findViewById(R.id.dig2);
        dig3 = findViewById(R.id.dig3);
        dig4 = findViewById(R.id.dig4);
        dig5 = findViewById(R.id.dig5);
        dig6 = findViewById(R.id.dig6);
        dig7 = findViewById(R.id.dig7);
        dig8 = findViewById(R.id.dig8);
        dig9 = findViewById(R.id.dig9);

    }

    @Override
    protected void onStart() {
        super.onStart();

        result2.setText(getIntent().getStringExtra("Result"));

        View.OnClickListener l = this::addDigit;

        dig0.setOnClickListener(l);
        dig1.setOnClickListener(l);
        dig2.setOnClickListener(l);
        dig3.setOnClickListener(l);
        dig4.setOnClickListener(l);
        dig5.setOnClickListener(l);
        dig6.setOnClickListener(l);
        dig7.setOnClickListener(l);
        dig8.setOnClickListener(l);
        dig9.setOnClickListener(l);




    }

    @Override
    protected void onPause() {
        super.onPause();

        getSharedPreferences("app", MODE_PRIVATE)
                .edit()
                .putString("result", result2.getText().toString())
                .apply();
        finish();

    }

    private void addDigit(View v) {

        if(!isResult) {

            result2.append(((Button)v).getText());

        }
        else {

            result2.setText("");
            result2.append(((Button)v).getText());

            isResult = false;

        }


    }

}