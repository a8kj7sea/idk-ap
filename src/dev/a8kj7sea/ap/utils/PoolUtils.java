/*
 MIT License

Copyright (c) 2023 a8kj7sea

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */







package dev.a8kj7sea.ap.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import dev.a8kj7sea.ap.Afkpool;

public class PoolUtils {

    public static boolean isPlayerInWater(Player player) {
        Block block = player.getLocation().getBlock();
        return block.getType() == Material.WATER || block.getType() == Material.STATIONARY_WATER;
    }

    public static void setPos(Location location, int posNum) {
        if (!Afkpool.getConfiguration().get().contains("afkpool.location")) {
            return;
        }
        if (posNum == 1) {
            Afkpool.getConfiguration().get().set("afkpool.location.pos1.world", (String) location.getWorld().getName());
            Afkpool.getConfiguration().get().set("afkpool.location.pos1.x", (double) location.getX());
            Afkpool.getConfiguration().get().set("afkpool.location.pos1.y", (double) location.getY());
            Afkpool.getConfiguration().get().set("afkpool.location.pos1.z", (double) location.getZ());
            Afkpool.getConfiguration().save();
        } else if (posNum == 2) {
            Afkpool.getConfiguration().get().set("afkpool.location.pos2.world", (String) location.getWorld().getName());
            Afkpool.getConfiguration().get().set("afkpool.location.pos2.x", (double) location.getX());
            Afkpool.getConfiguration().get().set("afkpool.location.pos2.y", (double) location.getY());
            Afkpool.getConfiguration().get().set("afkpool.location.pos2.z", (double) location.getZ());
            Afkpool.getConfiguration().save();
        } else {
            System.err.println("Error in " + PoolUtils.class.getProtectionDomain().getCodeSource());
        }
    }

    public static Location getPos(int posNum) {
        String worldName = getWorldName();
        double x, y, z;
        if (posNum == 1) {
            x = Afkpool.getConfiguration().get().getDouble("afkpool.location.pos1.x");
            y = Afkpool.getConfiguration().get().getDouble("afkpool.location.pos1.y");
            z = Afkpool.getConfiguration().get().getDouble("afkpool.location.pos1.z");
            return new Location(Bukkit.getWorld(worldName), x, y, z);
        } else if (posNum == 2) {
            x = Afkpool.getConfiguration().get().getDouble("afkpool.location.pos2.x");
            y = Afkpool.getConfiguration().get().getDouble("afkpool.location.pos2.y");
            z = Afkpool.getConfiguration().get().getDouble("afkpool.location.pos2.z");
            return new Location(Bukkit.getWorld(worldName), x, y, z);
        } else {
            System.err.println("Please please please don't be idiot");
            return null;
        }
    }

    private static String getWorldName() {
        if (!Afkpool.getConfiguration().get().contains("afkpool.location")) {
            return null;
        }
        if (Afkpool.getConfiguration().get().getString("afkpool.location.pos2.world")
                .equalsIgnoreCase(Afkpool.getConfiguration().get().getString("afkpool.location.pos1.world"))) {
            return Afkpool.getConfiguration().get().getString("afkpool.location.pos1.world");
        }
        return null;
    }

    public static boolean inPoolRegion(Player player, Location pos1, Location pos2) {
        Location playerLocation = player.getLocation();

        double minX = Math.min(pos1.getX(), pos2.getX());
        double maxX = Math.max(pos1.getX(), pos2.getX());
        double minY = Math.min(pos1.getY(), pos2.getY());
        double maxY = Math.max(pos1.getY(), pos2.getY());
        double minZ = Math.min(pos1.getZ(), pos2.getZ());
        double maxZ = Math.max(pos1.getZ(), pos2.getZ());

        return (playerLocation.getX() >= minX && playerLocation.getX() <= maxX
                && playerLocation.getY() >= minY && playerLocation.getY() <= maxY
                && playerLocation.getZ() >= minZ && playerLocation.getZ() <= maxZ);
    }

}
