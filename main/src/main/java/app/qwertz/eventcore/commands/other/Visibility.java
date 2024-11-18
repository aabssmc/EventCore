package app.qwertz.eventcore.commands.other;

import app.qwertz.eventcore.EventCore;
import app.qwertz.eventcore.api.VisibilityState;
import app.qwertz.eventcore.util.Config;
import app.qwertz.eventcore.util.SimplePlayerCommand;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.Nullable;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.TemporalAmount;
import java.util.ArrayList;
import java.util.Map;

import static app.qwertz.eventcore.EventCore.API;
import static app.qwertz.eventcore.util.Config.msg;

public class Visibility extends SimplePlayerCommand {

    public static ArrayList<Player> VisAll = new ArrayList<>();
    public static ArrayList<Player> VisStaff = new ArrayList<>();

    public Visibility(@NotNull String name, @Nullable String description, @Nullable String... aliases) {
        super(name, description, aliases);
    }
    @Override
    protected LiteralArgumentBuilder<CommandSourceStack> run(LiteralArgumentBuilder<CommandSourceStack> argumentBuilder) {
        return argumentBuilder
                .then(Commands.argument("visibility", StringArgumentType.word())
                        .suggests((context, builder) -> builder
                                .suggest(VisibilityState.ALL.name().toLowerCase())
                                .suggest(VisibilityState.STAFF.name().toLowerCase())
                                .suggest(VisibilityState.OFF.name().toLowerCase()).buildFuture()
                        ).executes(context -> {
                            Player p = (Player) context.getSource().getSender();
                            Instant now = Instant.now();
                            if (EventCore.cooldowns.containsKey(this.getClass().getName())){
                                Instant cool = EventCore.cooldowns.get(this.getClass().getName()).get(p);
                                if (cool != null && cool.isAfter(now)) {
                                    Duration remaining = Duration.between(now, cool);
                                    p.sendMessage(Config.msg("cooldown")
                                            .replaceText(builder -> builder.match("%time%").replacement(formatDuration(remaining))));
                                    return 0;
                                } else {
                                    Map<CommandSender, Instant> newMap = EventCore.cooldowns.get(this.getClass().getName());
                                    newMap.put(p, now.plus(cooldown()));
                                    EventCore.cooldowns.put(this.getClass().getName(), newMap);
                                }
                            }
                            VisibilityState state = VisibilityState.valueOf(context.getArgument("visibility", String.class).toUpperCase());
                            switch (state) {
                                case ALL -> {
                                    if (p.hasPermission("eventcore.visibility.all")) {
                                        if (VisAll.contains(p)) {
                                            p.sendMessage(msg("visibility.allalreadyhidden"));
                                            return 0;
                                        }
                                        API.setVisibilityState(p, VisibilityState.ALL);
                                        p.sendMessage(msg("visibility.allhidden"));
                                        return 1;
                                    }
                                }
                                case STAFF -> {
                                    if (p.hasPermission("eventcore.visibility.staff")) {
                                        if (VisStaff.contains(p)) {
                                            p.sendMessage(msg("visibility.staffalreadyhidden"));
                                            return 0;
                                        }
                                        API.setVisibilityState(p, VisibilityState.STAFF);
                                        p.sendMessage(msg("visibility.staffhidden"));
                                        return 1;
                                    }
                                }
                                case OFF -> {
                                    if (p.hasPermission("eventcore.visibility.off")) {
                                        if (!VisStaff.contains(p) && !VisAll.contains(p)) {
                                            p.sendMessage(msg("visibility.visibilityalreadyoff"));
                                            return 0;
                                        }
                                        API.setVisibilityState(p, VisibilityState.OFF);
                                        p.sendMessage(msg("visibility.visibilityoff"));
                                        return 1;
                                    }
                                }
                            }
                            p.sendMessage(msg("permission-message"));
                            return 1;
                        })
                )
                .executes(context -> {
                    context.getSource().getSender().sendMessage(msg("<red>/visibility <all | staff | off>"));
                    return 1;
                });
    }

    @Override
    public String permission() {
        return "eventcore.visibility.use";
    }

    public TemporalAmount cooldown() {
        return Duration.ofSeconds(5);
    }

    private String formatDuration(Duration duration) {
        long seconds = duration.getSeconds();
        long absSeconds = Math.abs(seconds);
        long hours = absSeconds / 3600;
        long minutes = (absSeconds % 3600) / 60;
        long secs = absSeconds % 60;
        StringBuilder result = new StringBuilder();
        if (hours > 0) {
            result.append(hours).append(" hour").append(hours > 1 ? "s" : "");
        }
        if (minutes > 0) {
            if (!result.isEmpty()) {
                result.append(", ");
            }
            result.append(minutes).append(" minute").append(minutes > 1 ? "s" : "");
        }
        if (secs > 0 || result.isEmpty()) {
            if (!result.isEmpty()) {
                result.append(" and ");
            }
            result.append(secs).append(" second").append(secs != 1 ? "s" : "");
        }
        return result.toString();
    }
}
