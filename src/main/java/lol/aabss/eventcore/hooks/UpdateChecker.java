package lol.aabss.eventcore.hooks;

import org.bukkit.command.CommandSender;
import org.json.JSONArray;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Objects;

import static lol.aabss.eventcore.EventCore.instance;
import static net.kyori.adventure.text.minimessage.MiniMessage.miniMessage;

@SuppressWarnings("deprecation")
public class UpdateChecker {

    public static boolean UPDATE_CHECKER;

    public static String latestVersion() {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.modrinth.com/v2/project/event/version"))
                .build();
        try {
            String body = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString()).get().body();
            return new JSONArray(body).getJSONObject(0).getString("version_number");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void updateCheck(CommandSender p){
        String latest = latestVersion();
        if (!Objects.equals(latest, instance.getDescription().getVersion())){
            p.sendMessage(miniMessage().deserialize("""
                        
                        <click:open_url:'https://modrinth.com/plugin/event/version/<NEW_VERSION>'><hover:show_text:'Click to update!'><gray>There is a new <gold>EventCore <gray>update! <dark_gray>(v<OLD_VERSION> -> v<NEW_VERSION>)
                        <yellow>Click <green>here</green> to download!
                        """.replaceAll("<NEW_VERSION>", latest)
                    .replaceAll("<OLD_VERSION>", instance.getDescription().getVersion())
            ));
        }
    }

}
