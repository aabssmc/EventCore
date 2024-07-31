package lol.aabss.eventcore.commands.other;

import lol.aabss.eventcore.util.SimpleCommand;
import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static lol.aabss.eventcore.EventCore.instance;
import static lol.aabss.eventcore.util.Config.msg;
import static lol.aabss.eventcore.util.Config.reloadConfig;
import static net.kyori.adventure.text.minimessage.MiniMessage.miniMessage;

public class MainCommand implements SimpleCommand {

    @Override
    public boolean run(CommandSender sender, Command command, String[] args) {
        switch (args[0]){
            case "reload" -> {
                instance.reloadConfig();
                reloadConfig();
                sender.sendMessage(msg("reloaded"));
            }
            case "scoreboard" -> {
                if (args.length > 1){
                    if (args[1].equals("line")){
                        if (args.length > 3){
                            try {
                                List<String> list = instance.getConfig().getStringList("scoreboard-lines");
                                List<String> lineList = new ArrayList<>(Arrays.stream(args).toList());
                                lineList.remove(0); lineList.remove(1); lineList.remove(2);
                                try {
                                    list.set(Integer.parseInt(args[2]) - 1, String.join(" ", lineList));
                                } catch (IndexOutOfBoundsException ignored){
                                    list.add(String.join(" ", lineList));
                                }
                                instance.getConfig().set("scoreboard-lines", list);
                                instance.reloadConfig();
                                reloadConfig();
                                sender.sendMessage(msg("success"));
                            } catch (NumberFormatException ignored){
                                sender.sendMessage(msg("number-out-of-bounds"));
                            }
                        }
                    } else if (args[1].equals("title")){
                        if (args.length > 2){
                            List<String> titleList = new ArrayList<>(Arrays.stream(args).toList());
                            titleList.remove(0); titleList.remove(1);
                            instance.getConfig().set("scoreboard-title", String.join(" ", titleList));
                            instance.reloadConfig();
                            reloadConfig();
                            sender.sendMessage(msg("success"));
                        }
                    } else {
                        sender.sendMessage(msg("<red>/eventcore scoreboard <line|title> <integer|string> [string]"));
                    }
                }
            }
            default -> sender.sendMessage(permissionMessage());
        }
        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, Command command, String[] args) {
        if (args[0].equals("scoreboard")){
            if (args.length > 1){
                if (args[1].equals("line")){
                    List<String> numbers = new ArrayList<>();
                    for (int i = 1; i < instance.getConfig().getStringList("scoreboard-lines").size(); i++){
                        numbers.add(i+"");
                    }
                    return numbers;
                }
            }
            return List.of("line", "title");
        }
        return List.of("reload", "scoreboard");
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
