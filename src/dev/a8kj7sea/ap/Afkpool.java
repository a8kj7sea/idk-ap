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







package dev.a8kj7sea.ap;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import dev.a8kj7sea.ap.afk.AfkSystem;
import dev.a8kj7sea.ap.command.AfkPoolCommand;
import dev.a8kj7sea.ap.configuration.impl.APItemsConfiguration;
import dev.a8kj7sea.ap.configuration.impl.Configuration;
import dev.a8kj7sea.ap.listener.*;

public class Afkpool extends JavaPlugin {

    private static Configuration configuration;
    private static APItemsConfiguration apItemsConfiguration;
    private static Afkpool instance;
    private static AfkSystem afkSystem;

    public void onEnable() {
        instance = this;
        afkSystem = new AfkSystem();
        configuration = new Configuration(this, true, "config.yml");
        apItemsConfiguration = new APItemsConfiguration(this, true, "items.yml");

        System.out.println("Afkpool plugin has been enabled \n wrote by a8kj7sea");
        Bukkit.getPluginManager().registerEvents(new APItemsInventoryCloseListener(), this);
        Bukkit.getPluginManager().registerEvents(new APItemsInventoryOpenListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerInteractListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerMoveListener(), this);
        getCommand("ap").setExecutor(new AfkPoolCommand());
        getCommand("ap").setTabCompleter(new AfkPoolCommand());
    }

    public void onDisable() {
        System.out.println("Afkpool plugin has been disabled \n wrote by a8kj7sea");
    }

    public static AfkSystem getAfkSystem() {
        return afkSystem;
    }

    public static Afkpool getInstance() {
        return instance;
    }

    public static Configuration getConfiguration() {
        return configuration;
    }

    public static APItemsConfiguration getApItemsConfiguration() {
        return apItemsConfiguration;
    }

}
