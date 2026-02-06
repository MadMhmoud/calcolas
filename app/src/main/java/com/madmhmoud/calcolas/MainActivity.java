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
        Button minusbtn = findViewById(R.id.minusbtn);
        Button equalbtn = findViewById(R.id.equalbtn);

        Button cleanbtn = findViewById(R.id.cleanbtn);
        Button dotbtn = findViewById(R.id.dotbtn);

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

        equalbtn.setOnClickListener(v -> result.setText(solve()));
        cleanbtn.setOnClickListener(v -> result.setText(""));



    }

    private String solve() {

        String current = String.valueOf(result.getText());

        isResult = true;

        return String.valueOf(ExprEval.eval(current)) ;
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





class ExprEval {

    public static double eval(String expr) {
        List<String> tokens = tokenize(expr);
        List<String> rpn = toRpn(tokens);
        return evalRpn(rpn);
    }

    private static List<String> tokenize(String s) {
        List<String> out = new ArrayList<>();
        int i = 0;

        while (i < s.length()) {
            char c = s.charAt(i);

            if (Character.isWhitespace(c)) { i++; continue; }

            if ("+-*/()".indexOf(c) >= 0) {
                out.add(String.valueOf(c));
                i++;
                continue;
            }

            if (Character.isDigit(c) || c == '.') {
                int start = i;
                boolean dotSeen = (c == '.');
                i++;
                while (i < s.length()) {
                    char d = s.charAt(i);
                    if (Character.isDigit(d)) { i++; continue; }
                    if (d == '.' && !dotSeen) { dotSeen = true; i++; continue; }
                    break;
                }
                out.add(s.substring(start, i));
                continue;
            }

            throw new IllegalArgumentException("Invalid char: " + c);
        }

        return out;
    }

    private static int prec(String op) {
        return (op.equals("*") || op.equals("/")) ? 2 : 1;
    }

    private static boolean isOp(String t) {
        return t.equals("+") || t.equals("-") || t.equals("*") || t.equals("/");
    }

    private static List<String> toRpn(List<String> tokens) {
        List<String> out = new ArrayList<>();
        Deque<String> ops = new ArrayDeque<>();

        for (String t : tokens) {
            if (isOp(t)) {
                while (!ops.isEmpty() && isOp(ops.peek()) && prec(ops.peek()) >= prec(t)) {
                    out.add(ops.pop());
                }
                ops.push(t);
            } else if (t.equals("(")) {
                ops.push(t);
            } else if (t.equals(")")) {
                while (!ops.isEmpty() && !ops.peek().equals("(")) out.add(ops.pop());
                if (ops.isEmpty() || !ops.peek().equals("(")) throw new IllegalArgumentException("Mismatched parentheses");
                ops.pop();
            } else {
                // number
                out.add(t);
            }
        }

        while (!ops.isEmpty()) {
            String op = ops.pop();
            if (op.equals("(") || op.equals(")")) throw new IllegalArgumentException("Mismatched parentheses");
            out.add(op);
        }

        return out;
    }

    private static double evalRpn(List<String> rpn) {
        Deque<Double> st = new ArrayDeque<>();

        for (String t : rpn) {
            if (!isOp(t)) {
                st.push(Double.parseDouble(t));
                continue;
            }

            if (st.size() < 2) throw new IllegalArgumentException("Bad expression");
            double b = st.pop();
            double a = st.pop();

            switch (t) {
                case "+": st.push(a + b); break;
                case "-": st.push(a - b); break;
                case "*": st.push(a * b); break;
                case "/": st.push(a / b); break;
            }
        }

        if (st.size() != 1) throw new IllegalArgumentException("Bad expression");
        return st.pop();
    }
}