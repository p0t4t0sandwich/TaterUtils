package dev.neuralnexus.taterutils.modules.spawn;

import dev.neuralnexus.taterlib.api.TaterAPIProvider;
import dev.neuralnexus.taterlib.event.api.CommandEvents;
import dev.neuralnexus.taterlib.plugin.Module;
import dev.neuralnexus.taterutils.TaterUtils;
import dev.neuralnexus.taterutils.modules.spawn.command.DelSpawnCommand;
import dev.neuralnexus.taterutils.modules.spawn.command.SetSpawnCommand;
import dev.neuralnexus.taterutils.modules.spawn.command.SpawnCommand;

/** Spawn module. */
public class SpawnModule implements Module {
    private static boolean STARTED = false;
    private static boolean RELOADED = false;

    @Override
    public String getName() {
        return "Spawn";
    }

    @Override
    public void start() {
        if (STARTED) {
            TaterUtils.getLogger().info("Submodule " + getName() + " has already started!");
            return;
        }
        STARTED = true;

        if (!RELOADED) {
            // Register commands
            CommandEvents.REGISTER_COMMAND.register(
                    (event -> {
                        if (!TaterAPIProvider.serverType().isProxy()) {
                            event.registerCommand(TaterUtils.getPlugin(), new SpawnCommand());
                            event.registerCommand(
                                    TaterUtils.getPlugin(),
                                    new SetSpawnCommand(),
                                    "addspawn",
                                    "createspawn");
                            event.registerCommand(
                                    TaterUtils.getPlugin(),
                                    new DelSpawnCommand(),
                                    "deletespawn",
                                    "removespawn",
                                    "rmspawn");
                        }
                    }));
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
