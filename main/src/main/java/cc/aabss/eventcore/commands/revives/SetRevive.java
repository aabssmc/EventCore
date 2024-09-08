package cc.aabss.eventcore.commands.revives;

import cc.aabss.eventcore.EventCore;
import cc.aabss.eventcore.util.Config;
import cc.aabss.eventcore.util.SimpleCommand;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class SetRevive implements SimpleCommand {

    @Override
    public void run(CommandSender sender, Command command, String[] args) {
        if (args.length == 0){
            sender.sendMessage(Config.msg("setrevive.specifyplayer"));
            return;
        }
        if (args.length == 1){
            sender.sendMessage(Config.msg("setrevive.specifyamount"));
            return;
        }

        EventCore.getOfflinePlayerAsync(args[0]).whenCompleteAsync((p, throwable) -> {
            String name = p.getName() == null ? p.getUniqueId().toString() : p.getName();
            EventCore.API.setRevives(p, Integer.parseInt(args[1]));
            sender.sendMessage(Config.msg("setrevive.set")
                    .replaceText(builder -> builder.match("%player%").replacement(name))
                    .replaceText(builder -> builder.match("%amount%").replacement(args[1]))
            );
            if (p.isOnline()) {
                requireNonNull(Bukkit.getPlayer(name)).sendMessage(Config.msg("setrevive.receive")
                        .replaceText(builder -> builder.match("%player%").replacement(sender.getName()))
                        .replaceText(builder -> builder.match("%amount%").replacement(args[1]))
                );
            }
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
