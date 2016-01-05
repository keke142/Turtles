package com.dootie.turtles.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

public class InventorySerializer {
    public static String toBase64(Inventory inventory) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream((OutputStream)outputStream);
            dataOutput.writeInt(inventory.getSize());
            dataOutput.writeUTF(inventory.getTitle());
            for (int i = 0; i < inventory.getSize(); ++i) {
                dataOutput.writeObject((Object)inventory.getItem(i));
            }
            dataOutput.close();
            return Base64Coder.encodeLines((byte[])outputStream.toByteArray());
        }
        catch (IOException e) {
            throw new IllegalStateException("Unable to save item stacks.", e);
        }
    }

    public static Inventory fromBase64(String data) throws IOException {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines((String)data));
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream((InputStream)inputStream);
            Inventory inventory = Bukkit.getServer().createInventory(null, dataInput.readInt(), dataInput.readUTF());
            for (int i = 0; i < inventory.getSize(); ++i) {
                inventory.setItem(i, (ItemStack)dataInput.readObject());
            }
            dataInput.close();
            return inventory;
        }
        catch (ClassNotFoundException e) {
            throw new IOException("Unable to decode class type.", e);
        }
    }
}

