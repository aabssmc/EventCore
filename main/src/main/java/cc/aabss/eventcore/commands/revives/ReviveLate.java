package cc.aabss.eventcore.commands.revives;

import cc.aabss.eventcore.EventCore;
import cc.aabss.eventcore.util.SimpleCommand;
import cc.aabss.eventcore.util.Config;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;


import static cc.aabss.eventcore.util.Config.msg;

public class ReviveLate implements SimpleCommand {

    @Override
    public void run(CommandSender sender, Command command, String[] args) {
        if (sender instanceof ConsoleCommandSender) {
            sender.sendMessage(msg("console"));
            return;
        }
        for (Player p : Bukkit.getOnlinePlayers()){
            if (!EventCore.API.isDead(p)) continue;
            if (Config.get("ignore-perm", Boolean.class) && p.hasPermission("eventcore.reviveall.bypass")) {
                continue;
            }
            EventCore.API.revive(p, (Player) sender, true);
        }
        Bukkit.broadcast(msg("revivelate.revived")
                .replaceText(builder -> builder.match("%player%").replacement(sender.getName())));
    }
}
