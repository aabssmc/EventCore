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
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

import static lol.aabss.eventcore.EventCore.API;

@Name("Revive Player")
@Description("Revives a player.")
@Examples({
        "on player use revive:",
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
        for (Player p : player.getArray(e)) {
            if (loc != null) {
                Location location = loc.getSingle(e);
                if (location != null) {
                    API.revive(p, location);
                }
            } else{
                API.revive(p);
            }
        }
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
