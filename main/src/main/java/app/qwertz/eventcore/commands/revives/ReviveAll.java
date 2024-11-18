package app.qwertz.eventcore.commands.revives;

import app.qwertz.eventcore.EventCore;
import app.qwertz.eventcore.util.Config;
import app.qwertz.eventcore.util.SimplePlayerCommand;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.Nullable;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ReviveAll extends SimplePlayerCommand {

    public ReviveAll(@NotNull String name, @Nullable String description, @Nullable String... aliases) {
        super(name, description, aliases);
    }

    @Override
    protected LiteralArgumentBuilder<CommandSourceStack> run(LiteralArgumentBuilder<CommandSourceStack> argumentBuilder) {
        return argumentBuilder
                .executes(context -> {
                    CommandSender sender = context.getSource().getSender();
                    for (Player list : Bukkit.getOnlinePlayers()) {
                        if (Config.get("ignore-perm", Boolean.class) && list.hasPermission("eventcore.reviveall.bypass")) {
                            continue;
                        }
                        EventCore.API.revive(list, (Player) sender, true);
                    }
                    Bukkit.broadcast(Config.msg("reviveall.revived")
                            .replaceText(builder -> builder.match("%player%").replacement(sender.getName())));
                    return 1;
                });
    }

}
