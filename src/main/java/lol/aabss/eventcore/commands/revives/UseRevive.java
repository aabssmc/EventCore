package lol.aabss.eventcore.commands.revives;

import lol.aabss.eventcore.util.Config;
import lol.aabss.eventcore.events.UseReviveEvent;
import lol.aabss.eventcore.util.SimpleCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static lol.aabss.eventcore.commands.revives.ToggleRevive.REVIVES;
import static lol.aabss.eventcore.util.Config.msg;
import static net.kyori.adventure.text.minimessage.MiniMessage.miniMessage;

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
        if (Config.getRevives((Player) sender) <= 0){
            sender.sendMessage(msg("userevive.notenough"));
            return true;
        }
        Integer revs = Config.getRevives((Player) sender);
        Config.setRevives((Player) sender, revs-1);
        Bukkit.broadcast(msg("userevive.request")
                .replaceText(builder -> builder.match("%player%").replacement(sender.getName())));
        Bukkit.getServer().getPluginManager().callEvent(new UseReviveEvent((Player)sender, revs, revs+1));
        return true;
    }
}
