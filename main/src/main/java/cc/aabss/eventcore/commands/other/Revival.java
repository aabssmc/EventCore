package cc.aabss.eventcore.commands.other;

import cc.aabss.eventcore.EventCore;
import cc.aabss.eventcore.util.Config;
import cc.aabss.eventcore.util.SimpleCommand;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static cc.aabss.eventcore.util.Config.msg;

public class Revival extends SimpleCommand {
    public Revival(@NotNull String name, String description, String... aliases) {
        super(name, description, aliases);
    }

    public static String answer;
    public static int winnerSize;
    public static List<String> winners = new ArrayList<>();
    public static boolean formerChatMute = false;

    @Override
    protected LiteralArgumentBuilder<CommandSourceStack> run(LiteralArgumentBuilder<CommandSourceStack> argumentBuilder) {
        return argumentBuilder
                .then(Commands.argument("winners", IntegerArgumentType.integer())
                        .then(Commands.argument("question", StringArgumentType.string())
                                .then(Commands.argument("answer", StringArgumentType.string())
                                        .executes(context -> {
                                            int winners = IntegerArgumentType.getInteger(context, "winners");
                                            String question = StringArgumentType.getString(context, "question");
                                            Bukkit.broadcast(msg("%prefix% <yellow>" + question + (question.endsWith("?") ? "" : "?") + " <gray>(" + winners + " winners)"));
                                            new BukkitRunnable() {
                                                int countdown = 3;
                                                @Override
                                                public void run() {
                                                    if (countdown > 0) {
                                                        Bukkit.broadcast(msg("%prefix% <gray>" + countdown + ".."));
                                                        countdown--;
                                                    } else {
                                                        cancel();
                                                        Bukkit.broadcast(msg("%prefix% <green>GO!!"));
                                                        answer = StringArgumentType.getString(context, "answer");
                                                        if (Config.get("show-revival-messages", Boolean.class)) {
                                                            formerChatMute = Mutechat.CHAT_MUTED;
                                                            Mutechat.CHAT_MUTED = false;
                                                        }
                                                    }
                                                }
                                            }.runTaskTimer(EventCore.instance, 0L, 20L);
                                            return 1;
                                        })
                                ).executes(context -> {
                                    context.getSource().getSender().sendMessage(msg("<red>/revival <winners> <question> <answer>"));
                                    return 1;
                                })
                        ).executes(context -> {
                            context.getSource().getSender().sendMessage(msg("<red>/revival <winners> <question> <answer>"));
                            return 1;
                        })
                )
                .executes(context -> {
                    context.getSource().getSender().sendMessage(msg("<red>/revival <winners> <question> <answer>"));
                    return 1;
                });
    }
}
