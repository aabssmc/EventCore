package lol.aabss.eventcore.commands.revives;

import lol.aabss.eventcore.events.UseReviveEvent;
import lol.aabss.eventcore.util.SimpleCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import static lol.aabss.eventcore.EventCore.API;
import static lol.aabss.eventcore.commands.revives.ToggleRevive.REVIVES;
import static lol.aabss.eventcore.util.Config.msg;

public class UseRevive implements SimpleCommand {

    @Override
    public boolean run(CommandSender sender, Command command, String[] args) {
        if (sender instanceof ConsoleCommandSender){
            sender.sendMessage(msg("console"));
            return true;
        }
        if (!REVIVES){
            sender.sendMessage(msg("userevive.revivesoff"));
            return true;
        }
        if (API.getRevives((Player) sender) <= 0){
            sender.sendMessage(msg("userevive.notenough"));
            return true;
        }
        int revs = API.getRevives((Player) sender);
        API.takeRevives((Player) sender, 1);
        Bukkit.broadcast(msg("userevive.request")
                .replaceText(builder -> builder.match("%player%").replacement(sender.getName())));
        Bukkit.getServer().getPluginManager().callEvent(new UseReviveEvent((Player) sender, revs, revs+1));
        return true;
    }
}
