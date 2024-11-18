package app.qwertz.eventcore;

import app.qwertz.eventcore.api.EventCoreAPIImpl;
import app.qwertz.eventcore.commands.alive.*;
import app.qwertz.eventcore.commands.dead.*;
import app.qwertz.eventcore.commands.other.*;
import app.qwertz.eventcore.commands.other.Timer;
import app.qwertz.eventcore.commands.revives.*;
import app.qwertz.eventcore.hooks.PlaceholderAPI;
import app.qwertz.eventcore.api.EventCoreAPI;

import app.qwertz.eventcore.util.Listeners;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.lifecycle.event.LifecycleEventManager;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import net.minecraft.commands.CommandBuildContext;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.CompletableFuture;

import static app.qwertz.eventcore.hooks.UpdateChecker.UPDATE_CHECKER;

public class EventCore extends JavaPlugin {

    public static EventCore instance;

    public List<Player> Alive = new ArrayList<>();
    public List<Player> Dead = new ArrayList<>();
    public List<Player> Recent = new ArrayList<>();

    public File datafile;
    public FileConfiguration dataconfig;
    public static EventCoreAPI API;
    public static CommandBuildContext COMMAND_BUILD_CONTEXT;
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

        // Permissions -----

        addPermission("eventcore.visibility.staffbypass", "Lets players see the permission holder during hide staff.");
        addPermission("eventcore.visibility.all", "Use '/visibility all'.");
        addPermission("eventcore.visibility.staff", "Use '/visibility staff'.");
        addPermission("eventcore.visibility.off", "Use '/visibility off'.");
        addPermission("eventcore.reviveall.bypass", "Won't revive the permission holder in a revive all.");


        // Commands --------

        if (Bukkit.getServer() instanceof CraftServer server) {
            COMMAND_BUILD_CONTEXT = CommandBuildContext.simple(server.getServer().registryAccess(), server.getServer().getWorldData().getDataConfiguration().enabledFeatures());
        }
        LifecycleEventManager<@NotNull Plugin> manager = this.getLifecycleManager();
        manager.registerEventHandler(LifecycleEvents.COMMANDS, event -> {

            Commands commands = event.registrar();
            new AliveList("alivelist", "Shows all alive players.").register(commands);
            new ClearAlive("clearalive", "Clears the inventory of all alive players.").register(commands);
            new GiveAlive("givealive", "Gives all alive players items.").register(commands);
            new HealAlive("healalive", "Heals all alive players.").register(commands);
            new KillAlive("killalive", "Kills all alive players.").register(commands);
            new PotionAlive("potionalive", "Applies a potion effect to all alive players.", "effectalive").register(commands);
            new TpAlive("tpalive", "Teleports all alive players to sender.", "teleportalive").register(commands);

            // ---
            new DeadList("deadlist", "Shows all dead players.").register(commands);
            new ClearDead("cleardead", "Clears the inventory of all dead players.").register(commands);
            new GiveDead("givedead", "Gives all dead players items.").register(commands);
            new HealDead("healdead", "Heals all dead players.").register(commands);
            new KillDead("killdead", "Kills all dead players.").register(commands);
            new PotionDead("potiondead", "Applies a potion effect to all dead players.", "effectdead").register(commands);
            new TpDead("tpdead", "Teleports all dead players to sender.", "teleportdead").register(commands);

            // ---
            new BalRevive("balrevive", "Gets the revive balance of a player.", "balrev", "revbal", "revivebal", "revivebalance").register(commands);
            new GiveRevive("giverevive", "Gives a revival token to a player.", "addrevive", "giverev", "addrev").register(commands);
            new RecentRev("recentrev", "Revives recently killed players.", "revrecent").register(commands);
            new Revive("revive", "Revives a player.", "rev").register(commands);
            new ReviveAll("reviveall", "Revives all players.", "revall").register(commands);
            new ReviveLate("revivelate", "Revives all dead players.", "revlate").register(commands);
            new SetRevive("setrevive", "Sets the revival token amount of a player.").register(commands);
            new TakeRevive("takerevive", "Takes a revival token from a player.", "removerevive").register(commands);
            new ToggleRevive("togglerevive", "Toggles the use of revivals.", "revivetoggle").register(commands);
            new Unrevive("unrevive", "Unrevives a player.").register(commands);
            new UseRevive("userevive", "Uses a revival token.").register(commands);

            // ---
            new ClearChat("clearchat", "Clears the chat.", "chatclear").register(commands);
            new MainCommand("eventcore", "Main command for EventCore.").register(commands);
            new Mutechat("mutechat", "Mutes the chat.", "chatmute").register(commands);
            new Revival("revival", "Runs a revival. (use quotes for spaces.)").register(commands);
            new Timer("timer", "Makes a new timer.", "countdown").register(commands);
            new Visibility("visibility", "Toggles player visibility.", "hide").register(commands);
        });

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
                        .loadClasses("app.qwertz.eventcore", "hooks.skript")
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

    public void addPermission(String perm, String description) {
        if (Bukkit.getPluginManager().getPermission(perm) == null) {
            Bukkit.getPluginManager().addPermission(new Permission(perm, description));
        }
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
