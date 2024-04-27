package lol.aabss.eventcore.commands.revives;

import lol.aabss.eventcore.util.Config;
import lol.aabss.eventcore.util.SimpleCommand;
import net.kyori.adventure.text.TextReplacementConfig;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

import static lol.aabss.eventcore.util.Config.msg;

public class TakeRevive implements SimpleCommand {

    @Override
    public boolean run(CommandSender sender, Command command, String[] args) {
        if (args.length == 0){
            sender.sendMessage(msg("takerevive.specifyplayer"));
            return true;
        }
        if (args.length == 1){
            sender.sendMessage(msg("takerevive.specifyamount"));
            return true;
        }

        Player p = Bukkit.getPlayer(args[0]);
        if (p == null){
            sender.sendMessage(msg("takerevive.invalidplayer"));
            return true;
        }
        Config.setRevives(p, Config.getRevives(p)-Integer.parseInt(args[1]));
        sender.sendMessage(msg("takerevive.take")
                .replaceText(builder -> builder.match("%player%").replacement(p.getName()))
                .replaceText(builder -> builder.match("%amount%").replacement(args[1]))
        );
        sender.sendMessage(msg("takerevive.lose")
                .replaceText(builder -> builder.match("%player%").replacement(sender.getName()))
                .replaceText(builder -> builder.match("%amount%").replacement(args[1]))
        );
        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, Command command, String[] args) {
        if (args.length == 1){
            final List<String> completions = new ArrayList<>();
            Bukkit.getOnlinePlayers().forEach(player -> completions.add(player.getName()));
            return completions;
        }
        return null;
    }

}
