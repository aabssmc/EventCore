package lol.aabss.eventcore.Events;

import lol.aabss.eventcore.EventCore;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class Join implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        EventCore.Alive.remove(event.getPlayer().getName());
        EventCore.Dead.add(event.getPlayer().getName());
    }
}
