package cc.aabss.eventcore.commands.revives;

import cc.aabss.eventcore.EventCore;
import cc.aabss.eventcore.util.SimplePlayerCommand;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.Nullable;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static cc.aabss.eventcore.util.Config.msg;

public class ReviveLate extends SimplePlayerCommand {

    public ReviveLate(@NotNull String name, @Nullable String description, @Nullable String... aliases) {
        super(name, description, aliases);
    }

    @Override
    protected LiteralArgumentBuilder<CommandSourceStack> run(LiteralArgumentBuilder<CommandSourceStack> argumentBuilder) {
        return argumentBuilder
                .executes(context -> {
                    CommandSender sender = context.getSource().getSender();
                    for (Player p : Bukkit.getOnlinePlayers()){
                        if (!EventCore.API.isDead(p)) continue;
                        EventCore.API.revive(p, (Player) sender, true);
                    }
                    Bukkit.broadcast(msg("revivelate.revived")
                            .replaceText(builder -> builder.match("%player%").replacement(sender.getName())));
                    return 1;
                });
    }
}
