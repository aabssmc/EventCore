package cc.aabss.eventcore.commands.revives;

import cc.aabss.eventcore.EventCore;
import cc.aabss.eventcore.util.Config;
import cc.aabss.eventcore.util.SimpleCommand;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class RecentRev extends SimpleCommand {

    public RecentRev(@NotNull String name, @Nullable String description, @Nullable String... aliases) {
        super(name, description, aliases);
    }

    @Override
    public void run(CommandSender sender, String commandLabel, String[] args) {
        if (sender instanceof ConsoleCommandSender) {
            sender.sendMessage(Config.msg("console"));
            return;
        }
        List<Player> recentlyDead = new ArrayList<>(EventCore.instance.Recent);
        for (Player p : recentlyDead) {
            EventCore.API.revive(p, ((Player) sender), true);
        }
        Bukkit.broadcast(Config.msg("recentrev.revived")
                .replaceText(builder -> builder.match("%time%").replacement(Config.get("recent-rev-time", Integer.class).toString())));
    }

}
