package lol.aabss.eventcore.commands.revives;

import lol.aabss.eventcore.util.Config;
import lol.aabss.eventcore.EventCore;
import lol.aabss.eventcore.events.ReviveEvent;
import lol.aabss.eventcore.util.SimpleCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import static lol.aabss.eventcore.util.Config.msg;

public class ReviveAll implements SimpleCommand {

    @Override
    public boolean run(CommandSender sender, Command command, String[] args) {
        if (sender instanceof ConsoleCommandSender) {
            sender.sendMessage(msg("console"));
            return true;
        }
        EventCore.Alive.clear();
        EventCore.Dead.clear();
        for (Player list: Bukkit.getOnlinePlayers()) {
            if (Config.get("ignore-perm", Boolean.class) && list.hasPermission("eventcore.reviveall.bypass")){
                continue;
            }
            EventCore.Alive.add(list);
            list.teleport((Player) sender);
            Bukkit.getServer().getPluginManager().callEvent(new ReviveEvent(list, sender));
        }
        Bukkit.broadcast(msg("reviveall.revived")
                .replaceText(builder -> builder.match("%player%").replacement(sender.getName())));
        return true;
    }
}
