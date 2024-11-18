package app.qwertz.eventcore.api;

import app.qwertz.eventcore.EventCore;
import app.qwertz.eventcore.commands.other.Visibility;
import app.qwertz.eventcore.events.ReviveEvent;
import app.qwertz.eventcore.events.VisibilityEvent;
import net.kyori.adventure.text.TextReplacementConfig;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.io.IOException;
import java.util.List;

import static app.qwertz.eventcore.EventCore.instance;
import static app.qwertz.eventcore.util.Config.msg;
import static app.qwertz.eventcore.util.Config.reloadConfig;

public class EventCoreAPIImpl implements EventCoreAPI {

    private final EventCore plugin;
    private BukkitTask timerTask;
    private long remainingSeconds;

    public EventCoreAPIImpl(EventCore plugin) {
        this.plugin = plugin;
    }

    @Override
    public List<Player> getAlive() {
        return plugin.Alive;
    }

    @Override
    public List<Player> getDead() {
        return plugin.Dead;
    }

    @Override
    public List<Player> getRecentlyDead() {
        return plugin.Recent;
    }

    public boolean isAlive(Player p) {
        return instance.Alive.contains(p);
    }

    public boolean isDead(Player p) {
        return instance.Dead.contains(p);
    }

    public boolean isRecentlyDead(Player p) {
        return instance.Recent.contains(p);
    }

    public int getRevives(OfflinePlayer p) {
        if (plugin.dataconfig.get("revives." + p.getUniqueId()) == null){
            return 0;
        }
        return plugin.dataconfig.getInt("revives." + p.getUniqueId());
    }

    public void setRevives(OfflinePlayer p, Integer i) {
        plugin.dataconfig.set("revives." + p.getUniqueId(), i == 0 ? null : i);
        try {
            plugin.dataconfig.save(plugin.datafile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        reloadConfig();
    }

    public void takeRevives(OfflinePlayer p, Integer i) {
        int balance = getRevives(p)-i;
        plugin.dataconfig.set("revives." + p.getUniqueId(), balance == 0 ? null : balance);
        try {
            plugin.dataconfig.save(plugin.datafile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        reloadConfig();
    }

    public void addRevives(OfflinePlayer p, Integer i) {
        int balance = getRevives(p)+i;
        plugin.dataconfig.set("revives." + p.getUniqueId(), balance == 0 ? null : balance);
        try {
            plugin.dataconfig.save(plugin.datafile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        reloadConfig();
    }

    public void revive(Player p, Location location) {
        revive(p);
        p.teleport(location);
    }

    public void revive(Player p) {
        new ReviveEvent(p, Bukkit.getConsoleSender()).callEvent();
        setRevived(p);
    }

    public void revive(Player revived, Player reviver, boolean teleport) {
        new ReviveEvent(revived, reviver).callEvent();
        setRevived(revived);
        if (teleport) revived.teleport(reviver);
    }

    private void setRevived(Player revived) {
        plugin.Recent.removeIf(player -> player.getUniqueId().equals(revived.getUniqueId()));
        plugin.Dead.removeIf(player -> player.getUniqueId().equals(revived.getUniqueId()));
        plugin.Alive.removeIf(player -> player.getUniqueId().equals(revived.getUniqueId()));
        plugin.Alive.add(revived);
    }

    public void unrevive(Player p, boolean kill) {
        plugin.Recent.removeIf(player -> player.getUniqueId().equals(p.getUniqueId()));
        plugin.Alive.removeIf(player -> player.getUniqueId().equals(p.getUniqueId()));
        plugin.Dead.removeIf(player -> player.getUniqueId().equals(p.getUniqueId()));
        plugin.Dead.add(p);
        if (kill) p.setHealth(0);
    }

    public void toggleRevives() {
        plugin.getConfig().set("revives-enabled", !plugin.getConfig().getBoolean("revives-enabled", true));
    }

    public VisibilityState getVisibilityState(Player p){
        if (Visibility.VisAll.contains(p)){
            return VisibilityState.ALL;
        } else if (Visibility.VisStaff.contains(p)){
            return VisibilityState.STAFF;
        } else {
            return VisibilityState.OFF;
        }
    }

    public void setVisibilityState(Player p, VisibilityState state){
        if (state == VisibilityState.ALL){
            for (Player player : Bukkit.getOnlinePlayers()){
                p.hidePlayer(plugin, player);
            }
            Visibility.VisAll.add(p);
            Visibility.VisStaff.removeIf(player -> player.getUniqueId().equals(p.getUniqueId()));
        } else if (state == VisibilityState.STAFF){
            for (Player player : Bukkit.getOnlinePlayers()) {
                p.hidePlayer(instance, player);
                if (player.hasPermission("eventcore.visibility.staffbypass")) {
                    p.showPlayer(instance, player);
                }
            }
            Visibility.VisStaff.add(p);
            Visibility.VisAll.removeIf(player -> player.getUniqueId().equals(p.getUniqueId()));
        } else if (state == VisibilityState.OFF){
            for (Player player : Bukkit.getOnlinePlayers()) {
                p.showPlayer(instance, player);
            }
            Visibility.VisStaff.removeIf(player -> player.getUniqueId().equals(p.getUniqueId()));
            Visibility.VisAll.removeIf(player -> player.getUniqueId().equals(p.getUniqueId()));
        }
        new VisibilityEvent(p, state).callEvent();
    }

    public void clearChat(boolean sendMessage){
        clearChat(sendMessage, Bukkit.getConsoleSender());
    }

    public void clearChat(boolean sendMessage, CommandSender sender){
        Bukkit.getOnlinePlayers().forEach(p -> {
            p.sendMessage(" \n ".repeat(1000));
            if (sendMessage){
                p.sendMessage(msg("clearchat.cleared").replaceText(TextReplacementConfig.builder().match("%player%").replacement(sender.getName()).build()));
            }
        });
    }

    @Override
    public boolean startTimer(long seconds) {
        if (timerTask != null && !timerTask.isCancelled()) {
            return false;
        }
        remainingSeconds = seconds;
        Bukkit.broadcast(msg("timer.started")
                        .replaceText(TextReplacementConfig.builder().match("%seconds%").replacement(String.valueOf(remainingSeconds)).build())
        );
        timerTask = new BukkitRunnable() {
            @Override
            public void run() {
                if (remainingSeconds <= 0) {
                    Bukkit.broadcast(msg("timer.finished"));
                    cancel();
                } else {
                    Bukkit.broadcast(msg("timer.remaining")
                            .replaceText(TextReplacementConfig.builder().match("%seconds%").replacement(String.valueOf(remainingSeconds)).build())
                    );
                    remainingSeconds--;
                }
            }
        }.runTaskTimer(plugin, 0L, 20L);
        return true;
    }

    @Override
    public boolean stopTimer() {
        if (timerTask != null && !timerTask.isCancelled()) {
            timerTask.cancel();
            Bukkit.broadcast(msg("timer.cancelled"));
            return true;
        } else {
            return false;
        }
    }
}
