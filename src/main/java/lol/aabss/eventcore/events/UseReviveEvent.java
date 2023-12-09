package lol.aabss.eventcore.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class UseReviveEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private final Player revived;
    private final Integer oldamount;
    private Integer amount;

    /**
     * @param revived The player requesting to be revived
     * @param amount The amount of revives the player has
     * @param oldamount The old amount of revives the player has
     */
    public UseReviveEvent(Player revived, Integer amount, Integer oldamount){
        this.revived = revived;
        this.amount = amount;
        this.oldamount = oldamount;
    }

    /**
     * @return The player requesting to be revived
     */
    @NotNull
    public Player getRevived() {
        return revived;
    }

    /**
     * @return The amount of revives the player has.
     */
    @NotNull
    public Integer getReviveAmount() {
        return amount;
    }

    /**
     * @param amount Sets the revive balance of the player getting revived
     */
    @NotNull
    public void setReviveAmount(Integer amount) {
        this.amount = amount;
    }

    /**
     * @return The amount of revives the player had before using a revive.
     */
    @NotNull
    public Integer getOldReviveAmount() {
        return oldamount;
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
