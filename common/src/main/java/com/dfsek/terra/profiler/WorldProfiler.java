package com.dfsek.terra.profiler;

import com.dfsek.terra.api.platform.world.World;
import com.dfsek.terra.world.TerraWorld;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import net.jafama.FastMath;

import java.util.Map;

public class WorldProfiler {
    private final BiMap<String, Measurement> measures = HashBiMap.create();
    private final World world;
    private boolean isProfiling;

    public WorldProfiler(World w) {
        if(!TerraWorld.isTerraWorld(w))
            throw new IllegalArgumentException("Attempted to instantiate profiler on non-Terra managed world!");
        this.addMeasurement(new Measurement(2500000, DataType.PERIOD_MILLISECONDS), "TotalChunkGenTime")
                .addMeasurement(new Measurement(1500000, DataType.PERIOD_MILLISECONDS), "FloraTime")
                .addMeasurement(new Measurement(10000000, DataType.PERIOD_MILLISECONDS), "TreeTime")
                .addMeasurement(new Measurement(1500000, DataType.PERIOD_MILLISECONDS), "OreTime")
                .addMeasurement(new Measurement(5000000, DataType.PERIOD_MILLISECONDS), "CaveTime")
                .addMeasurement(new Measurement(1500000, DataType.PERIOD_MILLISECONDS), "StructureTime");

        isProfiling = false;
        this.world = w;
    }

    public String getResultsFormatted() {
        if(! isProfiling) return "Profiler is not currently running.";
        StringBuilder result = new StringBuilder("Gaea World Profiler Results (Min / Avg / Max / Std Dev): \n");
        for(Map.Entry<String, Measurement> e : measures.entrySet()) {
            result
                    .append(e.getKey())
                    .append(": ")
                    .append(e.getValue().getDataHolder().getFormattedData(e.getValue().getMin()))
                    .append(" / ")
                    .append(e.getValue().getDataHolder().getFormattedData(e.getValue().average()))
                    .append(" / ")
                    .append(e.getValue().getDataHolder().getFormattedData(e.getValue().getMax()))
                    .append(" / ")
                    .append((double) FastMath.round((e.getValue().getStdDev() / 1000000) * 100D) / 100D)
                    .append("ms")
                    .append(" (x").append(e.getValue().size()).append(")\n");
        }
        return result.toString();
    }

    public void reset() {
        for(Map.Entry<String, Measurement> e : measures.entrySet()) {
            e.getValue().reset();
        }
    }

    public com.dfsek.terra.profiler.WorldProfiler addMeasurement(Measurement m, String name) {
        measures.put(name, m);
        return this;
    }

    public void setMeasurement(String id, long value) {
        if(isProfiling) measures.get(id).record(value);
    }

    public ProfileFuture measure(String id) {
        if(isProfiling) return measures.get(id).beginMeasurement();
        else return null;
    }

    public String getID(Measurement m) {
        return measures.inverse().get(m);
    }

    public boolean isProfiling() {
        return isProfiling;
    }

    public void setProfiling(boolean enabled) {
        this.isProfiling = enabled;
    }

    public World getWorld() {
        return world;
    }
}
