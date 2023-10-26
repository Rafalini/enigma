package main.trees.needly;

import main.abstractions.AbstractLeave;

public class Needle extends AbstractLeave {
    int length;

    @Override
    public float photosynthesis() {
        return (float) (Math.sqrt(length) * fotosynthesisEfficiency);
    }
}
