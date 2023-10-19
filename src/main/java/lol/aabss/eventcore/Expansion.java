package lol.aabss.eventcore;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Expansion extends PlaceholderExpansion {


    @Override
    public @NotNull String getIdentifier() {
        return "eventcore";
    }

    @Override
    public @NotNull String getAuthor() {
        return "aabss";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public @Nullable String onPlaceholderRequest(Player player, @NotNull String params) {
        if (params.equals("alive")){
            return EventCore.Alive.size() + "";
        }
        if (params.equals("dead")) {
            return EventCore.Dead.size() + "";
        }
        if (params.equals("status")){
            if (player == null){
                return null;
            }
            else{
                if (EventCore.Dead.contains(player.getName())){
                    return "Dead";
                }
                else if (EventCore.Alive.contains(player.getName())){
                    return "Alive";
                }
                else{
                    return null;
                }
            }
        }
        return params;
    }
}
