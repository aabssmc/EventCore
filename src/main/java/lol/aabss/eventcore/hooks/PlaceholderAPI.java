package lol.aabss.eventcore.hooks;

import lol.aabss.eventcore.util.Config;
import lol.aabss.eventcore.EventCore;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class PlaceholderAPI extends PlaceholderExpansion {


    @Override
    public @NotNull String getIdentifier() {
        return "eventcore";
    }

    @Override
    public @NotNull String getAuthor() {
        return "aabss";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.1";
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public @Nullable String onPlaceholderRequest(Player player, @NotNull String params) {
        return switch (params) {
            case "alive" -> EventCore.Alive.size() + "";
            case "dead" -> EventCore.Dead.size() + "";
            case "status" -> {
                if (player == null) {
                    yield null;
                } else {
                    if (EventCore.Dead.contains(player)) {
                        yield "Dead";
                    } else if (EventCore.Alive.contains(player)) {
                        yield "Alive";
                    } else {
                        yield null;
                    }
                }
            }
            case "revive", "revives" -> {
                if (player == null) {
                    yield null;
                } else {
                    yield Config.getRevives(player).toString();
                }
            }
            default -> params;
        };
    }
}
