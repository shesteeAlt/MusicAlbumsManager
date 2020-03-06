package com.shestee;

import com.shestee.interfaces.Cli;

import java.util.Scanner;

public class CliImpl implements Cli {

    @Override
    public void print(String text) {
        System.out.print(text);
    }

    @Override
    public void printf(String text, Object ... args) {
        System.out.printf(text, args);
    }

    @Override
    public void println(String text) {
        System.out.println(text);
    }

    @Override
    public String readLine() {
        Scanner input = new Scanner(System.in);
        return input.nextLine();
    }
}
