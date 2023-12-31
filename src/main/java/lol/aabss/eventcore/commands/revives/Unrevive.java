package lol.aabss.eventcore.commands.revives;

import lol.aabss.eventcore.Config;
import lol.aabss.eventcore.EventCore;

import lol.aabss.eventcore.events.ReviveEvent;
import lol.aabss.eventcore.events.UseReviveEvent;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Unrevive implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        String prefix = Config.getString("prefix");
        String permmessage = Config.getString("permission-message");
        if (sender.hasPermission("eventcore.unrevive")){
            if (sender instanceof ConsoleCommandSender) {
                sender.sendMessage(Config.color(prefix + " &cThis command is only executable by a player."));
            }
            else{
                Player p = (Player) sender;
                if (args.length == 0){
                    p.sendMessage(Config.color(prefix + " &cPlease set a player to unrevive!"));
                }
                else{
                    if (Bukkit.getPlayer(args[0]) == null) {
                        p.sendMessage(Config.color(prefix + " &cPlease set a valid player!"));
                    }
                    else{
                        Player arg = Bukkit.getPlayer(args[0]);
                        assert arg != null;
                        if (EventCore.Dead.contains(arg.getName())){
                            p.sendMessage(Config.color(prefix + " &cThat player is already dead!"));
                        }
                        else{
                            EventCore.Alive.remove(arg.getName());
                            EventCore.Dead.add(arg.getName());
                            Bukkit.broadcastMessage(Config.color("\n" + prefix + " &a" + args[0] + " has been unrevived by " + sender.getName() + "!" + "\n"));
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
