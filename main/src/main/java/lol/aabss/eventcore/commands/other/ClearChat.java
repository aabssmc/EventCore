package lol.aabss.eventcore.commands.other;

import lol.aabss.eventcore.EventCore;
import lol.aabss.eventcore.util.SimpleCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class ClearChat implements SimpleCommand {
    @Override
    public boolean run(CommandSender sender, Command command, String[] args) {
        EventCore.API.clearChat(true, sender);
        return true;
    }
}
