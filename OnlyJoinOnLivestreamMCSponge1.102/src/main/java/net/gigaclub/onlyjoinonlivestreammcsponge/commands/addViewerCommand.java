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
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import java.util.ArrayList;

public class addViewerCommand implements CommandExecutor {
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

        String viewer = args.<String>getOne("message").get().toLowerCase().replaceAll("\\s","");

        if(Main.listOfStreamers.contains(src.getName().toLowerCase())) {
            if(!Main.listOfStreamers.contains(viewer)) {
                ArrayList<String> listOfViewers = new ArrayList<>();
                if(Main.plugin.config.getNode("ViewerListOf", src.getName().toLowerCase(), "Viewer").getValue() != null) {
                    listOfViewers = (ArrayList<String>) Main.plugin.config.getNode("ViewerListOf", src.getName().toLowerCase(), "Viewer").getValue();
                }
                if(!listOfViewers.contains(viewer)) {
                    listOfViewers.add(viewer);
                    Main.listOfAllViewers.replace(src.getName().toLowerCase(), listOfViewers);
                    Main.plugin.config.getNode("ViewerListOf", src.getName().toLowerCase(), "Viewer").setValue(listOfViewers);
                    Main.plugin.saveConfig();
                    CommandSource cs = Sponge.getServer().getConsole();
                    Sponge.getCommandManager().process(cs, "whitelist add " + viewer);
                    src.sendMessage(Text.of(TextColors.GREEN, "Der Viewer " + viewer + " wurde zu deiner Liste hinzugefügt!"));
                } else {
                    src.sendMessage(Text.of(TextColors.RED, "Der Viewer " + viewer + " steht bereits auf deiner Liste!"));
                }
            } else {
                src.sendMessage(Text.of(TextColors.RED, "Der Spieler " + viewer + " ist ein Streamer!"));
            }
        } else {
            src.sendMessage(Text.of(TextColors.RED, "Du darfst diesen Command nicht nutzen!"));
        }
        return CommandResult.success();
    }

    public static CommandSpec build() {
        return CommandSpec.builder()
                .description(Text.of("add a Viewer"))
                .arguments(
                        GenericArguments.remainingJoinedStrings(Text.of("message"))
                )
                .executor(new addViewerCommand())
                .build();
    }
}
