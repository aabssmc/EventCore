package lol.aabss.eventcore;

import ch.njol.skript.Skript;
import lol.aabss.eventcore.commands.alive.*;
import lol.aabss.eventcore.commands.dead.*;
import lol.aabss.eventcore.commands.revives.*;
import lol.aabss.eventcore.hooks.*;
import lol.aabss.eventcore.commands.*;

import lol.aabss.eventcore.util.Listeners;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import static lol.aabss.eventcore.hooks.UpdateChecker.UPDATE_CHECKER;


public class EventCore extends JavaPlugin {

    public static EventCore instance;

    public static ArrayList<Player> Alive = new ArrayList<>();
    public static ArrayList<Player> Dead = new ArrayList<>();
    public static ArrayList<Player> Recent = new ArrayList<>();

    public static File datafile;
    public static FileConfiguration dataconfig;

    @Override
    public void onEnable() {
        instance = this;

        datafile = new File(instance.getDataFolder(), "data.yml");
        dataconfig = YamlConfiguration.loadConfiguration(datafile);

        // Registering bStats
        new Metrics(this, 19718);

        Objects.requireNonNull(getCommand("alivelist")).setExecutor(new AliveList());
        Objects.requireNonNull(getCommand("clearalive")).setExecutor(new ClearAlive());
        Objects.requireNonNull(getCommand("givealive")).setExecutor(new GiveAlive());
        Objects.requireNonNull(getCommand("healalive")).setExecutor(new HealAlive());
        Objects.requireNonNull(getCommand("killalive")).setExecutor(new KillAlive());
        Objects.requireNonNull(getCommand("tpalive")).setExecutor(new TpAlive());

        // ---
        Objects.requireNonNull(getCommand("cleardead")).setExecutor(new ClearDead());
        Objects.requireNonNull(getCommand("deadlist")).setExecutor(new DeadList());
        Objects.requireNonNull(getCommand("givedead")).setExecutor(new GiveDead());
        Objects.requireNonNull(getCommand("healdead")).setExecutor(new HealDead());
        Objects.requireNonNull(getCommand("killdead")).setExecutor(new KillDead());
        Objects.requireNonNull(getCommand("tpdead")).setExecutor(new TpDead());

        // ---
        Objects.requireNonNull(getCommand("balrevive")).setExecutor(new BalRevive());
        Objects.requireNonNull(getCommand("giverevive")).setExecutor(new GiveRevive());
        Objects.requireNonNull(getCommand("recentrev")).setExecutor(new RecentRev());
        Objects.requireNonNull(getCommand("revive")).setExecutor(new Revive());
        Objects.requireNonNull(getCommand("reviveall")).setExecutor(new ReviveAll());
        Objects.requireNonNull(getCommand("setrevive")).setExecutor(new SetRevive());
        Objects.requireNonNull(getCommand("togglerevive")).setExecutor(new ToggleRevive());
        Objects.requireNonNull(getCommand("takerevive")).setExecutor(new TakeRevive());
        Objects.requireNonNull(getCommand("unrevive")).setExecutor(new Unrevive());
        Objects.requireNonNull(getCommand("userevive")).setExecutor(new UseRevive());

        // ---
        Objects.requireNonNull(getCommand("eventcore")).setExecutor(new MainCommand());
        Objects.requireNonNull(getCommand("mutechat")).setExecutor(new Mutechat());
        Objects.requireNonNull(getCommand("visibility")).setExecutor(new Visibility());

        // Registering tab completions
        Objects.requireNonNull(getCommand("givealive")).setTabCompleter(new GiveAlive());
        Objects.requireNonNull(getCommand("givedead")).setTabCompleter(new GiveDead());
        Objects.requireNonNull(getCommand("balrevive")).setTabCompleter(new BalRevive());
        Objects.requireNonNull(getCommand("giverevive")).setTabCompleter(new GiveRevive());
        Objects.requireNonNull(getCommand("revive")).setTabCompleter(new Revive());
        Objects.requireNonNull(getCommand("setrevive")).setTabCompleter(new SetRevive());
        Objects.requireNonNull(getCommand("takerevive")).setTabCompleter(new TakeRevive());
        Objects.requireNonNull(getCommand("visibility")).setTabCompleter(new Visibility());

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

}
