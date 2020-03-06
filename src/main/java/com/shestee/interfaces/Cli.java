package com.shestee.interfaces;

public interface Cli {
    void print(String text);

    void printf(String text, Object ... args);

    void println(String text);

    String readLine();
}
