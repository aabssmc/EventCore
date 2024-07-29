package lol.aabss.eventcore.events;

import cc.aabss.eventcoreapi.VisibilityState;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public class VisibilityEvent extends Event {

    /**
     * @param player The player changing visibility.
     * @param state The state of the visibility.
     */
    public VisibilityEvent(Player player, VisibilityState state){
        this.player = player;
        this.state = state;
    }

    private final Player player;
    private final VisibilityState state;

    public Player getPlayer(){
        return player;
    }

    public VisibilityState getState(){
        return state;
    }

    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlerList(){
        return handlers;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
