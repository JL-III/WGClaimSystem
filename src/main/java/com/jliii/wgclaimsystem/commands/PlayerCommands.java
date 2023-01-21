package com.jliii.wgclaimsystem.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.logging.Logger;

public class PlayerCommands implements CommandExecutor {

    Logger logger;

    public PlayerCommands(Logger logger) {
        this.logger = logger;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player player)) {
            logger.warning("You must be a player to execute this command.");
            return true;
        }

        player.sendMessage("You have used the claim command");

        return false;
    }
}
