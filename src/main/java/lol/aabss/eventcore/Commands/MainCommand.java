package lol.aabss.eventcore.Commands;

import lol.aabss.eventcore.EventCore;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class MainCommand implements CommandExecutor {


    private final EventCore plugin;

    public MainCommand(EventCore plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        CommandSender s = sender;
        String permmessage = this.plugin.getConfig().getString("permission-message");
        if (sender.hasPermission("eventcore.command")){
            if (args.length == 0){
                String prefix = this.plugin.getConfig().getString("prefix");
                s.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + ChatColor.RED + " /eventcore <reload | help>"));
            }
            else{
                if (args[0].equals("help")){
                    String prefix = this.plugin.getConfig().getString("prefix");
                    s.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "help coming soon"));
                }
                else if (args[0].equals("reload")){
                    this.plugin.reloadConfig();
                    String prefix = this.plugin.getConfig().getString("prefix");
                    System.out.print(ChatColor.GREEN + "Config reloaded!");
                    s.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + ChatColor.GREEN + " Config reloaded!"));
                }
                else{
                    String prefix = this.plugin.getConfig().getString("prefix");
                    s.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + ChatColor.RED + " /eventcore <reload | help>"));
                }
            }
        }
        else{
            String prefix = this.plugin.getConfig().getString("prefix");
            sender.sendMessage(prefix + " " + permmessage);
        }
        return true;
    }
}
