package fr.rqndomhax.commands;

import fr.rqndomhax.core.BlockFinderConfig;
import org.bukkit.command.CommandSender;

public abstract class CCancel {

    public static boolean parseCommand(CommandSender sender, BlockFinderConfig config) {
        if (!config.inProgress) {
            sender.sendMessage("BlockFinder: search has not been started !");
            return false;
        }
        config.inProgress = false;
        if (config.blockSearch != null)
            config.blockSearch.cancel();
        sender.sendMessage("BlockFinder: search stopped !");
        return true;
    }

}
