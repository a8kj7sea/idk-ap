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
import java.util.List;
import java.util.Map;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import dev.a8kj7sea.ap.Afkpool;
import net.md_5.bungee.api.ChatColor;

public class ItemStackConfigurationUtils {

    public static void collectAndStoreItemsIntoConfigurationFile(Inventory inventory) {
        ConfigurationSection inventorySection = Afkpool.getApItemsConfiguration().get()
                .getConfigurationSection("ap-items.contents");
        if (inventorySection == null) {
            inventorySection = Afkpool.getApItemsConfiguration().get().createSection("ap-items.contents");
        }

        for (int i = 0; i < inventory.getSize(); i++) {
            ItemStack item = inventory.getItem(i);
            if (item == null || item.getType() == Material.AIR) {
                continue;
            }

            ConfigurationSection itemSection = inventorySection.createSection(String.valueOf(i));

            itemSection.set("itemid", item.getType().toString());
            itemSection.set("itemamount", item.getAmount());
            itemSection.set("itemdata", item.getDurability());
            if (item.hasItemMeta()) {
                ItemMeta meta = item.getItemMeta();
                if (meta.hasDisplayName()) {
                    itemSection.set("name", meta.getDisplayName());
                }
                if (meta.hasLore()) {
                    itemSection.set("lore", meta.getLore());
                }
                if (meta.hasEnchants()) {
                    List<String> enchantments = new ArrayList<>();
                    for (Map.Entry<Enchantment, Integer> entry : meta.getEnchants().entrySet()) {
                        enchantments.add(entry.getKey().getName() + "#" + entry.getValue());
                    }
                    itemSection.set("enchantments", enchantments);
                }
            }
        }

        Afkpool.getApItemsConfiguration().save();
    }

    public static void fillItemsInInventory(Inventory inventory) {

        ConfigurationSection inventorySection = Afkpool.getApItemsConfiguration().get()
                .getConfigurationSection("ap-items.contents");
        if (inventorySection == null) {
            return;
        }

        for (String slotString : inventorySection.getKeys(false)) {
            ConfigurationSection itemSection = inventorySection.getConfigurationSection(slotString);

            Material material = Material.getMaterial(itemSection.getString("itemid"));
            int amount = itemSection.getInt("itemamount");
            short durability = (short) itemSection.getInt("itemdata");
            ItemStack item = new ItemStack(material, amount, durability);
            ItemMeta meta = item.getItemMeta();
            if (itemSection.contains("itemname")) {
                meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', itemSection.getString("itemname")));
            }
            if (itemSection.contains("lore")) {
                List<String> lore = new ArrayList<>();
                for (String loreLine : itemSection.getStringList("lore")) {
                    lore.add(ChatColor.translateAlternateColorCodes('&', loreLine));
                }
                meta.setLore(lore);
            }
            if (itemSection.contains("enchantments")) {
                for (String enchantmentString : itemSection.getStringList("enchantments")) {
                    String[] parts = enchantmentString.split("#");
                    Enchantment enchantment = Enchantment.getByName(parts[0]);
                    int level = Integer.parseInt(parts[1]);
                    meta.addEnchant(enchantment, level, true);
                }
            }
            item.setItemMeta(meta);

            inventory.addItem(item);

        }
    }

}