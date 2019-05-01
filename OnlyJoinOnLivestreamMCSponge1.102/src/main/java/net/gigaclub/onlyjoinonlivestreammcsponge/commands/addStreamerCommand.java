package net.gigaclub.onlyjoinonlivestreammcsponge.commands;

import net.gigaclub.onlyjoinonlivestreammcsponge.Main;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class addStreamerCommand implements CommandExecutor {
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

        String streamer = args.<String>getOne("message").get().toLowerCase();

        if(!Main.listOfStreamers.contains(streamer)) {
            Main.listOfStreamers.add(streamer);
            Main.listOfAllViewers.put(streamer, new ArrayList<>());
            Main.plugin.config.getNode("Streamer").setValue(Main.listOfStreamers);
            for(Map.Entry<String, ArrayList<String>> entry : Main.listOfAllViewers.entrySet()) {
                Main.plugin.config.getNode("ViewerOfList", entry.getKey(), "Viewer").setValue(entry.getValue());
            }
            Main.plugin.saveConfig();
            src.sendMessage(Text.of(TextColors.GREEN, streamer + " wurde als Streamer hinzugefügt!"));
        } else {
            src.sendMessage(Text.of(TextColors.RED, streamer + " ist bereits ein Streamer!"));
        }



        return CommandResult.success();
    }

    public static CommandSpec build() {
        return CommandSpec.builder()
                .description(Text.of("add a Streamer"))
                .permission("OJL.addStreamer")
                .arguments(
                        GenericArguments.remainingJoinedStrings(Text.of("message"))
                )
                .executor(new addStreamerCommand())
                .build();
    }
}
