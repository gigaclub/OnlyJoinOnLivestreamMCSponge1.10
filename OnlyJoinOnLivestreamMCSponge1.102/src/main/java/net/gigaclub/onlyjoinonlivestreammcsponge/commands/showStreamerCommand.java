package net.gigaclub.onlyjoinonlivestreammcsponge.commands;

import net.gigaclub.onlyjoinonlivestreammcsponge.Main;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class showStreamerCommand implements CommandExecutor {
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

        if(!Main.listOfStreamers.isEmpty()) {
            src.sendMessage(Text.of(TextColors.GREEN, "Hier ist die Liste der Streamer:"));
            for(String streamer : Main.listOfStreamers) {
                src.sendMessage(Text.of(TextColors.GREEN, streamer));
            }
        } else {
            src.sendMessage(Text.of(TextColors.RED, "Es gibt keine Streamer!"));
        }

        return CommandResult.success();
    }

    public static CommandSpec build() {
        return CommandSpec.builder()
                .description(Text.of("show Streamer list"))
                .permission("OJL.showStreamer")
                .arguments()
                .executor(new showStreamerCommand())
                .build();
    }
}
