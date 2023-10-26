package main.trees;

import main.abstractions.AbstractFruit;
import main.abstractions.AbstractLeave;
import main.trees.leafy.Fruit;

import java.util.Iterator;
import java.util.List;

public class Branch {

    public void growNewFruit(AbstractFruit newFruit){
        fruits.add(newFruit);
    }
    public void growNewLeave(AbstractLeave newLeave){
        leaves.add(newLeave);
    }

    public List<AbstractFruit> getFruits(){return this.fruits;}
    public List<AbstractLeave> getLeaves(){return this.leaves;}

    public float photosynthesis(){
        Iterator<AbstractLeave> leaves = this.leaves.iterator();
        final float[] energy = {0};
        leaves.forEachRemaining(element -> {
            energy[0] += element.photosynthesis();
        });

        return energy[0];
    }

    public void dropLeave(){
        if (!this.leaves.isEmpty())
            this.leaves.remove(0);
    }

    public void dropAllLeaves(){
        this.leaves.clear();
    }
    List<AbstractFruit> fruits;
    List<AbstractLeave> leaves;
}