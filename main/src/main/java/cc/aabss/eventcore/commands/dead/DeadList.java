package cc.aabss.eventcore.commands.dead;

import cc.aabss.eventcore.EventCore;
import cc.aabss.eventcore.util.Config;
import cc.aabss.eventcore.util.SimpleCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class DeadList implements SimpleCommand {

    @Override
    public boolean run(CommandSender sender, Command command, String[] args) {
        List<String> names = new ArrayList<>();
        EventCore.instance.Dead.forEach(player -> names.add(player.getName()));
        if (names.isEmpty()){
            sender.sendMessage(Config.msg("deadlist.empty"));
            return true;
        }
        if (names.size() == 1){
            sender.sendMessage(Config.msg("deadlist.one-player")
                    .replaceText(builder -> builder.matchLiteral("%dead%").replacement(EventCore.formatList(names))));
            return true;
        }
        sender.sendMessage(Config.msg("deadlist.players")
                .replaceText(builder -> builder.matchLiteral("%dead%").replacement(EventCore.formatList(names)))
                .replaceText(builder -> builder.matchLiteral("%amount%").replacement(String.valueOf(names.size())))
        );
        return true;
    }
}
