package com.jliii.wgclaimsystem.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import java.util.logging.Logger;

public class PlayerCommands implements CommandExecutor {

    Logger logger;
    FileConfiguration fileConfiguration;

    public PlayerCommands(Logger logger, FileConfiguration fileConfiguration) {

        this.logger = logger;
        this.fileConfiguration = fileConfiguration;
    }

    public void reload(FileConfiguration fileConfiguration) {
        this.fileConfiguration = fileConfiguration;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player player)) {
            logger.warning("You must be a player to execute this command.");
            return true;
        }

        player.sendMessage("You have used the claim command");
//        player.sendMessage("FileConfiguration: " + fileConfiguration.getString("claim-world"));

        return false;
    }


}
