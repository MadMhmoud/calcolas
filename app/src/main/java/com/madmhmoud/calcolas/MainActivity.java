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

import java.util.ArrayList;
import java.util.Objects;

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


        Button plusBtn = findViewById(R.id.plusbtn);
        Button multiBtn = findViewById(R.id.multibtn);
        Button minusBtn = findViewById(R.id.minusbtn);
        Button divisionBtn = findViewById(R.id.divisionbtn);
        Button equalBtn = findViewById(R.id.equalbtn);

        Button cleanBtn = findViewById(R.id.cleanbtn);
        Button dotBtn = findViewById(R.id.dotbtn);
        Button backBtn = findViewById(R.id.backbtn);

        result = findViewById(R.id.resultview);

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

        if(isValidExpression(result.getText().toString())) {

            isResult = true;

            return lastResult();
        }
        else {

            isResult = true;

            return "wrong syntax!";
        }


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

    private String lastResult() {

        ArrayList<String> arrayTokenized = tokenize(result.getText().toString());

        for(int i = 0 ; i < arrayTokenized.size() ; i++) { // the *

            if(Objects.equals(arrayTokenized.get(i), "*")) {

                double a =  Double.parseDouble(arrayTokenized.get(i - 1));
                double b =  Double.parseDouble(arrayTokenized.get(i + 1));

                double r = a * b;

                arrayTokenized.set(i, String.valueOf(r));

                arrayTokenized.remove(i + 1);
                arrayTokenized.remove(i - 1);

                i = 0;

            }

        }

        for(int i = 0 ; i < arrayTokenized.size() ; i++) { // the /

            if(Objects.equals(arrayTokenized.get(i), "/")) {

                double a =  Double.parseDouble(arrayTokenized.get(i - 1));
                double b =  Double.parseDouble(arrayTokenized.get(i + 1));

                double r = a / b;

                arrayTokenized.set(i, String.valueOf(r));

                arrayTokenized.remove(i + 1);
                arrayTokenized.remove(i - 1);

                i = 0;

            }

        }

        for(int i = 0 ; i < arrayTokenized.size() ; i++) { // the +

            if(Objects.equals(arrayTokenized.get(i), "+")) {

                double a =  Double.parseDouble(arrayTokenized.get(i - 1));
                double b =  Double.parseDouble(arrayTokenized.get(i + 1));

                double r = a + b;

                arrayTokenized.set(i, String.valueOf(r));

                arrayTokenized.remove(i + 1);
                arrayTokenized.remove(i - 1);

                i = 0;

            }

        }

        for(int i = 0 ; i < arrayTokenized.size() ; i++) { // the -

            if(Objects.equals(arrayTokenized.get(i), "-")) {

                double a =  Double.parseDouble(arrayTokenized.get(i - 1));
                double b =  Double.parseDouble(arrayTokenized.get(i + 1));

                double r = a - b;

                arrayTokenized.set(i, String.valueOf(r));

                arrayTokenized.remove(i + 1);
                arrayTokenized.remove(i - 1);

                i = 0;

            }

        }

        isResult = true;

        return arrayTokenized.get(0);


    }

    public static boolean isValidExpression(String expr) {
        if (expr == null || expr.isEmpty())
            return false;

        // Remove spaces
        expr = expr.replaceAll("\\s+", "");

        // Check allowed characters
        if (!expr.matches("[0-9+\\-*/().]+"))
            return false;

        // Cannot start with * or /
        if (expr.startsWith("*") || expr.startsWith("/"))
            return false;

        // Cannot end with operator or dot
        if (expr.matches(".*[+\\-*/.]$"))
            return false;

        int parentheses = 0;
        boolean lastWasOperator = false;
        boolean lastWasDot = false;

        for (int i = 0; i < expr.length(); i++) {
            char c = expr.charAt(i);

            if (c == '(') {
                parentheses++;
                lastWasOperator = false;
            }
            else if (c == ')') {
                parentheses--;
                if (parentheses < 0) return false;
                lastWasOperator = false;
            }
            else if ("+-*/".indexOf(c) != -1) {
                if (lastWasOperator)
                    return false;

                lastWasOperator = true;
                lastWasDot = false;
            }
            else if (c == '.') {
                if (lastWasDot)
                    return false;

                lastWasDot = true;
                lastWasOperator = false;
            }
            else { // digit
                lastWasOperator = false;
                lastWasDot = false;
            }
        }

        return parentheses == 0;
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
