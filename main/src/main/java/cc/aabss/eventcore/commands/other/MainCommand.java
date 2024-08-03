package cc.aabss.eventcore.commands.other;

import cc.aabss.eventcore.EventCore;
import cc.aabss.eventcore.util.Config;
import cc.aabss.eventcore.util.SimpleCommand;
import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

import static net.kyori.adventure.text.minimessage.MiniMessage.miniMessage;

public class MainCommand implements SimpleCommand {

    @Override
    public void run(CommandSender sender, Command command, String[] args) {
        if (args[0].equals("reload")) {
            EventCore.instance.reloadConfig();
            Config.reloadConfig();
            sender.sendMessage(Config.msg("reloaded"));
        } else {
            sender.sendMessage(permissionMessage());
        }
    }

    @Override
    public List<String> tabComplete(CommandSender sender, Command command, String[] args) {
        return List.of("reload");
    }

    @Override
    public Component permissionMessage() {
        return miniMessage().deserialize("<br><click:open_url:'https://modrinth.com/plugin/event'><b><gold>EventCore</gold></b> <yellow>by aabss</yellow> <gray>(@big.abs)</gray></click><br>");
    }

    @Override
    public String permission() {
        return "eventcore.command.main";
    }

}
