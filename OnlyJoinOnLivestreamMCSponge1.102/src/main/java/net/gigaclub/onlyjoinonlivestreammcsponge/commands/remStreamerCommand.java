package net.gigaclub.onlyjoinonlivestreammcsponge.commands;

import net.gigaclub.onlyjoinonlivestreammcsponge.Main;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.service.user.UserStorageService;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class remStreamerCommand implements CommandExecutor {
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

        String streamer = args.<String>getOne("message").get().toLowerCase().replaceAll("\\s","");

        if(Main.listOfStreamers.contains(streamer)) {
            Main.listOfStreamers.remove(streamer);
            Main.listOfAllViewers.replace(streamer, new ArrayList<>());
            Main.plugin.config.getNode("Streamer").setValue(Main.listOfStreamers);
            for(Map.Entry<String, ArrayList<String>> entry : Main.listOfAllViewers.entrySet()) {
                Main.plugin.config.getNode("ViewerListOf", entry.getKey(), "Viewer").setValue(entry.getValue());
            }
            Main.plugin.saveConfig();
            Main.listOfAllViewers.remove(streamer);
            CommandSource cs = Sponge.getServer().getConsole();
            Optional<Player> player = Sponge.getServer().getPlayer(streamer);
            if(player.isPresent() && Sponge.getServer().getOnlinePlayers().contains(player.get())) {
                UUID test2 = player.get().getUniqueId();
                UserStorageService uss = Sponge.getServiceManager().provideUnchecked(UserStorageService.class);
                Optional<User> oUser = uss.get(test2);
                String name = oUser.get().getName();
                boolean available = false;
                ArrayList<String> listOfViewers;
                Optional<Player> playerOptinal = Sponge.getServer().getPlayer(name);
                List<String> los = new ArrayList<>();
                los = (ArrayList<String>) Main.listOfStreamers.clone();
                los.remove(src.getName().toLowerCase());
                for(int i = 0; i < los.size(); i++) {
                    if(!Sponge.getServer().getOnlinePlayers().isEmpty()) {
                        if (!Sponge.getServer().getPlayer(los.get(i)).isPresent()) {
                            los.remove(los.get(i));
                            i++;
                        }
                    } else {
                        los.remove(los.get(i));
                        i++;
                    }
                }
                for(String st : los) {
                    List<String> listOfViewers2 = new ArrayList<>();
                    if(Main.plugin.config.getNode("ViewerListOf", st, "Viewer").getValue() != null) {
                        listOfViewers2 = (ArrayList<String>) Main.plugin.config.getNode("ViewerListOf", st, "Viewer").getValue();
                        if (listOfViewers2.contains(name.toLowerCase())) {
                            available = true;
                        }
                    }
                }
                if(!available) {
                    Sponge.getCommandManager().process(cs, "kick " + name + " Du bist nun kein Streamer mehr!");
                    Sponge.getCommandManager().process(cs, "whitelist remove " + name);
                }
                src.sendMessage(Text.of(TextColors.GREEN, streamer + " wurde aus der Streamer-Liste entfernt!"));
            } else {
                String js = Main.jsonGetRequest("https://api.mojang.com/users/profiles/minecraft/" + streamer);
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
                    List<String> los = new ArrayList<>();
                    los = (ArrayList<String>) Main.listOfStreamers.clone();
                    los.remove(src.getName().toLowerCase());
                    for(int i = 0; i < los.size(); i++) {
                        if(!Sponge.getServer().getOnlinePlayers().isEmpty()) {
                            if (!Sponge.getServer().getPlayer(los.get(i)).isPresent()) {
                                los.remove(los.get(i));
                                i++;
                            }
                        } else {
                            los.remove(los.get(i));
                            i++;
                        }
                    }
                    for(String st : los) {
                        List<String> listOfViewers2 = new ArrayList<>();
                        if(Main.plugin.config.getNode("ViewerListOf", st, "Viewer").getValue() != null) {
                            listOfViewers2 = (ArrayList<String>) Main.plugin.config.getNode("ViewerListOf", st, "Viewer").getValue();
                            if (listOfViewers2.contains(spieler.toLowerCase())) {
                                available = true;
                            }
                        }
                    }
                    if(!available) {
                        Sponge.getCommandManager().process(cs, "whitelist remove " + spieler);
                    }
                    src.sendMessage(Text.of(TextColors.GREEN, streamer + " wurde aus der Streamer-Liste entfernt!"));
                }
            }
        } else {
            src.sendMessage(Text.of(TextColors.RED, streamer + " existiert nicht in der Streamer-Liste!"));
        }

        return CommandResult.success();
    }

    public static CommandSpec build() {
        return CommandSpec.builder()
                .description(Text.of("remove a Streamer"))
                .permission("OJL.remStreamer")
                .arguments(
                        GenericArguments.remainingJoinedStrings(Text.of("message"))
                )
                .executor(new remStreamerCommand())
                .build();

    }
}
