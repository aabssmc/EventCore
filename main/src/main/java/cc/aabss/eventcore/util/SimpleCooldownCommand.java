package cc.aabss.eventcore.util;

import cc.aabss.eventcore.EventCore;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.TemporalAmount;
import java.util.HashMap;
import java.util.Map;

public abstract class SimpleCooldownCommand extends SimpleCommand {

    protected SimpleCooldownCommand(@NotNull String name, @Nullable String description, @Nullable String... aliases) {
        super(name, description, aliases);
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        Instant now = Instant.now();
        if (!EventCore.cooldowns.containsKey(this.getClass().getName())){
            Map<CommandSender, Instant> senderCooldownMap = new HashMap<>();
            senderCooldownMap.put(sender, now.plus(cooldown()));
            EventCore.cooldowns.put(this.getClass().getName(), senderCooldownMap);
            return super.execute(sender, commandLabel, args);
        }
        Instant cool = EventCore.cooldowns.get(this.getClass().getName()).get(sender);
        if (cool != null && cool.isAfter(now)) {
            Duration remaining = Duration.between(now, cool);
            sender.sendMessage(Config.msg("cooldown")
                    .replaceText(builder -> builder.match("%time%").replacement(formatDuration(remaining))));
            return true;
        }
        Map<CommandSender, Instant> newMap = EventCore.cooldowns.get(this.getClass().getName());
        newMap.put(sender, now.plus(cooldown()));
        EventCore.cooldowns.put(this.getClass().getName(), newMap);
        return super.execute(sender, commandLabel, args);
    }

    protected abstract TemporalAmount cooldown();

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