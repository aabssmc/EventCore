package cc.aabss.eventcore.commands.other;

import cc.aabss.eventcore.util.SimpleCommand;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import static cc.aabss.eventcore.EventCore.API;
import static cc.aabss.eventcore.util.Config.msg;

public class Timer extends SimpleCommand {

    public Timer(@NotNull String name, String description, String... aliases) {
        super(name, description, aliases);
    }

    @Override
    protected LiteralArgumentBuilder<CommandSourceStack> run(LiteralArgumentBuilder<CommandSourceStack> argumentBuilder) {
        return argumentBuilder
                .then(Commands.argument("timer", StringArgumentType.word())
                        .suggests((context, builder) ->
                                builder.suggest("cancel").buildFuture()
                        ).executes(context -> {
                            CommandSender sender = context.getSource().getSender();
                            String argument = StringArgumentType.getString(context, "timer");
                            if (argument.equals("cancel")) {
                                if (!API.stopTimer()) {
                                    sender.sendMessage(msg("timer.no-timer-running"));
                                    return 0;
                                }
                                return 1;
                            } else {
                                long l;
                                try {
                                    l = Long.parseLong(argument);
                                } catch (NumberFormatException ignored) {
                                    l = 15;
                                }
                                if (!API.startTimer(l)) {
                                    sender.sendMessage(msg("timer.already-running"));
                                }
                            }
                            return 1;
                        })
                )
                .executes(context -> {
                    context.getSource().getSender().sendMessage(msg("<red>/timer <seconds|cancel>"));
                    return 1;
                });
    }
}
