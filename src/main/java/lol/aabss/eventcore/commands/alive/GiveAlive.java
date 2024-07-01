package lol.aabss.eventcore.commands.alive;

import lol.aabss.eventcore.EventCore;
import lol.aabss.eventcore.util.SimpleCommand;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

import static lol.aabss.eventcore.EventCore.API;
import static lol.aabss.eventcore.util.Config.msg;

public class GiveAlive implements SimpleCommand {

    @Override
    public boolean run(CommandSender sender, Command command, String[] args) {
        if (args.length == 0){
            sender.sendMessage(msg("givealive.no-item"));
            return true;
        }
        Material mat = Material.matchMaterial(args[0].toUpperCase());
        if (args.length == 1){
            if (mat == null){
                sender.sendMessage(msg("givealive.invalid-item"));
                return true;
            }
            for (Player p : EventCore.instance.Alive){
                ItemStack item = new ItemStack(mat, 64);
                p.getInventory().addItem(item);
            }
            sender.sendMessage(msg("givealive.gave-64")
                    .replaceText(builder -> builder.matchLiteral("%item%").replacement(mat.name())));
            return true;
        }
        Integer amount = Integer.parseInt(args[1]);
        if (mat == null){
            sender.sendMessage(msg("givealive.invalid-item"));
            return true;
        }
        for (Player p : EventCore.instance.Alive){
            ItemStack item = new ItemStack(mat, Integer.parseInt(args[1]));
            p.getInventory().addItem(item);
        }
        sender.sendMessage(msg("givealive.gave")
                .replaceText(builder -> builder.matchLiteral("%item%").replacement(mat.name()))
                .replaceText(builder -> builder.matchLiteral("%amount%").replacement(String.valueOf(amount))));
        return true;
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
