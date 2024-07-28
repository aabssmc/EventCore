package lol.aabss.eventcore.util;

import org.apache.commons.lang3.tuple.Triple;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAmount;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static lol.aabss.eventcore.EventCore.cooldowns;
import static lol.aabss.eventcore.util.Config.msg;

public interface SimpleCooldownCommand extends SimpleCommand {

    @Override
    default boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Instant now = Instant.now();
        if (!cooldowns.containsKey(this.getClass().getName())){
            cooldowns.put(this.getClass().getName(), Map.of(sender, now.plus(cooldown())));
            return SimpleCommand.super.onCommand(sender, command, label, args);
        }
        Instant cool = cooldowns.get(this.getClass().getName()).get(sender);
        if (cool != null && cool.isAfter(now)) {
            Duration remaining = Duration.between(now, cool);
            sender.sendMessage(msg("cooldown")
                    .replaceText(builder -> builder.match("%time%").replacement(formatDuration(remaining))));
            return true;
        }
        Map<CommandSender, Instant> newMap = cooldowns.get(this.getClass().getName());
        newMap.put(sender, now.plus(cooldown()));
        cooldowns.put(this.getClass().getName(), newMap);
        return SimpleCommand.super.onCommand(sender, command, label, args);
    }

    TemporalAmount cooldown();

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