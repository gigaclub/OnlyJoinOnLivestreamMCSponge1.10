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
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class remViewerCommand implements CommandExecutor {
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

        String viewer = args.<String>getOne("message").get().toLowerCase().replaceAll("\\s","");

        if(Main.listOfStreamers.contains(src.getName().toLowerCase())) {
            ArrayList<String> listOfViewers = new ArrayList<>();
            if(Main.plugin.config.getNode("ViewerListOf", src.getName().toLowerCase(), "Viewer").getValue() != null) {
                listOfViewers = (ArrayList<String>) Main.plugin.config.getNode("ViewerListOf", src.getName().toLowerCase(), "Viewer").getValue();
            }
            if(listOfViewers.contains(viewer)) {
                listOfViewers.remove(viewer);
                Main.listOfAllViewers.replace(src.getName().toLowerCase(), listOfViewers);
                Main.plugin.config.getNode("ViewerListOf", src.getName().toLowerCase(), "Viewer").setValue(listOfViewers);
                Main.plugin.saveConfig();
                CommandSource cs = Sponge.getServer().getConsole();
                    String js = Main.jsonGetRequest("https://api.mojang.com/users/profiles/minecraft/" + viewer);
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
                        if (playerOptinal.isPresent() && Sponge.getServer().getOnlinePlayers().contains(playerOptinal.get()) && !available) {
                            Sponge.getCommandManager().process(cs, "kick " + spieler + " Du wurdest von der Viewer-List entfernt!");
                            Sponge.getCommandManager().process(cs, "whitelist remove " + spieler);
                            src.sendMessage(Text.of(TextColors.GREEN, "Der Viewer " + viewer + " wurde aus deiner Liste entfernt!"));
                        } else if(!available) {
                            Sponge.getCommandManager().process(cs, "whitelist remove " + spieler);
                            src.sendMessage(Text.of(TextColors.GREEN, "Der Viewer " + viewer + " wurde aus deiner Liste entfernt!"));
                        }
                    }
            } else {
                src.sendMessage(Text.of(TextColors.RED, "Der Viewer " + viewer + " steht nicht auf deiner Liste!"));
            }
        } else {
            src.sendMessage(Text.of(TextColors.RED, "Du darfst diesen Command nicht nutzen!"));
        }

        return CommandResult.success();
    }

    public static CommandSpec build() {
        return CommandSpec.builder()
                .description(Text.of("remove a Viewer"))
                .arguments(
                        GenericArguments.remainingJoinedStrings(Text.of("message"))
                )
                .executor(new remViewerCommand())
                .build();
    }
}
