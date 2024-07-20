package lol.aabss.eventcore.util;

import lol.aabss.eventcore.EventCore;
import lol.aabss.eventcore.commands.Visibility;
import lol.aabss.eventcore.hooks.UpdateChecker;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import static lol.aabss.eventcore.commands.Mutechat.CHAT_MUTED;
import static lol.aabss.eventcore.hooks.UpdateChecker.UPDATE_CHECKER;
import static lol.aabss.eventcore.util.Config.msg;
import static lol.aabss.eventcore.EventCore.*;

public class Listeners implements org.bukkit.event.Listener {

    @EventHandler
    public void onLeave(PlayerQuitEvent event){
        Player p = event.getPlayer();
        API.unrevive(p, false);
        instance.Dead.removeIf(player -> player.getUniqueId().equals(p.getUniqueId()));
        for (Player player : Visibility.VisStaff){
            player.showPlayer(instance, p);
        }
        for (Player player : Visibility.VisAll){
            player.showPlayer(instance, p);
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player p = event.getPlayer();
        instance.Recent.removeIf(player -> player.getUniqueId().equals(p.getUniqueId()));
        instance.Alive.removeIf(player -> player.getUniqueId().equals(p.getUniqueId()));
        instance.Dead.removeIf(player -> player.getUniqueId().equals(p.getUniqueId()));
        instance.Dead.add(event.getPlayer());
        if (UPDATE_CHECKER && p.hasPermission("eventcore.admin")){
            UpdateChecker.updateCheck(p);
        }
        for (Player player : Visibility.VisStaff){
            if (!p.hasPermission("eventcore.visibility.staffbypass")){
                player.hidePlayer(instance, p);
            } else{
                player.showPlayer(instance, p);
            }
        }
        for (Player player : Visibility.VisAll){
            player.hidePlayer(instance, p);
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event){
        if (API.isAlive(event.getPlayer())){
            API.unrevive(event.getPlayer(), false);
            if (!API.isRecentlyDead(event.getPlayer())){
                instance.Recent.add(event.getEntity());
                Bukkit.getScheduler().runTaskLater(EventCore.getPlugin(EventCore.class), () ->
                                instance.Recent.remove(event.getEntity()),
                        Config.get("recent-rev-time", Integer.class)*20
                );
            }
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event){
        if (CHAT_MUTED && !event.getPlayer().hasPermission("eventcore.mutechat.bypass")){
            event.setCancelled(true);
            event.getPlayer().sendMessage(msg("mutechat.cant-talk"));
        }
    }

}
