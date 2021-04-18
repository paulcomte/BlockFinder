package fr.rqndomhax.commands;

import fr.rqndomhax.core.BlockFinderConfig;
import fr.rqndomhax.utils.blocksearch.BlockSearch;
import org.bukkit.command.CommandSender;

public class CStart {

    public static boolean parseCommand(CommandSender sender, BlockFinderConfig config) {

        if (config.inProgress) {
            sender.sendMessage("BlockFinder: search has already started !");
            return false;
        }
        if (config.world == null) {
            sender.sendMessage("BlockFinder: No world has been selected !");
            return false;
        }
        config.inProgress = true;
        config.blockSearch = new BlockSearch(config);
        sender.sendMessage("BlockFinder: search started !");
        return true;
    }

}
