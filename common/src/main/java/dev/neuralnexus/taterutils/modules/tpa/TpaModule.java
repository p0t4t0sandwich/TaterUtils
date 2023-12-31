package dev.neuralnexus.taterutils.modules.tpa;

import dev.neuralnexus.taterlib.Utils;
import dev.neuralnexus.taterlib.api.TaterAPIProvider;
import dev.neuralnexus.taterlib.event.api.CommandEvents;
import dev.neuralnexus.taterlib.plugin.Module;
import dev.neuralnexus.taterutils.TaterUtils;
import dev.neuralnexus.taterutils.api.TaterUtilsAPIProvider;
import dev.neuralnexus.taterutils.modules.tpa.command.TpAcceptCommand;
import dev.neuralnexus.taterutils.modules.tpa.command.TpDenyCommand;
import dev.neuralnexus.taterutils.modules.tpa.command.TpHereCommand;
import dev.neuralnexus.taterutils.modules.tpa.command.TpaCommand;

/** TPA module. */
public class TpaModule implements Module {
    private static boolean STARTED = false;
    private static boolean RELOADED = false;

    @Override
    public String getName() {
        return "TPA";
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
                            event.registerCommand(TaterUtils.getPlugin(), new TpaCommand());
                            event.registerCommand(
                                    TaterUtils.getPlugin(), new TpHereCommand(), "tpahere");
                            event.registerCommand(TaterUtils.getPlugin(), new TpAcceptCommand());
                            event.registerCommand(TaterUtils.getPlugin(), new TpDenyCommand());
                        }
                    }));

            // Set repeating task that clears stale TPA requests every minute
            Utils.repeatTaskAsync(
                    () -> TaterUtilsAPIProvider.get().getTpaAPI().checkExpired(), 0L, 1200L);
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
