package app.qwertz.eventcore.hooks.skript.conditions;

import app.qwertz.eventcore.EventCore;
import ch.njol.skript.conditions.base.PropertyCondition;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


@Name("Is Recently Dead")
@Description("Returns true if the player is recently dead.")
@Examples({
        "on player use revive:",
        "\tif player is recently dead:",
        "\t\tadd 1 to rev balance of player",
        "\t\tsend \"wait!\""
})
@Since("2.0")
public class CondIsRecentlyDead extends PropertyCondition<Player> {

    static {
        register(CondIsRecentlyDead.class,
                "recent[ly] (dead|killed)",
                "player");
    }

    @Override
    public boolean check(Player player) {
        return EventCore.API.isRecentlyDead(player);
    }

    @Override
    protected @NotNull String getPropertyName() {
        return "recently dead";
    }
}
