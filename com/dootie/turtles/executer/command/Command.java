package com.dootie.turtles.executer.command;

import com.dootie.turtles.executer.Executer;

public abstract class Command {

    public abstract void execute(Executer parser, String[] arguments);
}

