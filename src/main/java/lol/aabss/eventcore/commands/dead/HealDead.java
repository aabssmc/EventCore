package lol.aabss.eventcore.commands.dead;

import lol.aabss.eventcore.util.SimpleCommand;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static lol.aabss.eventcore.EventCore.instance;
import static lol.aabss.eventcore.util.Config.msg;

public class HealDead implements SimpleCommand {

    @Override
    public boolean run(CommandSender sender, Command command, String[] args) {
        for (Player p : instance.Dead){
            p.setHealth(p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
        }
        sender.sendMessage(msg("healdead.healed"));
        return true;
    }
}
