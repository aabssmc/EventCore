package lol.aabss.eventcore.hooks.skript.expressions;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.PropertyExpression;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import lol.aabss.eventcore.util.Config;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

@Name("Revive Balance")
@Description("The revive balance of a player.")
@Examples({
        "set player's revives to 100"
})
@Since("1.2")

public class ExprRevives extends PropertyExpression<Player, Integer> {

    static{
        register(ExprRevives.class, Integer.class, "revive(s| bal[ance])", "players");
    }

    @Override
    protected Integer @NotNull [] get(@NotNull Event event, Player @NotNull [] source) {
        List<Integer> revives = new ArrayList<>();
        for (Player p : getExpr().getArray(event)) {
            revives.add(Config.getRevives(p));
        }
        return revives.toArray(Integer[]::new);
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public @NotNull Class<? extends Integer> getReturnType() {
        return Integer.class;
    }

    @Override
    public @NotNull String toString(@Nullable Event e, boolean debug) {
        return "revive balance of player";
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?> @NotNull [] exprs, int matchedPattern, @NotNull Kleenean isDelayed, SkriptParser.@NotNull ParseResult parseResult) {
        setExpr((Expression<? extends Player>) exprs[0]);
        return true;
    }

    @Override
    public void change(@NotNull Event e, Object @NotNull [] delta, Changer.@NotNull ChangeMode mode){
        for (Player p : getExpr().getArray(e)) {
            if (mode == Changer.ChangeMode.SET) {
                Config.setRevives(p, (Integer) delta[0]);
            } else if (mode == Changer.ChangeMode.REMOVE) {
                Config.setRevives(p, Config.getRevives(p)-(Integer) delta[0]);
            } else if (mode == Changer.ChangeMode.ADD) {
                Config.setRevives(p, Config.getRevives(p)+(Integer) delta[0]);
            } else if (mode == Changer.ChangeMode.RESET) {
                Config.setRevives(p, 0);
            } else {
                assert false;
            }
        }
    }

    @Override
    public Class<?> @NotNull [] acceptChange(final Changer.@NotNull ChangeMode mode) {
        if (
                mode == Changer.ChangeMode.SET ||
                mode == Changer.ChangeMode.REMOVE ||
                mode == Changer.ChangeMode.ADD ||
                mode == Changer.ChangeMode.RESET
        ) {
            return CollectionUtils.array(Integer.class);
        }
        return CollectionUtils.array();
    }

}
