package net.gigaclub.onlyjoinonlivestreammcspigot.commands;

import net.gigaclub.onlyjoinonlivestreammcspigot.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.HashMap;

public class addViewerCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(Main.listOfStreamers.contains(sender.getName().toLowerCase())) {
            if(args.length == 1) {
                if(!Main.listOfStreamers.contains(args[0].toLowerCase())) {
                    FileConfiguration config = Main.getPlugin().getConfig();
                    ArrayList<String> listOfViewers = (ArrayList<String>) config.getStringList("ViewerListOf." + sender.getName().toLowerCase() + ".Viewer");
                    if (!listOfViewers.contains(args[0].toLowerCase())) {
                        listOfViewers.add(args[0].toLowerCase());
                        Main.listOfAllViewers.replace(sender.getName().toLowerCase(), listOfViewers);
                        config.set("ViewerListOf." + sender.getName().toLowerCase() + ".Viewer", listOfViewers);
                        sender.sendMessage("§aDer Spieler §6" + args[0] + " §awurde zu deiner Liste hinzugefügt!");
                    } else {
                        sender.sendMessage("§cDer Name §6" + args[0] + " §csteht bereits auf deiner Liste!");
                    }
                    Main.getPlugin().saveConfig();
                } else {
                    sender.sendMessage("§cDer Spieler §6" + args[0] + " §cist ein Streamer!");
                }
            } else {
                sender.sendMessage("§cDer Command heißt §6/addViewer <Name>§c!");
            }
        } else {
            sender.sendMessage("§cDu darfst diesen Command nicht nutzen!");
        }
        return false;
    }
}
