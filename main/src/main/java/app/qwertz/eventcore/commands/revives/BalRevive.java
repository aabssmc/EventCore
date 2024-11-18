package app.qwertz.eventcore.commands.revives;

import app.qwertz.eventcore.EventCore;
import app.qwertz.eventcore.util.Config;
import app.qwertz.eventcore.util.SimpleCommand;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BalRevive extends SimpleCommand {

    public BalRevive(@NotNull String name, @Nullable String description, @Nullable String... aliases) {
        super(name, description, aliases);
    }

    @Override
    protected LiteralArgumentBuilder<CommandSourceStack> run(LiteralArgumentBuilder<CommandSourceStack> argumentBuilder) {
        return argumentBuilder
                .then(Commands.argument("player", StringArgumentType.word())
                        .suggests((context, builder) -> {
                            for (Player p : Bukkit.getOnlinePlayers()) {
                                builder.suggest(p.getName());
                            }
                            return builder.buildFuture();
                        })
                        .executes(context -> {
                            CommandSender sender = context.getSource().getSender();
                            EventCore.getOfflinePlayerAsync(context.getArgument("player", String.class)).whenCompleteAsync((p, throwable) -> {
                                String name = p.getName() == null ? p.getUniqueId().toString() : p.getName();
                                sender.sendMessage(Config.msg("balrevive.player")
                                        .replaceText(builder -> builder.matchLiteral("%revives%").replacement(String.valueOf(EventCore.API.getRevives(p))))
                                        .replaceText(builder -> builder.matchLiteral("%player%").replacement(name)));
                            });
                            return 1;
                        })
                )
                .executes(context -> {
                    CommandSender sender = context.getSource().getSender();
                    if (sender instanceof Player) {
                        sender.sendMessage(Config.msg("balrevive.self")
                                .replaceText(builder -> builder.matchLiteral("%revives%").replacement(String.valueOf(EventCore.API.getRevives((Player) sender)))));
                        return 1;
                    }
                    return 0;
                });
    }
}
