package net.gigaclub.onlyjoinonlivestreammcsponge;

import com.google.inject.Inject;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;

import net.gigaclub.onlyjoinonlivestreammcsponge.commands.*;
import net.gigaclub.onlyjoinonlivestreammcsponge.functions.JoinEvent;
import net.gigaclub.onlyjoinonlivestreammcsponge.functions.onCommandExecuteEvent;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.plugin.Plugin;


@Plugin(
        id = "onlyjoinonlivestreammcsponge110",
        name = "OnlyJoinOnLivestreamMCSponge110",
        description = "Plugin for Streamer",
        version = "1.0",
        url = "https://GigaClub.net",
        authors = {
                "GigaClub"
        }
)
public class Main {

    public static Main plugin;
    public static ArrayList<String> listOfStreamers;
    public static HashMap<String, ArrayList<String>> listOfAllViewers;

    @Inject
    @DefaultConfig(sharedRoot = false)
    private Path defaultConfig;

    @Inject
    @DefaultConfig(sharedRoot = false)
    private ConfigurationLoader<CommentedConfigurationNode> loader;

    @Inject
    @ConfigDir(sharedRoot = false)
    private Path configDir;

    @Inject
    private Logger logger;

    public ConfigurationNode config;

    @Listener
    public void preInit(GamePreInitializationEvent event) {

        plugin = this;
        loadConfig();
        listOfStreamers = new ArrayList<>();
        if(config.getNode("Streamer").getValue() != null) {
            listOfStreamers = (ArrayList<String>) config.getNode("Streamer").getValue();
        }
        listOfAllViewers = new HashMap<>();
        for (String streamer : listOfStreamers) {
            if(config.getNode("ViewerListOf", streamer, "Viewer").getValue() != null) {
                listOfAllViewers.put(streamer, (ArrayList<String>) config.getNode("ViewerListOf", streamer, "Viewer").getValue());
            }
        }
    }
    @Listener
    public void init(GameInitializationEvent event) {
        Sponge.getCommandManager().register(plugin, addStreamerCommand.build(), "addStreamer");
        Sponge.getCommandManager().register(plugin, addViewerCommand.build(), "addViewer");
        Sponge.getCommandManager().register(plugin, remStreamerCommand.build(), "remStreamer");
        Sponge.getCommandManager().register(plugin, remViewerCommand.build(), "remViewer");
        Sponge.getCommandManager().register(plugin, showStreamerCommand.build(), "showStreamer");
        Sponge.getCommandManager().register(plugin, showViewerCommand.build(), "showViewer");

        Sponge.getEventManager().registerListeners(this, new JoinEvent());
        Sponge.getEventManager().registerListeners(this, new onCommandExecuteEvent());
    }
    @Listener
    public void serverStarted(GameStartedServerEvent event) {
        Sponge.getServer().setHasWhitelist(true);
    }

    public void loadConfig() {
        try {
            config = loader.load();

            if(!defaultConfig.toFile().exists()) {
                config.getNode("Streamer").setValue(new ArrayList<>());
                loader.save(config);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveConfig() {
        try {
            loader.save(config);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
