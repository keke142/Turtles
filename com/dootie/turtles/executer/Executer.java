package com.dootie.turtles.executer;


import com.dootie.turtles.executer.command.Command;
import com.dootie.turtles.repository.Turtle;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

public class Executer {
    private final World world;
    private final Turtle turtle;
    private final JavaPlugin plugin;
    private int currentSelectedSlot = 0;
    private int lineNumber = 0;
    private Executer parser;
    private Timer timer;
    private int speed;
    private int nextLine;

    public Executer(JavaPlugin plugin, World world, Turtle turtle) {
        this.plugin = plugin;
        this.world = world;
        this.turtle = turtle;
        this.speed = 1;
        this.nextLine = 0;
    }

    public void parse(){
        this.lineNumber = 0;
        parser = this;
        
        if (this.getScript() == null) turtle.sendError("No script is found.");
            
        if (this.turtle.isBusy()) return;
        
        this.turtle.setBusy(true);
        
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() { execute(); }
        }, 0, 1000/speed);
        
        this.lineNumber = 0;
    }
    
    public void execute(){
        try{
            nextLine = 0;
            
            String[] lines = turtle.getScript().split(";");

            String line = lines[lineNumber];

            String[] parts = line.split(" ");

            Command command = new CommandResolver(parts[0], Arrays.copyOfRange(parts, 1, parts.length)).resolve(parser);

            if (command != null){
                command.execute(parser, Arrays.copyOfRange(parts, 1, parts.length));
            }else{
                Executer.this.turtle.setBusy(false);
                    turtle.sendError("Command not found: " + parts[0] + ".");
                    Executer.this.turtle.setBusy(false);
                    stop();
            }

            if(lineNumber < lines.length-1){
                if(nextLine == 0) lineNumber++;
                else lineNumber = nextLine-1;
            }else{
                Executer.this.turtle.setBusy(false);
                stop();
            }
        }catch(IndexOutOfBoundsException ex){
            Bukkit.getServer().getPlayer(turtle.getOwner()).sendMessage("Error in the script. Missing ';'");
            Executer.this.turtle.setBusy(false);
            stop();
        }
        
        if(turtle.getLocation(world).getBlock().getType() != Material.SKULL){
            turtle.setBusy(false);
            stop();
        }
    }

    public void setCurrentSelectedSlot(int currentSelectedSlot) {
        this.currentSelectedSlot = currentSelectedSlot;
    }

    public int getCurrentSelectedSlot() {
        return this.currentSelectedSlot;
    }

    public void setLineNumber(int line) {
        this.nextLine = line+1;
    }

    public int getLineNumber() {
        return this.lineNumber;
    }

    public Turtle getTurtle() {
        return this.turtle;
    }

    public World getWorld() {
        return this.world;
    }

    public String getScript() {
        return this.turtle.getScript();
    }
    
    public int getSpeed(){
        return this.speed;
    }
    
    public void setSpeed(int speed){
        this.speed = speed;
    }
    
    public void stop(){
        turtle.sendError("Turtle stopped the script.");
        timer.cancel();
    }

}

