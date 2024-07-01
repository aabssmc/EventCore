package lol.aabss.eventcore.commands.dead;

import lol.aabss.eventcore.EventCore;
import lol.aabss.eventcore.util.SimpleCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

import static lol.aabss.eventcore.EventCore.formatList;
import static lol.aabss.eventcore.util.Config.msg;

public class DeadList implements SimpleCommand {

    @Override
    public boolean run(CommandSender sender, Command command, String[] args) {
        List<String> names = new ArrayList<>();
        EventCore.instance.Dead.forEach(player -> names.add(player.getName()));
        if (names.isEmpty()){
            sender.sendMessage(msg("deadlist.empty"));
            return true;
        }
        if (names.size() == 1){
            sender.sendMessage(msg("deadlist.one-player")
                    .replaceText(builder -> builder.matchLiteral("%dead%").replacement(formatList(names))));
            return true;
        }
        sender.sendMessage(msg("deadlist.players")
                .replaceText(builder -> builder.matchLiteral("%dead%").replacement(formatList(names)))
                .replaceText(builder -> builder.matchLiteral("%amount%").replacement(String.valueOf(names.size())))
        );
        return true;
    }
}
