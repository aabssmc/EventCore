package app.qwertz.eventcore.commands.revives;

import app.qwertz.eventcore.EventCore;
import app.qwertz.eventcore.util.Config;
import app.qwertz.eventcore.util.SimpleCommand;
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
