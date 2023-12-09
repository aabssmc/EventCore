package lol.aabss.eventcore.commands.revives;

import lol.aabss.eventcore.Config;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class BalRevive implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        String permmessage = Config.getString("permission-message");
        String prefix = Config.getString("prefix");
        if (sender instanceof ConsoleCommandSender){
            sender.sendMessage(Config.color(prefix + " &cThis command is only executable by players!"));
        }
        else{
            if (sender.hasPermission("eventcore.balrevive")){
                if (args.length == 0){
                    sender.sendMessage(Config.color(prefix + " &eYou have " + Config.getRevives((Player) sender) + " revives!"));
                }
                else{
                    if (args[0].equals(sender.getName())){
                        sender.sendMessage(Config.color(prefix + " &eYou have " + Config.getRevives((Player) sender) + " revives!"));
                    }
                    else{
                        if (Bukkit.getPlayer(args[0]) == null){
                            sender.sendMessage(Config.color(prefix + " &cInvalid player!"));
                        }
                        else{
                            sender.sendMessage(Config.color(prefix + " &e" + args[0] + "have " + Config.getRevives(Bukkit.getPlayer(args[0])) + " revives!"));
                        }
                    }
                }
            }
            else{
                sender.sendMessage(Config.color(prefix + " " + permmessage));
            }
        }

        return true;
    }
}
