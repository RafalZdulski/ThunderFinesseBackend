package com.thunderfinesse.thunderback.subprograms.executors;

public class JarExecutorException extends Exception {
    public final Exception e;
    public JarExecutorException(Exception e) {
        this.e = e;
    }
}
