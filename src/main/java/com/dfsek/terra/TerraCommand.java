package com.dfsek.terra;

import com.dfsek.terra.biome.TerraBiomeGrid;
import com.dfsek.terra.biome.UserDefinedBiome;
import com.dfsek.terra.config.ConfigUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.polydev.gaea.profiler.WorldProfiler;

public class TerraCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        switch(args[0]) {
            case "reload":
                ConfigUtil.loadConfig(Terra.getInstance());
                sender.sendMessage("Reloaded Terra config.");
                break;
            case "biome":
                if(!(sender instanceof Player)) return false;
                sender.sendMessage("You are in " + ((UserDefinedBiome) TerraBiomeGrid.fromWorld(((Player) sender).getWorld()).getBiome(((Player) sender).getLocation())).getConfig().getFriendlyName());
            case "profile":
                if(! (sender instanceof Player)) {
                    sender.sendMessage("Command is for players only.");
                    return true;
                }
                Player p = (Player) sender;
                if(p.getWorld().getGenerator() instanceof TerraChunkGenerator) {
                    WorldProfiler profile = TerraProfiler.fromWorld(p.getWorld());
                    if(args.length > 1 && "query".equals(args[1])) {
                        sender.sendMessage(profile.getResultsFormatted());
                        return true;
                    } else if(args.length > 1 && "reset".equals(args[1])) {
                        profile.reset();
                        sender.sendMessage("Profiler has been reset.");
                        return true;
                    } else if(args.length > 1 && "start".equals(args[1])) {
                        profile.setProfiling(true);
                        sender.sendMessage("Profiler has started.");
                        return true;
                    } else if(args.length > 1 && "stop".equals(args[1])) {
                        profile.setProfiling(false);
                        sender.sendMessage("Profiler has stopped.");
                        return true;
                    }
                } else sender.sendMessage("World is not a Terra world!");
            }
        return true;
    }
}