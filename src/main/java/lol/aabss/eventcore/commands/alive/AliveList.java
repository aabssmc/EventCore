package lol.aabss.eventcore.commands.alive;

import lol.aabss.eventcore.EventCore;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class AliveList implements CommandExecutor {

    private final EventCore plugin;

    public AliveList(EventCore plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String permmessage = this.plugin.getConfig().getString("permission-message");
        String prefix = this.plugin.getConfig().getString("prefix");
        if (sender.hasPermission("eventcore.alivelist")){
            if (EventCore.Alive.isEmpty()){
                sender.sendMessage(ChatColor.GRAY + "There are 0 players alive\nThere are no players alive.");
            }
            else{
                if (EventCore.Alive.size() == 1){
                    sender.sendMessage(ChatColor.GRAY + "There is 1 player alive" + "\n" + EventCore.Alive);
                }
                else{
                    sender.sendMessage(ChatColor.GRAY + "There are " + EventCore.Alive.size() + " players alive" + "\n" + EventCore.Alive);
                }
            }
        }
        else {
            sender.sendMessage(prefix + " " + permmessage);
        }
        return true;
    }
}
