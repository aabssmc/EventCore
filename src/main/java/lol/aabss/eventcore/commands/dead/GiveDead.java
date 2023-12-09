package lol.aabss.eventcore.commands.dead;

import lol.aabss.eventcore.Config;
import lol.aabss.eventcore.EventCore;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class GiveDead implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String permmessage = Config.getString("permission-message");
        String prefix = Config.getString("prefix");
        if (sender.hasPermission("eventcore.givedead")){
            if (args.length == 0){
                sender.sendMessage(Config.color(prefix + " &cPlease specify an item!"));
            }
            else{
                if (args.length == 1){
                    for (String player : EventCore.Dead){
                        Player p = Bukkit.getPlayer(player);
                        if (p != null) {
                            ItemStack item = new ItemStack(Material.valueOf(args[0]), 64);
                            p.getInventory().addItem(item);
                        }
                    }
                    sender.sendMessage(Config.color(prefix + " &eGave all dead players 64x " + Material.valueOf(args[0]) + "!"));
                }
                else{
                    for (String player : EventCore.Dead){
                        Player p = Bukkit.getPlayer(player);
                        if (p != null) {
                            ItemStack item = new ItemStack(Material.valueOf(args[0]), Integer.parseInt(args[1]));
                            p.getInventory().addItem(item);
                        }
                    }
                    sender.sendMessage(Config.color(prefix + " &eGave all dead players " + Integer.parseInt(args[1]) + "x " + Material.valueOf(args[0]) + "!"));
                }
            }
        }
        else {
            sender.sendMessage(Config.color(prefix + " " + permmessage));
        }
        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 1){
            final List<String> completions = new ArrayList<>();
            for (Material p : Material.values()){
                completions.add(p.name());
            }
            return completions;
        }
        return null;
    }
}
