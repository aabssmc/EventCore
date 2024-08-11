package cc.aabss.eventcore.util;

import cc.aabss.eventcore.EventCore;
import cc.aabss.eventcore.commands.other.Mutechat;
import cc.aabss.eventcore.commands.other.Visibility;
import cc.aabss.eventcore.hooks.UpdateChecker;
import cc.aabss.eventcore.util.fastboard.FastBoard;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import static cc.aabss.eventcore.hooks.UpdateChecker.UPDATE_CHECKER;
import static cc.aabss.eventcore.util.Config.*;
import static cc.aabss.eventcore.util.fastboard.FastBoard.BOARDS;

public class Listeners implements org.bukkit.event.Listener {

    @EventHandler
    public void onLeave(PlayerQuitEvent event){
        Player p = event.getPlayer();
        EventCore.API.unrevive(p, false);
        EventCore.instance.Dead.removeIf(player -> player.getUniqueId().equals(p.getUniqueId()));
        for (Player player : Visibility.VisStaff){
            player.showPlayer(EventCore.instance, p);
        }
        for (Player player : Visibility.VisAll){
            player.showPlayer(EventCore.instance, p);
        }
        for (UUID uuid : BOARDS.keySet()){
            if (p.getUniqueId().equals(uuid)){
                BOARDS.get(uuid).delete();
                BOARDS.remove(p.getUniqueId());
                return;
            }
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player p = event.getPlayer();
        EventCore.instance.Recent.removeIf(player -> player.getUniqueId().equals(p.getUniqueId()));
        EventCore.instance.Alive.removeIf(player -> player.getUniqueId().equals(p.getUniqueId()));
        EventCore.instance.Dead.removeIf(player -> player.getUniqueId().equals(p.getUniqueId()));
        EventCore.instance.Dead.add(event.getPlayer());
        if (UPDATE_CHECKER && p.hasPermission("eventcore.admin")){
            UpdateChecker.updateCheck(p);
        }
        for (Player player : Visibility.VisStaff){
            if (!p.hasPermission("eventcore.visibility.staffbypass")){
                player.hidePlayer(EventCore.instance, p);
            } else{
                player.showPlayer(EventCore.instance, p);
            }
        }
        for (Player player : Visibility.VisAll){
            player.hidePlayer(EventCore.instance, p);
        }
        if (!EventCore.instance.getConfig().getBoolean("enable-scoreboard", false)) return;
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!p.isOnline()) {
                    this.cancel();
                    return;
                }
                updateBoard(p);
            }
        }.runTaskTimer(EventCore.instance, 0, 20);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event){
        if (EventCore.API.isAlive(event.getPlayer())){
            EventCore.API.unrevive(event.getPlayer(), false);
            if (!EventCore.API.isRecentlyDead(event.getPlayer())){
                EventCore.instance.Recent.add(event.getEntity());
                Bukkit.getScheduler().runTaskLater(EventCore.getPlugin(EventCore.class), () ->
                                EventCore.instance.Recent.remove(event.getEntity()),
                        Config.get("recent-rev-time", Integer.class)*20
                );
            }
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event){
        if (Mutechat.CHAT_MUTED && !event.getPlayer().hasPermission("eventcore.mutechat.bypass")){
            event.setCancelled(true);
            event.getPlayer().sendMessage(msg("mutechat.cant-talk"));
        }
    }

}
