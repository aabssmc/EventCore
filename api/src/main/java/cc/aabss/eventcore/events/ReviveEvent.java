package cc.aabss.eventcore.events;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class ReviveEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private final Player revived;
    private final CommandSender reviver;

    /**
     * @param revived The player that got revived
     * @param reviver The sender that revived the player
     */
    public ReviveEvent(Player revived, CommandSender reviver){
        this.revived = revived;
        this.reviver = reviver;
    }

    /**
     * @return The player that got revived
     */
    @NotNull
    public Player getRevived() {
        return revived;
    }

    /**
     * @return The sender that revived the player
     */
    @NotNull
    public CommandSender getReviver() {
        return reviver;
    }

    public static HandlerList getHandlerList(){
        return handlers;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
