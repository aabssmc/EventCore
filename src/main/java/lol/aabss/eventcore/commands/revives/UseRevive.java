package lol.aabss.eventcore.commands.revives;

import lol.aabss.eventcore.Config;
import lol.aabss.eventcore.events.UseReviveEvent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class UseRevive implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        String prefix = Config.getString("prefix");
        String permmessage = Config.getString("permission-message");
        if (sender.hasPermission("eventcore.userevive")){
            if (sender instanceof ConsoleCommandSender){
                sender.sendMessage(Config.color(prefix + " &cThis command is only executable by a player."));
            }
            else{
                if (Config.getRevives((Player) sender) <= 0){
                    sender.sendMessage(Config.color(prefix + " &cYou do not have enough revives!"));
                }
                else{
                    Integer revs = Config.getRevives((Player) sender);
                    Config.takeRevives((Player) sender, 1);
                    Bukkit.broadcastMessage(Config.color("\n&e" + sender.getName() + " has requested to be revived!" + "\n"));
                    Bukkit.getServer().getPluginManager().callEvent(new UseReviveEvent((Player)sender, revs, revs+1));
                }
            }
        }
        else{
            sender.sendMessage(Config.color(prefix + " " + permmessage));
        }
        return true;
    }
}
