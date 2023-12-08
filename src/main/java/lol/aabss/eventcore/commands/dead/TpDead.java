package lol.aabss.eventcore.commands.dead;

import lol.aabss.eventcore.Config;
import lol.aabss.eventcore.EventCore;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TpDead implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String prefix = Config.getString("prefix");
        String permmessage = Config.getString("permission-message");
        if (sender.hasPermission("eventcore.tpdead")){
            if (sender instanceof Player){
                Player p = (Player) sender;
                for (Player list: Bukkit.getOnlinePlayers()) {
                    if (EventCore.Dead.contains(list.getName())){
                        list.teleport(p.getLocation());
                        list.sendMessage(Config.color(prefix + "&e" + " You have been teleported."));
                    }
                }
                Bukkit.broadcastMessage(Config.color(prefix + "&e" + sender.getName() + " has teleport all dead players to them"));
            }
            else{
                sender.sendMessage(Config.color(prefix + " &cThis command is only executable by players!"));
            }
        }
        else{
            sender.sendMessage(prefix + " " + permmessage);
        }
        return true;
    }
}

