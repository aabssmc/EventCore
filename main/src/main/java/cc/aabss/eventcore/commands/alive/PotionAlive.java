package cc.aabss.eventcore.commands.alive;

import cc.aabss.eventcore.util.SimpleCommand;
import net.kyori.adventure.text.TextReplacementConfig;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

import static cc.aabss.eventcore.EventCore.API;
import static cc.aabss.eventcore.util.Config.msg;

public class PotionAlive implements SimpleCommand {
    @Override
    public void run(CommandSender sender, Command command, String[] args) {
        if (args.length >= 1){
            if (args[0].equalsIgnoreCase("clear") || args[0].equalsIgnoreCase("minecraft:clear")){
                for (Player p : API.getAlive()){
                    p.clearActivePotionEffects();
                }
                sender.sendMessage("potionalive.cleared");
                return;
            }
            NamespacedKey key = NamespacedKey.fromString(args[0]);
            PotionEffectType potion = Registry.POTION_EFFECT_TYPE.get(key == null ? NamespacedKey.minecraft("none") : key);
            if (potion == null){
                sender.sendMessage(msg("potionalive.invalid"));
                return;
            }
            for (Player p : API.getAlive()){
                p.addPotionEffect(new PotionEffect(potion, duration(args)*20, amplifier(args), ambient(args), particles(args), icon(args)));
            }
            sender.sendMessage(msg("potionalive.gave")
                    .replaceText(TextReplacementConfig.builder().match("%potioneffect%").replacement(potion.getKey().toString()).build())
                    .replaceText(TextReplacementConfig.builder().match("%amplifier%").replacement(String.valueOf(amplifier(args))).build())
                    .replaceText(TextReplacementConfig.builder().match("%time%").replacement(String.valueOf(duration(args))).build())
            );
        } else {
            sender.sendMessage(msg("<red>/potionalive <effect> [seconds] [amplifier] [ambient?] [particles?] [icon?]"));
        }
    }

    private int duration(String[] args) {
        if (args.length < 2) {
            return 60;
        }
        if (args[1].equalsIgnoreCase("infinite")) {
            return PotionEffect.INFINITE_DURATION;
        }
        try {
            return Integer.parseInt(args[1]);
        } catch (NumberFormatException ignored) {
            return 60;
        }
    }

    private int amplifier(String[] args) {
        if (args.length < 3) {
            return 1;
        }
        try {
            return Integer.parseInt(args[2]);
        } catch (NumberFormatException ignored) {
            return 1;
        }
    }

    private boolean ambient(String[] args) {
        if (args.length < 4) {
            return false;
        }
        return Boolean.parseBoolean(args[3]);
    }

    private boolean particles(String[] args) {
        if (args.length < 5) {
            return false;
        }
        return Boolean.parseBoolean(args[4]);
    }

    private boolean icon(String[] args) {
        if (args.length < 6) {
            return false;
        }
        return Boolean.parseBoolean(args[5]);
    }

    @Override
    public List<String> tabComplete(CommandSender sender, Command command, String[] args) {
        if (args.length == 1){
            List<String> list = new ArrayList<>(Registry.POTION_EFFECT_TYPE.stream().map(potionEffectType -> potionEffectType.getKey().toString()).toList());
            list.addAll(List.of("clear", "minecraft:clear"));
            return list;
        } else if (args.length == 2) {
            return List.of("infinite");
        } else if (args.length == 4 || args.length == 5 || args.length == 6) {
            return List.of("true", "false");
        }
        return SimpleCommand.super.tabComplete(sender, command, args);
    }
}
