package cc.aabss.eventcore.commands.other;

import cc.aabss.eventcore.EventCore;
import cc.aabss.eventcore.util.Config;
import cc.aabss.eventcore.util.SimpleCommand;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.Nullable;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static net.kyori.adventure.text.minimessage.MiniMessage.miniMessage;

public class MainCommand extends SimpleCommand {

    public MainCommand(@NotNull String name, @Nullable String description, @Nullable String... aliases) {
        super(name, description, aliases);
    }

    @Override
    public void run(CommandSender sender, String commandLabel, String[] args) {
        if (args[0].equals("reload")) {
            EventCore.instance.reloadConfig();
            Config.reloadConfig();
            sender.sendMessage(Config.msg("reloaded"));
        } else {
            sender.sendMessage(permissionMessage());
        }
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String commandLabel, String[] args) {
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
