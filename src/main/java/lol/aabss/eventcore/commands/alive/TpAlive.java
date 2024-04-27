package lol.aabss.eventcore.commands.alive;

import lol.aabss.eventcore.EventCore;

import lol.aabss.eventcore.util.SimpleCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static lol.aabss.eventcore.util.Config.msg;

public class TpAlive implements SimpleCommand {

    @Override
    public boolean run(CommandSender sender, Command command, String[] args) {
        if (!(sender instanceof Player p)) {
            sender.sendMessage(msg("console"));
            return true;
        }
        for (Player player: EventCore.Alive) {
            player.teleport(p.getLocation());
            player.sendMessage(msg("tpalive.teleported"));
        }
        Bukkit.broadcast(msg("tpalive.teleport-broadcast")
                .replaceText(builder -> builder.matchLiteral("%sender%").replacement(sender.getName())));
        return true;
    }
}
