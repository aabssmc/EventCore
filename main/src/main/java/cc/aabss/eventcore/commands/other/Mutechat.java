package cc.aabss.eventcore.commands.other;

import cc.aabss.eventcore.util.SimpleCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import static cc.aabss.eventcore.util.Config.*;

public class Mutechat implements SimpleCommand {

    public static boolean CHAT_MUTED = false;

    @Override
    public void run(CommandSender sender, Command command, String[] args) {
        CHAT_MUTED = !CHAT_MUTED;
        Bukkit.broadcast(msg("mutechat." + (CHAT_MUTED ? "muted" : "unmuted")));
    }

}
