package lol.aabss.eventcore.util;

import com.google.common.base.Charsets;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.InputStream;
import java.io.InputStreamReader;

import static lol.aabss.eventcore.EventCore.*;
import static net.kyori.adventure.text.minimessage.MiniMessage.miniMessage;

public class Config {

    public static Component getComponent(String path){
        if (instance.getConfig().getString(path) == null){
            return Component.empty();
        }
        return miniMessage().deserialize(instance.getConfig().getString(path, path));
    }

    public static void reloadConfig() {
        FileConfiguration config = YamlConfiguration.loadConfiguration(instance.datafile);
        final InputStream defConfigStream = instance.getResource("data.yml");
        if (defConfigStream == null) {
            return;
        }
        config.setDefaults(YamlConfiguration.loadConfiguration(new InputStreamReader(defConfigStream, Charsets.UTF_8)));
    }

    public static Component msg(String path){
        Component prefix = miniMessage().deserialize(instance.getConfig().getString("prefix", "<gold><bold>EventCore <gray>»"));
        Component msg = miniMessage().deserialize(instance.getConfig().getString(path, path));
        return msg.replaceText(builder -> builder.match("%prefix%").replacement(prefix));
    }

    public static void sendMessagePrefix(CommandSender sender, String msg){
        sender.sendMessage(getComponent("prefix").append(miniMessage().deserialize(msg)));
    }

    // dont touch


    public static <T> T get(String path, Class<T> clazz){
        return instance.getConfig().getObject(path, clazz);
    }

    public static Object get(String path){
        return instance.getConfig().get(path);
    }


}
