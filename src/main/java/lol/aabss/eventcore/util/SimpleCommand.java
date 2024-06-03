
//
// im proud of this :))
//

package lol.aabss.eventcore.util;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabExecutor;
import org.bukkit.permissions.Permission;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public interface SimpleCommand extends TabExecutor {

    default void register(){
        register(this.getClass().getSimpleName().toLowerCase());
    }

    default void register(String name){
        PluginCommand cmd = Bukkit.getServer().getPluginCommand(name);
        if (cmd != null){
            cmd.setExecutor(this);
            cmd.setTabCompleter(this);
        }
        if (Bukkit.getPluginManager().getPermission(this.permission()) == null) {
            Bukkit.getPluginManager().addPermission(new Permission(this.permission()));
        }
    }


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
        String arg = args[args.length-1].toLowerCase();
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
        return Component.text("No permission!");
    }

    default String permission(){
        return pluginName().toLowerCase()+".command"+this.getClass().getSimpleName().toLowerCase();
    }

    default String pluginName(){
        try {
            InputStream inputStream = ClassLoader.getSystemResource("plugin.yml").openStream();
            InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(streamReader);
            for (String line; (line = reader.readLine()) != null;) {
                if (line.startsWith("name: ")){
                    return line.replace("name: ", "");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "";
    }

}