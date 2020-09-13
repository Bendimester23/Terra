package com.dfsek.terra.math;

import org.polydev.gaea.math.FastNoise;
import org.polydev.gaea.math.parsii.eval.Expression;
import org.polydev.gaea.math.parsii.eval.Function;

import java.util.List;

public class NoiseFunction2 implements Function {
    private FastNoise gen;

    @Override
    public int getNumberOfArguments() {
        return 2;
    }

    @Override
    public double eval(List<Expression> list) {
        return gen.getSimplexFractal((float) list.get(0).evaluate(), (float) list.get(1).evaluate());
    }

    public void setNoise(FastNoise gen, boolean override) {
        if(this.gen == null || override) this.gen = gen;
    }

    @Override
    public boolean isNaturalFunction() {
        return true;
    }
}