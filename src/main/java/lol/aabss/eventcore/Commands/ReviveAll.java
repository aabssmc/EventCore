package lol.aabss.eventcore.Commands;

import lol.aabss.eventcore.EventCore;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class ReviveAll implements CommandExecutor {
    private final EventCore plugin;

    public ReviveAll(EventCore plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String prefix = this.plugin.getConfig().getString("prefix");
        String permmessage = this.plugin.getConfig().getString("permission-message");
        if (sender.hasPermission("eventcore.reviveall")) {
            if (sender instanceof ConsoleCommandSender) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + ChatColor.RED + " This command is only executable by a player."));
            }
            else {
                EventCore.Alive.clear();
                EventCore.Dead.clear();
                for (Player list: Bukkit.getOnlinePlayers()) {
                    EventCore.Alive.add(list.getName());
                }
                Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "\n" + prefix + ChatColor.GREEN + " All players have been revived by " + sender.getName() + "!" + "\n"));
            }
        }
        else{
            sender.sendMessage(prefix + " " + permmessage);
        }
        return true;
    }
}
