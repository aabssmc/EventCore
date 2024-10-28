package cc.aabss.eventcore.commands.dead;

import cc.aabss.eventcore.EventCore;
import cc.aabss.eventcore.util.Config;
import cc.aabss.eventcore.util.SimpleCommand;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import net.minecraft.commands.arguments.item.ItemArgument;
import org.jetbrains.annotations.Nullable;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class GiveDead extends SimpleCommand {

    public GiveDead(@NotNull String name, @Nullable String description, @Nullable String... aliases) {
        super(name, description, aliases);
    }

    @Override
    protected LiteralArgumentBuilder<CommandSourceStack> run(LiteralArgumentBuilder<CommandSourceStack> argumentBuilder) {
        return argumentBuilder
                .then(Commands.argument("item", ItemArgument.item(EventCore.COMMAND_BUILD_CONTEXT))
                        .then(Commands.argument("amount", IntegerArgumentType.integer())
                                .executes(context -> {
                                    Integer integer = context.getArgument("amount", Integer.class);
                                    ItemStack itemStack = ItemArgument.getItem(context, "item").createItemStack(integer, false).asBukkitCopy();
                                    for (Player p : EventCore.instance.Dead){
                                        p.getInventory().addItem(itemStack);
                                    }
                                    context.getSource().getSender().sendMessage(Config.msg("givedead.gave")
                                            .replaceText(builder -> builder.matchLiteral("%item%").replacement(itemStack.getType().name()))
                                            .replaceText(builder -> builder.matchLiteral("%amount%").replacement(String.valueOf(integer))));
                                    return 1;
                                })
                        )
                        .executes(context -> {
                            ItemStack itemStack = ItemArgument.getItem(context, "item").createItemStack(1, false).asBukkitCopy();
                            for (Player p : EventCore.instance.Dead){
                                p.getInventory().addItem(itemStack);
                            }
                            context.getSource().getSender().sendMessage(Config.msg("givedead.gave-64")
                                    .replaceText(builder -> builder.matchLiteral("%item%").replacement(itemStack.getType().name())));
                            return 1;
                        }))
                .executes(context -> {
                    context.getSource().getSender().sendMessage(Config.msg("givedead.no-item"));
                    return 0;
                });
    }
}
