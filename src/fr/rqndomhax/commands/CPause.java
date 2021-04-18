package fr.rqndomhax.commands;

import fr.rqndomhax.core.BlockFinderConfig;
import org.bukkit.command.CommandSender;

public class CPause {

    public static boolean parseCommand(CommandSender sender, BlockFinderConfig config) {
        if (!config.inProgress || config.blockSearch == null) {
            sender.sendMessage("BlockFinder: search has not been started !");
            return false;
        }
        if (config.blockSearch.isPaused) {
            config.blockSearch.updateStampsSincePause();
            config.blockSearch.isPaused = false;
            sender.sendMessage("BlockFinder: search has been resumed !");
            return true;
        }
        config.blockSearch.isPaused = true;
        sender.sendMessage("BlockFinder: search has been paused !");
        return true;
    }

}
