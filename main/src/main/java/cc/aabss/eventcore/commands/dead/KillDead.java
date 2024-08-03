package cc.aabss.eventcore.commands.dead;

import cc.aabss.eventcore.EventCore;
import cc.aabss.eventcore.util.Config;
import cc.aabss.eventcore.util.SimpleCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KillDead implements SimpleCommand {

    @Override
    public boolean run(CommandSender sender, Command command, String[] args) {
        for (Player p : EventCore.instance.Dead){
            p.setHealth(0);
        }
        sender.sendMessage(Config.msg("killdead.killed"));
        return true;
    }
}
