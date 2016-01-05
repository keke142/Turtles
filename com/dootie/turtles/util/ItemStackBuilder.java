package com.dootie.turtles.util;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemStackBuilder {
    private Material type;
    private String name;
    private int amount = 1;
    private List<String> lores = new ArrayList<String>();

    public static ItemStackBuilder start() {
        return new ItemStackBuilder();
    }

    public ItemStackBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public ItemStackBuilder setAmount(int amount) {
        this.amount = amount;
        return this;
    }

    public ItemStackBuilder setType(Material type) {
        this.type = type;
        return this;
    }

    public ItemStackBuilder addLore(String lore) {
        this.lores.add(lore);
        return this;
    }

    public ItemStack get() {
        ItemStack itemStack = new ItemStack(this.type, this.amount);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(this.name);
        meta.setLore(this.lores);
        itemStack.setItemMeta(meta);
        return itemStack;
    }
}

