package lol.aabss.eventcore.commands;

import lol.aabss.eventcore.Config;
import lol.aabss.eventcore.EventCore;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

public class MainCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        CommandSender s = sender;
        String permmessage = Config.getString("permission-message");
        String prefix = Config.getString("prefix");
        if (sender.hasPermission("eventcore.command")){
            if (args.length == 0){
                s.sendMessage(Config.color(prefix +" &c/eventcore <reload>"));
            }
            else{
                if (args[0].equals("reload")){
                    EventCore.getPlugin(EventCore.class).reloadConfig();
                    File configFile = new File(EventCore.getPlugin(EventCore.class).getDataFolder(), "data.yml");
                    FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);
                    try {
                        config.save(configFile);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    EventCore.getPlugin(EventCore.class).getLogger().info(" &aConfig reloaded!");
                    s.sendMessage(Config.color(prefix + " &aConfig reloaded!"));
                }
                else{
                    s.sendMessage(Config.color(prefix + " &a/eventcore <reload>"));
                }
            }
        }
        else{
            sender.sendMessage(Config.color(prefix + " " + permmessage));
        }
        return true;
    }
}
