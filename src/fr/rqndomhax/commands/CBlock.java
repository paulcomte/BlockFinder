package fr.rqndomhax.commands;

import fr.rqndomhax.core.BlockFinderConfig;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;

public abstract class CBlock {

    public static boolean parseCommand(CommandSender sender, String[] args, BlockFinderConfig config) {

        if (config.inProgress) {
            sender.sendMessage("BlockFinder: you cannot change the search's target while search is in progress !");
            return false;
        }
        if (args.length != 2) {
            sender.sendMessage("/blockfinder block <blockname> - set the targeted block (default: chest).");
            return false;
        }
        Material material = Material.matchMaterial(args[1]);
        if (material == null) {
            sender.sendMessage("BlockFinder: this block does not exist !");
            return false;
        }
        config.target = material;
        sender.sendMessage("BlockFinder: target: " + material.name());
        return true;
    }

}
