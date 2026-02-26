package com.madmhmoud.calcolas;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;


public class expandedActivity extends AppCompatActivity {

    TextView result2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_expanded);


        result2 = findViewById(R.id.result2);



    }

    @Override
    protected void onStart() {
        super.onStart();

        result2.setText(getIntent().getStringExtra("Result"));

        getSharedPreferences("app", MODE_PRIVATE)
                .edit()
                .putString("result", result2.getText().toString())
                .apply();
        finish();



    }

}