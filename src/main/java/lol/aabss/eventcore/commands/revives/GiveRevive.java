package lol.aabss.eventcore.commands.revives;

import lol.aabss.eventcore.util.SimpleCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

import static lol.aabss.eventcore.EventCore.API;
import static lol.aabss.eventcore.util.Config.msg;

public class GiveRevive implements SimpleCommand {

    @Override
    public boolean run(CommandSender sender, Command command, String[] args) {
        if (args.length == 0){
            sender.sendMessage(msg("giverevive.specifyplayer"));
            return true;
        }
        if (args.length == 1){
            sender.sendMessage(msg("giverevive.specifyamount"));
            return true;
        }

        Player p = Bukkit.getPlayer(args[0]);
        if (p == null){
            sender.sendMessage(msg("giverevive.invalidplayer"));
            return true;
        }
        API.addRevives(p, Integer.valueOf(args[1]));
        sender.sendMessage(msg("giverevive.give")
                .replaceText(builder -> builder.match("%player%").replacement(p.getName()))
                .replaceText(builder -> builder.match("%amount%").replacement(args[1]))
        );
        p.sendMessage(msg("giverevive.receive")
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
