package lol.aabss.eventcore.hooks;

import lol.aabss.eventcore.EventCore;
import lol.aabss.eventcore.commands.other.Mutechat;
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

    @SuppressWarnings("deprecation")
    @Override
    public @NotNull String getVersion() {
        return EventCore.instance.getDescription().getVersion();
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
            case "alive" -> EventCore.instance.Alive.size() + "";
            case "dead" -> EventCore.instance.Dead.size() + "";
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
            case "mutechat" -> String.valueOf(Mutechat.CHAT_MUTED);
            case "visibility" -> {
                if (player == null) {
                    yield null;
                } else {
                    yield API.getVisibilityState(player).name();
                }
            }
        };
    }
}
