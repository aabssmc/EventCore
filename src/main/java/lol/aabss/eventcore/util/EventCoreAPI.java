package lol.aabss.eventcore.util;

import lol.aabss.eventcore.EventCore;
import lol.aabss.eventcore.commands.Visibility;
import lol.aabss.eventcore.commands.revives.ToggleRevive;
import lol.aabss.eventcore.events.ReviveEvent;
import lol.aabss.eventcore.events.VisibilityEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.io.IOException;

import static lol.aabss.eventcore.EventCore.instance;
import static lol.aabss.eventcore.util.Config.reloadConfig;

public class EventCoreAPI {

    private final EventCore plugin;

    public EventCoreAPI(EventCore plugin) {
        this.plugin = plugin;
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

    public int getRevives(Player p) {
        if (plugin.dataconfig.get("revives." + p.getUniqueId()) == null){
            return 0;
        }
        return plugin.dataconfig.getInt("revives." + p.getUniqueId());
    }

    public void setRevives(Player p, Integer i) {
        plugin.dataconfig.set("revives." + p.getUniqueId(), i);
        try {
            plugin.dataconfig.save(plugin.datafile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        reloadConfig();
    }

    public void takeRevives(Player p, Integer i) {
        plugin.dataconfig.set("revives." + p.getUniqueId(), getRevives(p)-i);
        try {
            plugin.dataconfig.save(plugin.datafile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        reloadConfig();
    }

    public void addRevives(Player p, Integer i) {
        plugin.dataconfig.set("revives." + p.getUniqueId(), getRevives(p)+i);
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
        if (plugin.Alive.contains(p)){
            unrevive(p, false);
        }
        new ReviveEvent(p, Bukkit.getConsoleSender()).callEvent();
        plugin.Recent.remove(p);
        plugin.Dead.remove(p);
        plugin.Alive.add(p);
    }

    public void revive(Player revived, Player reviver, boolean teleport) {
        if (plugin.Alive.contains(revived)){
            unrevive(revived, false);
        }
        new ReviveEvent(revived, reviver).callEvent();
        plugin.Recent.remove(revived);
        plugin.Dead.remove(revived);
        plugin.Alive.add(revived);
        if (teleport) revived.teleport(reviver);
    }

    public void unrevive(Player p, boolean kill) {
        plugin.Recent.remove(p);
        plugin.Alive.remove(p);
        plugin.Dead.add(p);
        if (kill) p.setHealth(0);
    }

    public void toggleRevives() {
        ToggleRevive.REVIVES = !ToggleRevive.REVIVES;
    }

    public VisibilityEvent.VisibilityState getVisibilityState(Player p){
        if (Visibility.VisAll.contains(p)){
            return VisibilityEvent.VisibilityState.ALL;
        } else if (Visibility.VisStaff.contains(p)){
            return VisibilityEvent.VisibilityState.STAFF;
        } else {
            return VisibilityEvent.VisibilityState.OFF;
        }
    }

    public void setVisibilityState(Player p, VisibilityEvent.VisibilityState state){
        if (state == VisibilityEvent.VisibilityState.ALL){
            for (Player player : Bukkit.getOnlinePlayers()){
                p.hidePlayer(plugin, player);
            }
            Visibility.VisAll.add(p);
            Visibility.VisStaff.remove(p);
        } else if (state == VisibilityEvent.VisibilityState.STAFF){
            for (Player player : Bukkit.getOnlinePlayers()) {
                p.hidePlayer(instance, player);
                if (player.hasPermission("eventcore.visibility.staffbypass")) {
                    p.showPlayer(instance, player);
                }
            }
            Visibility.VisStaff.add(p);
            Visibility.VisAll.remove(p);
        } else if (state == VisibilityEvent.VisibilityState.OFF){
            for (Player player : Bukkit.getOnlinePlayers()) {
                p.showPlayer(instance, player);
            }
            Visibility.VisStaff.remove(p);
            Visibility.VisAll.remove(p);
        }
        new VisibilityEvent(p, state).callEvent();
    }
}
