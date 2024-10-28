package cc.aabss.eventcore.commands.revives;

import cc.aabss.eventcore.EventCore;
import cc.aabss.eventcore.events.UseReviveEvent;
import cc.aabss.eventcore.util.Config;
import cc.aabss.eventcore.util.SimpleCommand;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class UseRevive extends SimpleCommand {

    public UseRevive(@NotNull String name, @Nullable String description, @Nullable String... aliases) {
        super(name, description, aliases);
    }

    @Override
    protected LiteralArgumentBuilder<CommandSourceStack> run(LiteralArgumentBuilder<CommandSourceStack> argumentBuilder) {
        return argumentBuilder
                .requires(commandSourceStack -> commandSourceStack.getSender() instanceof Player)
                .executes(context -> {
                    CommandSender sender = context.getSource().getSender();
                    if (!EventCore.instance.getConfig().getBoolean("revives", true)){
                        sender.sendMessage(Config.msg("userevive.revivesoff"));
                        return 0;
                    }
                    if (EventCore.API.getRevives((Player) sender) <= 0){
                        sender.sendMessage(Config.msg("userevive.notenough"));
                        return 0;
                    }
                    int revs = EventCore.API.getRevives((Player) sender);
                    EventCore.API.takeRevives((Player) sender, 1);
                    Bukkit.broadcast(Config.msg("userevive.request")
                            .replaceText(builder -> builder.match("%player%").replacement(sender.getName())));
                    Bukkit.getServer().getPluginManager().callEvent(new UseReviveEvent((Player) sender, revs, revs+1));
                    return 1;
                });
    }

}
