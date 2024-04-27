package lol.aabss.eventcore.commands.revives;

import lol.aabss.eventcore.util.Config;
import lol.aabss.eventcore.EventCore;

import lol.aabss.eventcore.events.ReviveEvent;
import lol.aabss.eventcore.util.SimpleCommand;
import net.kyori.adventure.text.TextReplacementConfig;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import org.bukkit.Bukkit;

import static lol.aabss.eventcore.util.Config.msg;

public class RecentRev implements SimpleCommand {

    @Override
    public boolean run(CommandSender sender, Command command, String[] args) {
        if (sender instanceof ConsoleCommandSender) {
            sender.sendMessage(msg("console"));
            return true;
        }
        for (Player p : EventCore.Recent){
            EventCore.Dead.remove(p);
            EventCore.Alive.add(p);
            p.teleport(((Player) sender).getLocation());
            Bukkit.getServer().getPluginManager().callEvent(new ReviveEvent(p, sender));
        }
        EventCore.Recent.clear();
        Bukkit.broadcast(msg("recentrev.revived")
                .replaceText(builder -> builder.match("%time%").replacement(Config.get("recent-rev-time", Integer.class).toString())));
        return true;
    }

}
