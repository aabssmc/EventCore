package lol.aabss.eventcore.api;

import lol.aabss.eventcore.EventCore;
import lol.aabss.eventcore.commands.Visibility;
import lol.aabss.eventcore.commands.revives.ToggleRevive;
import aabss.eventcoreapi.EventCoreAPI;
import aabss.eventcoreapi.VisibilityState;
import lol.aabss.eventcore.events.ReviveEvent;
import lol.aabss.eventcore.events.VisibilityEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.List;

import static lol.aabss.eventcore.EventCore.instance;
import static lol.aabss.eventcore.util.Config.reloadConfig;

public class EventCoreAPIImpl implements EventCoreAPI {

    private final EventCore plugin;

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
        ToggleRevive.REVIVES = !ToggleRevive.REVIVES;
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
}
