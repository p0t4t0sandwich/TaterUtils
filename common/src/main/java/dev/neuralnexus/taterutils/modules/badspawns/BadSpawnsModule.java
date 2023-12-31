package dev.neuralnexus.taterutils.modules.badspawns;

import dev.neuralnexus.taterlib.api.TaterAPIProvider;
import dev.neuralnexus.taterlib.entity.Entity;
import dev.neuralnexus.taterlib.event.api.EntityEvents;
import dev.neuralnexus.taterlib.plugin.Module;
import dev.neuralnexus.taterutils.TaterUtils;
import dev.neuralnexus.taterutils.TaterUtilsConfig;

import java.util.*;

/** BadSpawns module. */
public class BadSpawnsModule implements Module {
    public static Set<String> bannedMobs = new HashSet<>();
    public static Map<String, SpawnRegion> regions = new HashMap<>();
    private static boolean STARTED = false;
    private static boolean RELOADED = false;

    @Override
    public String getName() {
        return "BadSpawns";
    }

    @Override
    public void start() {
        if (STARTED) {
            TaterUtils.getLogger().info("Submodule " + getName() + " has already started!");
            return;
        }
        STARTED = true;

        bannedMobs.addAll(TaterUtilsConfig.BadSpawnsConfig.getBannedMobs());

        // Test
        bannedMobs.add("minecraft:zombie");
        //

        for (Map<?, ?> region : TaterUtilsConfig.BadSpawnsConfig.getRegions()) {
            String name = (String) region.get("name");
            try {
                // Build region
                regions.put(
                        name,
                        SpawnRegion.build(
                                name,
                                (String) region.get("type"),
                                (String) region.get("minX"),
                                (String) region.get("maxX"),
                                (String) region.get("minY"),
                                (String) region.get("maxY"),
                                (String) region.get("minZ"),
                                (String) region.get("maxZ"),
                                (List<String>) region.get("worlds"),
                                (List<String>) region.get("biomes"),
                                (List<String>) region.get("mobs")));
            } catch (Exception e) {
                TaterUtils.getLogger()
                        .info("Failed to build region " + name + "!\n" + e.getMessage());
                e.printStackTrace();
            }
        }

        if (!RELOADED) {
            if (!TaterAPIProvider.serverType().isProxy()) {
                // Register listeners
                EntityEvents.SPAWN.register(
                        event -> {
                            Entity entity = event.getEntity();

                            // Check banned mobs
                            if (bannedMobs.contains(entity.getType())) {
                                event.setCancelled(true);
                            }
                            // Check regions
                            for (SpawnRegion region : regions.values()) {
                                if (region.calculate(entity)) {
                                    event.setCancelled(true);
                                }
                            }
                        });
            }
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
        bannedMobs.clear();
        regions.clear();

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
