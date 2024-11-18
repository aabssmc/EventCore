package app.qwertz.eventcore.commands.dead;

import app.qwertz.eventcore.util.SimpleCommand;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.command.brigadier.argument.ArgumentTypes;
import io.papermc.paper.registry.RegistryKey;
import io.papermc.paper.registry.TypedKey;
import net.kyori.adventure.text.TextReplacementConfig;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.jetbrains.annotations.Nullable;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.jetbrains.annotations.NotNull;

import static app.qwertz.eventcore.EventCore.API;
import static app.qwertz.eventcore.util.Config.msg;

public class PotionDead extends SimpleCommand {
    public PotionDead(@NotNull String name, @Nullable String description, @Nullable String... aliases) {
        super(name, description, aliases);
    }

    @Override
    protected LiteralArgumentBuilder<CommandSourceStack> run(LiteralArgumentBuilder<CommandSourceStack> argumentBuilder) {
        return argumentBuilder
                .then(Commands.argument("effect", ArgumentTypes.resourceKey(RegistryKey.POTION))
                        .then(Commands.argument("duration", IntegerArgumentType.integer())
                                .then(Commands.argument("amplifier", IntegerArgumentType.integer())
                                        .then(Commands.argument("ambient", BoolArgumentType.bool())
                                                .then(Commands.argument("particles", BoolArgumentType.bool())
                                                        .then(Commands.argument("icon", BoolArgumentType.bool())
                                                                .executes(context -> potion(IntegerArgumentType.getInteger(context, "duration")*20,
                                                                        IntegerArgumentType.getInteger(context, "amplifier"),
                                                                        BoolArgumentType.getBool(context, "ambient"),
                                                                        BoolArgumentType.getBool(context, "particles"),
                                                                        BoolArgumentType.getBool(context, "icon"), context)
                                                                )
                                                        )
                                                        .executes(context -> potion(IntegerArgumentType.getInteger(context, "duration")*20,
                                                                IntegerArgumentType.getInteger(context, "amplifier"),
                                                                BoolArgumentType.getBool(context, "ambient"),
                                                                BoolArgumentType.getBool(context, "particles"),
                                                                false, context)
                                                        )
                                                )
                                                .executes(context -> potion(IntegerArgumentType.getInteger(context, "duration")*20,
                                                        IntegerArgumentType.getInteger(context, "amplifier"),
                                                        BoolArgumentType.getBool(context, "ambient"),
                                                        false, false, context)
                                                )
                                        )
                                        .executes(context -> potion(IntegerArgumentType.getInteger(context, "duration")*20,
                                                IntegerArgumentType.getInteger(context, "amplifier"),
                                                false, false, false, context)
                                        )
                                )
                                .executes(context -> potion(IntegerArgumentType.getInteger(context, "duration")*20,
                                        1, false, false, false, context)
                                )
                        )
                        .executes(context -> potion(20, 1, false, false, false, context))
                )
                .executes(context -> {
                    context.getSource().getSender().sendMessage(msg("<red>/potiondead <effect> [seconds] [amplifier] [ambient?] [particles?] [icon?]"));
                    return 0;
                });
    }

    private int potion(int duration, int amplifier, boolean ambient, boolean particles, boolean icon, CommandContext<CommandSourceStack> context) {
        PotionEffect potionEffect = new PotionEffect(
                Registry.POTION_EFFECT_TYPE.get(NamespacedKey.fromString(context.getArgument("effect", TypedKey.class).key().value())),
                duration, amplifier, ambient, particles, icon);
        for (Player p : API.getDead()){
            p.addPotionEffect(potionEffect);
        }
        context.getSource().getSender().sendMessage(msg("potiondead.gave")
                .replaceText(TextReplacementConfig.builder().match("%potioneffect%").replacement(potionEffect.getType().getKey().toString()).build())
                .replaceText(TextReplacementConfig.builder().match("%amplifier%").replacement(String.valueOf(potionEffect.getAmplifier())).build())
                .replaceText(TextReplacementConfig.builder().match("%time%").replacement(String.valueOf(potionEffect.getDuration())).build())
        );
        return 1;
    }
}
