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

import java.util.ArrayList;

public class remViewerCommand implements CommandExecutor {
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

        String viewer = args.<String>getOne("message").get().toLowerCase();

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
                src.sendMessage(Text.of(TextColors.GREEN, "Der Viewer " + viewer + " wurde aus deiner Liste entfernt!"));
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
