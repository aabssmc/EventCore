package lol.aabss.eventcore.commands.alive;

import lol.aabss.eventcore.Config;
import lol.aabss.eventcore.EventCore;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class AliveList implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        String permmessage = Config.getString("permission-message");
        String prefix = Config.getString("prefix");
        if (sender.hasPermission("eventcore.alivelist")){
            if (EventCore.Alive.isEmpty()){
                sender.sendMessage(Config.color("&7There are 0 players alive\nThere are no players alive."));
            }
            else{
                if (EventCore.Alive.size() == 1){
                    sender.sendMessage(Config.color("&7There is 1 player alive" + "\n" + EventCore.Alive));
                }
                else{
                    sender.sendMessage(Config.color("&7There are " + EventCore.Alive.size() + " players alive" + "\n" + EventCore.Alive));
                }
            }
        }
        else {
            sender.sendMessage(Config.color(prefix + " " + permmessage));
        }
        return true;
    }
}
