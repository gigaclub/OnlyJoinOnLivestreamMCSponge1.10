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

import java.util.ArrayList;

public class showViewerCommand implements CommandExecutor {
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if(Main.listOfStreamers.contains(src.getName().toLowerCase())) {
            ArrayList<String> listOfViewers = new ArrayList<>();
            if(Main.plugin.config.getNode("ViewerListOf", src.getName().toLowerCase(), "Viewer").getValue() != null) {
                listOfViewers = (ArrayList<String>) Main.plugin.config.getNode("ViewerListOf", src.getName().toLowerCase(), "Viewer").getValue();
            }
            if(!listOfViewers.isEmpty()) {
                src.sendMessage(Text.of(TextColors.GREEN, "Hier ist deine Viewer Liste:"));
                for(String viewer : listOfViewers) {
                    src.sendMessage(Text.of(TextColors.GREEN, viewer));
                }
            } else {
                src.sendMessage(Text.of(TextColors.RED, "Du hast keine Viewer auf deiner Liste!"));
            }
        } else {
            src.sendMessage(Text.of(TextColors.RED, "Du darfst diesen Command nicht nutzen!"));
        }

        return CommandResult.success();
    }

    public static CommandSpec build() {
        return CommandSpec.builder()
                .description(Text.of("show Viewer list"))
                .arguments()
                .executor(new showViewerCommand())
                .build();
    }
}
