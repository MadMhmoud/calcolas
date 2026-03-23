package com.madmhmoud.calcolas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Objects;


public class MainActivity extends AppCompatActivity {

    Button num9;
    Button num8;
    Button num7;
    Button num6;
    Button num5;
    Button num4;
    Button num3;
    Button num2;
    Button num1;
    Button num0;
    Button plusBtn;
    Button multiBtn;
    Button minusBtn;
    Button divisionBtn;
    Button equalBtn;
    Button cleanBtn;
    Button dotBtn;
    Button backBtn;
    TextView result;
    Button rotatebtn;

    boolean isResult = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        num9 = findViewById(R.id.num9);
        num8 = findViewById(R.id.num8);
        num7 = findViewById(R.id.num7);
        num6 = findViewById(R.id.num6);
        num5 = findViewById(R.id.num5);
        num4 = findViewById(R.id.num4);
        num3 = findViewById(R.id.num3);
        num2 = findViewById(R.id.num2);
        num1 = findViewById(R.id.num1);
        num0 = findViewById(R.id.num0);


        plusBtn = findViewById(R.id.plusbtn);
        multiBtn = findViewById(R.id.multibtn);
        minusBtn = findViewById(R.id.minusbtn);
        divisionBtn = findViewById(R.id.divisionbtn);
        equalBtn = findViewById(R.id.equalbtn);

        cleanBtn = findViewById(R.id.cleanbtn);
        dotBtn = findViewById(R.id.dotbtn);
        backBtn = findViewById(R.id.backbtn);

        result = findViewById(R.id.resultview);
        rotatebtn = findViewById(R.id.rotateBtn);
    }

    @Override
    protected void onStart() {
        super.onStart();

        View.OnClickListener l = this::addDigit;

        num0.setOnClickListener(l);
        num1.setOnClickListener(l);
        num2.setOnClickListener(l);
        num3.setOnClickListener(l);
        num4.setOnClickListener(l);
        num5.setOnClickListener(l);
        num6.setOnClickListener(l);
        num7.setOnClickListener(l);
        num8.setOnClickListener(l);
        num9.setOnClickListener(l);

        minusBtn.setOnClickListener(l);
        plusBtn.setOnClickListener(l);
        dotBtn.setOnClickListener(l);
        divisionBtn.setOnClickListener(l);
        multiBtn.setOnClickListener(l);

        equalBtn.setOnClickListener(v -> result.setText(solve()));
        cleanBtn.setOnClickListener(v -> result.setText(""));
        backBtn.setOnClickListener(v -> deleteDigit());

        rotatebtn.setOnClickListener(v -> {

            Intent intent = new Intent(MainActivity.this, expandedActivity.class);

            intent.putExtra("Result", result.getText().toString());

            startActivity(intent);

        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        String val = getSharedPreferences("app", MODE_PRIVATE).getString("result", null);

            result.setText(val);

    }

    private void deleteDigit() {

        if(result.getText().length() > 0 && !isResult) {

            result.setText(result.getText().toString().substring(0, result.getText().length() - 1));

        } else {
            result.setText("");
            isResult = false;
        }

    }

    private String solve() {

        String problem = result.getText().toString();

        return MadMath.solveStringSafe(problem);

    }


    private void addDigit(View v) {

        if(!isResult) {

            result.append(((Button)v).getText());

        }
        else {

            result.setText("");
            result.append(((Button)v).getText());

            isResult = false;

        }


    }


}
