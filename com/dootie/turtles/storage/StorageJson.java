package com.dootie.turtles.storage;

import com.dootie.turtles.repository.ITurtleRepository;
import com.dootie.turtles.repository.Turtle;
import com.dootie.turtles.util.InventorySerializer;
import com.google.common.base.Joiner;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.util.UUID;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class StorageJson
implements IStorage {
    private ITurtleRepository repository;
    private File file;

    public StorageJson(ITurtleRepository repository, File file) {
        this.repository = repository;
        this.file = file;
    }

    @Override
    public void read() throws StorageException {
        if (!this.file.exists()) {
            return;
        }
        try {
            String data = Joiner.on((String)"").join(Files.readAllLines(this.file.toPath(), Charset.forName("UTF-8")));
            if (data != null) {
                JSONArray turtles = (JSONArray)JSONValue.parse((String)data);
                for (Object object : turtles) {
                    JSONObject turtleObject = (JSONObject)object;
                    long x = (Long)turtleObject.get((Object)"x");
                    long y = (Long)turtleObject.get((Object)"y");
                    long z = (Long)turtleObject.get((Object)"z");
                    Turtle turtle = this.repository.createTurtle(UUID.fromString((String)turtleObject.get((Object)"owner")), (int)x, (int)y, (int)z);
                    turtle.setInventory(InventorySerializer.fromBase64((String)turtleObject.get((Object)"inventory")));
                }
            }
        }
        catch (IOException e) {
            throw new StorageException(e.toString());
        }
    }

    @Override
    public void write() throws StorageException {
        JSONArray turtles = new JSONArray();
        for (Turtle turtle : this.repository.getTurtles()) {
            JSONObject turtleObject = new JSONObject();
            turtleObject.put((Object)"x", (Object)turtle.getX());
            turtleObject.put((Object)"y", (Object)turtle.getY());
            turtleObject.put((Object)"z", (Object)turtle.getZ());
            turtleObject.put((Object)"owner", (Object)turtle.getOwner().toString());
            turtleObject.put((Object)"inventory", (Object)InventorySerializer.toBase64(turtle.getInventory()));
            turtles.add((Object)turtleObject);
        }
        try {
            Files.write(this.file.toPath(), turtles.toJSONString().getBytes(), new OpenOption[0]);
        }
        catch (IOException e) {
            throw new StorageException(e.toString());
        }
    }
}

