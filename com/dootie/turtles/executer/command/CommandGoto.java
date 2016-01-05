package com.dootie.turtles.executer.command;

import com.dootie.turtles.executer.Executer;

public class CommandGoto
extends Command {

    Executer parser;
    String[] arguments;

    @Override
    public void execute(Executer parser, String[] arguments){
        this.parser = parser;
        this.arguments = arguments;
        if (this.arguments.length == 1) {
            parser.parse(Integer.parseInt(arguments[0]));
        }
    }
}

