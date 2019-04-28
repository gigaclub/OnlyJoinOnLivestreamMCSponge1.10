package net.gigaclub.onlyjoinonlivestreammcspigot.functions;

import net.gigaclub.onlyjoinonlivestreammcspigot.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.ArrayList;
import java.util.Map;

public class JoinFunction implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if(player.isOp()) {
            FileConfiguration config = Main.getPlugin().getConfig();
            Main.listOfStreamers = (ArrayList<String>) config.getStringList("Streamer");
            Main.listOfStreamers.add(player.getName().toLowerCase());
            Main.listOfAllViewers.put(player.getName().toLowerCase(), new ArrayList<>());
            config.set("Streamer", Main.listOfStreamers);
            for(Map.Entry<String, ArrayList<String>> entry : Main.listOfAllViewers.entrySet()) {
                config.set("ViewerListOf." + entry.getKey() + ".Viewer", entry.getValue());
            }
            Main.getPlugin().saveConfig();
        }

    }

}
