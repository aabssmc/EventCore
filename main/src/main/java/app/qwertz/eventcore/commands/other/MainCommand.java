package app.qwertz.eventcore.commands.other;

import app.qwertz.eventcore.EventCore;
import app.qwertz.eventcore.util.Config;
import app.qwertz.eventcore.util.SimpleCommand;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;

import static net.kyori.adventure.text.minimessage.MiniMessage.miniMessage;

public class MainCommand extends SimpleCommand {

    public MainCommand(@NotNull String name, @Nullable String description, @Nullable String... aliases) {
        super(name, description, aliases);
    }

    @Override
    protected LiteralArgumentBuilder<CommandSourceStack> execute(LiteralArgumentBuilder<CommandSourceStack> argumentBuilder) {
        return run(argumentBuilder);
    }

    @Override
    protected LiteralArgumentBuilder<CommandSourceStack> run(LiteralArgumentBuilder<CommandSourceStack> argumentBuilder) {
        return argumentBuilder
                .then(Commands.argument("arg", StringArgumentType.word())
                        .requires(commandSourceStack -> commandSourceStack.getSender().hasPermission(permission()))
                        .suggests((context, builder) ->
                                builder.suggest("reload").buildFuture()
                        )
                        .executes(context -> {
                            if (StringArgumentType.getString(context, "arg").equalsIgnoreCase("reload")) {
                                EventCore.instance.reloadConfig();
                                Config.reloadConfig();
                                context.getSource().getSender().sendMessage(Config.msg("reloaded"));
                                return 1;
                            } else {
                                context.getSource().getSender().sendMessage(permissionMessage());
                                return 0;
                            }
                        }))
                .executes(context -> {
                    context.getSource().getSender().sendMessage(permissionMessage());
                    return 1;
                });
    }

    public Component permissionMessage() {
        return miniMessage().deserialize("<br><click:open_url:'https://modrinth.com/plugin/event'><b><gold>EventCore</gold></b> <yellow>by aabss and QWERTZ_EXE</yellow> <gray>(@big.abs, @qwertz_exe)</gray></click><br>");
    }

    @Override
    public String permission() {
        return "eventcore.command.main";
    }

}
