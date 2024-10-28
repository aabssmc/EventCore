package cc.aabss.eventcore.commands.dead;

import cc.aabss.eventcore.EventCore;
import cc.aabss.eventcore.util.Config;
import cc.aabss.eventcore.util.SimpleCommand;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.Material;
import org.jetbrains.annotations.Nullable;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ClearDead extends SimpleCommand {

    public ClearDead(@NotNull String name, @Nullable String description, @Nullable String... aliases) {
        super(name, description, aliases);
    }

    @Override
    protected LiteralArgumentBuilder<CommandSourceStack> run(LiteralArgumentBuilder<CommandSourceStack> argumentBuilder) {
        return argumentBuilder
                .executes(context -> {
                    for (Player p : EventCore.instance.Dead){
                        p.getOpenInventory().close();
                        p.getInventory().clear();
                        p.getInventory().addItem(new ItemStack(Material.AIR, 1));
                        for (Item item : p.getLocation().getNearbyEntitiesByType(Item.class, 3)){
                            item.remove();
                        }
                    }
                    context.getSource().getSender().sendMessage(Config.msg("cleardead.cleared"));
                    return 1;
                });
    }

}
