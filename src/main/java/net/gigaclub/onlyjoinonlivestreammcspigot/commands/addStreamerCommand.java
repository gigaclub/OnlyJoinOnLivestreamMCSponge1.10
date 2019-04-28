package net.gigaclub.onlyjoinonlivestreammcspigot.commands;

import net.gigaclub.onlyjoinonlivestreammcspigot.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class addStreamerCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender.hasPermission("OJL.addStreamer")) {
            if(args.length == 1) {
                FileConfiguration config = Main.getPlugin().getConfig();
                Main.listOfStreamers = (ArrayList<String>) config.getStringList("Streamer");
                if(!Main.listOfStreamers.contains(args[0].toLowerCase())) {
                    Main.listOfStreamers.add(args[0].toLowerCase());
                    Main.listOfAllViewers.put(args[0].toLowerCase(), new ArrayList<>());
                    config.set("Streamer", Main.listOfStreamers);
                    for(Map.Entry<String, ArrayList<String>> entry : Main.listOfAllViewers.entrySet()) {
                        config.set("ViewerListOf." + entry.getKey() + ".Viewer", entry.getValue());
                        sender.sendMessage(entry.getKey() + " " + entry.getValue());
                    }
                    sender.sendMessage("§aDer Streamer §6" + args[0] + " §awurde zu deiner Liste hinzugefügt!");
                } else {
                    sender.sendMessage("§cDer Name §6" + args[0] + " §csteht bereits auf deiner Liste!");
                }
                Main.getPlugin().saveConfig();
            } else {
                sender.sendMessage("§cDer Command heißt §6/addStreamer <Name>§c!");
            }
        } else {
            sender.sendMessage("§cDu darfst diesen Command nicht nutzen!");
        }
        return false;
    }
}
