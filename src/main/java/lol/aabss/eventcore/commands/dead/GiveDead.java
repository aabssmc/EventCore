package lol.aabss.eventcore.commands.dead;

import lol.aabss.eventcore.EventCore;
import lol.aabss.eventcore.util.SimpleCommand;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

import static lol.aabss.eventcore.util.Config.msg;

public class GiveDead implements SimpleCommand {

    @Override
    public boolean run(CommandSender sender, Command command, String[] args) {
        if (args.length == 0){
            sender.sendMessage(msg("givedead.no-item"));
            return true;
        }
        Material mat = Material.matchMaterial(args[0].toUpperCase());
        if (args.length == 1){
            if (mat == null){
                sender.sendMessage(msg("givedead.invalid-item"));
                return true;
            }
            for (Player p : EventCore.Dead){
                ItemStack item = new ItemStack(mat, 64);
                p.getInventory().addItem(item);
            }
            sender.sendMessage(msg("givedead.gave-64")
                    .replaceText(builder -> builder.matchLiteral("%item%").replacement(mat.name())));
            return true;
        }
        Integer amount = Integer.parseInt(args[1]);
        if (mat == null){
            sender.sendMessage(msg("givedead.invalid-item"));
            return true;
        }
        for (Player p : EventCore.Dead){
            ItemStack item = new ItemStack(mat, Integer.parseInt(args[1]));
            p.getInventory().addItem(item);
        }
        sender.sendMessage(msg("givedead.gave")
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
