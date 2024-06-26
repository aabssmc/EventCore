package lol.aabss.eventcore.util;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import static lol.aabss.eventcore.util.Config.msg;

public interface SimpleCooldownCommand extends SimpleCommand {
    Map<CommandSender, Instant> cooldowns = new HashMap<>();

    @Override
    default boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Instant now = Instant.now();
        Instant cool = cooldowns.get(sender);
        if (cool != null && cool.isAfter(now)) {
            Duration remaining = Duration.between(now, cool);
            sender.sendMessage(msg("cooldown")
                    .replaceText(builder -> builder.match("%time%").replacement(formatDuration(remaining))));
            return true;
        }
        cooldowns.put(sender, now.plus(cooldown()));
        return SimpleCommand.super.onCommand(sender, command, label, args);
    }

    Duration cooldown();

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