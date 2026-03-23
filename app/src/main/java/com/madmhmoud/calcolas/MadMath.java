package com.madmhmoud.calcolas;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;


public class MadMath {

    public static void main(String[] args) {
        
    }
    
    
    public static ArrayList<String> tokenize(String string) {
        
        ArrayList<String> list = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        
        boolean wasOperater = true;
        
        
        
        
        for(int i = 0 ; i < string.length() ; i++) { 
            
            char c = string.charAt(i);
            
            
            if(Character.isDigit(c) || c == '.') { // is a digit
                
                if(!wasOperater) { // the c before was NOT op
                    
                    builder.append(c);
                    
                    wasOperater = false;
                    
                    continue;
                    
                }
                else { // the c before was an op
                    
                    
                    builder.setLength(0);
                    builder.append(c);
                    
                    wasOperater = false;
                    
                    continue;
                    
                }
            }
            
            else if("/*-+^".indexOf(c) != -1) { // c is an op
                
                if(wasOperater) { // c before was an op
                    
                    if(c == '-' && builder.length() == 0) {
                        
                        builder.setLength(0);
                        builder.append(c);
                        
                        wasOperater = false;
                        
                        continue;
                        
                    }
                    else {
                        throw new RuntimeException("Expected an opertater!");
                    }
                }
                
                else { // c before was a digit
                    
                    
                    if(builder.length() > 0) {
                        
                        list.add(builder.toString()); // flush hte number
                        builder.setLength(0);
                    }
                    
                    wasOperater = true;
                    
                    list.add(c + "");
                    
                    
                }
                
            }
            
            else if(c == '(' || c == ')') {
                
                
                if(builder.length() > 0) {
                    
                    list.add(builder.toString());
                    builder.setLength(0);
                    
                }
                
                list.add(c + "");
                
            }
            
            else {
                
                throw new RuntimeException("Wrong Syntax!");
                
            }
            
            
            
        }
        
        if(builder.length() > 0) {
        list.add(builder.toString());
        }
        
        return list;
        
    }
    
    public static ArrayList<String> toRPN(ArrayList<String> src) {
        
        Deque<String> stack = new ArrayDeque<>();
        ArrayList<String> output = new ArrayList<>();
        
        
        for (int i = 0; i < src.size(); i++) {
            
            String token = src.get(i);
            char c = token.charAt(0);
            
            if (token.isEmpty()) continue;
            
            if(Character.isDigit(c) || token.length() > 1) {
                 output.add(token);
            }
            
            else if (c == '(') {
                 stack.push(token);
            }
            
            else if (c == ')') {

                while (!stack.isEmpty() && !stack.peek().equals("(")) {
                     output.add(stack.pop());
                }

                stack.pop(); 
                
                }
            
            else if (isOperator(token)) {

            while (!stack.isEmpty()
                    && !stack.peek().equals("(")
                    && precedence(stack.peek()) >= precedence(token)) {

                    output.add(stack.pop());
               }

            stack.push(token);

             }

             }
        
                while (!stack.isEmpty()) {
                output.add(stack.pop());
                }

            return output;
        }
    
    public static String solveRPN(ArrayList<String> src) {
        
        Deque<String> stack = new ArrayDeque<>();
        
        for(int i = 0 ; i < src.size() ; i++) {
            
            String token = src.get(i);
            char c = token.charAt(0);
            
            
            if(Character.isDigit(c) || token.length() > 1) {
                stack.push(token);
                continue;
            }
            
            
            if(c == '+') {
                double a = Double.parseDouble(stack.pop());
                double b = Double.parseDouble(stack.pop());
                
                double r = a + b;
                stack.push(String.valueOf(r));
                continue;
            }
            
            if(c == '-') {
                double a = Double.parseDouble(stack.pop());
                double b = Double.parseDouble(stack.pop());
                
                
                double r = b - a;
                stack.push(String.valueOf(r));
                continue;
            }
            
            if(c == '*') {
                double a = Double.parseDouble(stack.pop());
                double b = Double.parseDouble(stack.pop());
                
                double r = a * b;
                stack.push(String.valueOf(r));
                continue;
            }
            if(c == '/') {
                double a = Double.parseDouble(stack.pop());
                double b = Double.parseDouble(stack.pop());
                
                double r = b / a;
                stack.push(String.valueOf(r));
                continue;
            }
            if(c == '^') {
                double a = Double.parseDouble(stack.pop());
                double b = Double.parseDouble(stack.pop());
                
                double r = Math.pow(b, a);
                stack.push(String.valueOf(r));
                continue;
            }
            
            
        }
        
        return stack.peek();
    }
    
    public static String solveString(String src) {
        
        ArrayList<String> tokenized = tokenize(src);
        ArrayList<String> rpn = toRPN(tokenized);
        
        String result = solveRPN(rpn);
        
        return result;
    }
    
    public static boolean verfiyProblem(String src) {
        
        StringBuilder builder = new StringBuilder();
        Deque<Character> stack = new ArrayDeque<>();
        
        
        for(int i = 0 ; i < src.length() ; i++) {
            
            char c = src.charAt(i);
            
            
            if(Character.isDigit(c) || c == '.' || (c == '-' && builder.length() == 0)) {
                
                builder.append(c);
                
            }
            
            else if(isOperator(c + "")) {
                
                if (i == 0) {
                    return false;
                }
                
                if(i == src.length() - 1) {
                    return false;
                }
                
                if(!checkNum(builder.toString())) {
                    return false;
                }
                else {
                    builder.setLength(0);
                }
                
                if(isOperator(src.charAt(i + 1) + "")) {
                    return false;
                    
                }
                
            }
            
            else if(c == '(') {
                
                stack.push(c);
            }
            
            else if(c == ')') {
                
                if(stack.pop() == '(') {
                    continue;
                }
                else {
                    return false;
                }
                
            }
            
            
        }
        
        if(stack.isEmpty()) {
            return true;
        }
        else {
            return false;
        }
        
        
    }
    
    public static String solveStringSafe(String src) {
        
        if(verfiyProblem(src)) {
            
            ArrayList<String> tokenized = tokenize(src);
            ArrayList<String> rpn = toRPN(tokenized);
        
            String result = solveRPN(rpn);
        
            return result;
        }
        
        return null;
        
    }
    
    
    public static boolean isOperator(String s) {
        
            return s.equals("+") || s.equals("-") || s.equals("*") || s.equals("/") | s.equals("^");
    
        }
    
    public static int precedence(String s) {
        
            if (s.equals("+") || s.equals("-")) return 1;
            if (s.equals("*") || s.equals("/")) return 2;
            if (s.equals("^")) return 3;
        
            return 0;
        }
    
    public static boolean checkNum(String src) {
        
        int count = 0;
        
        for(int i = 0 ; src.length() > i ; i++) {
            
            if(src.charAt(i) == '.') {
                count++;
            }
            
        }
        
        if(count > 1) {
            
            return false;
        }
        else {
            return  true;
        }
        
    }
    
    
}
