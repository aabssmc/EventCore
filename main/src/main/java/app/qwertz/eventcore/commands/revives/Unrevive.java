package app.qwertz.eventcore.commands.revives;

import app.qwertz.eventcore.EventCore;
import app.qwertz.eventcore.util.Config;
import app.qwertz.eventcore.util.SimpleCommand;
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

public class Unrevive extends SimpleCommand {

    public Unrevive(@NotNull String name, @Nullable String description, @Nullable String... aliases) {
        super(name, description, aliases);
    }

    @Override
    protected LiteralArgumentBuilder<CommandSourceStack> run(LiteralArgumentBuilder<CommandSourceStack> argumentBuilder) {
        return argumentBuilder
                .then(Commands.argument("players", ArgumentTypes.players())
                        .executes(context -> {
                            CommandSender sender = context.getSource().getSender();
                            List<Player> players = context.getArgument("players", PlayerSelectorArgumentResolver.class).resolve(context.getSource());
                            List<String> unrevivedPlayers = new ArrayList<>();
                            for (Player p : players) {
                                if (EventCore.instance.Dead.contains(p)){
                                    sender.sendMessage(Config.msg("unrevive.alreadydead")
                                            .replaceText(builder -> builder.match("%player%").replacement(p.getName())));
                                    continue;
                                }
                                unrevivedPlayers.add(p.getName());
                                EventCore.API.unrevive(p, true);
                            }
                            if (!unrevivedPlayers.isEmpty()) {
                                sender.sendMessage(Config.msg("unrevive.unrevived")
                                        .replaceText(builder -> builder.match("%player%").replacement(EventCore.formatList(unrevivedPlayers)))
                                        .replaceText(builder -> builder.match("%unreviver%").replacement(sender.getName()))
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
