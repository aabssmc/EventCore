package lol.aabss.eventcore.commands.alive;

import lol.aabss.eventcore.Config;
import lol.aabss.eventcore.EventCore;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class KillAlive implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        String permmessage = Config.getString("permission-message");
        String prefix = Config.getString("prefix");
        if (sender.hasPermission("eventcore.killalive")){
            for (String player : EventCore.Alive){
                Player p = Bukkit.getPlayer(player);
                if (p != null) {
                    p.setHealth(0);
                }
            }
            sender.sendMessage(Config.color(prefix + " &eAll alive players have been killed."));
        }
        else {
            sender.sendMessage(Config.color(prefix + " " + permmessage));
        }
        return true;
    }
}
