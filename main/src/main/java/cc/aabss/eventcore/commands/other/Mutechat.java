package cc.aabss.eventcore.commands.other;

import cc.aabss.eventcore.util.SimpleCommand;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;

import static cc.aabss.eventcore.util.Config.*;

public class Mutechat extends SimpleCommand {

    public static boolean CHAT_MUTED = false;

    public Mutechat(@NotNull String name, @Nullable String description, @Nullable String... aliases) {
        super(name, description, aliases);
    }

    @Override
    protected LiteralArgumentBuilder<CommandSourceStack> run(LiteralArgumentBuilder<CommandSourceStack> argumentBuilder) {
        return argumentBuilder
                .executes(context -> {
                    CHAT_MUTED = !CHAT_MUTED;
                    Bukkit.broadcast(msg("mutechat." + (CHAT_MUTED ? "muted" : "unmuted")));
                    return 1;
                });
    }

}
