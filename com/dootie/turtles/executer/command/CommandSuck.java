package com.dootie.turtles.executer.command;

import com.dootie.turtles.executer.Executer;
import com.dootie.turtles.util.DirectionHelper;
import java.util.HashMap;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;

public class CommandSuck
extends Command {
    Executer parser;
    String[] arguments;

    @Override
    public void execute(Executer parser, String[] arguments){
        this.parser = parser;
        this.arguments = arguments;
        if (this.arguments.length != 1) {
            return;
        }
        String direction = this.arguments[0];
        if (!DirectionHelper.isValidDirection(direction)) {
            parser.getTurtle().sendError("Invalid direction given: " + direction + ".");
        }
        int[] coordinates = DirectionHelper.getCoordinates(direction, this.parser.getTurtle().getX(), this.parser.getTurtle().getY(), this.parser.getTurtle().getZ());
        for (Entity e : this.parser.getWorld().getEntities()) {
            if (e.getType() != EntityType.DROPPED_ITEM) continue;
            Item item = (Item)e;
            Block blockIsIn = this.parser.getWorld().getBlockAt(e.getLocation());
            if (blockIsIn.getX() != coordinates[0] || blockIsIn.getY() != coordinates[1] || blockIsIn.getZ() != coordinates[2]) continue;
            e.remove();
            HashMap excess = this.parser.getTurtle().getInventory().addItem(new ItemStack[]{item.getItemStack()});
            for (Object excessItem : excess.entrySet())
                this.parser.getWorld().dropItem(this.parser.getTurtle().getLocation(this.parser.getWorld()), (ItemStack)excessItem);
        }
    }
}

