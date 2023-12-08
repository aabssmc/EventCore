package lol.aabss.eventcore.commands;

import lol.aabss.eventcore.Config;
import lol.aabss.eventcore.EventCore;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class MainCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        CommandSender s = sender;
        String permmessage = Config.getString("permission-message");
        String prefix = Config.getString("prefix");
        if (sender.hasPermission("eventcore.command")){
            if (args.length == 0){
                s.sendMessage(Config.color(prefix + ChatColor.RED + " /eventcore <reload | help>"));
            }
            else{
                if (args[0].equals("help")){
                    s.sendMessage(Config.color(prefix + " help coming soon"));
                }
                else if (args[0].equals("reload")){
                    EventCore.getPlugin(EventCore.class).reloadConfig();
                    EventCore.getPlugin(EventCore.class).getLogger().info(ChatColor.GREEN + "Config reloaded!");
                    s.sendMessage(Config.color(prefix + ChatColor.GREEN + " Config reloaded!"));
                }
                else{
                    s.sendMessage(Config.color(prefix + ChatColor.RED + " /eventcore <reload | help>"));
                }
            }
        }
        else{
            sender.sendMessage(prefix + " " + permmessage);
        }
        return true;
    }
}
