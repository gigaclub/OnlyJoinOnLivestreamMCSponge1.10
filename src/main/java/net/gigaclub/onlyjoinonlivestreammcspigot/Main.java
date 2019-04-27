package net.gigaclub.onlyjoinonlivestreammcspigot;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import net.gigaclub.onlyjoinonlivestreammcspigot.commands.*;
import net.gigaclub.onlyjoinonlivestreammcspigot.functions.*;

import java.util.Objects;

public final class Main extends JavaPlugin {

    private static Main plugin;

    @Override
    public void onEnable() {
        plugin = this;

        Objects.requireNonNull(getCommand("addViewer")).setExecutor(new addViewerCommand());
        Objects.requireNonNull(getCommand("remViewer")).setExecutor(new remViewerCommand());
        Objects.requireNonNull(getCommand("showViewer")).setExecutor(new showViewerCommand());

        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new checkGroupFunction(), this);
        pluginManager.registerEvents(new checkStreamerStatusFunction(), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
