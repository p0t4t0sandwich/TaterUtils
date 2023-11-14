package dev.neuralnexus.taterutils.common;

import dev.neuralnexus.taterlib.lib.dejvokep.boostedyaml.YamlDocument;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 * Config handler.
 */
public class TaterUtilsConfig {
    private static YamlDocument config;

    /**
     * Load the config
     * @param configFolder The path to the config file
     */
    public static void loadConfig(String configFolder) {
        try {
            config = YamlDocument.create(new File("." + File.separator + configFolder + File.separator + TaterUtils.Constants.PROJECT_NAME, TaterUtils.Constants.PROJECT_ID + ".config.yml"),
                    Objects.requireNonNull(TaterUtils.class.getClassLoader().getResourceAsStream(TaterUtils.Constants.PROJECT_ID + ".config.yml"))
            );
            config.reload();
        } catch (IOException | NullPointerException e) {
            TaterUtils.getLogger().info("Failed to load " + TaterUtils.Constants.PROJECT_ID + ".config.yml!\n" + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Unload the config
     */
    public static void unloadConfig() {
        config = null;
    }

    /**
     * Save the config
     */
    public static void saveConfig() {
        try {
            config.save();
        } catch (IOException e) {
            TaterUtils.getLogger().info("Failed to save " + TaterUtils.Constants.PROJECT_ID + ".config.ymll!\n" + e.getMessage());
        }
    }

    /**
     * Check to see if a module is enabled
     * @param module The module
     */
    public static boolean isModuleEnabled(String module) {
        return config.getBoolean("modules" + module + ".enabled");
    }

    /**
     * Home config.
     */
    public static class HomeConfig {
        /**
         * Get a message from the config.
         * @param path The path to the message.
         */
        public static String getMessage(String path) {
            return config.getString("modules.home.messages." + path);
        }
    }

    /**
     * OreWatcher config.
     */
    public static class OreWatcherConfig {
        /**
         * Get the alert threshold.
         * @return The alert threshold.
         */
        public static int getAlertThreshold() {
            return config.getInt("modules.oreWatcher.alertThreshold");
        }

        /**
         * Get whether to cancel the event if the threshold is met.
         * @return Whether to cancel the event if the threshold is met.
         */
        public static boolean getCancelMinedOverThreshold() {
            return config.getBoolean("modules.oreWatcher.cancelMinedOverThreshold");
        }

        /**
         * Get whether to send an alert to admins.
         * @return Whether to send an alert to admins.
         */
        public static boolean getAdminAlertEnabled() {
            return config.getBoolean("modules.oreWatcher.adminAlertEnabled");
        }

        /**
         * Get the admin alert message.
         * @return The admin alert message.
         */
        public static String getAdminAlertMessage() {
            return config.getString("modules.oreWatcher.adminAlertMessage");
        }

        /**
         * Get whether to send an alert to the player.
         * @return Whether to send an alert to the player.
         */
        public static boolean getPlayerAlertEnabled() {
            return config.getBoolean("modules.oreWatcher.playerAlertEnabled");
        }

        /**
         * Get the player alert message.
         * @return The player alert message.
         */
        public static String getPlayerAlertMessage() {
            return config.getString("modules.oreWatcher.playerAlertMessage");
        }
    }
}
