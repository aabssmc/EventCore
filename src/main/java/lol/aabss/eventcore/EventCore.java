package lol.aabss.eventcore;

import lol.aabss.eventcore.commands.alive.AliveList;
import lol.aabss.eventcore.commands.alive.TpAlive;
import lol.aabss.eventcore.commands.dead.DeadList;
import lol.aabss.eventcore.commands.dead.Revive;
import lol.aabss.eventcore.commands.dead.ReviveAll;
import lol.aabss.eventcore.commands.dead.TpDead;
import lol.aabss.eventcore.hooks.PlaceholderAPI;
import lol.aabss.eventcore.commands.*;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public class EventCore extends JavaPlugin {

    public static ArrayList<String> Alive = new ArrayList<>();
    public static ArrayList<String> Dead = new ArrayList<>();


    @Override
    public void onEnable() {

        // Registering bStats
        Metrics metrics = new Metrics(this, 19718);

        // Registering all commands
        getCommand("eventcore").setExecutor(new MainCommand(this));
        getCommand("revive").setExecutor(new Revive(this));
        getCommand("reviveall").setExecutor(new ReviveAll(this));
        getCommand("tpalive").setExecutor(new TpAlive(this));
        getCommand("tpdead").setExecutor(new TpDead(this));
        getCommand("alivelist").setExecutor(new AliveList(this));
        getCommand("visibility").setExecutor(new Visibility(this));
        getCommand("deadlist").setExecutor(new DeadList(this));

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
