package com.jliii.wgclaimsystem.commands;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sun.source.util.Plugin;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.logging.Logger;

public class PlayerCommands implements CommandExecutor {

    Logger logger;
    FileConfiguration fileConfiguration;
    WorldEditPlugin worldEditPlugin;
    String worldKey;

    public PlayerCommands(Logger logger, FileConfiguration fileConfiguration) {
        this.logger = logger;
        this.fileConfiguration = fileConfiguration;
        worldEditPlugin = (WorldEditPlugin) Bukkit.getPluginManager().getPlugin("WorldEdit");
        this.worldKey = fileConfiguration.getString("claim-world");
    }

    public void reload(FileConfiguration fileConfiguration) {

        this.fileConfiguration = fileConfiguration;
        this.worldKey = fileConfiguration.getString("claim-world");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player player)) {
            logger.warning("You must be a player to execute this command.");
            return true;
        }

        LocalPlayer localPlayer = WorldGuardPlugin.inst().wrapPlayer(player);
        com.sk89q.worldedit.world.World wgWorld = BukkitAdapter.adapt(player.getWorld());

        player.sendMessage("You have used the claim command");
//        player.sendMessage("FileConfiguration: " + fileConfiguration.getString("claim-world"));

        RegionContainer regionContainer = WorldGuard.getInstance().getPlatform().getRegionContainer();


        if (regionContainer == null) {
            logger.warning("RegionContainer is null");
            return true;
        }

        if (worldKey == null) {
            logger.warning("claim-world is not set in the config.yml");
            return true;
        }

        if (Bukkit.getWorld(worldKey) == null) {
            logger.warning("claim-world is not a valid world");
            return true;
        }

        RegionManager regionManager;

        if (Bukkit.getWorld(worldKey) == null) {
            logger.warning("world is null");
            return true;
        } else {
            regionManager = regionContainer.get(BukkitAdapter.adapt(Objects.requireNonNull(Bukkit.getWorld(worldKey))));
        }


        if (regionManager == null) {
            player.sendMessage("No region manager found");
            return true;
        }

        if (regionManager.getRegions().keySet().isEmpty()) {
            player.sendMessage("No regions found");
            return true;
        }

        for (String region : regionManager.getRegions().keySet()) {
            player.sendMessage(region);
        }
        return true;
    }


}
