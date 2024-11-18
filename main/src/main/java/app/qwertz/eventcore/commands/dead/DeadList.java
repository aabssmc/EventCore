package app.qwertz.eventcore.commands.dead;

import app.qwertz.eventcore.EventCore;
import app.qwertz.eventcore.util.Config;
import app.qwertz.eventcore.util.SimpleCommand;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class DeadList extends SimpleCommand {

    public DeadList(@NotNull String name, @Nullable String description, @Nullable String... aliases) {
        super(name, description, aliases);
    }

    @Override
    protected LiteralArgumentBuilder<CommandSourceStack> run(LiteralArgumentBuilder<CommandSourceStack> argumentBuilder) {
        return argumentBuilder
                .executes(context -> {
                    List<String> names = new ArrayList<>();
                    EventCore.instance.Dead.forEach(player -> names.add(player.getName()));
                    if (names.isEmpty()){
                        context.getSource().getSender().sendMessage(Config.msg("deadlist.empty"));
                        return 0;
                    }
                    if (names.size() == 1){
                        context.getSource().getSender().sendMessage(Config.msg("deadlist.one-player")
                                .replaceText(builder -> builder.matchLiteral("%dead%").replacement(EventCore.formatList(names))));
                        return 0;
                    }
                    context.getSource().getSender().sendMessage(Config.msg("deadlist.players")
                            .replaceText(builder -> builder.matchLiteral("%dead%").replacement(EventCore.formatList(names)))
                            .replaceText(builder -> builder.matchLiteral("%amount%").replacement(String.valueOf(names.size())))
                    );
                    return 1;
                });
    }

}
