package lol.aabss.eventcore.hooks.skript.events;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import lol.aabss.eventcore.events.UseReviveEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
@Name("On Player Use Revive")
@Description("Called when a player requests to be revived.")
@Examples({
        "on player use revive:",
        "\tbroadcast \"ok\""
})
@Since("1.2")
public class EvtUseRevive extends SkriptEvent {

    static{
        Skript.registerEvent("on revive", EvtUseRevive.class, UseReviveEvent.class,
                "[player] (use|request) revive",
                "[player] request[ed] to be revive[d]"
        );
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
