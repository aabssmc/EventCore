package lol.aabss.eventcore.commands;

import lol.aabss.eventcore.events.VisibilityEvent;
import lol.aabss.eventcore.util.SimpleCooldownCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static lol.aabss.eventcore.EventCore.API;
import static lol.aabss.eventcore.util.Config.msg;

public class Visibility implements SimpleCooldownCommand {

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
                        p.sendMessage(msg("visibility.allalreadyhidden"));
                        return true;
                    }
                    API.setVisibilityState(p, VisibilityEvent.VisibilityState.ALL);
                    p.sendMessage(msg("visibility.allhidden"));
                    return true;
                }
            }
            case "staff" -> {
                if (p.hasPermission("eventcore.visibility.staff")) {
                    if (VisStaff.contains(p)) {
                        p.sendMessage(msg("visibility.staffalreadyhidden"));
                        return true;
                    }
                    API.setVisibilityState(p, VisibilityEvent.VisibilityState.STAFF);
                    p.sendMessage(msg("visibility.staffhidden"));
                    return true;
                }
            }
            case "off" -> {
                if (p.hasPermission("eventcore.visibility.off")) {
                    if (!VisStaff.contains(p) && !VisAll.contains(p)) {
                        p.sendMessage(msg("visibility.visibilityalreadyoff"));
                        return true;
                    }
                    API.setVisibilityState(p, VisibilityEvent.VisibilityState.OFF);
                    p.sendMessage(msg("visibility.visibilityoff"));
                    return true;
                }
            }
            default -> p.sendMessage(msg("<red>/visibility <all | staff | off>"));
        }
        p.sendMessage(msg("permission-message"));
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

    @Override
    public Duration cooldown() {
        return Duration.ofSeconds(3);
    }
}
