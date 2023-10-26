package main.abstractions;

import java.util.List;
import main.trees.Branch;

public abstract class AbstractTree {
        public abstract void grow();

        public abstract void bloom();

        public abstract void performPhotosynthesis();
        protected int rootDepth;
        protected int height;

        protected List<Branch> branches;
    }