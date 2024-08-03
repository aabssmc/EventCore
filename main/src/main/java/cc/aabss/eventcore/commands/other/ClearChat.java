package cc.aabss.eventcore.commands.other;

import cc.aabss.eventcore.EventCore;
import cc.aabss.eventcore.util.SimpleCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class ClearChat implements SimpleCommand {
    @Override
    public void run(CommandSender sender, Command command, String[] args) {
        EventCore.API.clearChat(true, sender);
    }
}
