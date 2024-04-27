
//
// im proud of this :))
//

package lol.aabss.eventcore.util;

import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import static lol.aabss.eventcore.util.Config.msg;

public interface SimpleCommand extends TabExecutor {

    @Override
    default boolean onCommand(@NotNull CommandSender sender, org.bukkit.command.@NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission(permission())) {
            sender.sendMessage(permissionMessage());
            return true;
        }
        return run(sender, command, args);
    }

    @Override
    @Nullable
    default List<String> onTabComplete(@NotNull CommandSender sender, org.bukkit.command.@NotNull Command command, @NotNull String label, @NotNull String[] args) {
        final List<String> completions = new ArrayList<>();
        List<String> tabs = tabComplete(sender, command, args);
        if (args.length == 0 || tabs == null || tabs.isEmpty()) {
            return completions;
        }
        String arg = args[args.length - 1].toLowerCase();
        for (String s : tabs) {
            if (s.toLowerCase().startsWith(arg)) {
                completions.add(s);
            }
        }
        return completions;
    }


    boolean run(CommandSender sender, org.bukkit.command.Command command, String[] args);

    default List<String> tabComplete(CommandSender sender, org.bukkit.command.Command command, String[] args){
        return List.of();
    }

    default Component permissionMessage() {
        return msg("permission-message");
    }

    default Component prefix(){
        return Config.getComponent("prefix");
    }

    default String permission(){
        return "eventcore.command."+this.getClass().getSimpleName().toLowerCase();
    }

}