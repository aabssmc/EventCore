package lol.aabss.eventcore.commands;

import lol.aabss.eventcore.util.SimpleCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

import static lol.aabss.eventcore.EventCore.instance;
import static lol.aabss.eventcore.util.Config.msg;

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
            p.sendMessage(msg("<red>/visibility <all | staff | off>"));
            return true;
        }
        switch (args[0]) {
            case "all" -> {
                if (p.hasPermission("eventcore.visibility.all")) {
                    if (VisAll.contains(p)) {
                        sender.sendMessage(msg("visibility.allalreadyhidden"));
                        return true;
                    }
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        p.hidePlayer(instance, player);
                    }
                    VisAll.add(p);
                    VisStaff.remove(p);
                    sender.sendMessage(msg("visibility.allhidden"));
                    return true;
                }
                sender.sendMessage(msg("permission-message"));
                return true;
            }
            case "staff" -> {
                if (p.hasPermission("eventcore.visibility.staff")) {
                    if (VisStaff.contains(p)) {
                        sender.sendMessage(msg("visibility.staffalreadyhidden"));
                        return true;
                    }
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        if (!player.hasPermission("eventcore.visibility.staffbypass")) {
                            p.hidePlayer(instance, player);
                        }
                    }
                    VisStaff.add(p);
                    VisAll.remove(p);
                    sender.sendMessage(msg("visibility.staffhidden"));
                    return true;
                }
                sender.sendMessage(msg("permission-message"));
                return true;
            }
            case "off" -> {
                if (!p.hasPermission("eventcore.visibility.off")) {
                    sender.sendMessage(msg("permission-message"));
                    return true;
                }
                if (!VisStaff.contains(p) && !VisAll.contains(p)) {
                    sender.sendMessage(msg("visibility.visibilityalreadyoff"));
                    return true;
                }
                for (Player player : Bukkit.getOnlinePlayers()) {
                    p.showPlayer(instance, player);
                }
                VisAll.remove(p);
                VisStaff.remove(p);
                sender.sendMessage(msg("visibility.visibilityoff"));
                return true;
            }
            default -> p.sendMessage(msg("<red>/visibility <all | staff | off>"));
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
