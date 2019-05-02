package net.gigaclub.onlyjoinonlivestreammcsponge.functions;

import net.gigaclub.onlyjoinonlivestreammcsponge.Main;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

public class JoinEvent {

    @Listener
    public void onJoin(ClientConnectionEvent.Join event) {
        Cause cause = event.getCause();
        Optional<Player> playerO = cause.first(Player.class);
        Player player = playerO.get();
        if(player.hasPermission("OJL.addStreamer")) {
            if(!Main.listOfStreamers.contains(player.getName().toLowerCase())) {
                Main.listOfStreamers.add(player.getName().toLowerCase());
                Main.listOfAllViewers.put(player.getName().toLowerCase(), new ArrayList<>());
                Main.plugin.config.getNode("Streamer").setValue(Main.listOfStreamers);
                for (Map.Entry<String, ArrayList<String>> entry : Main.listOfAllViewers.entrySet()) {
                    Main.plugin.config.getNode("ViewerListOf", entry.getKey(), "Viewer").setValue(entry.getValue());
                }
                Main.plugin.saveConfig();
                player.sendMessage(Text.of(TextColors.GREEN,"Da du Operator bist wurdest du der Streamer Gruppe hinzugewiesen!"));
            }
        }

    }

}
