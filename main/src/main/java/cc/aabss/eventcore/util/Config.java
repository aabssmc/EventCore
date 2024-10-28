package cc.aabss.eventcore.util;

import cc.aabss.eventcore.EventCore;
import com.google.common.base.Charsets;
import cc.aabss.eventcore.util.fastboard.FastBoard;
import cc.aabss.eventcore.util.fastboard.FastBoardBase;
import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static cc.aabss.eventcore.util.fastboard.FastBoard.BOARDS;
import static net.kyori.adventure.text.minimessage.MiniMessage.miniMessage;

public class Config {

    public static Component getComponent(String path){
        if (EventCore.instance.getConfig().getString(path) == null){
            return Component.empty();
        }
        return miniMessage().deserialize(EventCore.instance.getConfig().getString(path, path));
    }

    public static void reloadConfig() {
        EventCore.instance.saveDefaultConfig();
        FileConfiguration config = YamlConfiguration.loadConfiguration(EventCore.instance.datafile);
        final InputStream defConfigStream = EventCore.instance.getResource("data.yml");
        if (defConfigStream == null) {
            return;
        }
        config.setDefaults(YamlConfiguration.loadConfiguration(new InputStreamReader(defConfigStream, Charsets.UTF_8)));
        BOARDS.values().forEach(FastBoardBase::delete);
        if (EventCore.instance.getConfig().getBoolean("enable-scoreboard", false)) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                updateBoard(p);
            }
        }
    }

    public static void updateBoard(Player p) {
        FastBoard fastBoard = BOARDS.get(p.getUniqueId());
        if (fastBoard == null || fastBoard.isDeleted()){
            fastBoard = new FastBoard(p);
            BOARDS.put(p.getUniqueId(), fastBoard);
        }
        fastBoard.updateTitle(scoreboardTitle(p));
        List<Component> lines = scoreboardLines(p);
        for (int i = 0; i < lines.size(); i++){
            fastBoard.updateLine(i, lines.get(i));
        }
    }

    public static Component msg(String path){
        Component prefix = miniMessage().deserialize(EventCore.instance.getConfig().getString("prefix", "<gold><bold>EventCore <gray>»"));
        Component msg = miniMessage().deserialize(EventCore.instance.getConfig().getString(path, path));
        return msg.replaceText(builder -> builder.match("%prefix%").replacement(prefix));
    }

    public static Component scoreboardTitle(OfflinePlayer player){
        String string = EventCore.instance.getConfig().getString("scoreboard-title", "<gold><bold>EventCore");
        if (papi(string)){
            string = PlaceholderAPI.setPlaceholders(player, string);
        }
        assert player.getName() != null;
        return miniMessage().deserialize(string).replaceText(TextReplacementConfig.builder().match("%player%").replacement(player.getName()).build());
    }

    public static List<Component> scoreboardLines(OfflinePlayer player){
        List<String> strings = EventCore.instance.getConfig().getStringList("scoreboard-lines");
        List<Component> lines = new ArrayList<>();
        for (String line : strings){
            if (papi(line)){
                line = PlaceholderAPI.setPlaceholders(player, line);
            }
            lines.add(miniMessage().deserialize(line));
        }
        return lines;
    }

    private static boolean papi(String line){
        return (EventCore.classExists("me.clip.placeholderapi.PlaceholderAPI") && PlaceholderAPI.containsPlaceholders(line));
    }

    public static void sendMessagePrefix(CommandSender sender, String msg){
        sender.sendMessage(getComponent("prefix").append(miniMessage().deserialize(msg)));
    }

    // dont touch

    public static <T> T get(String path, Class<T> clazz){
        return EventCore.instance.getConfig().getObject(path, clazz);
    }

    public static Object get(String path){
        return EventCore.instance.getConfig().get(path);
    }


}
