package cc.aabss.eventcore.commands.revives;

import cc.aabss.eventcore.EventCore;
import cc.aabss.eventcore.util.Config;
import cc.aabss.eventcore.util.SimpleCommand;
import org.jetbrains.annotations.Nullable;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class ToggleRevive extends SimpleCommand {

    public ToggleRevive(@NotNull String name, @Nullable String description, @Nullable String... aliases) {
        super(name, description, aliases);
    }

    @Override
    public void run(CommandSender sender, String commandLabel, String[] args) {
        EventCore.API.toggleRevives();
        sender.sendMessage(Config.msg("togglerevive."+(EventCore.instance.getConfig().getBoolean("revives-enabled", true) ? "enabled" : "disabled")));
    }
}
