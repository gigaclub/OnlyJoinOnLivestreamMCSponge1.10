package net.gigaclub.onlyjoinonlivestreammcspigot.commands;

import net.gigaclub.onlyjoinonlivestreammcspigot.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class remStreamerCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender.hasPermission("OJL.remStreamer")) {
            if(args.length == 1) {
                FileConfiguration config = Main.getPlugin().getConfig();
                Main.listOfStreamers = (ArrayList<String>) config.getStringList("Streamer");
                if(Main.listOfStreamers.contains(args[0].toLowerCase())) {
                    Main.listOfStreamers.remove(args[0].toLowerCase());
                    Main.listOfAllViewers.replace(args[0].toLowerCase(), new ArrayList<>());
                    config.set("Streamer", Main.listOfStreamers);
                    for(Map.Entry<String, ArrayList<String>> entry : Main.listOfAllViewers.entrySet()) {
                        config.set("ViewerListOf." + entry.getKey() + ".Viewer", entry.getValue());
                        sender.sendMessage(entry.getKey() + " " + entry.getValue());
                    }
                    Main.listOfAllViewers.remove(args[0].toLowerCase());
                    sender.sendMessage("§aDer Streamer §6" + args[0] + " §awurde aus der Liste §cenfernt!");
                } else {
                    sender.sendMessage("§cDer Name §6" + args[0] + " §csteht nicht auf deiner Liste!");
                }
                Main.getPlugin().saveConfig();
            } else {
                sender.sendMessage("§cDer Command heißt §6/remStreamer <Name>§c!");
            }
        } else {
            sender.sendMessage("§cDu darfst diesen Command nicht nutzen!");
        }
        return false;
    }
}
