package lol.aabss.eventcore.commands.dead;

import lol.aabss.eventcore.EventCore;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import org.bukkit.Bukkit;

public class Revive implements CommandExecutor {

    private final EventCore plugin;

    public Revive(EventCore plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String prefix = this.plugin.getConfig().getString("prefix");
        String permmessage = this.plugin.getConfig().getString("permission-message");
        if (sender.hasPermission("eventcore.revive")){
            if (sender instanceof ConsoleCommandSender) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + ChatColor.RED + " This command is only executable by a player."));
            }
            else{
                Player p = (Player) sender;
                if (args.length == 0){
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + ChatColor.RED + " Please set a player to revive!"));
                }
                else{
                    if (Bukkit.getPlayer(args[0]) == null) {
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + ChatColor.RED + " Please set a valid player!"));
                    }
                    else{
                        Player arg = Bukkit.getPlayer(args[0]);
                        if (EventCore.Alive.contains(arg.getName())){
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + ChatColor.RED + " That player is already alive!"));
                        }
                        else{
                            EventCore.Alive.add(arg.getName());
                            EventCore.Dead.remove(arg.getName());
                            arg.teleport(p.getLocation());
                            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "\n" + prefix + ChatColor.GREEN + " " + args[0] + " has been revived by " + sender.getName() + "!" + "\n"));
                        }
                    }
                }
            }
        }
        else{
            sender.sendMessage(prefix + " " + permmessage);
        }
        return true;
    }
}
