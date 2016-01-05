package com.dootie.turtles.repository;

import com.dootie.turtles.repository.Turtle;
import java.util.List;
import java.util.UUID;

public interface ITurtleRepository {
    public List<Turtle> getTurtles();

    public void removeTurtle(int var1, int var2, int var3);

    public Turtle createTurtle(UUID var1, int var2, int var3, int var4);

    public Turtle getTurtle(int var1, int var2, int var3);
}

