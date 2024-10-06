package cc.aabss.eventcore;

import cc.aabss.eventcore.api.EventCoreAPIImpl;
import cc.aabss.eventcore.commands.alive.*;
import cc.aabss.eventcore.commands.dead.*;
import cc.aabss.eventcore.commands.other.ClearChat;
import cc.aabss.eventcore.commands.other.Visibility;
import cc.aabss.eventcore.commands.revives.*;
import cc.aabss.eventcore.hooks.PlaceholderAPI;
import cc.aabss.eventcore.api.EventCoreAPI;
import cc.aabss.eventcore.commands.other.MainCommand;
import cc.aabss.eventcore.commands.other.Mutechat;

import cc.aabss.eventcore.util.Listeners;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.CompletableFuture;

import static cc.aabss.eventcore.hooks.UpdateChecker.UPDATE_CHECKER;


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

        new AliveList("alivelist", "Shows all alive players.").register();
        new ClearAlive("clearalive", "Clears the inventory of all alive players.").register();
        new GiveAlive("givealive", "Gives all alive players items.").register();
        new HealAlive("healalive", "Heals all alive players.").register();
        new KillAlive("killalive", "Kills all alive players.").register();
        new PotionAlive("potionalive", "Applys a potion effect to all alive players.", "effectalive").register();
        new TpAlive("tpalive", "Teleports all alive players to sender.", "teleportalive").register();

        // ---
        new DeadList("deadlist", "Shows all dead players.").register();
        new ClearDead("cleardead", "Clears the inventory of all dead players.").register();
        new GiveDead("givedead", "Gives all dead players items.").register();
        new HealDead("healdead", "Heals all dead players.").register();
        new KillDead("killdead", "Kills all dead players.").register();
        new PotionDead("potiondead", "Applys a potion effect to all dead players.", "effectdead").register();
        new TpDead("tpdead", "Teleports all dead players to sender.", "teleportdead").register();

        // ---
        new BalRevive("balrevive", "Gets the revive balance of a player.", "balrev", "revbal", "revivebal", "revivebalance").register();
        new GiveRevive("giverevive", "Gives a revival token to a player.", "addrevive", "giverev", "addrev").register();
        new RecentRev("recentrev", "Revives recently killed players.", "revrecent").register();
        new Revive("revive", "Revives a player.", "rev").register();
        new ReviveAll("reviveall", "Revives all players.", "revall").register();
        new ReviveLate("revivelate", "Revives all dead players.", "revlate").register();
        new SetRevive("setrevive", "Sets the revival token amount of a player.").register();
        new TakeRevive("takerevive", "Takes a revival token from a player.", "removerevive").register();
        new ToggleRevive("togglerevive", "Toggles the use of revivals.", "revivetoggle").register();
        new Unrevive("unrevive", "Unrevives a player.").register();
        new UseRevive("userevive", "Uses a revival token.").register();

        // ---
        new ClearChat("clearchat", "Clears the chat.", "chatclear").register();
        new MainCommand("eventcore", "Main command for EventCore.").register();
        new Mutechat("mutechat", "Mutes the chat.", "chatmute").register();
        new Visibility("visibility", "Toggles player visibility.", "hide").register();

        // Registering PlaceholderAPI
        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            getLogger().info("PlaceholderAPI found! Registering placeholders...");
            new PlaceholderAPI().register();
            getLogger().info("All Placeholders loaded!");
        } else{
            getLogger().warning("PlaceholderAPI not found, skipping...");
        }

        // Registering Skript
        if (Bukkit.getPluginManager().getPlugin("Skript") != null){
            getLogger().info("Skript found! Registering elements...");
            try {
                ch.njol.skript.Skript.registerAddon(this)
                        .loadClasses("cc.aabss.eventcore", "hooks.skript")
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

    public static boolean classExists(String className){
        try {
            Class.forName(className);
            return true;
        } catch (ClassNotFoundException ignored) {
            return false;
        }
    }

    public static CompletableFuture<OfflinePlayer> getOfflinePlayerAsync(String name) {
        return CompletableFuture.supplyAsync(() -> Bukkit.getOfflinePlayer(name));
    }

    public static CompletableFuture<OfflinePlayer> getOfflinePlayerAsync(UUID uuid) {
        return CompletableFuture.supplyAsync(() -> Bukkit.getOfflinePlayer(uuid));
    }

}
