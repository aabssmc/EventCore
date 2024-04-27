package lol.aabss.eventcore.commands.revives;

import lol.aabss.eventcore.util.SimpleCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import static lol.aabss.eventcore.util.Config.msg;

public class ToggleRevive implements SimpleCommand {

    public static boolean REVIVES = false;

    @Override
    public boolean run(CommandSender sender, Command command, String[] args) {
        REVIVES = !REVIVES;
        sender.sendMessage(msg("togglerevive."+(REVIVES ? "enabled" : "disabled)")));
        return true;
    }
}
