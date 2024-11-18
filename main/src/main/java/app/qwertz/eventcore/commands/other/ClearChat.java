package app.qwertz.eventcore.commands.other;

import app.qwertz.eventcore.EventCore;
import app.qwertz.eventcore.util.SimpleCommand;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;

public class ClearChat extends SimpleCommand {

    public ClearChat(@NotNull String name, @Nullable String description, @Nullable String... aliases) {
        super(name, description, aliases);
    }

    @Override
    protected LiteralArgumentBuilder<CommandSourceStack> run(LiteralArgumentBuilder<CommandSourceStack> argumentBuilder) {
        return argumentBuilder
                .executes(context -> {
                    EventCore.API.clearChat(true, context.getSource().getSender());
                    return 1;
                });
    }
}
