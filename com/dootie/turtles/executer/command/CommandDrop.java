package com.dootie.turtles.executer.command;

import com.dootie.turtles.executer.ParseException;
import com.dootie.turtles.executer.Executer;
import com.dootie.turtles.repository.Turtle;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

public class CommandDrop
extends Command {

    Executer parser;
    String[] arguments;

    @Override
    public void execute(Executer parser, String[] arguments){
        this.parser = parser;
        this.arguments = arguments;
        
        boolean dropWholeStack = false;
        if (this.arguments.length == 1 && this.arguments[0].equals("stack"))
            dropWholeStack = true;
        
        Turtle turtle = this.parser.getTurtle();
        ItemStack stack = this.parser.getTurtle().getInventory().getItem(this.parser.getCurrentSelectedSlot());
        if (stack != null){
            ItemStack toDrop = stack.clone();
            toDrop.setAmount(dropWholeStack ? stack.getAmount() : 1);
            this.parser.getWorld().dropItem(new Location(this.parser.getWorld(), (double)turtle.getX(), (double)(turtle.getY() - 1), (double)turtle.getZ()), toDrop);
            stack.setAmount(stack.getAmount() - 1);
            if (stack.getAmount() <= 0 || dropWholeStack)
                turtle.getInventory().setItem(this.parser.getCurrentSelectedSlot(), null);
        }
    }
}

