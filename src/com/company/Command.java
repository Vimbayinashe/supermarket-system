package com.company;

@FunctionalInterface
public interface Command {
    void execute(String option);
}
