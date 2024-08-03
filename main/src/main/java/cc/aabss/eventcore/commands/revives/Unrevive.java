package cc.aabss.eventcore.commands.revives;

import cc.aabss.eventcore.EventCore;
import cc.aabss.eventcore.util.Config;
import cc.aabss.eventcore.util.SimpleCommand;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;

public class Unrevive implements SimpleCommand {

    @Override
    public void run(CommandSender sender, Command command, String[] args) {
        if (sender instanceof ConsoleCommandSender) {
            sender.sendMessage(Config.msg("console"));
            return;
        }
        if (args.length == 0){
            sender.sendMessage(Config.msg("unrevive.specifyplayer"));
            return;
        }
        Player p = Bukkit.getPlayer(args[0]);
        if (p == null) {
            sender.sendMessage(Config.msg("unrevive.invalidplayer"));
            return;
        }
        if (EventCore.instance.Dead.contains(p)){
            sender.sendMessage(Config.msg("unrevive.alreadydead")
                    .replaceText(builder -> builder.match("%player%").replacement(p.getName())));
            return;
        }
        EventCore.API.unrevive(p, true);
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
