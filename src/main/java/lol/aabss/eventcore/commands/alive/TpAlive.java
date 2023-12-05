package lol.aabss.eventcore.commands.alive;

import lol.aabss.eventcore.EventCore;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TpAlive implements CommandExecutor {

    private final EventCore plugin;

    public TpAlive(EventCore plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String prefix = this.plugin.getConfig().getString("prefix");
        String permmessage = this.plugin.getConfig().getString("permission-message");
        if (sender.hasPermission("eventcore.tpalive")){
            if (sender instanceof Player){
                Player p = (Player) sender;
                for (Player list: Bukkit.getOnlinePlayers()) {
                    if (EventCore.Alive.contains(list.getName())){
                        list.teleport(p.getLocation());
                        list.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + ChatColor.YELLOW + " You have been teleported."));
                    }
                }
                Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', prefix + ChatColor.YELLOW + ((Player) sender).getName() + " has teleport all alive players to them"));
            }
            else{
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + ChatColor.RED + " This command is only executable by players!"));
            }
        }
        else{
            sender.sendMessage(prefix + " " + permmessage);
        }
        return true;
    }
}
