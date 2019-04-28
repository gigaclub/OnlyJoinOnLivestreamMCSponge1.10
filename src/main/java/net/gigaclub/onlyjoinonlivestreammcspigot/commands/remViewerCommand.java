package net.gigaclub.onlyjoinonlivestreammcspigot.commands;

import net.gigaclub.onlyjoinonlivestreammcspigot.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;

public class remViewerCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender.hasPermission("OJL.remViewer")) {
            if(args.length == 1) {
                FileConfiguration config = Main.getPlugin().getConfig();
                ArrayList<String> listOfViewers = (ArrayList<String>) config.getStringList("Viewer");
                if(listOfViewers.contains(args[0])) {
                    listOfViewers.remove(args[0]);
                    config.set("Viewer", listOfViewers);
                    sender.sendMessage("§aDer Spieler §6" + args[0] + " §awurde aus der Liste §cenfernt!");
                } else {
                    sender.sendMessage("§cDer Name §6" + args[0] + " §csteht nicht auf deiner Liste!");
                }
                Main.getPlugin().saveConfig();
            } else {
                sender.sendMessage("§cDer Command heißt §6/remViewer <Name>§c!");
            }
        } else {
            sender.sendMessage("§cDu darfst diesen Command nicht nutzen!");
        }
        return false;
    }
}
