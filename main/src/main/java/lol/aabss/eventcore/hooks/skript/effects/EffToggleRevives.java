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
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static lol.aabss.eventcore.EventCore.API;


@Name("Toggle Revives")
@Description("Toggles the revives.")
@Examples({
        "toggle revives"
})
@Since("2.0")
public class EffToggleRevives extends Effect {

    static {
        Skript.registerEffect(EffToggleRevives.class, "toggle [the] revive(s| tokens)");
    }

    @Override
    protected void execute(@NotNull Event e) {
        API.toggleRevives();
    }

    @Override
    public @NotNull String toString(@Nullable Event e, boolean debug) {
        return "toggle revives";
    }

    @Override
    public boolean init(Expression<?> @NotNull [] exprs, int matchedPattern, @NotNull Kleenean isDelayed, SkriptParser.@NotNull ParseResult parseResult) {
        return true;
    }
}
