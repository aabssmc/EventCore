package cc.aabss.eventcore.commands.alive;

import cc.aabss.eventcore.EventCore;
import cc.aabss.eventcore.util.Config;
import cc.aabss.eventcore.util.SimpleCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TpAlive implements SimpleCommand {

    @Override
    public boolean run(CommandSender sender, Command command, String[] args) {
        if (!(sender instanceof Player p)) {
            sender.sendMessage(Config.msg("console"));
            return true;
        }
        for (Player player: EventCore.instance.Alive) {
            player.teleport(p.getLocation());
        }
        Bukkit.broadcast(Config.msg("tpalive.teleport-broadcast")
                .replaceText(builder -> builder.matchLiteral("%sender%").replacement(sender.getName())));
        return true;
    }
}
