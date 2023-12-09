package lol.aabss.eventcore.hooks;

import lol.aabss.eventcore.Config;
import lol.aabss.eventcore.EventCore;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class PlaceholderAPI extends PlaceholderExpansion {


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
        else if (params.equals("dead")) {
            return EventCore.Dead.size() + "";
        }
        else if (params.equals("status")){
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
        else if (params.equals("revive") || params.equals("revives")){
            if (player == null){
                return null;
            }
            else{
                return Config.getRevives(player).toString();
            }
        }
        return params;
    }
}
