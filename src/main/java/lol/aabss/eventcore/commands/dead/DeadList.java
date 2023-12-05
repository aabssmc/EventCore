package lol.aabss.eventcore.commands.dead;

import lol.aabss.eventcore.EventCore;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class DeadList implements CommandExecutor {

    private final EventCore plugin;

    public DeadList(EventCore plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String permmessage = this.plugin.getConfig().getString("permission-message");
        String prefix = this.plugin.getConfig().getString("prefix");
        if (sender.hasPermission("eventcore.deadlist")){
            if (EventCore.Dead.isEmpty()){
                sender.sendMessage(ChatColor.GRAY + "There are 0 players dead\nThere are no players dead.");
            }
            else{
                if (EventCore.Dead.size() == 1){
                    sender.sendMessage(ChatColor.GRAY + "There is 1 player dead" + "\n" + EventCore.Dead);
                }
                else{
                    sender.sendMessage(ChatColor.GRAY + "There are " + EventCore.Dead.size() + " players alive" + "\n" + EventCore.Dead);
                }
            }
        }
        else{
            sender.sendMessage(prefix + " " + permmessage);
        }
        return true;
    }
}