package fr.rqndomhax.core;

import fr.rqndomhax.commands.BlockFinderCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class BlockFinder extends JavaPlugin {

    @Override
    public void onEnable() {
        Setup.init(new BlockFinderConfig(this));
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }
}

abstract class Setup {

    public static void init(BlockFinderConfig config) {
        config.plugin.getCommand("blockfinder").setExecutor(new BlockFinderCommand(config));
    }

}
