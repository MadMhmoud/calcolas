package com.madmhmoud.calcolas;

import java.util.ArrayList;
import java.util.Objects;

public class madMath {

    public static ArrayList<String> tokenize(String expression) {

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








    public static String lastResult(String result) {

        ArrayList<String> arrayTokenized = tokenize(result);

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


        return arrayTokenized.get(0);


    }



}
