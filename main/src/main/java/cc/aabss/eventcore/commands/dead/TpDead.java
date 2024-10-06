package cc.aabss.eventcore.commands.dead;

import cc.aabss.eventcore.EventCore;
import cc.aabss.eventcore.util.Config;
import cc.aabss.eventcore.util.SimpleCommand;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.Nullable;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TpDead extends SimpleCommand {

    public TpDead(@NotNull String name, @Nullable String description, @Nullable String... aliases) {
        super(name, description, aliases);
    }

    @Override
    public void run(CommandSender sender, String commandLabel, String[] args) {
        if (!(sender instanceof Player p)) {
            sender.sendMessage(Config.msg("console"));
            return;
        }
        for (Player player: EventCore.instance.Dead) {
            player.teleport(p.getLocation());
        }
        Bukkit.broadcast(Config.msg("tpdead.teleport-broadcast")
                .replaceText(builder -> builder.matchLiteral("%sender%").replacement(sender.getName())));
    }
}
