package lol.aabss.eventcore.Events;

import lol.aabss.eventcore.EventCore;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class Leave implements Listener {
    @EventHandler
    public void onLeave(PlayerQuitEvent event){
        EventCore.Alive.remove(event.getPlayer().getName());
        EventCore.Dead.remove(event.getPlayer().getName());
    }
}
