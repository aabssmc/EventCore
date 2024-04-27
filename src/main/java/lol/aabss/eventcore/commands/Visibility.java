package lol.aabss.eventcore.commands;

import lol.aabss.eventcore.util.Config;
import lol.aabss.eventcore.EventCore;
import lol.aabss.eventcore.util.SimpleCommand;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import static lol.aabss.eventcore.EventCore.instance;
import static lol.aabss.eventcore.util.Config.msg;
import static net.kyori.adventure.text.minimessage.MiniMessage.miniMessage;

public class Visibility implements SimpleCommand {

    public static ArrayList<Player> VisAll = new ArrayList<>();
    public static ArrayList<Player> VisStaff = new ArrayList<>();

    @Override
    public boolean run(CommandSender sender, Command command, String[] args) {
        if (!(sender instanceof Player p)){
            sender.sendMessage(msg("console"));
            return true;
        }
        if (args.length == 0){
            Config.sendMessagePrefix(p, " <red>/visibility <all | staff | off>");
            return true;
        }
        switch (args[0]) {
            case "all":
                if (p.hasPermission("eventcore.visibility.all")) {
                    if (VisAll.contains(p)) {
                        sender.sendMessage(msg("visibility.allalreadyhidden"));
                    } else {
                        for (Player player : Bukkit.getOnlinePlayers()) {
                            p.hidePlayer(instance, player);
                        }
                        VisAll.add(p);
                        VisStaff.remove(p);
                        sender.sendMessage(msg("visibility.allhidden"));
                    }
                } else {
                    sender.sendMessage(msg("permission-message"));
                }
            case "staff":
                if (p.hasPermission("eventcore.visibility.staff")) {
                    if (VisStaff.contains(p)) {
                        sender.sendMessage(msg("visibility.staffalreadyhidden"));
                    } else {
                        for (Player player : Bukkit.getOnlinePlayers()) {
                            if (!player.hasPermission("eventcore.visibility.staffbypass")) {
                                p.hidePlayer(instance, player);
                            }
                        }
                        VisAll.remove(p);
                        VisStaff.add(p);
                        sender.sendMessage(msg("visibility.staffhidden"));
                    }
                } else {
                    sender.sendMessage(msg("permission-message"));
                }
            case "off":
                if (p.hasPermission("eventcore.visibility.off")) {
                    if (VisStaff.contains(p) || VisAll.contains(p)) {
                        for (Player player : Bukkit.getOnlinePlayers()) {
                            p.showPlayer(instance, player);
                        }
                        VisAll.remove(p);
                        VisStaff.remove(p);
                        sender.sendMessage(msg("visibility.visibilityoff"));
                    } else {
                        sender.sendMessage(msg("visibility.visibilityalreadyoff"));
                    }
                } else {
                    sender.sendMessage(msg("permission-message"));
                }
            default:
                Config.sendMessagePrefix(p, " <red>/visibility <all | staff | off>");
        }
        return true;
    }

    @Override
    public String permission() {
        return "eventcore.visibility.use";
    }

    @Override
    public List<String> tabComplete(CommandSender sender, Command command, String[] args) {
        return List.of("all", "staff", "off");
    }

}
