package net.gigaclub.onlyjoinonlivestreammcsponge.functions;

import net.gigaclub.onlyjoinonlivestreammcsponge.Main;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.command.SendCommandEvent;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

public class onCommandExecuteEvent {

    @Listener
    public void onOPCommandExecute(SendCommandEvent event) {

        String cmd = event.getCommand().toLowerCase();
        String args = event.getArguments().toLowerCase();
        Optional<Player> p = Sponge.getServer().getPlayer(args);
        if(p.isPresent()) {
            Player player = p.get();
            if (cmd.equals("op") && !Main.listOfStreamers.contains(args)) {
                Main.listOfStreamers.add(args);
                Main.listOfAllViewers.put(args, new ArrayList<>());
                Main.plugin.config.getNode("Streamer").setValue(Main.listOfStreamers);
                for (Map.Entry<String, ArrayList<String>> entry : Main.listOfAllViewers.entrySet()) {
                    Main.plugin.config.getNode("ViewerListOf", entry.getKey(), "Viewer").setValue(entry.getValue());
                }
                Main.plugin.saveConfig();
                player.sendMessage(Text.of(TextColors.GREEN, "Da du Operator bist wurdest du der Streamer Gruppe hinzugewiesen!"));
            }
        }



    }

}
