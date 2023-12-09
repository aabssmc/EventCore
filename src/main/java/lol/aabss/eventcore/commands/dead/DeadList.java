package lol.aabss.eventcore.commands.dead;

import lol.aabss.eventcore.Config;
import lol.aabss.eventcore.EventCore;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class DeadList implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String permmessage = Config.getString("permission-message");
        String prefix = Config.getString("prefix");
        if (sender.hasPermission("eventcore.deadlist")){
            if (EventCore.Dead.isEmpty()){
                sender.sendMessage(Config.color("&7There are 0 players dead\nThere are no players dead."));
            }
            else{
                if (EventCore.Dead.size() == 1){
                    sender.sendMessage(Config.color("&7There is 1 player dead" + "\n" + EventCore.Dead));
                }
                else{
                    sender.sendMessage(Config.color("&7There are " + EventCore.Dead.size() + " players dead" + "\n" + EventCore.Dead));
                }
            }
        }
        else{
            sender.sendMessage(Config.color(prefix + " " + permmessage));
        }
        return true;
    }
}
