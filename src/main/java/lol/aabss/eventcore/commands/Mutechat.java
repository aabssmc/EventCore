package lol.aabss.eventcore.commands;

import lol.aabss.eventcore.Config;
import lol.aabss.eventcore.EventCore;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import static lol.aabss.eventcore.EventCore.chatMuted;

public class Mutechat implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        String permmessage = Config.getString("permission-message");
        String prefix = Config.getString("prefix");
        if (sender.hasPermission("eventcore.mutechat")){
            if (chatMuted){
                chatMuted = false;
                Bukkit.broadcastMessage(Config.color(prefix + " &aChat is now unmuted"));
            }
            else{
                chatMuted = true;
                Bukkit.broadcastMessage(Config.color(prefix + " &cChat is now muted"));
            }
        }
        else {
            sender.sendMessage(Config.color(prefix + " " + permmessage));
        }
        return true;
    }
}
