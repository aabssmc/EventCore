package lol.aabss.eventcore.commands.revives;

import lol.aabss.eventcore.Config;
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

public class TakeRevive implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        String permmessage = Config.getString("permission-message");
        String prefix = Config.getString("prefix");
        if (sender.hasPermission("eventcore.takerevive")){
            if (args.length == 0){
                sender.sendMessage(Config.color(prefix + " &cPlease specify a player!"));
            }
            else{
                if (args.length == 1){
                    sender.sendMessage(Config.color(prefix + " &cPlease specify an amount!"));
                }
                else{
                    if (Bukkit.getPlayer(args[0]) == null){
                        sender.sendMessage(Config.color(prefix + " &cPlease input a valid player"));
                    }
                    else{
                        Player e = Bukkit.getPlayer(args[0]);
                        assert e != null;
                        Config.takeRevives(e, Integer.valueOf(args[1]));
                        sender.sendMessage(Config.color(prefix + " &eYou took " + Integer.valueOf(args[1]) + " revives from " + args[0] + "!"));
                        e.sendMessage(Config.color(prefix + " &eYou lost " + Integer.valueOf(args[1]) + " revives!"));
                    }
                }
            }
        }
        else{
            sender.sendMessage(Config.color(prefix + " " + permmessage));
        }
        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 1){
            final List<String> completions = new ArrayList<>();
            for (Player p : Bukkit.getOnlinePlayers()){
                completions.add(p.getName());
            }
            return completions;
        }
        return null;
    }

}
