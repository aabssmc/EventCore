package lol.aabss.eventcore.commands.revives;

import lol.aabss.eventcore.Config;
import lol.aabss.eventcore.EventCore;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.Collection;

public class ReviveAll implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String prefix = Config.getString("prefix");
        String permmessage = Config.getString("permission-message");
        boolean bypass = Config.getBoolean("ignore-perm");
        if (sender.hasPermission("eventcore.reviveall")) {
            if (sender instanceof ConsoleCommandSender) {
                sender.sendMessage(Config.color(prefix + " &cThis command is only executable by a player."));
            }
            else {
                EventCore.Alive.clear();
                EventCore.Dead.clear();
                for (Player list: Bukkit.getOnlinePlayers()) {
                    if (list != sender){
                        if (bypass){
                            if (!sender.hasPermission("eventcore.reviveall.bypass")){
                                EventCore.Alive.add(list.getName());
                                list.teleport((Player) sender);
                            }
                        }
                        else{
                            EventCore.Alive.add(list.getName());
                            list.teleport((Player) sender);
                        }
                    }
                }
                Bukkit.broadcastMessage(Config.color("\n" + prefix + " &aAll players have been revived by " + sender.getName() + "!" + "\n"));
            }
        }
        else{
            sender.sendMessage(prefix + " " + permmessage);
        }
        return true;
    }
}
