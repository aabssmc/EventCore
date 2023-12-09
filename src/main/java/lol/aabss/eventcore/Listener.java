package lol.aabss.eventcore;

import lol.aabss.eventcore.hooks.UpdateChecker;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

import static lol.aabss.eventcore.EventCore.chatMuted;

public class Listener implements org.bukkit.event.Listener {

    private final EventCore plugin;

    public Listener(EventCore plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event){
        EventCore.Alive.remove(event.getPlayer().getName());
        EventCore.Dead.remove(event.getPlayer().getName());
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player p = event.getPlayer();
        EventCore.Alive.remove(event.getPlayer().getName());
        EventCore.Dead.add(event.getPlayer().getName());
        if (p.hasPermission("eventcore.admin")){
            String prefix = this.plugin.getConfig().getString("prefix");
            new UpdateChecker(plugin, 113142).getVersion(version -> {
                if (!this.plugin.getDescription().getVersion().equals(version)) {
                    assert prefix != null;
                    String pr = Config.color(prefix);
                    TextComponent message = getTextComponent(version, pr);
                    p.spigot().sendMessage(message);
                }
            });
        }
    }

    @NotNull
    private static TextComponent getTextComponent(String version, String pr) {
        TextComponent message = new TextComponent("\n" + pr + ChatColor.GOLD + " Update " + version + " is out! Download it at" + ChatColor.YELLOW + " " + ChatColor.ITALIC + "https://www.spigotmc.org/resources/113124/\n");
        message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor.YELLOW + "" + ChatColor.UNDERLINE + "spigotmc.org/resources/113124/")));
        message.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.spigotmc.org/resources/113142/"));
        return message;
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event){
        EventCore.Alive.remove(event.getEntity().getName());
        EventCore.Dead.remove(event.getEntity().getName());
        EventCore.Dead.add(event.getEntity().getName());
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event){
        if (!chatMuted){
            if (!event.getPlayer().hasPermission("eventcore.mutechat.bypass")){
                String prefix = Config.getString("prefix");
                event.setCancelled(true);
                event.getPlayer().sendMessage(prefix + " &cChat is muted!");
            }
        }
    }

}
