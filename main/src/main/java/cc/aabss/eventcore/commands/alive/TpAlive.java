package cc.aabss.eventcore.commands.alive;

import cc.aabss.eventcore.EventCore;
import cc.aabss.eventcore.util.Config;
import cc.aabss.eventcore.util.SimpleCommand;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.Nullable;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TpAlive extends SimpleCommand {

    public TpAlive(@NotNull String name, @Nullable String description, @Nullable String... aliases) {
        super(name, description, aliases);
    }

    @Override
    protected LiteralArgumentBuilder<CommandSourceStack> run(LiteralArgumentBuilder<CommandSourceStack> argumentBuilder) {
        return argumentBuilder
                .requires(commandSourceStack -> commandSourceStack.getSender() instanceof Player)
                .executes(context -> {
                    CommandSender sender = context.getSource().getSender();
                    for (Player player: EventCore.instance.Alive) {
                        player.teleport(((Player) sender).getLocation());
                    }
                    Bukkit.broadcast(Config.msg("tpalive.teleport-broadcast")
                            .replaceText(builder -> builder.matchLiteral("%sender%").replacement(sender.getName())));
                    return 1;
                });
    }

}
