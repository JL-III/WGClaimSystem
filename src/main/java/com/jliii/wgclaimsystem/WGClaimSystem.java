package com.jliii.wgclaimsystem;

import com.jliii.wgclaimsystem.commands.AdminCommands;
import com.jliii.wgclaimsystem.commands.PlayerCommands;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class WGClaimSystem extends JavaPlugin {

    PluginCommand playerCommands = getCommand("claim");


    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        Bukkit.getPluginCommand("wgclaim").setExecutor(new AdminCommands(this));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

}
