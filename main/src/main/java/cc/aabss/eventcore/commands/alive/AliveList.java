package cc.aabss.eventcore.commands.alive;

import cc.aabss.eventcore.EventCore;
import cc.aabss.eventcore.util.Config;
import cc.aabss.eventcore.util.SimpleCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class AliveList implements SimpleCommand {

    @Override
    public void run(CommandSender sender, Command command, String[] args) {
        List<String> names = new ArrayList<>();
        EventCore.instance.Alive.forEach(player -> names.add(player.getName()));
        if (names.isEmpty()){
            sender.sendMessage(Config.msg("alivelist.empty"));
            return;
        }
        if (names.size() == 1){
            sender.sendMessage(Config.msg("alivelist.one-player")
                    .replaceText(builder -> builder.matchLiteral("%alive%").replacement(EventCore.formatList(names))));
            return;
        }
        sender.sendMessage(Config.msg("alivelist.players")
                .replaceText(builder -> builder.matchLiteral("%alive%").replacement(EventCore.formatList(names)))
                .replaceText(builder -> builder.matchLiteral("%amount%").replacement(String.valueOf(names.size())))
        );
    }
}
