package lol.aabss.eventcore;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class Config {

    public static String getString(String string){
        return ChatColor.translateAlternateColorCodes('&', EventCore.getPlugin(EventCore.class).getConfig().getString(string));
    }
    public static Boolean getBoolean(String string){
        return EventCore.getPlugin(EventCore.class).getConfig().getBoolean(string);
    }

    public static Integer getInteger(String string){
        return EventCore.getPlugin(EventCore.class).getConfig().getInt(string);
    }

    public static String color(String string){
        return ChatColor.translateAlternateColorCodes('&', string);
    }


    public static Integer getRevives(Player p){
        File configFile = new File(EventCore.getPlugin(EventCore.class).getDataFolder(), "data.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);
        if (config.get("revives." + p.getUniqueId()) == null){
            return 0;
        }
        return config.getInt("revives." + p.getUniqueId());
    }

    public static void giveRevives(Player p, Integer i){
        File configFile = new File(EventCore.getPlugin(EventCore.class).getDataFolder(), "data.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);
        config.set("revives." + p.getUniqueId(), getRevives(p)+i);
        try {
            config.save(configFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setRevives(Player p, Integer i){
        File configFile = new File(EventCore.getPlugin(EventCore.class).getDataFolder(), "data.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);
        config.set("revives." + p.getUniqueId(), i);
        try {
            config.save(configFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void takeRevives(Player p, Integer i){
        File configFile = new File(EventCore.getPlugin(EventCore.class).getDataFolder(), "data.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);
        config.set("revives." + p.getUniqueId(), getRevives(p)-i);
        try {
            config.save(configFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
