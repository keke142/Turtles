package com.dootie.turtles.executer.command;

import com.dootie.turtles.executer.Executer;
import com.dootie.turtles.repository.Turtle;
import com.dootie.turtles.util.DirectionHelper;
import com.dootie.turtles.Turtles;
import org.bukkit.Material;

public class CommandMove
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
        Turtle turtle = this.parser.getTurtle();
        String direction = this.arguments[0];
        if (!DirectionHelper.isValidDirection(direction)) {
            parser.getTurtle().sendError("Invalid direction given: " + direction + ".");
        }
        int[] coordinates = DirectionHelper.getCoordinates(direction, turtle.getX(), turtle.getY(), turtle.getZ());
        if (this.parser.getWorld().getBlockAt(coordinates[0], coordinates[1], coordinates[2]).getType() == Material.AIR) {
            this.parser.getWorld().getBlockAt(turtle.getX(), turtle.getY(), turtle.getZ()).setType(Material.AIR);
            turtle.setX(coordinates[0]);
            turtle.setY(coordinates[1]);
            turtle.setZ(coordinates[2]);
            this.parser.getWorld().getBlockAt(coordinates[0], coordinates[1], coordinates[2]).setType(Turtles.blockTurtle.getType());
        }
    }
}

