package cc.aabss.eventcore.commands.other;

import cc.aabss.eventcore.EventCore;
import cc.aabss.eventcore.util.SimpleCommand;
import org.jetbrains.annotations.Nullable;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class ClearChat extends SimpleCommand {

    public ClearChat(@NotNull String name, @Nullable String description, @Nullable String... aliases) {
        super(name, description, aliases);
    }

    @Override
    public void run(CommandSender sender, String commandLabel, String[] args) {
        EventCore.API.clearChat(true, sender);
    }
}
