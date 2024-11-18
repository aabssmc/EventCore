package app.qwertz.eventcore.commands.revives;

import app.qwertz.eventcore.EventCore;
import app.qwertz.eventcore.util.Config;
import app.qwertz.eventcore.util.SimplePlayerCommand;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.entity.Player;

import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class RecentRev extends SimplePlayerCommand {

    public RecentRev(@NotNull String name, @Nullable String description, @Nullable String... aliases) {
        super(name, description, aliases);
    }

    @Override
    protected LiteralArgumentBuilder<CommandSourceStack> run(LiteralArgumentBuilder<CommandSourceStack> argumentBuilder) {
        return argumentBuilder
                .executes(context -> {
                    List<Player> recentlyDead = new ArrayList<>(EventCore.instance.Recent);
                    for (Player p : recentlyDead) {
                        EventCore.API.revive(p, ((Player) context.getSource().getSender()), true);
                    }
                    Bukkit.broadcast(Config.msg("recentrev.revived")
                            .replaceText(builder -> builder.match("%time%").replacement(Config.get("recent-rev-time", Integer.class).toString())));
                    return 1;
                });
    }

}
