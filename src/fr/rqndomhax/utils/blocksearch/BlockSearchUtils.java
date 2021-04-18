package fr.rqndomhax.utils.blocksearch;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;

public class BlockSearchUtils {

    private static BlockSearch blockSearch;

    public static void init(BlockSearch blockSearch) {
        BlockSearchUtils.blockSearch = blockSearch;

        blockSearch.file = blockSearch.config.fileManager.getConfig(new SimpleDateFormat("yyyy-MM-dd HH-mm-ss", Locale.ENGLISH).format(new Date()));
        blockSearch.file.set("Config.Material", blockSearch.config.target.name());
        blockSearch.file.set("Config.xMin", blockSearch.config.xMin);
        blockSearch.file.set("Config.zMin", blockSearch.config.zMin);
        blockSearch.file.set("Config.xMax", blockSearch.config.xMax);
        blockSearch.file.set("Config.zMax", blockSearch.config.zMax);
        blockSearch.file.set("duration", 0);
        blockSearch.file.save();
    }

    protected static void readLine() {
        if (blockSearch.x == blockSearch.config.xMax && blockSearch.z == blockSearch.config.zMax) {
            showFinished();
            return;
        }
        updateCoords();

        Block block;
        for (int y = 0; y <= 256; blockSearch.current++, y++) {
            block = blockSearch.config.world.getBlockAt(blockSearch.x, y, blockSearch.z);
            if (block == null || !block.getType().equals(blockSearch.config.target))
                continue;
            blockSearch.targetCurrent++;
            blockSearch.file.set(Integer.toString(blockSearch.targetTotal + blockSearch.targetCurrent),
                    blockSearch.x + ":" + y + ":" + blockSearch.z);
        }
        blockSearch.file.save();
    }

    protected static void updateInfos() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - blockSearch.startStamp > 1000L) {
            blockSearch.total += blockSearch.current;
            blockSearch.targetTotal += blockSearch.targetCurrent;

            Bukkit.getLogger().log(Level.INFO,
                    "BlockFinder: Scanned " + blockSearch.current
                            + " blocks, for a total of " + blockSearch.total + " blocks (" + (currentTime - blockSearch.beginning) / 1000 + "s).");
            Bukkit.getLogger().log(Level.INFO,
                    "BlockFinder: Found " + blockSearch.targetCurrent + " " + blockSearch.config.target.name()
                            + " for a total of " + blockSearch.targetTotal + " " + blockSearch.config.target.name() + ".");

            blockSearch.current = 0;
            blockSearch.targetCurrent = 0;
            blockSearch.startStamp = currentTime;
            blockSearch.file.set("duration", currentTime - blockSearch.beginning + "s");
            blockSearch.file.save();
        }
    }

    private static void updateCoords() {
        if (blockSearch.x == blockSearch.config.xMax) {
            blockSearch.x = blockSearch.config.xMin;
            if (blockSearch.z < blockSearch.config.zMax)
                blockSearch.z++;
            else
                blockSearch.z--;
            return;
        }

        if (blockSearch.x < blockSearch.config.xMax)
            blockSearch.x++;
        else
            blockSearch.x--;
    }

    private static void showFinished() {
        Bukkit.getLogger().log(Level.INFO, "BlockFinder: The whole area has been scanned !");
        Bukkit.getLogger().log(Level.INFO, "BlockFinder: Took " + (System.currentTimeMillis() - blockSearch.beginning) / 1000 + "s to scan the whole area !");
        blockSearch.config.inProgress = false;
        blockSearch.cancel();
    }

}
