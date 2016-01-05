package com.dootie.turtles.repository;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.inventory.InventoryHolder;

public class TurtleRepositoryWorld
implements ITurtleRepository {
    private List<Turtle> turtles = new ArrayList<Turtle>();

    @Override
    public List<Turtle> getTurtles() {
        return this.turtles;
    }

    @Override
    public void removeTurtle(int x, int y, int z) {
        Turtle turtle = this.getTurtle(x, y, z);
        if (turtle != null) {
            this.turtles.remove(turtle);
        }
    }

    @Override
    public Turtle createTurtle(UUID owner, int x, int y, int z) {
        Turtle turtle = new Turtle(x, y, z, owner, Bukkit.createInventory((InventoryHolder)null, (int)9, (String)"Turtle"), this);
        this.turtles.add(turtle);
        return turtle;
    }

    @Override
    public Turtle getTurtle(int x, int y, int z) {
        for (Turtle turtle : this.turtles) {
            if (turtle.getX() != x || turtle.getY() != y || turtle.getZ() != z) continue;
            return turtle;
        }
        return null;
    }
}

