package net.gigaclub.onlyjoinonlivestreammcsponge.functions;

import net.gigaclub.onlyjoinonlivestreammcsponge.Main;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.network.ClientConnectionEvent;

import java.util.ArrayList;
import java.util.Optional;

public class LeaveEvent {

    @Listener
    public void onLeave(ClientConnectionEvent.Disconnect event) {
        Cause cause = event.getCause();
        Optional<Player> playerO = cause.first(Player.class);
        Player player = playerO.get();
        if(Main.listOfStreamers.contains(player.getName().toLowerCase())) {
            ArrayList<String> listOfViewers = new ArrayList<>();
            if(Main.plugin.config.getNode("ViewerListOf", player.getName().toLowerCase(), "Viewer").getValue() != null) {
                listOfViewers = (ArrayList<String>) Main.plugin.config.getNode("ViewerListOf", player.getName().toLowerCase(), "Viewer").getValue();
                CommandSource cs = Sponge.getServer().getConsole();
                for(String s : listOfViewers) {
                    String js = Main.jsonGetRequest("https://api.mojang.com/users/profiles/minecraft/" + s);
                    boolean test = false;
                    int count = 0;
                    StringBuilder sb = new StringBuilder();
                    for(int i = js.length()-1; i > 0; i--) {
                        if(js.charAt(i) == '"' && count < 2) {
                            test = true;
                            count++;
                            if(count > 1) {
                                test = false;
                            }
                        }
                        if (test) {
                            sb.append(js.charAt(i));
                        }
                    }
                    if(sb.length() > 0) {
                        sb.deleteCharAt(0);
                        String spieler = String.valueOf(sb.reverse());
                        boolean available = false;
                        Optional<Player> playerOptinal = Sponge.getServer().getPlayer(spieler);
                        for(String st : Main.listOfStreamers) {
                            listOfViewers = (ArrayList<String>) Main.plugin.config.getNode("ViewerListOf", st, "Viewer").getValue();
                            if(listOfViewers.contains(spieler)) {
                                available = true;
                            }
                        }
                        if (playerOptinal.isPresent() && Sponge.getServer().getOnlinePlayers().contains(playerOptinal.get()) && !available) {
                            Sponge.getCommandManager().process(cs, "kick " + spieler + " Du wurdest von der Viewer-List entfernt!");
                            Sponge.getCommandManager().process(cs, "whitelist remove " + spieler);
                        } else if(!available) {
                            Sponge.getCommandManager().process(cs, "whitelist remove " + spieler);
                        }
                    }
                }
            }

        }
    }

}
