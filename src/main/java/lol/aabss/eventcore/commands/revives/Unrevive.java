package lol.aabss.eventcore.commands.revives;

import lol.aabss.eventcore.EventCore;

import lol.aabss.eventcore.util.Config;
import lol.aabss.eventcore.util.SimpleCommand;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;

import static lol.aabss.eventcore.util.Config.msg;

public class Unrevive implements SimpleCommand {

    @Override
    public boolean run(CommandSender sender, Command command, String[] args) {
        if (sender instanceof ConsoleCommandSender) {
            sender.sendMessage(msg("console"));
            return true;
        }
        if (args.length == 0){
            sender.sendMessage(msg("unrevive.specifyplayer"));
            return true;
        }
        Player p = Bukkit.getPlayer(args[0]);
        if (p == null) {
            sender.sendMessage(msg("unrevive.invalidplayer"));
            return true;
        }
        if (EventCore.Dead.contains(p)){
            p.sendMessage(msg("unrevive.alreadydead")
                    .replaceText(builder -> builder.match("%player%").replacement(p.getName())));
            return true;
        }
        EventCore.Dead.add(p);
        EventCore.Alive.remove(p);
        p.teleport(((Player) sender).getLocation());
        Bukkit.broadcast(msg("unrevive.unrevived")
                .replaceText(builder -> builder.match("%player%").replacement(p.getName()))
                .replaceText(builder -> builder.match("%unreviver%").replacement(sender.getName())));
        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, Command command, String[] args) {
        if (args.length == 1){
            List<String> completions = new ArrayList<>();
            EventCore.Alive.forEach(player -> completions.add(player.getName()));
            return completions;
        }
        return null;
    }

}
