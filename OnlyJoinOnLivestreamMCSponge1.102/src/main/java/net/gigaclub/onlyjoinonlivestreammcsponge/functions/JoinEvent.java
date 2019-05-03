package net.gigaclub.onlyjoinonlivestreammcsponge.functions;

import net.gigaclub.onlyjoinonlivestreammcsponge.Main;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandSource;
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
                for(String user : Main.listOfStreamers) {
                    ArrayList<String> listOfViewers = new ArrayList<>();
                    if (Main.plugin.config.getNode("ViewerListOf", user, "Viewer").getValue() != null) {
                        listOfViewers = (ArrayList<String>) Main.plugin.config.getNode("ViewerListOf", user, "Viewer").getValue();
                    }
                    if (listOfViewers.contains(player.getName().toLowerCase())) {
                        listOfViewers.remove(player.getName().toLowerCase());
                        Main.listOfAllViewers.replace(user, listOfViewers);
                        Main.plugin.config.getNode("ViewerListOf", user, "Viewer").setValue(listOfViewers);
                        Main.plugin.saveConfig();
                    }
                }
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
        if(Main.listOfStreamers.contains(player.getName().toLowerCase())) {
            ArrayList<String> listOfViewers = new ArrayList<>();
            if(Main.plugin.config.getNode("ViewerListOf", player.getName().toLowerCase(), "Viewer").getValue() != null) {
                listOfViewers = (ArrayList<String>) Main.plugin.config.getNode("ViewerListOf", player.getName().toLowerCase(), "Viewer").getValue();
                CommandSource cs = Sponge.getServer().getConsole();
                for(String s : listOfViewers) {
                    Sponge.getCommandManager().process(cs, "whitelist add " + s);
                }
            }
        }

    }

}
