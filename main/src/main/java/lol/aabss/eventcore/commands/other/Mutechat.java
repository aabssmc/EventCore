package lol.aabss.eventcore.commands.other;

import lol.aabss.eventcore.util.SimpleCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import static lol.aabss.eventcore.util.Config.*;

public class Mutechat implements SimpleCommand {

    public static boolean CHAT_MUTED = false;

    @Override
    public boolean run(CommandSender sender, Command command, String[] args) {
        CHAT_MUTED = !CHAT_MUTED;
        Bukkit.broadcast(msg("mutechat." + (CHAT_MUTED ? "muted" : "unmuted")));
        return true;
    }

}
