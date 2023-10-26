package main.trees.leafy;

import main.trees.Branch;
import main.abstractions.AbstractTree;

import java.util.Collections;
import java.util.Iterator;

public class LeafyTree extends AbstractTree {

    float photosynthesisThreshold = 0.8f;
    @Override
    public void grow() {
        this.height++;
        if(this.height % 5 == 0) // root grows approx 5 times slower than trunk, leaves, branches etc
            this.rootDepth ++;

        if(this.height%2 == 0)
            this.branches.add(new Branch());

        Collections.shuffle(this.branches);
        Iterator<Branch> randomBranches = this.branches.subList(0, (int)(this.branches.size() * 0.25)).iterator(); //25% of branchess will grow a leave

        randomBranches.forEachRemaining(element -> {
            element.growNewLeave(new Leaf());
        });
    }

    @Override
    public void bloom() { //turn leave into fruit on each branch
        Iterator<Branch> randomBranches = this.branches.iterator();

        randomBranches.forEachRemaining(element -> {
            element.dropLeave();
            element.growNewFruit(new Fruit());
        });
    }

    @Override
    public void performPhotosynthesis() {
        Iterator<Branch> randomBranches = this.branches.iterator();

        randomBranches.forEachRemaining(element -> {
            if(element.photosynthesis() > photosynthesisThreshold)  //grow leaf if there is sufficient energy from photosynthesis
                element.growNewLeave(new Leaf());
        });
    }

    public void sheaveLeaves(){
        Iterator<Branch> randomBranches = this.branches.iterator();
        randomBranches.forEachRemaining(Branch::dropAllLeaves);
    }
}