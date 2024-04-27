package lol.aabss.eventcore.commands.revives;

import lol.aabss.eventcore.EventCore;

import lol.aabss.eventcore.events.ReviveEvent;
import lol.aabss.eventcore.util.Config;
import lol.aabss.eventcore.util.SimpleCommand;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;

import static lol.aabss.eventcore.util.Config.msg;

public class Revive implements SimpleCommand {

    @Override
    public boolean run(CommandSender sender, Command command, String[] args) {
        if (sender instanceof ConsoleCommandSender) {
            sender.sendMessage(msg("console"));
            return true;
        }
        if (args.length == 0){
            sender.sendMessage(msg("revive.specifyplayer"));
            return true;
        }
        Player p = Bukkit.getPlayer(args[0]);
        if (p == null) {
            sender.sendMessage(msg("revive.invalidplayer"));
            return true;
        }
        if (EventCore.Alive.contains(p)){
            p.sendMessage(msg("revive.alreadyalive")
                    .replaceText(builder -> builder.match("%player%").replacement(p.getName())));
            return true;
        }
        EventCore.Alive.add(p);
        EventCore.Dead.remove(p);
        p.teleport(((Player) sender).getLocation());
        Bukkit.broadcast(msg("revive.revived")
                .replaceText(builder -> builder.match("%player%").replacement(p.getName()))
                .replaceText(builder -> builder.match("%reviver%").replacement(sender.getName())));

        Bukkit.getServer().getPluginManager().callEvent(new ReviveEvent(p, sender));
        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, Command command, String[] args) {
        if (args.length == 1){
            List<String> completions = new ArrayList<>();
            EventCore.Dead.forEach(player -> completions.add(player.getName()));
            return completions;
        }
        return null;
    }

}
