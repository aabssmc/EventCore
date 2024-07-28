package lol.aabss.eventcore;

import ch.njol.skript.Skript;
import aabss.eventcoreapi.EventCoreAPI;
import lol.aabss.eventcore.api.EventCoreAPIImpl;
import lol.aabss.eventcore.commands.alive.*;
import lol.aabss.eventcore.commands.dead.*;
import lol.aabss.eventcore.commands.other.ClearChat;
import lol.aabss.eventcore.commands.other.MainCommand;
import lol.aabss.eventcore.commands.other.Mutechat;
import lol.aabss.eventcore.commands.other.Visibility;
import lol.aabss.eventcore.commands.revives.*;
import lol.aabss.eventcore.hooks.*;

import lol.aabss.eventcore.util.Listeners;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.*;

import static lol.aabss.eventcore.hooks.UpdateChecker.UPDATE_CHECKER;


public class EventCore extends JavaPlugin {

    public static EventCore instance;

    public List<Player> Alive = new ArrayList<>();
    public List<Player> Dead = new ArrayList<>();
    public List<Player> Recent = new ArrayList<>();

    public File datafile;
    public FileConfiguration dataconfig;
    public static EventCoreAPI API;
    public static Map<String, Map<CommandSender, Instant>> cooldowns = new HashMap<>();

    @Override
    public void onEnable() {
        instance = this;
        API = new EventCoreAPIImpl(this);
        EventCoreAPI.Factory.API = API;

        datafile = new File(instance.getDataFolder(), "data.yml");
        dataconfig = YamlConfiguration.loadConfiguration(datafile);

        // Registering bStats
        new Metrics(this, 19718);

        new AliveList().register();
        new ClearAlive().register();
        new GiveAlive().register();
        new HealAlive().register();
        new KillAlive().register();
        new TpAlive().register();

        // ---
        new DeadList().register();
        new ClearDead().register();
        new GiveDead().register();
        new HealDead().register();
        new KillDead().register();
        new TpDead().register();

        // ---
        new BalRevive().register();
        new GiveRevive().register();
        new RecentRev().register();
        new Revive().register();
        new ReviveAll().register();
        new ReviveLate().register();
        new SetRevive().register();
        new TakeRevive().register();
        new ToggleRevive().register();
        new Unrevive().register();
        new UseRevive().register();

        // ---
        new ClearChat().register();
        new MainCommand().register("eventcore");
        new Mutechat().register();
        new Visibility().register();


        // Registering PlaceholderAPI
        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            getLogger().info("PlaceholderAPI found! Registering placeholders...");
            new PlaceholderAPI().register();
            getLogger().info("All Placeholders loaded!");
        }
        else{
            getLogger().warning("PlaceholderAPI not found, skipping...");
        }

        // Registering Skript
        if (Bukkit.getPluginManager().getPlugin("Skript") != null){
            getLogger().info("Skript found! Registering elements...");
            try {
                Skript.registerAddon(this)
                        .loadClasses("lol.aabss.eventcore", "hooks.skript")
                        .setLanguageFileDirectory("lang");
            } catch (IOException e) {
                throw new RuntimeException();
            }
            getLogger().info("All Skript elements loaded!");
        }
        else{
            getLogger().warning("Skript not found, skipping...");
        }


        // Registering all events
        getServer().getPluginManager().registerEvents(new Listeners(), this);

        // Messages
        getLogger().info("EventCore config loading...");
        if (!datafile.exists()) {
            saveResource("data.yml", false);
        }
        saveDefaultConfig();
        UPDATE_CHECKER = getConfig().getBoolean("update-checker");
        getLogger().info("EventCore config loaded!");
        getLogger().info("EventCore is now enabled!");
    }

    @Override
    public void onDisable() {
        Bukkit.getScheduler().cancelTasks(this);
        getLogger().info("EventCore disabled!");
    }

    public static String formatList(List<?> list){
        List<String> stringlist = new ArrayList<>();
        list.forEach(o -> stringlist.add(o.toString()));
        Collections.sort(stringlist);
        StringBuilder builder = new StringBuilder();
        int i = 0;
        if (stringlist.size() == 1){
            return stringlist.get(0);
        }
        for (Object obj : stringlist){
            if (i == stringlist.size() - 1) {
                builder.append(obj);
            } else if (i == stringlist.size() - 2) {
                builder.append(obj).append(" and ");
            } else {
                builder.append(obj).append(", ");
            }
            i++;
        }
        return builder.toString();
    }

}
