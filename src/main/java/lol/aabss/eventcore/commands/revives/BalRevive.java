package lol.aabss.eventcore.commands.revives;

import lol.aabss.eventcore.util.Config;
import lol.aabss.eventcore.util.SimpleCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

import static lol.aabss.eventcore.util.Config.msg;

public class BalRevive implements SimpleCommand {

    @Override
    public boolean run(CommandSender sender, Command command, String[] args) {
        if (sender instanceof ConsoleCommandSender){
            sender.sendMessage(msg("console"));
            return true;
        }
        if (args.length == 0 || args[0].equals(sender.getName())){
            sender.sendMessage(msg("balrevive.self")
                    .replaceText(builder -> builder.matchLiteral("%revives%").replacement(Config.getRevives((Player) sender).toString())));
            return true;
        }
        Player p = Bukkit.getPlayer(args[0]);
        if (p == null){
            sender.sendMessage(msg("balrevive.invalid-player"));
            return true;
        }
        sender.sendMessage(msg("balrevive.player")
                .replaceText(builder -> builder.matchLiteral("%revives%").replacement(Config.getRevives(p).toString()))
                .replaceText(builder -> builder.matchLiteral("%player%").replacement(p.getName())));
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
