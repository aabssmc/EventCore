package lol.aabss.eventcore.hooks.skript.events;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import lol.aabss.eventcore.events.ReviveEvent;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class EvtRevive extends SkriptEvent {

    static{
        Skript.registerEvent("Player Revive", EvtRevive.class, ReviveEvent.class,
                        "[player] revive[d]"
                )
                .description("Called when a player gets revived.")
                .examples(
                        "on player revive:",
                        "\tif name of player is \"Oiiink\":",
                        "\t\tkill player"
                )
                .since("1.2");
        EventValues.registerEventValue(ReviveEvent.class, Player.class, new Getter<>() {
            @Override
            public Player get(ReviveEvent e) {
                return e.getRevived();
            }
        }, 0);
        EventValues.registerEventValue(ReviveEvent.class, CommandSender.class, new Getter<>() {
            @Override
            public CommandSender get(ReviveEvent e) {
                return e.getReviver();
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
        return "revive event";
    }
}
