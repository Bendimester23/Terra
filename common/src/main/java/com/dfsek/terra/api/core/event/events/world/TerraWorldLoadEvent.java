package com.dfsek.terra.api.core.event.events.world;

import com.dfsek.terra.api.core.event.events.Event;
import com.dfsek.terra.world.TerraWorld;

/**
 * Called upon initialization of a TerraWorld.
 */
public class TerraWorldLoadEvent implements Event {
    private final TerraWorld world;

    public TerraWorldLoadEvent(TerraWorld world) {
        this.world = world;
    }

    public TerraWorld getWorld() {
        return world;
    }
}
