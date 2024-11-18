package app.qwertz.eventcore.util;

import app.qwertz.eventcore.EventCore;
import app.qwertz.eventcore.commands.other.Mutechat;
import app.qwertz.eventcore.commands.other.Revival;
import app.qwertz.eventcore.commands.other.Visibility;
import app.qwertz.eventcore.hooks.UpdateChecker;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

import static app.qwertz.eventcore.hooks.UpdateChecker.UPDATE_CHECKER;
import static app.qwertz.eventcore.util.Config.*;
import static app.qwertz.eventcore.util.fastboard.FastBoard.BOARDS;

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

        if (Revival.answer != null) {
            if (event.getMessage().equals(Revival.answer) && !Revival.winners.contains(event.getPlayer().getName())) {
                Revival.winners.add(event.getPlayer().getName());
                if (Revival.winners.size() >= Revival.winnerSize) {
                    Bukkit.broadcast(msg("%prefix% <gold>The winner(s) are: <yellow>"+EventCore.formatList(Revival.winners)));
                    Revival.answer = null;
                    Revival.winners.clear();
                    Revival.winnerSize = 0;
                    if (Config.get("show-revival-messages", Boolean.class)) {
                        Mutechat.CHAT_MUTED = Revival.formerChatMute;
                    }
                }
            }
        }
    }

}
