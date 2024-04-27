package lol.aabss.eventcore.commands.dead;

import lol.aabss.eventcore.EventCore;
import lol.aabss.eventcore.util.SimpleCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static lol.aabss.eventcore.util.Config.msg;

public class KillDead implements SimpleCommand {

    @Override
    public boolean run(CommandSender sender, Command command, String[] args) {
        for (Player p : EventCore.Dead){
            p.setHealth(0);
        }
        sender.sendMessage(msg("killdead.killed"));
        return true;
    }
}
