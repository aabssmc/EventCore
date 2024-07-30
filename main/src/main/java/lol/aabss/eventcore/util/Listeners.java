package lol.aabss.eventcore.util;

import lol.aabss.eventcore.EventCore;
import lol.aabss.eventcore.commands.other.Visibility;
import lol.aabss.eventcore.hooks.UpdateChecker;
import lol.aabss.eventcore.util.fastboard.FastBoard;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.List;
import java.util.UUID;

import static lol.aabss.eventcore.commands.other.Mutechat.CHAT_MUTED;
import static lol.aabss.eventcore.hooks.UpdateChecker.UPDATE_CHECKER;
import static lol.aabss.eventcore.EventCore.*;
import static lol.aabss.eventcore.util.Config.*;
import static lol.aabss.eventcore.util.fastboard.FastBoard.BOARDS;

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
        if (instance.getConfig().getBoolean("enable-scoreboard", false)){
            updateBoard(p);
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
