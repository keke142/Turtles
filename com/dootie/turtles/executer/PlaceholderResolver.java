package com.dootie.turtles.executer;

import com.dootie.turtles.executer.placeholders.Placeholder;
import java.util.HashMap;
import java.util.Map;


public class PlaceholderResolver {
    
    private final String name;
    public static final Map<String, Placeholder> placeholders;
    
    static {
        placeholders = new HashMap<String, Placeholder>();
    }

    public PlaceholderResolver(String name) {
        this.name = name;
    }

    public Placeholder resolve() {
        return placeholders.get(name);
    }
}
