package dev.neuralnexus.taterutils.modules.orewatcher;

import dev.neuralnexus.taterlib.Utils;
import dev.neuralnexus.taterlib.event.api.BlockEvents;
import dev.neuralnexus.taterlib.plugin.Module;
import dev.neuralnexus.taterutils.TaterUtils;
import dev.neuralnexus.taterutils.api.TaterUtilsAPIProvider;
import dev.neuralnexus.taterutils.modules.orewatcher.listeners.OreWatcherBlockListener;

/** OreWatcher module. */
public class OreWatcherModule implements Module {
    private static boolean STARTED = false;
    private static boolean RELOADED = false;

    @Override
    public String getName() {
        return "OreWatcher";
    }

    @Override
    public void start() {
        if (STARTED) {
            TaterUtils.getLogger().info("Submodule " + getName() + " has already started!");
            return;
        }
        STARTED = true;

        if (!RELOADED) {
            // Register block events
            BlockEvents.BLOCK_BREAK.register(OreWatcherBlockListener::onBlockBreak);

            // Reset the average per minute every 30 minutes
            Utils.repeatTaskAsync(
                    () -> TaterUtilsAPIProvider.get().getOreWatcherAPI().resetAveragePerMinute(),
                    0L,
                    36000L);
        }
        TaterUtils.getLogger().info("Submodule " + getName() + " has been started!");
    }

    @Override
    public void stop() {
        if (!STARTED) {
            TaterUtils.getLogger().info("Submodule " + getName() + " has already stopped!");
            return;
        }
        STARTED = false;
        RELOADED = true;

        // Remove references to objects

        TaterUtils.getLogger().info("Submodule " + getName() + " has been stopped!");
    }

    @Override
    public void reload() {
        if (!STARTED) {
            TaterUtils.getLogger().info("Submodule " + getName() + " has not been started!");
            return;
        }
        RELOADED = true;

        // Stop
        stop();

        // Start
        start();

        TaterUtils.getLogger().info("Submodule " + getName() + " has been reloaded!");
    }
}
