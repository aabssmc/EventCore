package cc.aabss.eventcore.commands.revives;

import cc.aabss.eventcore.EventCore;
import cc.aabss.eventcore.util.Config;
import cc.aabss.eventcore.util.SimpleCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class ToggleRevive implements SimpleCommand {

    @Override
    public void run(CommandSender sender, Command command, String[] args) {
        EventCore.API.toggleRevives();
        sender.sendMessage(Config.msg("togglerevive."+(EventCore.instance.getConfig().getBoolean("revives-enabled", true) ? "enabled" : "disabled")));
    }
}
