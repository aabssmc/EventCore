package lol.aabss.eventcore.hooks;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static lol.aabss.eventcore.EventCore.API;

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
            case "alive" -> API.getAlive().size() + "";
            case "dead" -> API.getDead().size() + "";
            case "status" -> {
                if (player == null) {
                    yield null;
                } else {
                    if (API.isDead(player)) {
                        yield "Dead";
                    } else if (API.isAlive(player)) {
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
                    yield String.valueOf(API.getRevives(player));
                }
            }
            default -> params;
        };
    }
}
