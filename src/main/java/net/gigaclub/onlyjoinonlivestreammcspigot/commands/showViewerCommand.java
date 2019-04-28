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
        if(sender.hasPermission("OJL.showViewer")) {
            FileConfiguration config = Main.getPlugin().getConfig();
            ArrayList<String> listOfViewers = (ArrayList<String>) config.getStringList("Viewer");
            if(!listOfViewers.isEmpty()) {
                sender.sendMessage("§aHier ist die Liste der Viewer:");
                for (String viewer : listOfViewers) {
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
