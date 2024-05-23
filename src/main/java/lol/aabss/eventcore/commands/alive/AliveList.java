package lol.aabss.eventcore.commands.alive;

import lol.aabss.eventcore.util.SimpleCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

import static lol.aabss.eventcore.EventCore.*;
import static lol.aabss.eventcore.util.Config.msg;

public class AliveList implements SimpleCommand {

    @Override
    public boolean run(CommandSender sender, Command command, String[] args) {
        List<String> names = new ArrayList<>();
        API.getAlive().forEach(player -> names.add(player.getName()));
        if (names.isEmpty()){
            sender.sendMessage(msg("alivelist.empty"));
            return true;
        }
        if (names.size() == 1){
            sender.sendMessage(msg("alivelist.one-player")
                    .replaceText(builder -> builder.matchLiteral("%alive%").replacement(formatList(names))));
            return true;
        }
        sender.sendMessage(msg("alivelist.players")
                .replaceText(builder -> builder.matchLiteral("%alive%").replacement(formatList(names)))
                .replaceText(builder -> builder.matchLiteral("%amount%").replacement(String.valueOf(names.size())))
        );
        return true;
    }
}
