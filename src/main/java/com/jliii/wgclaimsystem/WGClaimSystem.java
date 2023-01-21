package com.jliii.wgclaimsystem;

import com.jliii.wgclaimsystem.commands.PlayerCommands;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class WGClaimSystem extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getCommand("claim").setExecutor(new PlayerCommands(this.getLogger()));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
