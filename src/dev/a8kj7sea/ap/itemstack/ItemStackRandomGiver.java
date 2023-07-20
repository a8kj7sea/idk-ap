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







package dev.a8kj7sea.ap.itemstack;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import dev.a8kj7sea.ap.Afkpool;
import dev.a8kj7sea.ap.constants.SoundConstants;
import net.md_5.bungee.api.ChatColor;

public class ItemStackRandomGiver {

    private static ItemStack generateRandomItem() {
        List<String> itemKeys = new ArrayList<String>(
                Afkpool.getApItemsConfiguration().get().getConfigurationSection("ap-items.contents").getKeys(false));
        if (itemKeys.isEmpty()) {
            return null;
        } else {
            String randomItemKey = (String) itemKeys.get((new Random()).nextInt(itemKeys.size()));
            String itemPath = "Items." + randomItemKey;
            String myitem = Afkpool.getApItemsConfiguration().get().getString(itemPath + ".itemid");
            int myitem_amount = Afkpool.getApItemsConfiguration().get().getInt(itemPath + ".itemamount");
            int myitem_data = Afkpool.getApItemsConfiguration().get().getInt(itemPath + ".itemdata");
            ItemStack randomo = new ItemStack(Material.getMaterial(myitem), myitem_data);
            randomo.setDurability((short) myitem_data);
            randomo.setAmount(myitem_amount);
            List<String> myitem_enchantments = Afkpool.getApItemsConfiguration().get()
                    .getStringList(itemPath + ".enchantments");
            String myitem_lr;
            if (!myitem_enchantments.isEmpty()) {
                Iterator<String> var9 = myitem_enchantments.iterator();

                while (var9.hasNext()) {
                    String myitem_ench = (String) var9.next();
                    String[] parts = myitem_ench.split("#");
                    myitem_lr = parts[0];
                    int enchantmentLevel = Integer.parseInt(parts[1]);
                    randomo.addUnsafeEnchantment(Enchantment.getByName(myitem_lr), enchantmentLevel);
                }
            }

            List<String> myitem_lore = Afkpool.getApItemsConfiguration().get().getStringList(itemPath + ".lore");
            ItemMeta meta;
            if (!myitem_lore.isEmpty()) {
                ArrayList<String> coloredLore = new ArrayList<String>();
                meta = randomo.getItemMeta();
                Iterator<String> var17 = myitem_lore.iterator();

                while (var17.hasNext()) {
                    myitem_lr = (String) var17.next();
                    coloredLore.add(ChatColor.translateAlternateColorCodes('&', myitem_lr));
                }

                meta.setLore(coloredLore);
                randomo.setItemMeta(meta);
            }

            String myitem_name = Afkpool.getApItemsConfiguration().get().getString(itemPath + ".itemname");
            if (myitem_name != null) {
                myitem_name = myitem_name.replace("&", "§");
                meta = randomo.getItemMeta();
                meta.setDisplayName(myitem_name);
                randomo.setItemMeta(meta);
            }

            return randomo;
        }
    }

    private static ItemStack randomItem() {
        ItemStack randomItemStack = generateRandomItem();
        randomItemStack.setAmount(new Random().nextInt(Afkpool.getConfiguration().get().getInt("Settings.max-amount")));
        return randomItemStack;
    }

    public static void giveRandomItem(Player player) {
        player.getInventory().addItem(randomItem());
        SoundConstants.playSound("success", player);
    }

}
