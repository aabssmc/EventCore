package lol.aabss.eventcore.commands;

import lol.aabss.eventcore.Config;
import lol.aabss.eventcore.EventCore;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Visibility implements CommandExecutor, TabCompleter {

    public static ArrayList<Player> VisAll = new ArrayList<>();
    public static ArrayList<Player> VisStaff = new ArrayList<>();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        String prefix = Config.getString("prefix");
        String permmessage = Config.getString("permission-message");
        Player p = (Player) sender;
        if (p.hasPermission("visibility.use")){
            if (args.length == 0){
                p.sendMessage(Config.color(prefix + " &c/visibility <all | staff | off>"));
            }
            else{
                if (args[0].equals("all")){
                    if (p.hasPermission("visibility.all")){
                        if (Visibility.VisAll.contains(p)) {
                            p.sendMessage(Config.color(prefix + " &cAll players are already hidden."));
                        }
                        else{
                            for (Player player: Bukkit.getOnlinePlayers()){
                                p.showPlayer(EventCore.getPlugin(EventCore.class), player);
                                p.hidePlayer(EventCore.getPlugin(EventCore.class), player);
                            }
                            Visibility.VisAll.add(p);
                            Visibility.VisStaff.remove(p);
                            p.sendMessage(Config.color(prefix + " &aAll players are now hidden."));
                        }
                    }
                    else{
                        p.sendMessage(Config.color(prefix + " &c" + permmessage));
                    }
                }
                else if (args[0].equals("staff")){
                    if (p.hasPermission("visibility.staff")){
                        if (Visibility.VisStaff.contains(p)) {
                            p.sendMessage(Config.color(prefix + " &cAll players but staff are already hidden."));
                        }
                        else{
                            for (Player player: Bukkit.getOnlinePlayers()){
                                p.showPlayer(EventCore.getPlugin(EventCore.class), player);
                                p.hidePlayer(EventCore.getPlugin(EventCore.class), player);
                            }
                            for (Player player: Bukkit.getOnlinePlayers()){
                                if (player.hasPermission("visibility.staffbypass")){
                                    p.showPlayer(EventCore.getPlugin(EventCore.class), player);
                                }
                            }
                            Visibility.VisAll.remove(p);
                            Visibility.VisStaff.add(p);
                            p.sendMessage(Config.color(prefix + " &aAll players but staff are now hidden."));
                        }
                    }
                    else{
                        p.sendMessage(Config.color(prefix + " &c" + permmessage));
                    }
                }
                else if (args[0].equals("off")){
                    if (p.hasPermission("visibility.off")){
                        if (Visibility.VisAll.contains(p) || Visibility.VisStaff.contains(p)) {
                            for (Player player: Bukkit.getOnlinePlayers()){
                                p.showPlayer(EventCore.getPlugin(EventCore.class), player);
                            }
                            Visibility.VisAll.remove(p);
                            Visibility.VisStaff.remove(p);
                            p.sendMessage(Config.color(prefix + " &aAll players are now visible."));

                        }
                        else{
                            p.sendMessage(Config.color(prefix + " &cVisibility is already off."));
                        }
                    }
                    else{
                        p.sendMessage(Config.color(prefix + " &c" + permmessage));
                    }
                }
                else{
                    p.sendMessage(Config.color(prefix + " &c/visibility <all | staff | off>"));
                }
            }
        }
        else{
            p.sendMessage(Config.color(prefix + " &c" + permmessage));
        }
        return false;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 1){
            final List<String> completions = new ArrayList<>();
            completions.add("all");
            completions.add("staff");
            completions.add("off");
            return completions;
        }
        return null;
    }

}
