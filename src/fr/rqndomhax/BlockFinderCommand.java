package fr.rqndomhax;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class BlockFinderCommand implements CommandExecutor {

    Config config;

    public BlockFinderCommand(Config config) {
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
                if (config.inProgress) {
                    sender.sendMessage("BlockFinder: search has already started !");
                    return false;
                }
                if (config.world == null) {
                    sender.sendMessage("BlockFinder: No world has been selected !");
                    return false;
                }
                config.inProgress = true;
                config.search = new Search(config);
                sender.sendMessage("BlockFinder: search started !");
                return true;
            case "cancel":
                if (!config.inProgress) {
                    sender.sendMessage("BlockFinder: search has not been started !");
                    return false;
                }
                config.inProgress = false;
                if (config.search != null)
                    config.search.cancel();
                sender.sendMessage("BlockFinder: search stopped !");
                return true;
            case "pause":
                if (!config.inProgress || config.search == null) {
                    sender.sendMessage("BlockFinder: search has not been started !");
                    return false;
                }
                if (config.search.isPaused) {
                    config.search.updateStampsSincePause();
                    config.search.isPaused = false;
                    sender.sendMessage("BlockFinder: search has been resumed !");
                    return true;
                }
                config.search.isPaused = true;
                sender.sendMessage("BlockFinder: search has been paused !");
                return true;
            case "block":
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
            default:
                if (args.length != 5) {
                    sender.sendMessage("/blockfinder <world> <xmin> <zmin> <xmax> <zmax> - sets the search's area.");
                    return false;
                }
                World currentWorld = Bukkit.getWorld(args[0]);
                if (currentWorld == null) {
                    sender.sendMessage("BlockFinder: World does not exist !");
                    return false;
                }
                if (!getCoordinates(args)) {
                    sender.sendMessage("BlockFinder: At least 1 coordinates is not an integer !");
                    return false;
                }
                config.world = currentWorld;
                sender.sendMessage("BlockFinder:" +
                        "\n  World: " + currentWorld.getName() +
                        "\n  xMin = " + config.xMin +
                        "\n  zMin = " + config.zMin +
                        "\n  xMax = " + config.xMax +
                        "\n  zMax = " + config.zMax +
                        "\n  target = " + config.target.name());
                return true;
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

    private boolean getCoordinates(String[] args) {
        int current;

        try {
            current = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            return false;
        }
        config.xMin = current;

        try {
            current = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            return false;
        }
        config.zMin = current;

        try {
            current = Integer.parseInt(args[3]);
        } catch (NumberFormatException e) {
            return false;
        }
        config.xMax = current;

        try {
            current = Integer.parseInt(args[4]);
        } catch (NumberFormatException e) {
            return false;
        }
        config.zMax = current;
        return true;
    }

}
