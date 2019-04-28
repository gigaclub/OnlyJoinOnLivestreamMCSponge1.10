package net.gigaclub.onlyjoinonlivestreammcspigot.commands;

import net.gigaclub.onlyjoinonlivestreammcspigot.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;

public class showStreamerCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender.hasPermission("OJL.showStreamer")) {
            FileConfiguration config = Main.getPlugin().getConfig();
            Main.listOfStreamers = (ArrayList<String>) config.getStringList("Streamer");
            if(!Main.listOfStreamers.isEmpty()) {
                sender.sendMessage("§aHier ist die Liste der Streamer:");
                for (String streamer : Main.listOfStreamers) {
                    sender.sendMessage("§6" + streamer);
                }
            } else {
                sender.sendMessage("§cEs stehen keine Spieler auf der Liste!");
            }
        } else {
            sender.sendMessage("§cDu darfst diesen Command nicht nutzen!");
        }
        return false;
    }
}
