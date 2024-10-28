package cc.aabss.eventcore.commands.revives;

import cc.aabss.eventcore.EventCore;
import cc.aabss.eventcore.util.Config;
import cc.aabss.eventcore.util.SimplePlayerCommand;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.Nullable;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static java.util.Objects.requireNonNull;

public class GiveRevive extends SimplePlayerCommand {

    public GiveRevive(@NotNull String name, @Nullable String description, @Nullable String... aliases) {
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
                                    EventCore.getOfflinePlayerAsync(context.getArgument("player", String.class)).whenCompleteAsync((p, throwable) -> {
                                        String name = p.getName() == null ? p.getUniqueId().toString() : p.getName();
                                        Integer amount = context.getArgument("amount", Integer.class);
                                        EventCore.API.addRevives(p, amount);
                                        sender.sendMessage(Config.msg("giverevive.give")
                                                .replaceText(builder -> builder.match("%player%").replacement(name))
                                                .replaceText(builder -> builder.match("%amount%").replacement(String.valueOf(amount)))
                                        );
                                        if (p.isOnline()) {
                                            requireNonNull(Bukkit.getPlayer(name)).sendMessage(Config.msg("giverevive.receive")
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
                            sender.sendMessage(Config.msg("giverevive.specifyamount"));
                            return 0;
                        })
                )
                .executes(context -> {
                    CommandSender sender = context.getSource().getSender();
                    sender.sendMessage(Config.msg("giverevive.specifyplayer"));
                    return 0;
                });
    }

}
