package lol.aabss.eventcore;

import lol.aabss.eventcore.commands.alive.*;
import lol.aabss.eventcore.commands.dead.*;
import lol.aabss.eventcore.commands.revives.*;
import lol.aabss.eventcore.hooks.*;
import lol.aabss.eventcore.commands.*;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Objects;

public class EventCore extends JavaPlugin {

    public static ArrayList<String> Alive = new ArrayList<>();
    public static ArrayList<String> Dead = new ArrayList<>();

    @Override
    public void onEnable() {

        // Registering bStats
        Metrics metrics = new Metrics(this, 19718);

        // Registering all commands
        Objects.requireNonNull(getCommand("alivelist")).setExecutor(new AliveList());
        Objects.requireNonNull(getCommand("clearalive")).setExecutor(new ClearAlive());
        Objects.requireNonNull(getCommand("givealive")).setExecutor(new GiveAlive());
        Objects.requireNonNull(getCommand("killalive")).setExecutor(new KillAlive());
        Objects.requireNonNull(getCommand("tpalive")).setExecutor(new TpAlive());

        // ---
        Objects.requireNonNull(getCommand("cleardead")).setExecutor(new ClearDead());
        Objects.requireNonNull(getCommand("deadlist")).setExecutor(new DeadList());
        Objects.requireNonNull(getCommand("givedead")).setExecutor(new GiveDead());
        Objects.requireNonNull(getCommand("killdead")).setExecutor(new KillDead());
        Objects.requireNonNull(getCommand("tpdead")).setExecutor(new TpDead());

        // ---
        Objects.requireNonNull(getCommand("revive")).setExecutor(new Revive());
        Objects.requireNonNull(getCommand("reviveall")).setExecutor(new ReviveAll());
        Objects.requireNonNull(getCommand("balrevive")).setExecutor(new BalRevive());
        Objects.requireNonNull(getCommand("giverevive")).setExecutor(new GiveRevive());
        Objects.requireNonNull(getCommand("setrevive")).setExecutor(new SetRevive());
        Objects.requireNonNull(getCommand("takerevive")).setExecutor(new TakeRevive());
        Objects.requireNonNull(getCommand("userevive")).setExecutor(new UseRevive());

        // ---
        Objects.requireNonNull(getCommand("eventcore")).setExecutor(new MainCommand());
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
            getLogger().info("PlaceholderAPI not found, skipping...");
        }

        // Registering all events
        getServer().getPluginManager().registerEvents(new Listener(this), this);

        // Update Checker
        new UpdateChecker(this, 113142).getVersion(version -> {
            if (this.getDescription().getVersion().equals(version)) {
                getLogger().info("You are on the latest version!.");
            } else {
                getLogger().info("\nThere is a new update available at https://www.spigotmc.org/resources/113142/\n");
            }
        });

    // Messages
        getLogger().info("EventCore config loading...");
        saveDefaultConfig();
        getLogger().info("EventCore config loaded!");
        getLogger().info("EventCore is now enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("EventCore disabled!");
    }

}
