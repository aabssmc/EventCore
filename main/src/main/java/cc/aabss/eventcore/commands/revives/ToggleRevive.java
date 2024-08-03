package cc.aabss.eventcore.commands.revives;

import cc.aabss.eventcore.EventCore;
import cc.aabss.eventcore.util.Config;
import cc.aabss.eventcore.util.SimpleCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class ToggleRevive implements SimpleCommand {

    public static boolean REVIVES = false;

    @Override
    public void run(CommandSender sender, Command command, String[] args) {
        EventCore.API.toggleRevives();
        sender.sendMessage(Config.msg("togglerevive."+(REVIVES ? "enabled" : "disabled")));
    }
}
