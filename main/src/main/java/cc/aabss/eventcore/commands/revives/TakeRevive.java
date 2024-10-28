package cc.aabss.eventcore.commands.revives;

import cc.aabss.eventcore.EventCore;
import cc.aabss.eventcore.util.Config;
import cc.aabss.eventcore.util.SimpleCommand;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import static java.util.Objects.requireNonNull;

public class TakeRevive extends SimpleCommand {

    public TakeRevive(@NotNull String name, @Nullable String description, @Nullable String... aliases) {
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
                        .then(Commands.argument("amount", IntegerArgumentType.integer())
                                .executes(context -> {
                                    CommandSender sender = context.getSource().getSender();
                                    String player = StringArgumentType.getString(context, "player");
                                    Integer amount = IntegerArgumentType.getInteger(context, "amount");
                                    EventCore.getOfflinePlayerAsync(player).whenCompleteAsync((p, throwable) -> {
                                        String name = p.getName() == null ? p.getUniqueId().toString() : p.getName();
                                        EventCore.API.takeRevives(p, amount);
                                        sender.sendMessage(Config.msg("takerevive.take")
                                                .replaceText(builder -> builder.match("%player%").replacement(name))
                                                .replaceText(builder -> builder.match("%amount%").replacement(String.valueOf(amount)))
                                        );
                                        if (p.isOnline()) {
                                            requireNonNull(Bukkit.getPlayer(name)).sendMessage(Config.msg("takerevive.lose")
                                                    .replaceText(builder -> builder.match("%player%").replacement(sender.getName()))
                                                    .replaceText(builder -> builder.match("%amount%").replacement(String.valueOf(amount)))
                                            );
                                        }
                                    });
                                    return 1;
                                })
                        )
                        .executes(context -> {
                            CommandSender sender = context.getSource().getSender();
                            sender.sendMessage(Config.msg("tetrevive.specifyamount"));
                            return 0;
                        })
                )
                .executes(context -> {
                    CommandSender sender = context.getSource().getSender();
                    sender.sendMessage(Config.msg("tetrevive.specifyplayer"));
                    return 0;
                });
    }

}
