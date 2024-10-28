package cc.aabss.eventcore.commands.revives;

import cc.aabss.eventcore.EventCore;
import cc.aabss.eventcore.util.Config;
import cc.aabss.eventcore.util.SimpleCommand;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;

public class ToggleRevive extends SimpleCommand {

    public ToggleRevive(@NotNull String name, @Nullable String description, @Nullable String... aliases) {
        super(name, description, aliases);
    }

    @Override
    protected LiteralArgumentBuilder<CommandSourceStack> run(LiteralArgumentBuilder<CommandSourceStack> argumentBuilder) {
        return argumentBuilder
                .executes(context -> {
                    EventCore.API.toggleRevives();
                    context.getSource().getSender().sendMessage(
                            Config.msg("togglerevive."+(EventCore.instance.getConfig().getBoolean("revives-enabled", true) ? "enabled" : "disabled"))
                    );
                    return 1;
                });
    }

}
