package com.dootie.turtles.executer.command;

import com.dootie.turtles.executer.ParseException;
import com.dootie.turtles.executer.Executer;

public class CommandSelect
extends Command {
    Executer parser;
    String[] arguments;

    @Override
    public void execute(Executer parser, String[] arguments){
        this.parser = parser;
        this.arguments = arguments;
        if (this.arguments.length != 1) return;
        
        int slot = 0;
        try {
            slot = Integer.parseInt(this.arguments[0]);
        } catch (NumberFormatException e) {
            ParseException.exception(parser.getTurtle(),"Invalid number: " + this.arguments[0] + ".");
        }
        
        if (slot < 0 || slot > this.parser.getTurtle().getInventory().getSize() - 1) 
            ParseException.exception(parser.getTurtle(),"Slot out of range: " + slot + ".");
        
        this.parser.setCurrentSelectedSlot(slot);
    }
}

