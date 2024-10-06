package cc.aabss.eventcore.commands.revives;

import cc.aabss.eventcore.EventCore;
import cc.aabss.eventcore.util.Config;
import cc.aabss.eventcore.util.SimpleCommand;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.Nullable;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ReviveAll extends SimpleCommand {

    public ReviveAll(@NotNull String name, @Nullable String description, @Nullable String... aliases) {
        super(name, description, aliases);
    }

    @Override
    public void run(CommandSender sender, String commandLabel, String[] args) {
        if (sender instanceof ConsoleCommandSender) {
            sender.sendMessage(Config.msg("console"));
            return;
        }
        for (Player list : Bukkit.getOnlinePlayers()) {
            if (Config.get("ignore-perm", Boolean.class) && list.hasPermission("eventcore.reviveall.bypass")) {
                continue;
            }
            EventCore.API.revive(list, (Player) sender, true);
        }
        Bukkit.broadcast(Config.msg("reviveall.revived")
                .replaceText(builder -> builder.match("%player%").replacement(sender.getName())));
    }
}
