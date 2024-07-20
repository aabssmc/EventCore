package lol.aabss.eventcore.commands.revives;

import lol.aabss.eventcore.EventCore;
import lol.aabss.eventcore.util.Config;
import lol.aabss.eventcore.util.SimpleCommand;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;

import static lol.aabss.eventcore.EventCore.API;
import static lol.aabss.eventcore.util.Config.msg;

public class RecentRev implements SimpleCommand {

    @Override
    public boolean run(CommandSender sender, Command command, String[] args) {
        if (sender instanceof ConsoleCommandSender) {
            sender.sendMessage(msg("console"));
            return true;
        }
        List<Player> recentlyDead = new ArrayList<>(EventCore.instance.Recent);
        for (Player p : recentlyDead) {
            API.revive(p, ((Player) sender), true);
        }
        Bukkit.broadcast(msg("recentrev.revived")
                .replaceText(builder -> builder.match("%time%").replacement(Config.get("recent-rev-time", Integer.class).toString())));
        return true;
    }

}
