package dev.neuralnexus.taterutils.modules.home.storage;

import dev.neuralnexus.taterlib.player.Player;
import dev.neuralnexus.taterlib.utils.Location;
import dev.neuralnexus.taterutils.api.NamedLocation;

import java.util.Optional;
import java.util.Set;

/** Storage API for the home module. */
public interface HomeStorage {
    /**
     * Get a player's home.
     *
     * @param player The player.
     * @param home The name of the home.
     */
    Optional<NamedLocation> getHome(Player player, String home);

    /**
     * Set a player's home.
     *
     * @param player The player.
     * @param home The name of the home.
     * @param location The location of the home.
     */
    void setHome(Player player, String home, Location location);

    /**
     * Delete a player's home.
     *
     * @param player The player.
     * @param home The name of the home.
     */
    void deleteHome(Player player, String home);

    /**
     * Get all of a player's homes.
     *
     * @param player The player.
     */
    Set<NamedLocation> getHomes(Player player);

    /**
     * Teleport a player to their home.
     *
     * @param player The player.
     */
    boolean teleportHome(Player player, String home);
}
