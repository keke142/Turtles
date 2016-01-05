package com.dootie.turtles.executer;

import com.dootie.turtles.repository.Turtle;

public class ParseException {
    public static void exception(Turtle turtle, String message) {
        turtle.sendErrorToOwner(message);
    }
}

