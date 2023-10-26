package main.trees.needly;

import main.abstractions.AbstractTree;
import main.trees.Branch;
import main.trees.leafy.Fruit;
import main.trees.leafy.Leaf;

import java.util.Collections;
import java.util.Iterator;

public class Conifer extends AbstractTree {

    float photosynthesisThreshold = 0.1f;
    float needleBatchSize = 50; //amount of needles to grow at once
    public void grow() {
        this.height++;
        if(this.height % 4 == 0) // root grows approx 4 times slower than trunk, leaves, branches etc on conifer trees
            this.rootDepth ++;

        if(this.height%2 == 0)
            this.branches.add(new Branch());

        Collections.shuffle(this.branches);
        Iterator<Branch> randomBranches = this.branches.subList(0, (int)(this.branches.size() * 0.25)).iterator(); //25% of branchess will grow a leave

        randomBranches.forEachRemaining(element -> {
            element.growNewLeave(new Needle());
        });
    }

    @Override
    public void bloom() { //turn leave into fruit on each branch
        Iterator<Branch> randomBranches = this.branches.iterator();

        randomBranches.forEachRemaining(element -> {
            element.growNewFruit(new Fruit());
        });
    }

    @Override
    public void performPhotosynthesis() {
        Iterator<Branch> randomBranches = this.branches.iterator();

        randomBranches.forEachRemaining(element -> {
            if(element.photosynthesis() > photosynthesisThreshold)  //grow leaf if there is sufficient energy from photosynthesis
                for (int i = 0; i < needleBatchSize; i++)
                    element.growNewLeave(new Needle());
        });
    }
}
