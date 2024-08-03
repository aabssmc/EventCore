package cc.aabss.eventcore.commands.alive;

import cc.aabss.eventcore.EventCore;
import cc.aabss.eventcore.util.Config;
import cc.aabss.eventcore.util.SimpleCommand;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ClearAlive implements SimpleCommand {

    @Override
    public void run(CommandSender sender, Command command, String[] args) {
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
