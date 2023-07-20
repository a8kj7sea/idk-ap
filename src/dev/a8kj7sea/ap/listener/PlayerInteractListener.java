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







package dev.a8kj7sea.ap.listener;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import dev.a8kj7sea.ap.constants.PluginConstants;
import dev.a8kj7sea.ap.constants.SoundConstants;
import dev.a8kj7sea.ap.utils.MessageUtils;
import dev.a8kj7sea.ap.utils.PoolUtils;

public class PlayerInteractListener implements Listener {

    public boolean hasValidWand(Player player) {
        ItemStack wand = player.getInventory().getItemInHand();

        if (wand != null && wand.getType() == Material.BLAZE_ROD && wand.hasItemMeta()) {
            ItemMeta itemMeta = wand.getItemMeta();
            String wandName = itemMeta.getDisplayName();

            return wandName != null && !wandName.isEmpty() && wandName.equalsIgnoreCase(PluginConstants.wandName);
        }

        return false;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();
        ItemStack item = event.getItem();
        Block clickedBlock = event.getClickedBlock();

        if (clickedBlock == null) {
            return;
        }

        Location clickedBlockLocation = clickedBlock.getLocation();

        if (action == Action.RIGHT_CLICK_BLOCK) {
            if (hasValidWand(player) && item != null && item.getType() == Material.BLAZE_ROD) {
                PoolUtils.setPos(clickedBlockLocation, 2);
                player.sendMessage(MessageUtils.getMessage("pos2-message"));
                SoundConstants.playSound("success", player);
            }
        } else if (action == Action.LEFT_CLICK_BLOCK) {
            if (hasValidWand(player) && item != null && item.getType() == Material.BLAZE_ROD) {
                PoolUtils.setPos(clickedBlockLocation, 1);
                player.sendMessage(MessageUtils.getMessage("pos1-message"));
                SoundConstants.playSound("success", player);
            }
        }
    }

}
