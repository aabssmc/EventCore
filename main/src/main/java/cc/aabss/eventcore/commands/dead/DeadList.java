package cc.aabss.eventcore.commands.dead;

import cc.aabss.eventcore.EventCore;
import cc.aabss.eventcore.util.Config;
import cc.aabss.eventcore.util.SimpleCommand;
import org.jetbrains.annotations.Nullable;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class DeadList extends SimpleCommand {

    public DeadList(@NotNull String name, @Nullable String description, @Nullable String... aliases) {
        super(name, description, aliases);
    }

    @Override
    public void run(CommandSender sender, String commandLabel, String[] args) {
        List<String> names = new ArrayList<>();
        EventCore.instance.Dead.forEach(player -> names.add(player.getName()));
        if (names.isEmpty()){
            sender.sendMessage(Config.msg("deadlist.empty"));
            return;
        }
        if (names.size() == 1){
            sender.sendMessage(Config.msg("deadlist.one-player")
                    .replaceText(builder -> builder.matchLiteral("%dead%").replacement(EventCore.formatList(names))));
            return;
        }
        sender.sendMessage(Config.msg("deadlist.players")
                .replaceText(builder -> builder.matchLiteral("%dead%").replacement(EventCore.formatList(names)))
                .replaceText(builder -> builder.matchLiteral("%amount%").replacement(String.valueOf(names.size())))
        );
    }
}
