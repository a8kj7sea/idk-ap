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







package dev.a8kj7sea.ap.afk;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import dev.a8kj7sea.ap.itemstack.ItemStackRandomGiver;
import dev.a8kj7sea.ap.utils.PoolUtils;
import dev.a8kj7sea.ap.utils.TimeParserUtils;

// i got help by chatgpt with this class fr :) , he was help me with time math to calculate time difference

public class AfkSystem {

    private Map<Player, LocationTimePair> playerLocations;

    public AfkSystem() {
        playerLocations = new HashMap<>();
    }

    public void updatePlayerLocation(Player player) {
        Location currentLocation = player.getLocation();

        if (!playerLocations.containsKey(player)) {
            playerLocations.put(player, new LocationTimePair(currentLocation, System.currentTimeMillis()));
            return;
        }

        LocationTimePair locationTimePair = playerLocations.get(player);
        Location previousLocation = locationTimePair.getLocation();
        long lastUpdateTime = locationTimePair.getLastUpdateTime();

        if (!currentLocation.equals(previousLocation)) {
            // Player moved, update the location and time
            playerLocations.put(player, new LocationTimePair(currentLocation, System.currentTimeMillis()));
        } else {
            // Player is in the same location
            long currentTime = System.currentTimeMillis();
            long timeDifference = currentTime - lastUpdateTime;

            if (timeDifference >= TimeParserUtils.returnAfkPoolTime() && PoolUtils.isPlayerInWater(player)) {
                ItemStackRandomGiver.giveRandomItem(player);
            }

        }
    }

    public Map<Player, LocationTimePair> getPlayerLocations() {
        return playerLocations;
    }

}
