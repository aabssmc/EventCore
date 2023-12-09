package lol.aabss.eventcore.hooks.skript.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import lol.aabss.eventcore.EventCore;
import lol.aabss.eventcore.events.ReviveEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.io.Console;

@Name("Revive Player")
@Description("Revives a player.")
@Examples({
        "on player revive:",
        "\trevive player"
})
@Since("1.2")
public class EffRevive extends Effect {

    static{
        Skript.registerEffect(EffRevive.class,
                "revive %player% [and [then] teleport (him|her|them|it) to %-location%]"
        );
    }

    private Expression<Player> player;
    private Expression<Location> loc;

    @Override
    protected void execute(@NotNull Event e) {
        Player p = player.getSingle(e);
        assert p != null;
        EventCore.Alive.remove(p.getName());
        EventCore.Dead.remove(p.getName());
        EventCore.Alive.add(p.getName());
        if (loc != null) {
            Location location = loc.getSingle(e);
            assert location != null;
            p.teleport(location);
        }
        Bukkit.getServer().getPluginManager().callEvent(new ReviveEvent(p, Bukkit.getConsoleSender()));
    }

    @Override
    public @NotNull String toString(@Nullable Event e, boolean debug) {
        return "revive player";
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?> @NotNull [] exprs, int matchedPattern, @NotNull Kleenean isDelayed, SkriptParser.@NotNull ParseResult parseResult) {
        player = (Expression<Player>) exprs[0];
        loc = (Expression<Location>) exprs[1];
        return true;
    }
}
