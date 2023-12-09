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

@Name("Revive All Players")
@Description("Revives all players.")
@Examples({
        "revive everyone"
})
@Since("1.3")
public class EffReviveRecent extends Effect {

    static{
        Skript.registerEffect(EffReviveRecent.class,
                "revive [all [[of] the]] recent[ly] (dead|killed) players [and [then] teleport (him|her|them|it) to %-location%]"
        );
    }

    private Expression<Location> loc;

    @Override
    protected void execute(@NotNull Event e) {
        for (String player : EventCore.Recent){
            Player p = Bukkit.getPlayer(player);
            if (p != null){
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
        }
        EventCore.Recent.clear();
    }

    @Override
    public @NotNull String toString(@Nullable Event e, boolean debug) {
        return "revive all recently dead players";
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?> @NotNull [] exprs, int matchedPattern, @NotNull Kleenean isDelayed, SkriptParser.@NotNull ParseResult parseResult) {
        loc = (Expression<Location>) exprs[0];
        return true;
    }
}
