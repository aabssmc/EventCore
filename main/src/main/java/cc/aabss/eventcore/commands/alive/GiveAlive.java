package cc.aabss.eventcore.commands.alive;

import cc.aabss.eventcore.EventCore;
import cc.aabss.eventcore.util.Config;
import cc.aabss.eventcore.util.SimpleCommand;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class GiveAlive implements SimpleCommand {

    @Override
    public void run(CommandSender sender, Command command, String[] args) {
        if (args.length == 0){
            sender.sendMessage(Config.msg("givealive.no-item"));
            return;
        }
        Material mat = Material.matchMaterial(args[0].toUpperCase());
        if (args.length == 1){
            if (mat == null){
                sender.sendMessage(Config.msg("givealive.invalid-item"));
                return;
            }
            for (Player p : EventCore.instance.Alive){
                ItemStack item = new ItemStack(mat, 64);
                p.getInventory().addItem(item);
            }
            sender.sendMessage(Config.msg("givealive.gave-64")
                    .replaceText(builder -> builder.matchLiteral("%item%").replacement(mat.name())));
            return;
        }
        Integer amount = Integer.parseInt(args[1]);
        if (mat == null){
            sender.sendMessage(Config.msg("givealive.invalid-item"));
            return;
        }
        for (Player p : EventCore.instance.Alive){
            ItemStack item = new ItemStack(mat, Integer.parseInt(args[1]));
            p.getInventory().addItem(item);
        }
        sender.sendMessage(Config.msg("givealive.gave")
                .replaceText(builder -> builder.matchLiteral("%item%").replacement(mat.name()))
                .replaceText(builder -> builder.matchLiteral("%amount%").replacement(String.valueOf(amount))));
    }

    @Override
    public List<String> tabComplete(CommandSender sender, Command command, String[] args) {
        if (args.length == 1) {
            final List<String> completions = new ArrayList<>();
            for (Material p : Material.values()) {
                completions.add(p.name().toLowerCase());
            }
            return completions;
        }
        return null;
    }
}
