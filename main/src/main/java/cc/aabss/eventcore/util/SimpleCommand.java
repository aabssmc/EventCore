
//
// im proud of this :))
//

package cc.aabss.eventcore.util;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.jetbrains.annotations.Nullable;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public abstract class SimpleCommand extends Command {

    public SimpleCommand(@NotNull String name, String description, String... aliases) {
        super(name, description, "/" + name, of(aliases));
        this.name = name;
    }

    private static List<String> of(@Nullable String... strings) {
        if (strings == null) {
            return List.of();
        }
        return List.of(strings);
    }

    private final String name;

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (!sender.hasPermission(permission())) {
            sender.sendMessage(permissionMessage());
            return true;
        }
        run(sender, commandLabel, args);
        return true;
    }

    public void register(){
        Bukkit.getCommandMap().register(name, "eventcore", this);
        if (Bukkit.getPluginManager().getPermission(this.permission()) == null) {
            Bukkit.getPluginManager().addPermission(new Permission(this.permission()));
        }
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args, @Nullable Location location) throws IllegalArgumentException {
        final List<String> completions = new ArrayList<>();
        List<String> tabs = tabComplete(sender, alias, args);
        if (args.length == 0 || tabs.isEmpty()) {
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


    protected abstract void run(CommandSender sender, String commandLabel, String[] args);

    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, String[] args){
        return List.of();
    }

    public Component permissionMessage() {
        return Component.text("No permission!");
    }

    public String permission(){
        return "eventcore.command."+this.getClass().getSimpleName().toLowerCase();
    }

}