package fr.rqndomhax.commands;

import fr.rqndomhax.core.BlockFinderConfig;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;

import java.util.concurrent.atomic.AtomicBoolean;

public class CSettings {

    public static boolean parseCommand(CommandSender sender, String[] args, BlockFinderConfig config) {

        if (args.length != 5) {
            sender.sendMessage("/blockfinder <world> <xmin> <zmin> <xmax> <zmax> - sets the search's area.");
            return false;
        }
        World currentWorld = Bukkit.getWorld(args[0]);
        if (currentWorld == null) {
            sender.sendMessage("BlockFinder: World does not exist !");
            return false;
        }
        if (!getCoordinates(args, config)) {
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

    private static boolean getCoordinates(String[] args, BlockFinderConfig config) {
        AtomicBoolean result = new AtomicBoolean(false);

        config.xMin = getCoordinate(args[1], result);
        if (!result.getAndSet(false))
            return false;

        config.zMin = getCoordinate(args[2], result);
        if (!result.getAndSet(false))
            return false;

        config.xMax = getCoordinate(args[3], result);
        if (!result.getAndSet(false))
            return false;

        config.zMax = getCoordinate(args[4], result);
        return result.get();
    }

    private static int getCoordinate(String arg, AtomicBoolean result) {
        int current;

        try {
            current = Integer.parseInt(arg);
        } catch (NumberFormatException e) {
            result.set(false);
            return 0;
        }
        result.set(true);
        return current;
    }


}
