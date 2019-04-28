package net.gigaclub.onlyjoinonlivestreammcspigot.commands;

import net.gigaclub.onlyjoinonlivestreammcspigot.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;

public class showViewerCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(Main.listOfStreamers.contains(sender.getName())) {
            FileConfiguration config = Main.getPlugin().getConfig();
            Main.listOfViewers = (ArrayList<String>) config.getStringList("ViewerListOf." + sender.getName() + ".Viewer");
            if(!Main.listOfViewers.isEmpty()) {
                sender.sendMessage("§aHier ist die Liste der Viewer:");
                for (String viewer : Main.listOfViewers) {
                    sender.sendMessage("§6" + viewer);
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