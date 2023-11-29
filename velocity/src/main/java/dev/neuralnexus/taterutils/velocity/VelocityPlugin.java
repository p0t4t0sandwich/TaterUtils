package dev.neuralnexus.taterutils.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.plugin.Dependency;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;

import dev.neuralnexus.taterlib.logger.GenericLogger;
import dev.neuralnexus.taterutils.common.TaterUtils;
import dev.neuralnexus.taterutils.common.TaterUtilsPlugin;

import org.slf4j.Logger;

/** Velocity entry point. */
@Plugin(
        id = TaterUtils.Constants.PROJECT_ID,
        name = TaterUtils.Constants.PROJECT_NAME,
        version = TaterUtils.Constants.PROJECT_VERSION,
        authors = TaterUtils.Constants.PROJECT_AUTHORS,
        description = TaterUtils.Constants.PROJECT_DESCRIPTION,
        url = TaterUtils.Constants.PROJECT_URL,
        dependencies = {
            @Dependency(id = "taterlib"),
            @Dependency(id = "luckperms", optional = true)
        })
public class VelocityPlugin implements TaterUtilsPlugin {
    @Inject
    public VelocityPlugin(ProxyServer server, Logger logger) {
        pluginStart(
                server,
                new GenericLogger(
                        "[" + TaterUtils.Constants.PROJECT_NAME + "] ",
                        TaterUtils.Constants.PROJECT_ID));
    }
}
