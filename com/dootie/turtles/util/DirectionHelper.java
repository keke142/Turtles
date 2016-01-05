package com.dootie.turtles.util;

public class DirectionHelper {
    public static boolean isValidDirection(String direction) {
        return direction.equalsIgnoreCase("north") || direction.equalsIgnoreCase("east") || direction.equalsIgnoreCase("south") || direction.equalsIgnoreCase("west") || direction.equalsIgnoreCase("up") || direction.equalsIgnoreCase("down");
    }

    public static int[] getCoordinates(String direction, int x, int y, int z) {
        if (direction.equalsIgnoreCase("north")) {
            --z;
        } else if (direction.equalsIgnoreCase("east")) {
            ++x;
        } else if (direction.equalsIgnoreCase("south")) {
            ++z;
        } else if (direction.equalsIgnoreCase("west")) {
            --x;
        } else if (direction.equalsIgnoreCase("up")) {
            ++y;
        } else if (direction.equalsIgnoreCase("down")) {
            --y;
        }
        return new int[]{x, y, z};
    }
}

