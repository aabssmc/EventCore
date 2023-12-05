package lol.aabss.eventcore.commands;

import lol.aabss.eventcore.EventCore;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Visibility implements CommandExecutor {

    public static ArrayList<Player> VisAll = new ArrayList<>();
    public static ArrayList<Player> VisStaff = new ArrayList<>();

    private final EventCore plugin;

    public Visibility(EventCore plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        String prefix = this.plugin.getConfig().getString("prefix");
        String permmessage = this.plugin.getConfig().getString("permission-message");
        Player p = (Player) sender;
        if (p.hasPermission("visibility.use")){
            if (args.length == 0){
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + ChatColor.RED + " /visibility <all | staff | off>"));
            }
            else{
                if (args[0].equals("all")){
                    if (p.hasPermission("visibility.all")){
                        if (Visibility.VisAll.contains(p)) {
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + ChatColor.RED + " All players are already hidden."));
                        }
                        else{
                            for (Player player: Bukkit.getOnlinePlayers()){
                                p.showPlayer(plugin, player);
                                p.hidePlayer(plugin, player);
                            }
                            Visibility.VisAll.add(p);
                            Visibility.VisStaff.remove(p);
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + ChatColor.GREEN + " All players are now hidden."));
                        }
                    }
                    else{
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + ChatColor.RED + " " + permmessage));
                    }
                }
                else if (args[0].equals("staff")){
                    if (p.hasPermission("visibility.staff")){
                        if (Visibility.VisStaff.contains(p)) {
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + ChatColor.RED + " All players but staff are already hidden."));
                        }
                        else{
                            for (Player player: Bukkit.getOnlinePlayers()){
                                p.showPlayer(plugin, player);
                                p.hidePlayer(plugin, player);
                            }
                            for (Player player: Bukkit.getOnlinePlayers()){
                                if (player.hasPermission("visibility.staffbypass")){
                                    p.showPlayer(plugin, player);
                                }
                            }
                            Visibility.VisAll.remove(p);
                            Visibility.VisStaff.add(p);
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + ChatColor.GREEN + " All players but staff are now hidden."));
                        }
                    }
                    else{
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + ChatColor.RED + " " + permmessage));
                    }
                }
                else if (args[0].equals("off")){
                    if (p.hasPermission("visibility.off")){
                        if (Visibility.VisAll.contains(p) || Visibility.VisStaff.contains(p)) {
                            for (Player player: Bukkit.getOnlinePlayers()){
                                p.showPlayer(plugin, player);
                            }
                            Visibility.VisAll.remove(p);
                            Visibility.VisStaff.remove(p);
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + ChatColor.GREEN + " All players are now visible."));

                        }
                        else{
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + ChatColor.RED + " Visibility is already off."));
                        }
                    }
                    else{
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + ChatColor.RED + " " + permmessage));
                    }
                }
                else{
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + ChatColor.RED + " /visibility <all | staff | off>"));
                }
            }
        }
        else{
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " " + permmessage));
        }
        return false;
    }
}
