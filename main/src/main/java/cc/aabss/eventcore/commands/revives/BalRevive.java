package cc.aabss.eventcore.commands.revives;

import cc.aabss.eventcore.EventCore;
import cc.aabss.eventcore.util.Config;
import cc.aabss.eventcore.util.SimpleCommand;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class BalRevive implements SimpleCommand {

    @Override
    public void run(CommandSender sender, Command command, String[] args) {
        if (sender instanceof ConsoleCommandSender){
            sender.sendMessage(Config.msg("console"));
            return;
        }
        if (args.length == 0 || args[0].equals(sender.getName())){
            sender.sendMessage(Config.msg("balrevive.self")
                    .replaceText(builder -> builder.matchLiteral("%revives%").replacement(String.valueOf(EventCore.API.getRevives((Player) sender)))));
            return;
        }
        EventCore.getOfflinePlayerAsync(args[0]).whenCompleteAsync((p, throwable) -> {
            String name = p.getName() == null ? p.getUniqueId().toString() : p.getName();
            sender.sendMessage(Config.msg("balrevive.player")
                    .replaceText(builder -> builder.matchLiteral("%revives%").replacement(String.valueOf(EventCore.API.getRevives(p))))
                    .replaceText(builder -> builder.matchLiteral("%player%").replacement(name)));
        });
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
