package app.qwertz.eventcore.hooks.skript.effects;

import app.qwertz.eventcore.EventCore;
import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

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
        for (Player p : EventCore.instance.Recent){
            if (loc != null) {
                Location location = loc.getSingle(e);
                if (location != null) {
                    EventCore.API.revive(p, location);
                }
            } else{
                EventCore.API.revive(p);
            }
        }
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
