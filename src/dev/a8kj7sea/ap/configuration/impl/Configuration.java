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







package dev.a8kj7sea.ap.configuration.impl;

import java.io.File;
import java.io.IOException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import dev.a8kj7sea.ap.configuration.IConfiguration;

public class Configuration implements IConfiguration {

    private File file;
    private FileConfiguration configurationFile;

    public Configuration(JavaPlugin javaPlugin, boolean defaultsave, String fileName) {
        file = new File(javaPlugin.getDataFolder(), fileName);
        file.getParentFile().mkdirs();
        if (!(file.exists())) {
            if (defaultsave) {
                javaPlugin.saveResource(fileName, defaultsave);
            } else {
                try {
                    file.createNewFile();
                } catch (IOException ioexception) {
                    ioexception.printStackTrace();
                }
            }

        }
        configurationFile = YamlConfiguration.loadConfiguration(file);

    }

    public void save() {
        try {
            configurationFile.save(file);
        } catch (IOException ioexception) {
            ioexception.printStackTrace();
        }
    }

    public void reload() {
        configurationFile = YamlConfiguration.loadConfiguration(file);
    }

    public FileConfiguration get() {
        return configurationFile;
    }

    public void delete() {
        file.delete();
    }
}