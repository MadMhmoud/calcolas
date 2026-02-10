package com.madmhmoud.calcolas;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView result;

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


        Button num9 = findViewById(R.id.num9);
        Button num8 = findViewById(R.id.num8);
        Button num7 = findViewById(R.id.num7);
        Button num6 = findViewById(R.id.num6);
        Button num5 = findViewById(R.id.num5);
        Button num4 = findViewById(R.id.num4);
        Button num3 = findViewById(R.id.num3);
        Button num2 = findViewById(R.id.num2);
        Button num1 = findViewById(R.id.num1);
        Button num0 = findViewById(R.id.num0);


        Button plusbtn = findViewById(R.id.plusbtn);
        Button multibtn = findViewById(R.id.multibtn);
        Button minusbtn = findViewById(R.id.minusbtn);
        Button divisionbtn = findViewById(R.id.divisionbtn);
        Button equalbtn = findViewById(R.id.equalbtn);

        Button cleanbtn = findViewById(R.id.cleanbtn);
        Button dotbtn = findViewById(R.id.dotbtn);
        Button backbtn = findViewById(R.id.backbtn);

        result = findViewById(R.id.resultview);

        View.OnClickListener l = v -> addDigit(v);;

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
        minusbtn.setOnClickListener(l);
        plusbtn.setOnClickListener(l);
        dotbtn.setOnClickListener(l);
        divisionbtn.setOnClickListener(l);
        multibtn.setOnClickListener(l);

        equalbtn.setOnClickListener(v -> result.setText(solve()));
        cleanbtn.setOnClickListener(v -> result.setText(""));
        backbtn.setOnClickListener(v -> deleteDigit());




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

        return (String.join(" | ", tokenize(result.getText().toString()))) ;
    }

    private ArrayList<String> tokenize(String expression) {

        expression = expression.trim();
        ArrayList<String> tokens = new ArrayList<>();

        if (expression.isEmpty()) return tokens;

        StringBuilder number = new StringBuilder();

        for (int i = 0; i < expression.length(); i++) {

            char c = expression.charAt(i);

            // skip spaces
            if (Character.isWhitespace(c)) continue;

            boolean isOperator = (c == '+' || c == '-' || c == '*' || c == '/');

            // treat '-' as unary minus if it's at the start OR follows another operator
            if (c == '-' && (tokens.isEmpty() && number.length() == 0)) {
                number.append(c);
                continue;
            }
            if (c == '-' && number.length() == 0 && !tokens.isEmpty()) {
                String prev = tokens.get(tokens.size() - 1);
                if (prev.equals("+") || prev.equals("-") || prev.equals("*") || prev.equals("/")) {
                    number.append(c);
                    continue;
                }
            }

            if (isOperator) {

                // flush current number
                if (number.length() == 0) {
                    throw new IllegalArgumentException("Wrong entry: operator without number at index " + i);
                }
                tokens.add(number.toString()); // adding the current number
                number.setLength(0);

                // add operator
                tokens.add(String.valueOf(c));

            }
            else {

                // allow digits and dot
                if (!(Character.isDigit(c) || c == '.')) {
                    throw new IllegalArgumentException("Wrong entry: invalid char '" + c + "' at index " + i);
                }
                number.append(c);

            }
        }

        // flush last number
        if (number.length() == 0) {
            throw new IllegalArgumentException("Wrong entry: ends with operator");
        }
        tokens.add(number.toString());

        return tokens;
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
