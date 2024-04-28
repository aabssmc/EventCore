package lol.aabss.eventcore.commands;

import lol.aabss.eventcore.util.Config;
import lol.aabss.eventcore.util.SimpleCommand;
import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

import static lol.aabss.eventcore.EventCore.instance;
import static lol.aabss.eventcore.util.Config.msg;
import static lol.aabss.eventcore.util.Config.reloadConfig;
import static net.kyori.adventure.text.minimessage.MiniMessage.miniMessage;

public class MainCommand implements SimpleCommand {

    @Override
    public boolean run(CommandSender sender, Command command, String[] args) {
        if (args.length == 0){
            sender.sendMessage(permissionMessage());
            return true;
        }
        if (!args[0].equals("reload")){
            Config.sendMessagePrefix(sender, " <red>/eventcore <reload>");
            return true;
        }
        instance.reloadConfig();
        reloadConfig();
        sender.sendMessage(msg("reloaded"));
        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, Command command, String[] args) {
        return List.of("reload");
    }

    @Override
    public Component permissionMessage() {
        return miniMessage().deserialize("<br><click:open_url:'https://modrinth.com/plugin/event'><b><gold>EventCore</gold></b> <yellow>by aabss</yellow> <gray>(@big.abs)</gray></click><br><dark_gray>thx xCheezie (Articuling)<br>");
    }

    @Override
    public String permission() {
        return "eventcore.command.main";
    }

}
