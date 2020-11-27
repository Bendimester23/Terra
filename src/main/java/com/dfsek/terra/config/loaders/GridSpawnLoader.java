package com.dfsek.terra.config.loaders;

import com.dfsek.tectonic.exception.LoadException;
import com.dfsek.tectonic.loading.ConfigLoader;
import com.dfsek.tectonic.loading.TypeLoader;
import com.dfsek.terra.procgen.GridSpawn;

import java.lang.reflect.Type;
import java.util.Map;

@SuppressWarnings("unchecked")
public class GridSpawnLoader implements TypeLoader<GridSpawn> {
    @Override
    public GridSpawn load(Type type, Object o, ConfigLoader configLoader) throws LoadException {
        Map<String, Integer> map = (Map<String, Integer>) o;
        return new GridSpawn(map.get("width"), map.get("padding"));
    }
}
