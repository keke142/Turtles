package com.dootie.turtles.executer;


import com.dootie.turtles.executer.command.Command;
import com.dootie.turtles.repository.Turtle;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

public class Executer {
    private final World world;
    private final Turtle turtle;
    private final JavaPlugin plugin;
    private int currentSelectedSlot = 0;
    private int lineNumber = 0;
    private Executer parser;
    public Timer timer;
    private int speed;

    public Executer(JavaPlugin plugin, World world, Turtle turtle) {
        this.plugin = plugin;
        this.world = world;
        this.turtle = turtle;
        this.speed = 1;
    }

    public void parse(){
        turtle.parser = parser;
        this.lineNumber = 0;
        parser = this;
        
        if (this.getScript() == null) ParseException.exception(turtle, "No script is found.");
            
        if (this.turtle.isBusy()) return;
        
        this.turtle.setBusy(true);
        
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() { execute(); }
        }, 0, 1000/speed);
        
        this.lineNumber = 0;
    }
    
    public void parse(int number){
        turtle.parser = parser;
        this.lineNumber = number;
        
        parser = this;
        
        if (this.getScript() == null) ParseException.exception(turtle, "No script is found.");
            
        if (this.turtle.isBusy()) return;
        
        this.turtle.setBusy(true);
        
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                execute();
            }
        }, 0, 1000/speed);
    }
    
    public void execute(){
        try{
            String[] lines = turtle.getScript().split(";");

            String line = lines[lineNumber];

            String[] parts = line.split(" ");

            Command command = new CommandResolver(parts[0], Arrays.copyOfRange(parts, 1, parts.length)).resolve(parser);

            if (command != null) command.execute(parser, Arrays.copyOfRange(parts, 1, parts.length));
            else{
                Executer.this.turtle.setBusy(false);
                    ParseException.exception(turtle, "Command not found: " + parts[0] + ".");
                    Executer.this.turtle.setBusy(false);
                    timer.cancel();
            }

            if(lineNumber < lines.length-1) lineNumber++;
            else{
                Executer.this.turtle.setBusy(false);
                timer.cancel();
            }
        }catch(IndexOutOfBoundsException ex){
            Bukkit.getServer().getPlayer(turtle.getOwner()).sendMessage("Error in the script. Missing ';'");
            timer.cancel(); 
        }
    }

    public void setCurrentSelectedSlot(int currentSelectedSlot) {
        this.currentSelectedSlot = currentSelectedSlot;
    }

    public int getCurrentSelectedSlot() {
        return this.currentSelectedSlot;
    }

    public void setLineNumber(int line) {
        this.lineNumber = line;
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

}

