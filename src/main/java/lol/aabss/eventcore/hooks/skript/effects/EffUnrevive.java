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
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

@Name("Unrevive Player")
@Description("Unrevives a player.")
@Examples({
        "on player revive:",
        "\tif name of player is \"Oiiink\":",
        "\t\tunrevive player"
})
@Since("1.2")
public class EffUnrevive extends Effect {

    static{
        Skript.registerEffect(EffUnrevive.class,
                "unrevive %player% [kill:(and|then|and then) kill (him|her|them|it)]"
        );
    }

    private Expression<Player> player;
    private boolean kill;

    @Override
    protected void execute(@NotNull Event e) {
        Player p = player.getSingle(e);
        assert p != null;
        EventCore.Alive.remove(p.getName());
        EventCore.Dead.remove(p.getName());
        EventCore.Dead.add(p.getName());
        if (kill){
            p.setHealth(0);
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
        kill = parseResult.hasTag("kill");
        return true;
    }
}
