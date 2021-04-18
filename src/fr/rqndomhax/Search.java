package fr.rqndomhax;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;

public class Search extends BukkitRunnable {

    boolean isPaused = false;
    FileManager.Config file;
    int z;
    int x;
    int targetTotal = 0;
    int targetCurrent = 0;
    int total = 0;
    int current = 0;
    long beginning = 0;
    long startStamp = 0;
    long pausedStamp = 0;

    Config config;

    public Search(Config config) {
        this.config = config;
        config.search = this;
        x = config.xMin;
        z = config.zMin;
        file = config.fileManager.getConfig(new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(new Date()));
        file.set("Config.Material", config.target.name());
        file.set("Config.xMin", config.xMin);
        file.set("Config.zMin", config.zMin);
        file.set("Config.xMax", config.xMax);
        file.set("Config.zMax", config.zMax);
        file.set("duration", 0);
        file.save();
        runTaskTimer(config.plugin, 1, 1);
    }

    @Override
    public void run() {
        if (isPaused) {
            if (pausedStamp == 0)
                pausedStamp = System.currentTimeMillis();
            return;
        }
        if (beginning == 0) {
            beginning = System.currentTimeMillis();
            startStamp = beginning;
        }
        readLine();
        updateInfos();
    }

    private void readLine() {
        if (x == config.xMax && z == config.zMax) {
            showFinished();
            return;
        }
        updateCoords();

        Block block;
        for (int y = 0; y <= 256; current++, y++) {
            block = config.world.getBlockAt(x, y, z);
            if (block == null || !block.getType().equals(config.target))
                continue;
            targetCurrent++;
            file.set(Integer.toString(targetTotal + targetCurrent), x + ":" + y + ":" + z);
        }
        file.save();
    }

    private void updateInfos() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - startStamp > 1000L) {
            total += current;
            targetTotal += targetCurrent;
            Bukkit.getLogger().log(Level.INFO, "BlockFinder: Scanned " + current + " blocks, for a total of " + total + " blocks (" + (currentTime - beginning) / 1000 + "s).");
            Bukkit.getLogger().log(Level.INFO, "BlockFinder: Found " + targetCurrent + " " + config.target.name() + " for a total of " + targetTotal + " " + config.target.name() + ".");
            current = 0;
            targetCurrent = 0;
            startStamp = currentTime;
            file.set("duration", currentTime - beginning + "s");
            file.save();
        }
    }

    private void showFinished() {
        Bukkit.getLogger().log(Level.INFO, "BlockFinder: The whole area has been scanned !");
        Bukkit.getLogger().log(Level.INFO, "BlockFinder: Took " + (System.currentTimeMillis() - beginning) / 1000 + "s to scan the whole area !");
        config.inProgress = false;
        cancel();
    }

    private void updateCoords() {
        if (x == config.xMax) {
            x = config.xMin;
            if (z < config.zMax)
                z++;
            else
                z--;
            return;
        }

        if (x < config.xMax)
            x++;
        else
            x--;
    }

    public void updateStampsSincePause() {
        if (startStamp != 0)
            startStamp -= System.currentTimeMillis() - pausedStamp;
        if (beginning != 0)
            beginning -= System.currentTimeMillis() - pausedStamp;
        pausedStamp = 0;
    }
}
