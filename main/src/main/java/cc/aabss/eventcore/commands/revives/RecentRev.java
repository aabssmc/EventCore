package cc.aabss.eventcore.commands.revives;

import cc.aabss.eventcore.EventCore;
import cc.aabss.eventcore.util.Config;
import cc.aabss.eventcore.util.SimpleCommand;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;

public class RecentRev implements SimpleCommand {

    @Override
    public boolean run(CommandSender sender, Command command, String[] args) {
        if (sender instanceof ConsoleCommandSender) {
            sender.sendMessage(Config.msg("console"));
            return true;
        }
        List<Player> recentlyDead = new ArrayList<>(EventCore.instance.Recent);
        for (Player p : recentlyDead) {
            EventCore.API.revive(p, ((Player) sender), true);
        }
        Bukkit.broadcast(Config.msg("recentrev.revived")
                .replaceText(builder -> builder.match("%time%").replacement(Config.get("recent-rev-time", Integer.class).toString())));
        return true;
    }

}
