package com.dootie.turtles.repository;

import com.dootie.turtles.executer.Executer;
import com.dootie.turtles.executer.command.Command;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

public class Turtle {
    public static final int SCRIPT_SLOT = 0;
    private int x;
    private int y;
    private int z;
    private List<Command> commands = new ArrayList<Command>();
    private boolean busy;
    private UUID owner;
    private Inventory inventory;
    private ITurtleRepository repository;
    public Executer parser;

    public Turtle(int x, int y, int z, UUID owner, Inventory inventory, ITurtleRepository repository) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.owner = owner;
        this.inventory = inventory;
        this.repository = repository;
    }

    public void addCommand(Command command) {
        this.commands.add(command);
    }

    public void setBusy(boolean busy) {
        this.busy = busy;
    }

    public boolean isBusy() {
        return this.busy;
    }

    public ITurtleRepository getTurtleRepository() {
        return this.repository;
    }

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return this.z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public UUID getOwner() {
        return this.owner;
    }

    public void setOwner(UUID owner) {
        this.owner = owner;
    }

    public Inventory getInventory() {
        return this.inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public ItemStack getScriptSlot() {
        return this.inventory.getItem(0);
    }

    public Location getLocation(World world) {
        return new Location(world, (double)this.x, (double)this.y, (double)this.z);
    }

    public String getScript() {
        String script = "";
        ItemStack itemStack = this.getScriptSlot();
        if (itemStack != null && itemStack.getType() == Material.BOOK_AND_QUILL) {
            BookMeta bookMeta = (BookMeta)itemStack.getItemMeta();
            for (String page : bookMeta.getPages()) {
                String newpage = page.replace("\n", "");
                    for(int a = 0; a < 16;a++)
                        newpage = newpage.replace("§"+Integer.toHexString(a), "");
                    
                script = script + newpage;
            }
        }
        return script;
    }

    public void sendMessageToOwner(String message) {
        if (Bukkit.getPlayer((UUID)this.owner) != null) {
            Bukkit.getPlayer((UUID)this.owner).sendMessage((Object)ChatColor.BOLD + "[Turtle]" + (Object)ChatColor.RESET + " [" + this.x + ", " + this.y + ", " + this.z + "] " + message);
        }
    }

    public void sendErrorToOwner(String error) {
        this.sendMessageToOwner((Object)ChatColor.RED + error);
    }
}

