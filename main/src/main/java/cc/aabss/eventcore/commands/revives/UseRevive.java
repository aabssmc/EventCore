package cc.aabss.eventcore.commands.revives;

import cc.aabss.eventcore.EventCore;
import cc.aabss.eventcore.events.UseReviveEvent;
import cc.aabss.eventcore.util.Config;
import cc.aabss.eventcore.util.SimpleCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class UseRevive extends SimpleCommand {

    public UseRevive(@NotNull String name, @Nullable String description, @Nullable String... aliases) {
        super(name, description, aliases);
    }

    @Override
    public void run(CommandSender sender, String commandLabel, String[] args) {
        if (sender instanceof ConsoleCommandSender){
            sender.sendMessage(Config.msg("console"));
            return;
        }
        if (!EventCore.instance.getConfig().getBoolean("revives", true)){
            sender.sendMessage(Config.msg("userevive.revivesoff"));
            return;
        }
        if (EventCore.API.getRevives((Player) sender) <= 0){
            sender.sendMessage(Config.msg("userevive.notenough"));
            return;
        }
        int revs = EventCore.API.getRevives((Player) sender);
        EventCore.API.takeRevives((Player) sender, 1);
        Bukkit.broadcast(Config.msg("userevive.request")
                .replaceText(builder -> builder.match("%player%").replacement(sender.getName())));
        Bukkit.getServer().getPluginManager().callEvent(new UseReviveEvent((Player) sender, revs, revs+1));
    }
}
