package com.dootie.turtles.executer;

import com.dootie.turtles.executer.command.Command;
import com.dootie.turtles.executer.command.CommandDig;
import com.dootie.turtles.executer.command.CommandDrop;
import com.dootie.turtles.executer.command.CommandGoto;
import com.dootie.turtles.executer.command.CommandMove;
import com.dootie.turtles.executer.command.CommandPlace;
import com.dootie.turtles.executer.command.CommandPrint;
import com.dootie.turtles.executer.command.CommandSelect;
import com.dootie.turtles.executer.command.CommandSuck;
import java.util.HashMap;
import java.util.Map;

public class CommandResolver {
    private final String name;
    private final String[] arguments;
    public static final Map<String, Command> commands;
    
    static {
        commands = new HashMap<String, Command>();
    }

    public CommandResolver(String name, String[] arguments) {
        this.name = name;
        this.arguments = arguments;
    }

    public Command resolve(Executer parser) {
        return commands.get(name);
    }
}

