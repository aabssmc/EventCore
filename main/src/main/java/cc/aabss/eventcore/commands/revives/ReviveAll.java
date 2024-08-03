package cc.aabss.eventcore.commands.revives;

import cc.aabss.eventcore.EventCore;
import cc.aabss.eventcore.util.Config;
import cc.aabss.eventcore.util.SimpleCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class ReviveAll implements SimpleCommand {

    @Override
    public boolean run(CommandSender sender, Command command, String[] args) {
        if (sender instanceof ConsoleCommandSender) {
            sender.sendMessage(Config.msg("console"));
            return true;
        }
        for (Player list : Bukkit.getOnlinePlayers()) {
            if (Config.get("ignore-perm", Boolean.class) && list.hasPermission("eventcore.reviveall.bypass")) {
                continue;
            }
            EventCore.API.revive(list, (Player) sender, true);
        }
        Bukkit.broadcast(Config.msg("reviveall.revived")
                .replaceText(builder -> builder.match("%player%").replacement(sender.getName())));
        return true;
    }
}
