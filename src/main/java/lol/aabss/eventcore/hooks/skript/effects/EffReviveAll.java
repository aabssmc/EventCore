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
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

import static lol.aabss.eventcore.EventCore.API;

@Name("Revive All Players")
@Description("Revives all players.")
@Examples({
        "revive everyone"
})
@Since("1.3")
public class EffReviveAll extends Effect {

    static{
        Skript.registerEffect(EffReviveAll.class,
                "revive (everyone|all players) [and [then] teleport (them|the players) to %-location%]"
        );
    }

    private Expression<Location> loc;

    @Override
    protected void execute(@NotNull Event e) {
        for (Player p : Bukkit.getOnlinePlayers()){
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
        return "revive all players";
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?> @NotNull [] exprs, int matchedPattern, @NotNull Kleenean isDelayed, SkriptParser.@NotNull ParseResult parseResult) {
        loc = (Expression<Location>) exprs[0];
        return true;
    }
}
