package com.jliii.wgclaimsystem.commands;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.CommandBlock;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.logging.Logger;

public class AdminCommands implements CommandExecutor {

    Plugin plugin;
    Logger logger;
    FileConfiguration fileConfiguration;
    PlayerCommands playerCommands;

    public AdminCommands(Plugin plugin) {
        this.plugin = plugin;
        this.logger = plugin.getLogger();
        this.fileConfiguration = plugin.getConfig();
        playerCommands = new PlayerCommands(logger, fileConfiguration);
        Bukkit.getPluginCommand("claim").setExecutor(playerCommands);
    }

    public void reload(){
        plugin.reloadConfig();
        this.fileConfiguration = plugin.getConfig();
        playerCommands.reload(fileConfiguration);
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender.hasPermission("wgclaim.admin.reload")) {
            if (args[0].equalsIgnoreCase("reload")) {
                reload();
                if (sender instanceof Player player) {
                    player.sendMessage(ChatColor.GREEN + "Successfully reloaded the plugin.");
//                    player.sendMessage(ChatColor.YELLOW + "FileConfiguration: " + fileConfiguration.getString("claim-world"));
                }
                logger.info("Successfully reloaded the plugin.");
            }
        }

        return true;

    }
}
