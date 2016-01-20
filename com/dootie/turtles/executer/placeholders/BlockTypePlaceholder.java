package com.dootie.turtles.executer.placeholders;

import com.dootie.turtles.repository.Turtle;
import com.dootie.turtles.util.DirectionHelper;
import org.bukkit.Material;
import org.bukkit.block.Block;


public class BlockTypePlaceholder extends Placeholder{

    @Override
    public String replace(Turtle turtle, String placeholder) {
        switch(placeholder){
            case "%block north type%": return getBlockType(turtle, "north");
            case "%block east type%": return getBlockType(turtle, "east");
            case "%block south type%": return getBlockType(turtle, "south");
            case "%block west type%": return getBlockType(turtle, "west");
            case "%block up type%": return getBlockType(turtle, "up");
            case "%block down type%": return getBlockType(turtle, "down");
        }
        return Material.AIR.toString();
    }
    
    public String getBlockType(Turtle turtle, String direction){
        int[] coordinates = DirectionHelper.getCoordinates(direction, turtle.getX(), turtle.getY(), turtle.getZ());
        Block block = turtle.executer.getWorld().getBlockAt(coordinates[0], coordinates[1], coordinates[2]);
        return block.getType().toString();
    }
}
