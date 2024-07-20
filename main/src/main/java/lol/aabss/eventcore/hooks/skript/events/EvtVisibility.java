package lol.aabss.eventcore.hooks.skript.events;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import aabss.eventcoreapi.VisibilityState;
import lol.aabss.eventcore.events.VisibilityEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class EvtVisibility extends SkriptEvent {

    static{
        Skript.registerEvent("Player Visibility", EvtVisibility.class, VisibilityEvent.class,
                        "[player] [change] visibility",
                "[player] visibility change"
                )
                .description("Called when a player changed their visibility.")
                .examples(
                        "on player visibility change:",
                        "\tbroadcast \"%player% -> %event-visibilitystate%\""
                )
                .since("1.2");
        EventValues.registerEventValue(VisibilityEvent.class, Player.class, new Getter<>() {
            @Override
            public Player get(VisibilityEvent e) {
                return e.getPlayer();
            }
        }, 0);
        EventValues.registerEventValue(VisibilityEvent.class, VisibilityState.class, new Getter<>() {
            @Override
            public VisibilityState get(VisibilityEvent e) {
                return e.getState();
            }
        }, 0);

    }

    @Override
    public boolean init(Literal<?> @NotNull [] args, int matchedPattern, SkriptParser.@NotNull ParseResult parseResult) {
        return true;
    }

    @Override
    public boolean check(@NotNull Event event) {
        return true;
    }

    @Override
    public @NotNull String toString(@Nullable Event e, boolean debug) {
        return "visibility event";
    }
}
