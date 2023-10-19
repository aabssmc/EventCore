package lol.aabss.eventcore;

import lol.aabss.eventcore.Metrics;
import lol.aabss.eventcore.Commands.*;
import lol.aabss.eventcore.Events.Death;
import lol.aabss.eventcore.Events.Join;
import lol.aabss.eventcore.Events.Leave;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public final class EventCore extends JavaPlugin {

    public static ArrayList<String> Alive = new ArrayList<>();
    public static ArrayList<String> Dead = new ArrayList<>();

    @Override
    public void onEnable() {
        // Registering all commands
        getCommand("eventcore").setExecutor(new MainCommand(this));
        getCommand("revive").setExecutor(new Revive(this));
        getCommand("reviveall").setExecutor(new ReviveAll(this));
        getCommand("tpalive").setExecutor(new TpAlive(this));
        getCommand("tpdead").setExecutor(new TpDead(this));
        getCommand("alivelist").setExecutor(new AliveList(this));
        getCommand("visibility").setExecutor(new Visibility(this));

        // Registering PlaceholderAPI
        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new Expansion().register();
        }

        // Registering bStats
        int pluginId = 19718;
        Metrics metrics = new Metrics(this, pluginId);

        // Registering all events
        getServer().getPluginManager().registerEvents(new Death(), this);
        getServer().getPluginManager().registerEvents(new Join(), this);
        getServer().getPluginManager().registerEvents(new Leave(), this);


        // Messages
        System.out.print("EventCore config loading...");
        saveDefaultConfig();
        System.out.print("EventCore config loaded!");
        System.out.print("EventCore is now enabled!");
    }

    @Override
    public void onDisable() {
        System.out.print("EventCore disabled.");
    }
}
