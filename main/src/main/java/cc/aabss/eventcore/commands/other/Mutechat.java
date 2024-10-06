package cc.aabss.eventcore.commands.other;

import cc.aabss.eventcore.util.SimpleCommand;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.Nullable;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import static cc.aabss.eventcore.util.Config.*;

public class Mutechat extends SimpleCommand {

    public static boolean CHAT_MUTED = false;

    public Mutechat(@NotNull String name, @Nullable String description, @Nullable String... aliases) {
        super(name, description, aliases);
    }

    @Override
    public void run(CommandSender sender, String commandLabel, String[] args) {
        CHAT_MUTED = !CHAT_MUTED;
        Bukkit.broadcast(msg("mutechat." + (CHAT_MUTED ? "muted" : "unmuted")));
    }

}
