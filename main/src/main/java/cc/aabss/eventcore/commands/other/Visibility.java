package cc.aabss.eventcore.commands.other;

import cc.aabss.eventcore.api.VisibilityState;
import cc.aabss.eventcore.util.SimpleCooldownCommand;
import org.jetbrains.annotations.Nullable;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.time.temporal.TemporalAmount;
import java.util.ArrayList;
import java.util.List;

import static cc.aabss.eventcore.EventCore.API;
import static cc.aabss.eventcore.util.Config.msg;

public class Visibility extends SimpleCooldownCommand {

    public static ArrayList<Player> VisAll = new ArrayList<>();
    public static ArrayList<Player> VisStaff = new ArrayList<>();

    public Visibility(@NotNull String name, @Nullable String description, @Nullable String... aliases) {
        super(name, description, aliases);
    }

    @Override
    public void run(CommandSender sender, String commandLabel, String[] args) {
        if (!(sender instanceof Player p)){
            sender.sendMessage(msg("console"));
            return;
        }
        if (args.length == 0){
            p.sendMessage(msg("<red>/visibility <all | staff | off>"));
            return;
        }
        switch (args[0]) {
            case "all" -> {
                if (p.hasPermission("eventcore.visibility.all")) {
                    if (VisAll.contains(p)) {
                        p.sendMessage(msg("visibility.allalreadyhidden"));
                        return;
                    }
                    API.setVisibilityState(p, VisibilityState.ALL);
                    p.sendMessage(msg("visibility.allhidden"));
                    return;
                }
            }
            case "staff" -> {
                if (p.hasPermission("eventcore.visibility.staff")) {
                    if (VisStaff.contains(p)) {
                        p.sendMessage(msg("visibility.staffalreadyhidden"));
                        return;
                    }
                    API.setVisibilityState(p, VisibilityState.STAFF);
                    p.sendMessage(msg("visibility.staffhidden"));
                    return;
                }
            }
            case "off" -> {
                if (p.hasPermission("eventcore.visibility.off")) {
                    if (!VisStaff.contains(p) && !VisAll.contains(p)) {
                        p.sendMessage(msg("visibility.visibilityalreadyoff"));
                        return;
                    }
                    API.setVisibilityState(p, VisibilityState.OFF);
                    p.sendMessage(msg("visibility.visibilityoff"));
                    return;
                }
            }
            default -> p.sendMessage(msg("<red>/visibility <all | staff | off>"));
        }
        p.sendMessage(msg("permission-message"));
    }

    @Override
    public String permission() {
        return "eventcore.visibility.use";
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, String[] args) {
        return List.of("all", "staff", "off");
    }

    @Override
    public TemporalAmount cooldown() {
        return Duration.ofSeconds(5);
    }
}
