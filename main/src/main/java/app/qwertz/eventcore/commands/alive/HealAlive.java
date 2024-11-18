package app.qwertz.eventcore.commands.alive;

import app.qwertz.eventcore.EventCore;
import app.qwertz.eventcore.util.Config;
import app.qwertz.eventcore.util.SimpleCommand;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.attribute.Attribute;
import org.jetbrains.annotations.Nullable;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class HealAlive extends SimpleCommand {

    public HealAlive(@NotNull String name, @Nullable String description, @Nullable String... aliases) {
        super(name, description, aliases);
    }

    @Override
    protected LiteralArgumentBuilder<CommandSourceStack> run(LiteralArgumentBuilder<CommandSourceStack> argumentBuilder) {
        return argumentBuilder
                .executes(context -> {
                    for (Player p : EventCore.instance.Alive){
                        p.setFireTicks(0);
                        p.setHealth(p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
                        p.setFoodLevel(20);
                        p.setFreezeTicks(0);
                    }
                    context.getSource().getSender().sendMessage(Config.msg("healalive.healed"));
                    return 1;
                });
    }
}
