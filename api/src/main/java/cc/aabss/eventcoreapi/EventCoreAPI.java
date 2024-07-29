package cc.aabss.eventcoreapi;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Main class for the EventCore API.
 */
public interface EventCoreAPI {

    /**
     * Made for user ease of access.
     * @return The EventCore API instance.
     */
    static EventCoreAPI getAPI() {
        return Factory.API;
    }

    /**
     * Gets all the players that are alive/aren't dead.
     * @return All the alive event players.
     */
    List<Player> getAlive();

    /**
     * Gets all the players that aren't alive/are dead.
     * @return All the dead event players.
     */
    List<Player> getDead();

    /**
     * Gets all the players that died but were alive before they died.
     * @return All the former alive players that died.
     */
    List<Player> getRecentlyDead();

    /**
     * If the player is alive/isn't dead, true.
     * @param p The player.
     * @return True if the player is alive.
     */
    boolean isAlive(Player p);

    /**
     * If the player isn't alive/is dead, true.
     * @param p The player.
     * @return True if the player is dead.
     */
    boolean isDead(Player p);

    /**
     * If the player has been recently killed and was alive, true.
     * @param p The player.
     * @return True if the player was alive and has been recently killed.
     */
    boolean isRecentlyDead(Player p);

    /**
     * Gets all the revives in the revive balance of a player.
     * @param p The player.
     * @return Size of all the revives of the player.
     */
    int getRevives(Player p);

    /**
     * Sets the revive amount of the specified player.
     * @param p The player.
     * @param i The new amount of revives.
     */
    void setRevives(Player p, Integer i);

    /**
     * Takes the revive amount from the player's revive balance.
     * @param p The player.
     * @param i The amount of revives to take.
     */
    void takeRevives(Player p, Integer i);

    /**
     * Adds the revive amount from the player's revive balance.
     * @param p The player.
     * @param i The amount of revives to add.
     */
    void addRevives(Player p, Integer i);

    /**
     * Revives a player at the specified location, reviver will be set to console.
     * @param p The player to be revived.
     * @param location The location to teleport the {@param p player} to.
     */
    void revive(Player p, Location location);

    /**
     * Revives a player, reviver will be set to console.
     * @param p The player to be revived.
     */
    void revive(Player p);

    /**
     * Makes the {@param reviver} revive the {@param revived} and optionally teleport the {@param revived} to the {@param reviver}.
     * @param revived The player to be revived.
     * @param reviver The player that will revive.
     * @param teleport Whether you the {@param revived} should be teleported to the {@param reviver}.
     */
    void revive(Player revived, Player reviver, boolean teleport);

    /**
     * Unrevives the player and optionally kills them.
     * @param p The player.
     * @param kill Whether the player should be killed.
     */
    void unrevive(Player p, boolean kill);

    /**
     * Toggles whether people are allowed to use revive balance.
     */
    void toggleRevives();

    /**
     * Gets the visibility state of a player.
     * @param p The player.
     * @return The visibility state of a player.
     */
    VisibilityState getVisibilityState(Player p);

    /**
     * Sets the visibility state of a player
     * @param p The player.
     * @param state The new visibility state.
     */
    void setVisibilityState(Player p, VisibilityState state);

    /**
     * Clears the chat (aka. sends a bunch of new lines).
     * @param sendMessage Whether it should tell everyone that the chat just got cleared.
     */
    void clearChat(boolean sendMessage);

    /**
     * Clears the chat (aka. sends a bunch of new lines).
     * @param sendMessage Whether it should tell everyone that the chat just got cleared.
     * @param sender The sender that will be clearing the chat.
     */
    void clearChat(boolean sendMessage, CommandSender sender);

    /**
     * Super simple class to get the EventCore API
     */
    class Factory {
        /**
         * The EventCore API
         */
        public static EventCoreAPI API;

        /**
         * Don't use this.
         */
        public Factory() {
            throw new RuntimeException();
        }
    }
}

