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

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class remStreamerCommand implements CommandExecutor {
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

        String streamer = args.<String>getOne("message").get().toLowerCase();

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
            UUID test2 = player.get().getUniqueId();
            UserStorageService uss = Sponge.getServiceManager().provideUnchecked(UserStorageService.class);
            Optional<User> oUser = uss.get(test2);
            String name = oUser.get().getName();
            Sponge.getCommandManager().process(cs, "kick " + name + " Du bist nun kein Streamer mehr!");
            Sponge.getCommandManager().process(cs, "whitelist remove " + name);
            src.sendMessage(Text.of(TextColors.GREEN, streamer + " wurde aus der Streamer-Liste entfernt!"));
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
