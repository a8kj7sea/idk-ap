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







package dev.a8kj7sea.ap.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import dev.a8kj7sea.ap.Afkpool;
import dev.a8kj7sea.ap.constants.PermissionConstants;
import dev.a8kj7sea.ap.constants.PluginConstants;
import dev.a8kj7sea.ap.constants.SoundConstants;
import dev.a8kj7sea.ap.utils.MessageUtils;

import static dev.a8kj7sea.ap.utils.ColorUtils.format;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AfkPoolCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            System.err.println("This command only for player usage !");
            return false;
        }

        Player player = (Player) sender;
        if (!player.hasPermission(PermissionConstants.useCommand)) {
            player.sendMessage(format(Afkpool.getConfiguration().get().getString("Messages.permissions-needed")));
            SoundConstants.playSound("failed", player);
            return false;
        }

        if (command.equals(label)) {
            player.sendMessage(format(Afkpool.getConfiguration().get().getString("Messages.incorrect-usage")));
            SoundConstants.playSound("incorrect usage", player);
        }

        if (args.length != 1) {
            player.sendMessage(format(Afkpool.getConfiguration().get().getString("Messages.incorrect-usage")));
            SoundConstants.playSound("incorrect usage", player);
            return false;
        } else {

            // if (args[0].equalsIgnoreCase("help")) {
            // Object helpMessage_ = Afkpool.getConfiguration().get().get("help-message");
            // if (helpMessage_ instanceof List) {
            // List<String> helpMessage =
            // Afkpool.getConfiguration().get().getStringList("Message.help-message");
            // if (helpMessage != null && !helpMessage.isEmpty()) {
            // for (String message : helpMessage) {
            // player.sendMessage(format(message));
            // SoundConstants.playSound("success", player);
            // }
            // } else {
            // player.sendMessage("&cThere is Error in configuration file please type /ap
            // fix");
            // SoundConstants.playSound("fix needed", player);

            // }

            // } else if (helpMessage_ instanceof String) {
            // player.sendMessage(format((String) helpMessage_));
            // SoundConstants.playSound("success", player);
            // } else {
            // player.sendMessage("&cThere is Error in configuration file please type /ap
            // fix");
            // SoundConstants.playSound("fix needed", player);
            // }
            // } else if (args[0].equalsIgnoreCase("fix")) {
            // Afkpool.getConfiguration().delete();
            // Bukkit.getPluginManager().disablePlugin(Afkpool.getInstance());
            // Bukkit.getPluginManager().enablePlugin(Afkpool.getInstance());
            // player.sendMessage(format(Afkpool.getConfiguration().get().getString("Messages.fix-message")));
            // SoundConstants.playSound("fixed", player);
            // } else if (args[0].equalsIgnoreCase("wand")) {
            // player.sendMessage(format(Afkpool.getConfiguration().get().getString("messages.get-wand-message")));
            // SoundConstants.playSound("success", player);
            // } else if (args[0].equalsIgnoreCase("rl")) {
            // Afkpool.getConfiguration().reload();
            // Bukkit.getPluginManager().disablePlugin(Afkpool.getInstance());
            // Bukkit.getPluginManager().enablePlugin(Afkpool.getInstance());
            // player.sendMessage(format(Afkpool.getConfiguration().get().getString("messages.reload-message")));
            // SoundConstants.playSound("success", player);
            // }

            String argument = args[0].toLowerCase();

            switch (argument) {
                case "help":
                    Object helpMessage_ = Afkpool.getConfiguration().get().get("help-message");
                    if (helpMessage_ instanceof List) {
                        List<String> helpMessage = Afkpool.getConfiguration().get()
                                .getStringList("Message.help-message");
                        if (helpMessage != null && !helpMessage.isEmpty()) {
                            for (String message : helpMessage) {
                                player.sendMessage(format(message));
                                SoundConstants.playSound("success", player);
                            }
                        } else {
                            player.sendMessage("&cThere is an error in the configuration file. Please type /ap fix");
                            SoundConstants.playSound("fix needed", player);
                        }
                    } else if (helpMessage_ instanceof String) {
                        player.sendMessage(format((String) helpMessage_));
                        SoundConstants.playSound("success", player);
                    } else {
                        player.sendMessage("&cThere is an error in the configuration file. Please type /ap fix");
                        SoundConstants.playSound("fix needed", player);
                    }
                    break;

                case "fix":
                    Afkpool.getConfiguration().delete();
                    Bukkit.getPluginManager().disablePlugin(Afkpool.getInstance());
                    Bukkit.getPluginManager().enablePlugin(Afkpool.getInstance());
                    player.sendMessage(format(MessageUtils.getMessage("Messages.fix-message")));
                    SoundConstants.playSound("fixed", player);
                    break;

                case "wand":
                    player.sendMessage(format(MessageUtils.getMessage("messages.get-wand-message")));
                    SoundConstants.playSound("success", player);
                    break;

                case "rl":
                    Afkpool.getConfiguration().reload();
                    Bukkit.getPluginManager().disablePlugin(Afkpool.getInstance());
                    Bukkit.getPluginManager().enablePlugin(Afkpool.getInstance());
                    player.sendMessage(format(MessageUtils.getMessage("messages.reload-message")));
                    SoundConstants.playSound("success", player);
                    break;

                case "gui":
                    player.openInventory(Bukkit.createInventory(null, 6 * 9, PluginConstants.storeInventoryTitle));
                    break;

                default:
                    player.sendMessage(format(MessageUtils.getMessage("Messages.incorrect-usage")));
                    SoundConstants.playSound("incorrect usage", player);
                    break;
            }

        }

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command arg1, String arg2, String[] args) {
        List<String> suggestions = Arrays.asList("rl", "fix", "help", "wand", "gui");
        if (!(sender instanceof Player)) {
            System.err.println("This command only for player usage !");
            return Collections.emptyList();
        }
        Player player = (Player) sender;
        if (!player.hasPermission(PermissionConstants.useCommand)) {
            player.sendMessage(format(MessageUtils.getMessage("Messages.permissions-needed")));
            SoundConstants.playSound("failed", player);
            return Collections.emptyList();
        } else {
            if (args.length != 1) {
                return Collections.emptyList();
            } else {
                return suggestions;
            }
        }
    }

}
