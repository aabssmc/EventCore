package lol.aabss.eventcore.commands.alive;

import lol.aabss.eventcore.EventCore;
import lol.aabss.eventcore.util.SimpleCommand;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static lol.aabss.eventcore.util.Config.msg;

public class HealAlive implements SimpleCommand {

    @Override
    public boolean run(CommandSender sender, Command command, String[] args) {
        for (Player p : EventCore.Alive){
            p.setHealth(p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
        }
        sender.sendMessage(msg("healalive.healed"));
        return true;
    }
}
