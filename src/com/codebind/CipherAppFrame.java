package com.codebind;

import com.sun.tools.javac.Main;
import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.Flow;

public class CipherAppFrame extends JFrame implements ActionListener {

    private JPanel panelMain;

    public CipherAppFrame() {
        this.setTitle("Cipher App");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(420, 420);
        Compute compute = new Compute();


        JPanel northPanel = new JPanel(new GridLayout(2, 1));

        JPanel welcomePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        welcomePanel.add(new JLabel("Welcome to Caesar Cipher!"));

        northPanel.add(welcomePanel);


        JPanel informationPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        informationPanel.add(new JLabel("Please enter a shift as a positive integer greater zero, and a message that you want to encrypt or decrypt."));

        northPanel.add(informationPanel);


        JPanel westPanel = new JPanel();
        westPanel.setLayout(new BoxLayout(westPanel, BoxLayout.Y_AXIS));

        JPanel shiftPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        shiftPanel.add(new JLabel("Shift: "));
        TextField shiftTxtField = new TextField();
        shiftPanel.add(shiftTxtField);
        JPanel encryptPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton encryptBtn = new JButton("Encrypt Message");
        encryptPanel.add(encryptBtn);
        JPanel decryptPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton decryptBtn = new JButton("Decrypt Message");
        decryptPanel.add(decryptBtn);

        westPanel.add(shiftPanel);
        westPanel.add(encryptPanel);
        westPanel.add(decryptPanel);


        JPanel middlePanel = new JPanel(new GridLayout(2, 1));
        TextArea inputTxtArea = new TextArea("Input: ");
        TextArea outputTxtArea = new TextArea("Output: ");
        middlePanel.add(inputTxtArea);
        middlePanel.add(outputTxtArea);

        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton clearBtn = new JButton("Clear all");
        southPanel.add(clearBtn);


        encryptBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int shift = Integer.parseInt(shiftTxtField.getText());
                String input = inputTxtArea.getText();
                input = input.replaceFirst("Input: ", "");
                String output = compute.movingShift(input, shift);
                outputTxtArea.setText("Output: " + output);
            }
        });
        decryptBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int shift = Integer.parseInt(shiftTxtField.getText());
                String input = inputTxtArea.getText();
                input = input.replaceFirst("Input: ", "");
                String output = compute.demovingShift(input, shift);
                outputTxtArea.setText("Output: " + output);
            }
        });
        clearBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                shiftTxtField.setText("");
                inputTxtArea.setText("Input: ");
                outputTxtArea.setText("Output: ");
            }
        });


        this.add(westPanel, BorderLayout.WEST);
        this.add(northPanel, BorderLayout.NORTH);
        this.add(middlePanel, BorderLayout.CENTER);
        this.add(southPanel, BorderLayout.SOUTH);


        this.pack();
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public static class Compute {
        public String movingShift(String s, int shift) {
            shift %= 26;
            char[] arr = s.toCharArray();
            for (int i = 0; i < arr.length;i++) {
                if (Character.isUpperCase(arr[i])) {
                    char letter = (char) (arr[i] + shift);
                    if (letter > 'Z') {
                        letter -= 26;
                    }
                    arr[i] = letter;
                } else if (Character.isLowerCase(arr[i])) {
                    char letter = (char) (arr[i] + shift);
                    if (letter > 'z') {
                        letter -= 26;
                    }
                    arr[i] = letter;
                }
                shift += 1;
                if (shift >= 26) {
                    shift = shift % 26;
                }
            }
            return String.valueOf(arr);
        }

        public String demovingShift(String s, int shift) {
            //Shift into negative ASCII values
            shift *= -1;
            shift %= 26;

            // transform cipher into plaintext
            StringBuilder plaintext = new StringBuilder();
            char[] arr = s.toCharArray();
            for (int i = 0; i < arr.length; i++) {
                if (Character.isUpperCase((arr[i]))) {
                    char letter = (char) ((arr[i] + shift));
                    if (letter < 'A') {
                        letter += 26;
                    }
                    arr[i] = letter;
                } else if (Character.isLowerCase(arr[i])) {
                    char letter = (char) (arr[i] + shift);
                    if (letter < 'a') {
                        letter += 26;
                    }
                    arr[i] = letter;
                } else {
                    char letter = arr[i];
                    arr[i] = letter;
                }
                shift -= 1;
                if (shift <= -26) {
                    shift = shift % 26;
                }
            }
            plaintext.append(String.valueOf(arr));
            return plaintext.toString();
        }
    }

    @Test
    public void test1() {
        String u = "I should have known that you would have a perfect answer for me!!!";
        String v = "J vltasl rlhr zdfog odxr ypw atasl rlhr p gwkzzyq zntyhv lvz wp!!!";
        Compute compute = new Compute();
        assertEquals(v, compute.movingShift(u, 1));
        assertEquals(u, compute.demovingShift(compute.movingShift(u, 1), 1));
    }
}


