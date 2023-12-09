package lol.aabss.eventcore.commands.revives;

import lol.aabss.eventcore.Config;
import lol.aabss.eventcore.EventCore;

import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Revive implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String prefix = Config.getString("prefix");
        String permmessage = Config.getString("permission-message");
        if (sender.hasPermission("eventcore.revive")){
            if (sender instanceof ConsoleCommandSender) {
                sender.sendMessage(Config.color(prefix + " &cThis command is only executable by a player."));
            }
            else{
                Player p = (Player) sender;
                if (args.length == 0){
                    p.sendMessage(Config.color(prefix + " &cPlease set a player to revive!"));
                }
                else{
                    if (Bukkit.getPlayer(args[0]) == null) {
                        p.sendMessage(Config.color(prefix + " &cPlease set a valid player!"));
                    }
                    else{
                        Player arg = Bukkit.getPlayer(args[0]);
                        assert arg != null;
                        if (EventCore.Alive.contains(arg.getName())){
                            p.sendMessage(Config.color(prefix + " &cThat player is already alive!"));
                        }
                        else{
                            EventCore.Alive.add(arg.getName());
                            EventCore.Dead.remove(arg.getName());
                            arg.teleport(p.getLocation());
                            Bukkit.broadcastMessage(Config.color("\n" + prefix + " &a" + args[0] + " has been revived by " + sender.getName() + "!" + "\n"));
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
