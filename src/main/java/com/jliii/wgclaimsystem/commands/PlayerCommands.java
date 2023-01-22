package com.jliii.wgclaimsystem.commands;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.math.BlockVector2;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import com.sun.source.util.Plugin;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.util.BlockVector;

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
        player.sendMessage(ChatColor.YELLOW + "-----------wg-claim---------");
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

        RegionQuery regionQuery = regionContainer.createQuery();
        ApplicableRegionSet applicableRegionSet = regionQuery.getApplicableRegions(localPlayer.getLocation());

        Chunk chunk = player.getLocation().getChunk();

        int bx = chunk.getX() << 4;
        int bz = chunk.getZ() << 4;
        Location pt1 = new Location(player.getWorld(), bx, 0, bz);
        Location pt2 = new Location(player.getWorld(), bx + 15, 256, bz + 15);
        ProtectedCuboidRegion region = new ProtectedCuboidRegion("ThisIsAnId", BukkitAdapter.asBlockVector(pt1), BukkitAdapter.asBlockVector(pt2));
        ApplicableRegionSet regions = regionManager.getApplicableRegions(region);

        //TODO: CHeck if this region overlaps with any other regions

        if (regions.size() > 0) {
            player.sendMessage("This region overlaps with another region");
            for (ProtectedRegion protectedRegion : regions) {
                player.sendMessage(ChatColor.AQUA + "regionId: " + ChatColor.WHITE + protectedRegion.getId());
                //TODO: spawn particles outlining the overlapping region that already exists
//                for (BlockVector2 blockVector : protectedRegion.getPoints()) {
//                    player.spawnParticle(Particle.FLAME, new Location(player.getWorld(), blockVector.getBlockX(), player.getLocation().getBlockY(), blockVector.getZ()), 1, 0, 0, 0, 0);
//                }
//                 for (int x = 0; x < 16; x++) {
//                     for (int z = 0; z < 16; z++) {
//                         player.spawnParticle(Particle.FLAME, chunk.getBlock(x, localPlayer.getLocation().getBlockY(), z).getLocation(), 1, 0, 0, 0, 0);
//                     }
//                 }
            }
            return true;
        }
//        player.sendMessage("Chunk: " + chunk.getX() + ", " + chunk.getZ());
//        for (int x = 0; x < 16; x++) {
//            for (int z = 0; z < 16; z++) {
//                logger.info("Block: " + x + ", " + z + " is in region: " + regionQuery.getApplicableRegions(BukkitAdapter.adapt(chunk.getBlock(x, 0, z).getLocation())).isOwnerOfAll(localPlayer));
//            }
//        }

        if (applicableRegionSet.size() == 0) {
            player.sendMessage("You are not currently in any regions");
            return true;
        } else {
            player.sendMessage("You own all regions: " + applicableRegionSet.isOwnerOfAll(localPlayer));
        }
        for (ProtectedRegion protectedRegion : applicableRegionSet) {
            player.sendMessage(ChatColor.AQUA + "regionId: " + ChatColor.WHITE + protectedRegion.getId());
            player.sendMessage(protectedRegion.getOwners().contains(localPlayer.getUniqueId()) ? ChatColor.GREEN + "You own this region" : ChatColor.RED + "You do not own this region");
        }


//        applicableRegionSet.isOwnerOfAll(localPlayer);

//        for (ProtectedRegion protectedRegion : regionManager.getRegions().values()) {
//            player.sendMessage(protectedRegion.getId());
//            player.sendMessage(protectedRegion.getOwners().contains(localPlayer.getUniqueId()) ? ChatColor.GREEN + "You own this region" : ChatColor.RED + "You do not own this region");
//        }

//        for (String region : regionManager.getRegions().keySet()) {
//            player.sendMessage(region);
//        }
        player.sendMessage(ChatColor.YELLOW + "-----------wg-claim---------");

        return true;
    }


}
