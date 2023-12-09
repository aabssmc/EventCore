package lol.aabss.eventcore.commands.dead;

import lol.aabss.eventcore.Config;
import lol.aabss.eventcore.EventCore;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class HealDead implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        String permmessage = Config.getString("permission-message");
        String prefix = Config.getString("prefix");
        if (sender.hasPermission("eventcore.healdead")){
            for (String player : EventCore.Dead){
                Player p = Bukkit.getPlayer(player);
                if (p != null) {
                    p.setHealth(p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
                }
            }
            sender.sendMessage(Config.color(prefix + " &eAll dead players have been healed."));
        }
        else {
            sender.sendMessage(Config.color(prefix + " " + permmessage));
        }
        return true;
    }
}
