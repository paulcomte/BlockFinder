package fr.rqndomhax.utils.blocksearch;

import fr.rqndomhax.core.BlockFinderConfig;
import fr.rqndomhax.utils.FileManager;
import org.bukkit.scheduler.BukkitRunnable;

public class BlockSearch extends BukkitRunnable {

    public boolean isPaused = false;
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

    BlockFinderConfig config;

    public BlockSearch(BlockFinderConfig config) {
        this.config = config;
        config.blockSearch = this;
        x = config.xMin;
        z = config.zMin;
        BlockSearchUtils.init(this);
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
        BlockSearchUtils.readLine();
        BlockSearchUtils.updateInfos();
    }

    public void updateStampsSincePause() {
        if (startStamp != 0)
            startStamp += System.currentTimeMillis() - pausedStamp;
        if (beginning != 0)
            beginning += System.currentTimeMillis() - pausedStamp;
        pausedStamp = 0;
    }
}
