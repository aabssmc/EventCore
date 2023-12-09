package lol.aabss.eventcore.commands.revives;

import lol.aabss.eventcore.Config;
import lol.aabss.eventcore.EventCore;

import lol.aabss.eventcore.events.ReviveEvent;
import lol.aabss.eventcore.events.UseReviveEvent;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class RecentRev implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        String prefix = Config.getString("prefix");
        String permmessage = Config.getString("permission-message");
        if (sender.hasPermission("eventcore.revive")){
            if (sender instanceof ConsoleCommandSender) {
                sender.sendMessage(Config.color(prefix + " &cThis command is only executable by a player."));
            }
            else{
                for (String p : EventCore.Recent){
                    Player player = Bukkit.getPlayer(p);
                    if (player != null){
                        EventCore.Alive.add(player.getName());
                        EventCore.Dead.remove(player.getName());
                        player.teleport(((Player) sender).getLocation());
                        Bukkit.getServer().getPluginManager().callEvent(new ReviveEvent(player, sender));
                    }
                }
                EventCore.Recent.clear();
                Bukkit.broadcastMessage(Config.color("\n" + prefix + " &aAll players that died from the past " + Config.getInteger("recent-rev-time") + " minutes have been revived!\n"));
            }
        }
        else{
            sender.sendMessage(prefix + " " + permmessage);
        }
        return true;
    }

}
