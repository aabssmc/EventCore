package cc.aabss.eventcore.commands.alive;

import cc.aabss.eventcore.EventCore;
import cc.aabss.eventcore.util.Config;
import cc.aabss.eventcore.util.SimpleCommand;
import org.bukkit.Material;
import org.jetbrains.annotations.Nullable;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ClearAlive extends SimpleCommand {

    public ClearAlive(@NotNull String name, @Nullable String description, @Nullable String... aliases) {
        super(name, description, aliases);
    }

    @Override
    public void run(CommandSender sender, String commandLabel, String[] args) {
        for (Player p : EventCore.instance.Alive){
            p.getOpenInventory().close();
            p.getInventory().clear();
            p.getInventory().addItem(new ItemStack(Material.AIR, 1));
            for (Item item : p.getLocation().getNearbyEntitiesByType(Item.class, 3)){
                item.remove();
            }
        }
        sender.sendMessage(Config.msg("clearalive.cleared"));
    }
}
