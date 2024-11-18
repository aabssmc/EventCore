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
import org.jetbrains.annotations.Nullable;

@Name("Revive Late Players")
@Description("Revives all the dead players.")
@Examples({
        "revive late players and teleport them to player",
        "revive all late players"
})
@Since("2.2")
public class EffReviveLate extends Effect {

    static{
        Skript.registerEffect(EffRevive.class,
                "revive [all] late players [and [then] teleport [all [of]] them to %-location%]"
        );
    }

    private Expression<Location> loc;

    @Override
    protected void execute(@NotNull Event event) {
        for (Player p : EventCore.instance.Dead){
            if (loc == null){
                EventCore.API.revive(p);
            } else{
                Location loc = this.loc.getSingle(event);
                if (loc != null){
                    EventCore.API.revive(p, loc);
                }
            }
        }
    }

    @Override
    public @NotNull String toString(@Nullable Event event, boolean b) {
        return "revive late";
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, @NotNull Kleenean kleenean, SkriptParser.@NotNull ParseResult parseResult) {
        loc = (Expression<Location>) expressions[0];
        return true;
    }
}
