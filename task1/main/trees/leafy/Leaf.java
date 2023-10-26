package main.trees.leafy;

import main.abstractions.AbstractLeave;

public class Leaf extends AbstractLeave {
    public Leaf(){}
    public Leaf(int size, String color){
        this.size = size;
        this.color = color;
    }
    int size;

    @Override
    public float photosynthesis() {
        return size * fotosynthesisEfficiency;
    }
}
