package cc.aabss.eventcore.commands.revives;

import cc.aabss.eventcore.EventCore;
import cc.aabss.eventcore.util.Config;
import cc.aabss.eventcore.util.SimpleCommand;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.command.brigadier.argument.ArgumentTypes;
import io.papermc.paper.command.brigadier.argument.resolvers.selector.PlayerSelectorArgumentResolver;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Revive extends SimpleCommand {

    public Revive(@NotNull String name, @Nullable String description, @Nullable String... aliases) {
        super(name, description, aliases);
    }

    @Override
    protected LiteralArgumentBuilder<CommandSourceStack> run(LiteralArgumentBuilder<CommandSourceStack> argumentBuilder) {
        return argumentBuilder
                .requires(commandSourceStack -> commandSourceStack.getSender() instanceof Player)
                .then(Commands.argument("players", ArgumentTypes.players())
                        .executes(context -> {
                            CommandSender sender = context.getSource().getSender();
                            List<Player> players = context.getArgument("players", PlayerSelectorArgumentResolver.class).resolve(context.getSource());
                            List<String> revivedPlayers = new ArrayList<>();
                            for (Player p : players) {
                                if (EventCore.API.isAlive(p)){
                                    continue;
                                }
                                revivedPlayers.add(p.getName());
                                EventCore.API.revive(p, ((Player) sender), true);
                            }
                            if (!revivedPlayers.isEmpty()) {
                                sender.sendMessage(Config.msg("revive.revived")
                                        .replaceText(builder -> builder.match("%player%").replacement(EventCore.formatList(revivedPlayers)))
                                        .replaceText(builder -> builder.match("%reviver%").replacement(sender.getName()))
                                );
                            }
                            return 1;
                        })
                )
                .executes(context -> {
                    CommandSender sender = context.getSource().getSender();
                    sender.sendMessage(Config.msg("revive.specifyplayer"));
                    return 0;
                });
    }

}
