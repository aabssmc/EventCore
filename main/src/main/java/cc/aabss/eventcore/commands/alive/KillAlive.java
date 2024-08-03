package cc.aabss.eventcore.commands.alive;

import cc.aabss.eventcore.EventCore;
import cc.aabss.eventcore.util.Config;
import cc.aabss.eventcore.util.SimpleCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KillAlive implements SimpleCommand {

    @Override
    public void run(CommandSender sender, Command command, String[] args) {
        for (Player p : EventCore.instance.Alive){
            p.setHealth(0);
        }
        sender.sendMessage(Config.msg("killalive.killed"));
    }
}
