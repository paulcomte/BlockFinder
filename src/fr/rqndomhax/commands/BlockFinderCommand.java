package fr.rqndomhax.commands;

import fr.rqndomhax.core.BlockFinderConfig;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class BlockFinderCommand implements CommandExecutor {

    BlockFinderConfig config;

    public BlockFinderCommand(BlockFinderConfig config) {
        this.config = config;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
        if (!sender.isOp())
            return false;

        if (args.length == 0 || args[0].equalsIgnoreCase("help")) {
            showHelp(sender);
            return false;
        }

        switch (args[0].toLowerCase()) {
            case "start":
                return CStart.parseCommand(sender, config);
            case "cancel":
                return CCancel.parseCommand(sender, config);
            case "pause":
                return CPause.parseCommand(sender, config);
            case "block":
                return CBlock.parseCommand(sender, args, config);
            default:
                return CSettings.parseCommand(sender, args, config);
        }
    }

    private void showHelp(CommandSender sender) {
        sender.sendMessage("/blockfinder help - shows this menu.");
        sender.sendMessage("/blockfinder <world> <xmin> <zmin> <xmax> <zmax> - sets the search's area.");
        sender.sendMessage("/blockfinder start - starts the search.");
        sender.sendMessage("/blockfinder pause - pause / resume search's progress.");
        sender.sendMessage("/blockfinder block <block_id> - set the targeted block (default: chest).");
        sender.sendMessage("/blockfinder cancel - stop the search.");
    }

}
