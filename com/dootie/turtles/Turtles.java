package com.dootie.turtles;

import com.dootie.turtles.executer.CommandResolver;
import com.dootie.turtles.executer.Executer;
import com.dootie.turtles.executer.PlaceholderResolver;
import com.dootie.turtles.executer.command.*;
import com.dootie.turtles.executer.placeholders.*;
import com.dootie.turtles.repository.ITurtleRepository;
import com.dootie.turtles.repository.Turtle;
import com.dootie.turtles.repository.TurtleRepositoryWorld;
import com.dootie.turtles.storage.IStorage;
import com.dootie.turtles.storage.StorageException;
import com.dootie.turtles.storage.StorageJson;
import com.dootie.turtles.util.ItemStackBuilder;
import java.io.File;
import java.util.logging.Level;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;

public class Turtles extends JavaPlugin implements Listener {
    public static ITurtleRepository repository;
    public static IStorage storage;
    public static ShapedRecipe recipeTurtle;
    public static ItemStack blockTurtle;
    public static ItemStack itemTurtle;

    @Override
    public void onEnable() {
        new Thread(new Runnable() {
            public void run() {
                enable();
            }
        }).start();
    }
    
    public void enable(){
        repository = new TurtleRepositoryWorld();
        storage = new StorageJson(repository, new File("turtles.json"));
        blockTurtle = ItemStackBuilder.start().setName("§9Turtle").setType(Material.SKULL).get();
        itemTurtle = ItemStackBuilder.start().setName("§9Turtle").setType(Material.SKULL_ITEM).get();
        recipeTurtle = new ShapedRecipe(itemTurtle);
        this.getServer().getPluginManager().registerEvents(this, this);
        
        addRecipes();
        readStorage();
        registerCommands();
        registerPlaceholders();
        
        this.getLogger().info("[Turtles] Turtles is enabled.");
    }
    
    private void addRecipes(){
        System.out.println("[Turtles] Adding recipes...");
        recipeTurtle.shape(new String[]{"sns", "srs", "scs"}).setIngredient('s', Material.STONE).setIngredient('n', Material.NETHER_STAR).setIngredient('r', Material.REDSTONE_BLOCK).setIngredient('c', Material.CHEST);
        this.getServer().addRecipe(recipeTurtle);
        System.out.println("[Turtles] Added recipes.");
    }
    
    private void readStorage(){
        System.out.println("[Turtles] Loading turtles...");
        try {
            storage.read();
        }
        catch (StorageException e) {
            this.getLogger().log(Level.SEVERE, "Could not read turtles to storage: {0}", e);
        }
        System.out.println("[Turtles] Turtles loaded.");
    }
    
    private void registerCommands(){
        System.out.println("[Turtles] Registering commands...");
        CommandResolver.commands.put("dig", new CommandDig());
        CommandResolver.commands.put("drop", new CommandDrop());
        CommandResolver.commands.put("move", new CommandMove());
        CommandResolver.commands.put("place", new CommandPlace());
        CommandResolver.commands.put("print", new CommandPrint());
        CommandResolver.commands.put("select", new CommandSelect());
        CommandResolver.commands.put("suck", new CommandSuck());
        CommandResolver.commands.put("goto", new CommandGoto());
        CommandResolver.commands.put("#", new CommandComment());
        System.out.println("[Turtles] Commands registered.");
    }
    
    private void registerPlaceholders(){
        System.out.println("[Turtles] Registering placeholders...");
        PlaceholderResolver.placeholders.put("%block north type%", new BlockTypePlaceholder());
        PlaceholderResolver.placeholders.put("%block east type%", new BlockTypePlaceholder());
        PlaceholderResolver.placeholders.put("%block south type%", new BlockTypePlaceholder());
        PlaceholderResolver.placeholders.put("%block west type%", new BlockTypePlaceholder());
        PlaceholderResolver.placeholders.put("%block up type%", new BlockTypePlaceholder());
        PlaceholderResolver.placeholders.put("%block down type%", new BlockTypePlaceholder());
        PlaceholderResolver.placeholders.put("%owner name%", new OwnerPlaceholder());
        PlaceholderResolver.placeholders.put("%owner uuid%", new OwnerPlaceholder());
        for(int i = 0; i < 9; i++){
            PlaceholderResolver.placeholders.put("%slot "+i+" type%", new OwnerPlaceholder());
            PlaceholderResolver.placeholders.put("%slot "+i+" amount%", new OwnerPlaceholder());
            PlaceholderResolver.placeholders.put("%slot "+i+" damage%", new OwnerPlaceholder());
            PlaceholderResolver.placeholders.put("%slot "+i+" maxdamage%", new OwnerPlaceholder());
        }
        System.out.println("[Turtles] Placeholders registered.");
    }
    
    @Override
    public void onDisable() {
        try {
            storage.write();
        }
        catch (StorageException e) {
            this.getLogger().log(Level.SEVERE, "Could not save turtles to storage: {0}", e);
        }
    }
    
    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args){
        if (label.equals("turtles")){
            sender.sendMessage("");
            sender.sendMessage(ChatColor.DARK_PURPLE+"Turtles plugin by Dootie");
            sender.sendMessage(ChatColor.BLUE+"Plugin avaiable in: "+ChatColor.GRAY+"https://www.spigotmc.org/resources/16666/");
            sender.sendMessage(ChatColor.BLUE+"Github: "+ChatColor.GRAY+"https://github.com/Dootie/Turtles");
            sender.sendMessage(ChatColor.BLUE+"Wiki: "+ChatColor.GRAY+"https://github.com/Dootie/Turtles/wiki");
            sender.sendMessage(ChatColor.BLUE+"Twitter: "+ChatColor.GRAY+"https://twitter.com/Dootie_");
        }
        return true;
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        if (e.getItemInHand() != null && e.getItemInHand().getItemMeta() != null && e.getItemInHand().getItemMeta().hasDisplayName() && e.getItemInHand().getItemMeta().getDisplayName().equals(blockTurtle.getItemMeta().getDisplayName())) {
            Location location = e.getBlockPlaced().getLocation();
            repository.createTurtle(e.getPlayer().getUniqueId(), location.getBlockX(), location.getBlockY(), location.getBlockZ());
            e.getPlayer().sendMessage("The turtle is succesfully placed.");
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Location location = e.getBlock().getLocation();
        Turtle turtle = repository.getTurtle(location.getBlockX(), location.getBlockY(), location.getBlockZ());
        if (turtle != null) {
            e.setCancelled(true);
            if(!turtle.getOwner().equals(e.getPlayer().getUniqueId()) && !e.getPlayer().hasPermission("turtle.destroy.others")) return;
            try{
                turtle.executer.stop();
            }catch(NullPointerException ex){}
            repository.removeTurtle(turtle.getX(), turtle.getY(), turtle.getZ());
            World world = e.getBlock().getWorld();
            world.getBlockAt(turtle.getX(), turtle.getY(), turtle.getZ()).setType(Material.AIR);
            for (ItemStack item : turtle.getInventory().getContents()) {
                if (item == null) continue;
                world.dropItemNaturally(turtle.getLocation(e.getBlock().getWorld()), item.clone());
            }
            if (e.getPlayer() == null || e.getPlayer() == null || e.getPlayer().getGameMode() != GameMode.CREATIVE) {
                world.dropItemNaturally(turtle.getLocation(world), blockTurtle.clone());
            }
            if (e.getPlayer() != null) {
                e.getPlayer().sendMessage("You broke a turtle.");
            }
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        Location location;
        Turtle turtle;
        if (e.getClickedBlock() != null && e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getItem() == null && (turtle = repository.getTurtle((location = e.getClickedBlock().getLocation()).getBlockX(), location.getBlockY(), location.getBlockZ())) != null) {
            if(!turtle.getOwner().equals(e.getPlayer().getUniqueId()) && !e.getPlayer().hasPermission("turtle.inventory.others")) return;
            e.getPlayer().openInventory(turtle.getInventory());
        }
    }

    @EventHandler
    public void onBlockPhysics(BlockPhysicsEvent e) {
        Location location;
        Turtle turtle;
        if ((e.getBlock().isBlockPowered() || e.getBlock().isBlockIndirectlyPowered()) && (turtle = repository.getTurtle((location = e.getBlock().getLocation()).getBlockX(), location.getBlockY(), location.getBlockZ())) != null) {
            Executer parser = new Executer(this, e.getBlock().getWorld(), turtle);
            parser.parse();
        }
    }

    @EventHandler
    public void onEntityExploded(EntityExplodeEvent e) {
        for (Block block : e.blockList()) {
            repository.removeTurtle(block.getX(), block.getY(), block.getZ());
        }
    }
    
    
}

