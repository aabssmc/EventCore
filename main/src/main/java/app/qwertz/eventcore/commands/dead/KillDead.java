package app.qwertz.eventcore.commands.dead;

import app.qwertz.eventcore.EventCore;
import app.qwertz.eventcore.util.Config;
import app.qwertz.eventcore.util.SimpleCommand;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.jetbrains.annotations.Nullable;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class KillDead extends SimpleCommand {

    public KillDead(@NotNull String name, @Nullable String description, @Nullable String... aliases) {
        super(name, description, aliases);
    }

    @Override
    protected LiteralArgumentBuilder<CommandSourceStack> run(LiteralArgumentBuilder<CommandSourceStack> argumentBuilder) {
        return argumentBuilder
                .executes(context -> {
                    for (Player p : EventCore.instance.Dead){
                        p.setHealth(0);
                    }
                    context.getSource().getSender().sendMessage(Config.msg("killdead.killed"));
                    return 1;
                });
    }
}
