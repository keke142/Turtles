package com.dootie.turtles.executer.placeholders;

import com.dootie.turtles.repository.Turtle;
import org.bukkit.Bukkit;


public class OwnerPlaceholder extends Placeholder{

    @Override
    public String replace(Turtle turtle, String placeholder) {
        switch(placeholder){
            case "%owner name%": return Bukkit.getPlayer(turtle.getOwner()).getName();
            case "%owner uuid%": return turtle.getOwner().toString();
        }
        return "";
    }
    
}
