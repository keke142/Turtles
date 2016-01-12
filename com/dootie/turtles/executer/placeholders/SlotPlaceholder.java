package com.dootie.turtles.executer.placeholders;

import com.dootie.turtles.repository.Turtle;


public class SlotPlaceholder extends Placeholder{

    @Override
    public String replace(Turtle turtle, String placeholder) {
        for(int i = 0; i < 9; i++){
            if(placeholder.startsWith("%slot "+i)){
                if(placeholder.equals("%slot "+i+" type%"))
                   return turtle.getInventory().getItem(i).getType().toString();
                else
                if(placeholder.equals("%slot "+i+" amount%"))
                    return Integer.toString(turtle.getInventory().getItem(i).getAmount());
                else
                if(placeholder.equals("%slot "+i+" damage%"))
                    return Integer.toString(turtle.getInventory().getItem(i).getDurability());
                else
                if(placeholder.equals("%slot "+i+" maxdamage%"))
                    return Integer.toString(turtle.getInventory().getItem(i).getType().getMaxDurability());
            }
        }
        return "AIR";
    }
    
}
