package lol.aabss.eventcore.hooks.skript.conditions;

import ch.njol.skript.conditions.base.PropertyCondition;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static lol.aabss.eventcore.EventCore.Dead;

@Name("Is Dead")
@Description("Returns true if the player is alive.")
@Examples({
        "on player use revive:",
        "\tif player is not dead:",
        "\t\tadd 1 to rev balance of player"
})
@Since("2.0")
public class CondIsDead extends PropertyCondition<Player> {

    static {
        register(CondIsDead.class,
                "event dead",
                "player");
    }

    @Override
    public boolean check(Player player) {
        return Dead.contains(player);
    }

    @Override
    protected @NotNull String getPropertyName() {
        return "event dead";
    }
}
