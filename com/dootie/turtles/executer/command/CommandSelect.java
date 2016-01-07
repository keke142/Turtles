package com.dootie.turtles.executer.command;

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
            parser.getTurtle().sendMessage("Invalid number: " + this.arguments[0] + ".");
        }
        
        if (slot < 0 || slot > this.parser.getTurtle().getInventory().getSize() - 1) 
            parser.getTurtle().sendError("Slot out of range: " + slot + ".");
        
        this.parser.setCurrentSelectedSlot(slot);
    }
}

