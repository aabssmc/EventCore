package cc.aabss.eventcore.hooks.skript.expressions;

import cc.aabss.eventcore.EventCore;
import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

@Name("All Recent Dead Players")
@Description("Gets all of the recent dead players.")
@Examples({
        "all recent dead players"
})
@Since("1.3")
public class ExprRecentDead extends SimpleExpression<Player> {

    static {
        Skript.registerExpression(ExprDead.class, Player.class, ExpressionType.SIMPLE,
                "[all [[of] the]] recent[ly] (dead|killed) players"
        );
    }

    @Override
    protected Player @NotNull [] get(@NotNull Event e) {
        return EventCore.instance.Recent.toArray(Player[]::new);
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public @NotNull Class<? extends Player> getReturnType() {
        return Player.class;
    }

    @Override
    public @NotNull String toString(@Nullable Event e, boolean debug) {
        return "all recent dead players";
    }

    @Override
    public boolean init(Expression<?> @NotNull [] exprs, int matchedPattern, @NotNull Kleenean isDelayed, SkriptParser.@NotNull ParseResult parseResult) {
        return true;
    }
}
