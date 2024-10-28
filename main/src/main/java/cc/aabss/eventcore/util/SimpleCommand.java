package cc.aabss.eventcore.util;

import cc.aabss.eventcore.EventCore;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import org.bukkit.Bukkit;
import org.bukkit.permissions.Permission;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class SimpleCommand {

    public SimpleCommand(@NotNull String name, String description, String... aliases) {
        this.name = name;
        this.description = description;
        this.aliases = aliases;
    }

    private final String name;
    private final String description;
    private final String[] aliases;

    public void register(Commands commands){
        commands.register(EventCore.instance.getPluginMeta(), execute(Commands.literal(name)).build(), description, List.of(aliases));
        if (Bukkit.getPluginManager().getPermission(this.permission()) == null) {
            Bukkit.getPluginManager().addPermission(new Permission(this.permission()));
        }
    }

    protected LiteralArgumentBuilder<CommandSourceStack> execute(LiteralArgumentBuilder<CommandSourceStack> argumentBuilder) {
        return run(argumentBuilder.requires(commandSourceStack -> commandSourceStack.getSender().hasPermission(permission())));
    }

    protected abstract LiteralArgumentBuilder<CommandSourceStack> run(LiteralArgumentBuilder<CommandSourceStack> argumentBuilder);

    public String permission(){
        return "eventcore.command."+this.getClass().getSimpleName().toLowerCase();
    }

}