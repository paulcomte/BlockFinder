package fr.rqndomhax;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

public class BlockFinder extends JavaPlugin {

    @Override
    public void onEnable() {
        new Setup(new Config(this)).init();
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }
}

class Setup {

    Config config;

    public Setup(Config config) {
        this.config = config;
    }

    public void init() {
        config.plugin.getCommand("blockfinder").setExecutor(new BlockFinderCommand(config));
    }
}

class Config {

    BlockFinder plugin;

    Search search = null;
    FileManager fileManager;
    Material target = Material.CHEST;
    World world = null;
    boolean inProgress = false;
    int xMin = 0;
    int zMin = 0;
    int xMax = 0;
    int zMax = 0;

    public Config(BlockFinder plugin) {
        this.plugin = plugin;
        fileManager = new FileManager(plugin);
    }
}