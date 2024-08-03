package cc.aabss.eventcore.commands.dead;

import cc.aabss.eventcore.EventCore;
import cc.aabss.eventcore.util.Config;
import cc.aabss.eventcore.util.SimpleCommand;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HealDead implements SimpleCommand {

    @Override
    public void run(CommandSender sender, Command command, String[] args) {
        for (Player p : EventCore.instance.Dead){
            p.setFireTicks(0);
            p.setHealth(p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
            p.setFoodLevel(20);
        }
        sender.sendMessage(Config.msg("healdead.healed"));
    }
}
