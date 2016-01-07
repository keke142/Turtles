package com.dootie.turtles.executer.command;

import com.dootie.turtles.executer.Executer;
import com.dootie.turtles.repository.Turtle;
import com.dootie.turtles.util.DirectionHelper;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

public class CommandPlace
extends Command {
    public static Map<Material, Material> ITEM_CONVERSIONS = new HashMap<Material, Material>();

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
        ItemStack stack = this.parser.getTurtle().getInventory().getItem(this.parser.getCurrentSelectedSlot());
        String direction = this.arguments[0];
        if (!DirectionHelper.isValidDirection(direction)) {
            parser.getTurtle().sendError("Invalid direction given: " + direction + ".");
        }
        int[] coordinates = DirectionHelper.getCoordinates(direction, turtle.getX(), turtle.getY(), turtle.getZ());
        Block block = this.parser.getWorld().getBlockAt(coordinates[0], coordinates[1], coordinates[2]);
        if (block.getType() == Material.AIR) {
            if (stack != null) {
                if (stack.getType().isBlock() || ITEM_CONVERSIONS.containsKey((Object)stack.getType())) {
                    Material typeToPlace = stack.getType();
                    if (ITEM_CONVERSIONS.containsKey((Object)stack.getType()))
                        typeToPlace = ITEM_CONVERSIONS.get((Object)stack.getType());
                    
                    block.setType(typeToPlace);
                    stack.setAmount(stack.getAmount() - 1);
                    if (stack.getAmount() <= 0)
                        turtle.getInventory().setItem(this.parser.getCurrentSelectedSlot(), null);
                }
            }
        }
    }

    static {
        ITEM_CONVERSIONS.put(Material.FLOWER_POT_ITEM, Material.FLOWER_POT);
        ITEM_CONVERSIONS.put(Material.CAULDRON_ITEM, Material.CAULDRON);
        ITEM_CONVERSIONS.put(Material.BREWING_STAND_ITEM, Material.BREWING_STAND);
        ITEM_CONVERSIONS.put(Material.SEEDS, Material.CROPS);
        ITEM_CONVERSIONS.put(Material.CARROT_ITEM, Material.CARROT);
        ITEM_CONVERSIONS.put(Material.POTATO_ITEM, Material.POTATO);
    }
}

