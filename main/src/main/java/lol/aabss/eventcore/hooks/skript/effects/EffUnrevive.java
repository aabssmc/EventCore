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
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

import static lol.aabss.eventcore.EventCore.API;

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
                "unrevive %players% [kill:(and|then|and then) kill (him|her|them|it)]"
        );
    }

    private Expression<Player> player;
    private boolean kill;

    @Override
    protected void execute(@NotNull Event e) {
        for (Player p : player.getArray(e)) {
            API.unrevive(p, kill);
        }
    }

    @Override
    public @NotNull String toString(@Nullable Event e, boolean debug) {
        return "unrevive player";
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?> @NotNull [] exprs, int matchedPattern, @NotNull Kleenean isDelayed, SkriptParser.@NotNull ParseResult parseResult) {
        player = (Expression<Player>) exprs[0];
        kill = parseResult.hasTag("kill");
        return true;
    }
}
