package lol.aabss.eventcore.commands.revives;

import lol.aabss.eventcore.util.Config;
import lol.aabss.eventcore.util.SimpleCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

import static lol.aabss.eventcore.EventCore.API;
import static lol.aabss.eventcore.util.Config.msg;

public class ReviveLate implements SimpleCommand {

    @Override
    public boolean run(CommandSender sender, Command command, String[] args) {
        if (sender instanceof ConsoleCommandSender) {
            sender.sendMessage(msg("console"));
            return true;
        }
        List<Player> dead = new ArrayList<>(API.getDead());
        for (Player p : dead){
            if (Config.get("ignore-perm", Boolean.class) && p.hasPermission("eventcore.reviveall.bypass")) {
                continue;
            }
            API.revive(p, (Player) sender, true);
        }
        Bukkit.broadcast(msg("revivelate.revived")
                .replaceText(builder -> builder.match("%player%").replacement(sender.getName())));
        return true;
    }
}
