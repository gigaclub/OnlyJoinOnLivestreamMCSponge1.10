package net.gigaclub.onlyjoinonlivestreammcspigot;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import net.gigaclub.onlyjoinonlivestreammcspigot.commands.*;
import net.gigaclub.onlyjoinonlivestreammcspigot.functions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public final class Main extends JavaPlugin {

    private static Main plugin;

    public static ArrayList<String> listOfStreamers;
    public static HashMap<String, ArrayList<String>> listOfAllViewers;

    @Override
    public void onEnable() {

        listOfStreamers = (ArrayList<String>) this.getConfig().getStringList("Streamer");
        listOfAllViewers = new HashMap<>();
        for(String streamer : listOfStreamers) {
            listOfAllViewers.put(streamer, (ArrayList<String>) this.getConfig().getStringList("ViewerListOf." + streamer + ".Viewer"));
        }

        Bukkit.setWhitelist(true);
        Bukkit.reloadWhitelist();
        System.out.println("Set whitelist true!");

        plugin = this;

        Objects.requireNonNull(getCommand("addViewer")).setExecutor(new addViewerCommand());
        Objects.requireNonNull(getCommand("remViewer")).setExecutor(new remViewerCommand());
        Objects.requireNonNull(getCommand("showViewer")).setExecutor(new showViewerCommand());
        Objects.requireNonNull(getCommand("addStreamer")).setExecutor(new addStreamerCommand());
        Objects.requireNonNull(getCommand("remStreamer")).setExecutor(new remStreamerCommand());
        Objects.requireNonNull(getCommand("showStreamer")).setExecutor(new showStreamerCommand());

        //PluginManager pluginManager = Bukkit.getPluginManager();
        //pluginManager.registerEvents(new checkGroupFunction(), this);
        //pluginManager.registerEvents(new checkStreamerStatusFunction(), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Main getPlugin() {
        return plugin;
    }
}
