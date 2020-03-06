package com.shestee;

import com.shestee.interfaces.Cli;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import static java.util.Arrays.asList;

public class CliUtil implements Cli {
    private final LinkedList<String> inputs;
    private final List<String> outputs = new LinkedList<>();

    public CliUtil(String... input) {
        inputs = new LinkedList<>(asList(input));
    }

    public static CliUtil withInput(String ... input) {
        return new CliUtil(input);
    }

    @Override
    public void print(String text) {
        outputs.add(text);
    }

    @Override
    public void printf(String text, Object ... args) {
        String output = String.format(text, args);
        outputs.add(output);
    }

    public void println(String text) {
        outputs.add(text);
    }

    public String readLine() {
        return inputs.pop();
    }

    public List<String> getOutputs() {
        return outputs;
    }
}
