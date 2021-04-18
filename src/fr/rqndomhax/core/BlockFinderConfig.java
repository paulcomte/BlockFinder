package fr.rqndomhax.core;

import fr.rqndomhax.utils.FileManager;
import fr.rqndomhax.utils.blocksearch.BlockSearch;
import org.bukkit.Material;
import org.bukkit.World;

@SuppressWarnings("DataClass")
public class BlockFinderConfig {

    public BlockFinder plugin;

    public BlockSearch blockSearch = null;
    public FileManager fileManager;
    public Material target = Material.CHEST;
    public World world = null;
    public boolean inProgress = false;
    public int xMin = 0;
    public int zMin = 0;
    public int xMax = 0;
    public int zMax = 0;

    public BlockFinderConfig(BlockFinder plugin) {
        this.plugin = plugin;
        fileManager = new FileManager(plugin);
    }

}
