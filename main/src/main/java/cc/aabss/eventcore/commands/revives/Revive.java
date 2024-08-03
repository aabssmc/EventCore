package cc.aabss.eventcore.commands.revives;

import cc.aabss.eventcore.EventCore;
import cc.aabss.eventcore.util.Config;
import cc.aabss.eventcore.util.SimpleCommand;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;

public class Revive implements SimpleCommand {

    @Override
    public boolean run(CommandSender sender, Command command, String[] args) {
        if (sender instanceof ConsoleCommandSender) {
            sender.sendMessage(Config.msg("console"));
            return true;
        }
        if (args.length == 0){
            sender.sendMessage(Config.msg("revive.specifyplayer"));
            return true;
        }
        Player p = Bukkit.getPlayer(args[0]);
        if (p == null) {
            sender.sendMessage(Config.msg("revive.invalidplayer"));
            return true;
        }
        if (EventCore.API.isAlive(p)){
            sender.sendMessage(Config.msg("revive.alreadyalive")
                    .replaceText(builder -> builder.match("%player%").replacement(p.getName())));
            return true;
        }
        EventCore.API.revive(p, ((Player) sender), true);
        sender.sendMessage(Config.msg("revive.revived")
                .replaceText(builder -> builder.match("%player%").replacement(p.getName()))
                .replaceText(builder -> builder.match("%reviver%").replacement(sender.getName()))
        );
        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, Command command, String[] args) {
        if (args.length == 1){
            List<String> completions = new ArrayList<>();
            Bukkit.getOnlinePlayers().forEach(player -> completions.add(player.getName()));
            return completions;
        }
        return null;
    }

}
