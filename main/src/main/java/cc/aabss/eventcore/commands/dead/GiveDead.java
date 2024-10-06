package cc.aabss.eventcore.commands.dead;

import cc.aabss.eventcore.EventCore;
import cc.aabss.eventcore.util.Config;
import cc.aabss.eventcore.util.SimpleCommand;
import org.bukkit.Material;
import org.jetbrains.annotations.Nullable;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class GiveDead extends SimpleCommand {

    public GiveDead(@NotNull String name, @Nullable String description, @Nullable String... aliases) {
        super(name, description, aliases);
    }

    @Override
    public void run(CommandSender sender, String commandLabel, String[] args) {
        if (args.length == 0){
            sender.sendMessage(Config.msg("givedead.no-item"));
            return;
        }
        Material mat = Material.matchMaterial(args[0].toUpperCase());
        if (args.length == 1){
            if (mat == null){
                sender.sendMessage(Config.msg("givedead.invalid-item"));
                return;
            }
            for (Player p : EventCore.instance.Dead){
                ItemStack item = new ItemStack(mat, 64);
                p.getInventory().addItem(item);
            }
            sender.sendMessage(Config.msg("givedead.gave-64")
                    .replaceText(builder -> builder.matchLiteral("%item%").replacement(mat.name())));
            return;
        }
        Integer amount = Integer.parseInt(args[1]);
        if (mat == null){
            sender.sendMessage(Config.msg("givedead.invalid-item"));
            return;
        }
        for (Player p : EventCore.instance.Dead){
            ItemStack item = new ItemStack(mat, Integer.parseInt(args[1]));
            p.getInventory().addItem(item);
        }
        sender.sendMessage(Config.msg("givedead.gave")
                .replaceText(builder -> builder.matchLiteral("%item%").replacement(mat.name()))
                .replaceText(builder -> builder.matchLiteral("%amount%").replacement(String.valueOf(amount))));
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, String[] args) {
        if (args.length == 1) {
            final List<String> completions = new ArrayList<>();
            for (Material p : Material.values()) {
                completions.add(p.name().toLowerCase());
            }
            return completions;
        }
        return List.of();
    }
}
