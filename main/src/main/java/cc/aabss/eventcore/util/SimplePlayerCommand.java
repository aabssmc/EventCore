package cc.aabss.eventcore.util;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public abstract class SimplePlayerCommand extends SimpleCommand{
    public SimplePlayerCommand(@NotNull String name, String description, String... aliases) {
        super(name, description, aliases);
    }

    @Override
    protected LiteralArgumentBuilder<CommandSourceStack> execute(LiteralArgumentBuilder<CommandSourceStack> argumentBuilder) {
        return run(argumentBuilder.requires(commandSourceStack -> commandSourceStack.getSender().hasPermission(permission()) && commandSourceStack.getSender() instanceof Player));
    }
}
