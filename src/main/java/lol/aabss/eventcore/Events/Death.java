package lol.aabss.eventcore.Events;

import lol.aabss.eventcore.EventCore;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class Death implements Listener {
    @EventHandler
    public void onDeath(PlayerDeathEvent event){
        if (EventCore.Alive.contains(event.getEntity().getName())){
            EventCore.Dead.add(event.getEntity().getName());
            EventCore.Alive.remove(event.getEntity().getName());
        }
    }
}
