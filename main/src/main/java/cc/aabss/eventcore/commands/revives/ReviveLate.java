package cc.aabss.eventcore.commands.revives;

import cc.aabss.eventcore.EventCore;
import cc.aabss.eventcore.util.SimpleCommand;
import cc.aabss.eventcore.util.Config;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.Nullable;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


import static cc.aabss.eventcore.util.Config.msg;

public class ReviveLate extends SimpleCommand {

    public ReviveLate(@NotNull String name, @Nullable String description, @Nullable String... aliases) {
        super(name, description, aliases);
    }

    @Override
    public void run(CommandSender sender, String commandLabel, String[] args) {
        if (sender instanceof ConsoleCommandSender) {
            sender.sendMessage(msg("console"));
            return;
        }
        for (Player p : Bukkit.getOnlinePlayers()){
            if (!EventCore.API.isDead(p)) continue;
            if (Config.get("ignore-perm", Boolean.class) && p.hasPermission("eventcore.reviveall.bypass")) {
                continue;
            }
            EventCore.API.revive(p, (Player) sender, true);
        }
        Bukkit.broadcast(msg("revivelate.revived")
                .replaceText(builder -> builder.match("%player%").replacement(sender.getName())));
    }
}
