package lol.aabss.eventcore.commands.dead;

import lol.aabss.eventcore.Config;
import lol.aabss.eventcore.EventCore;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KillDead implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String permmessage = Config.getString("permission-message");
        String prefix = Config.getString("prefix");
        if (sender.hasPermission("eventcore.killdead")){
            for (String player : EventCore.Dead){
                Player p = Bukkit.getPlayer(player);
                if (p != null) {
                    p.setHealth(0);
                }
            }
            sender.sendMessage(Config.color(prefix + " &eAll dead players have been killed."));
        }
        else {
            sender.sendMessage(Config.color(prefix + " " + permmessage));
        }
        return true;
    }
}
