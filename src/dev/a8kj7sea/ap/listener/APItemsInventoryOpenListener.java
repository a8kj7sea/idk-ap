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

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import dev.a8kj7sea.ap.constants.PermissionConstants;
import dev.a8kj7sea.ap.constants.PluginConstants;
import dev.a8kj7sea.ap.constants.SoundConstants;
import dev.a8kj7sea.ap.itemstack.ItemStackConfigurationUtils;
import dev.a8kj7sea.ap.utils.MessageUtils;

public class APItemsInventoryOpenListener implements Listener {

    @EventHandler
    public void onOpenItemsInventory(InventoryClickEvent event) {

        if (!(event.getWhoClicked() instanceof Player)) {
            return;
        }

        if (event.getWhoClicked().hasPermission(PermissionConstants.storeInInventory)
                && event.getInventory().getTitle().equalsIgnoreCase(PluginConstants.storeInventoryTitle)) {
            ItemStackConfigurationUtils.fillItemsInInventory(event.getInventory());
            event.getWhoClicked().sendMessage(MessageUtils.getMessage("opening-APInventory-message"));
            SoundConstants.playSound("success", (Player) event.getWhoClicked());
        }
    }

}
