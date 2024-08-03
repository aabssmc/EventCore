package cc.aabss.eventcore.hooks.skript.events;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import cc.aabss.eventcore.events.UseReviveEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class EvtUseRevive extends SkriptEvent {

    static{
        Skript.registerEvent("on revive", EvtUseRevive.class, UseReviveEvent.class,
                        "[player] (use|request) revive",
                        "[player] request[ed] to be revive[d]"
                )
                .description("Called when a player requests to be revived.")
                .examples(
                        "on player use revive:",
                        "\tbroadcast \"ok\""
                )
                .since("1.2");
        EventValues.registerEventValue(UseReviveEvent.class, Player.class, new Getter<>() {
            @Override
            public Player get(UseReviveEvent e) {
                return e.getRevived();
            }
        }, 0);
        EventValues.registerEventValue(UseReviveEvent.class, Integer.class, new Getter<>() {
            @Override
            public Integer get(UseReviveEvent e) {
                return e.getReviveAmount();
            }
        }, 0);
        EventValues.registerEventValue(UseReviveEvent.class, Integer.class, new Getter<>() {
            @Override
            public Integer get(UseReviveEvent e) {
                return e.getReviveAmount();
            }
        }, 1);
        EventValues.registerEventValue(UseReviveEvent.class, Integer.class, new Getter<>() {
            @Override
            public Integer get(UseReviveEvent e) {
                return e.getOldReviveAmount();
            }
        }, -1);
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
        return "revive event";
    }
}
