package searchclient;

import java.util.Comparator;

public abstract class Heuristic implements Comparator<State> {
    private int heurist;
    char[][] boxes;
    char[][] goal;

    public Heuristic(State initialState) {
        // Here's a chance to pre-process the static parts of the level.
        boxes = initialState.boxes;
        goal = initialState.goals;
        heurist = 0;

    }

    public int h(State n) {
        heurist = 0;
        for(int i = 0; i < n.boxes.length; i++){
            for(int j = 0; j < n.boxes[i].length; j++){
                if (boxes[i][j] >= 'A' && boxes[i][j] <= 'Z'){
                    char ca = boxes[i][j];
                    for(int a = 0; a < n.boxes.length; a++){
                        for (int b = 0; b < n.boxes[a].length; b++){
                            if(Character.toLowerCase(ca) == goal[a][b])
                                heurist += Math.abs(i - a) + Math.abs(j - b);
                        }
                    }
                }
            }
        }
        return heurist;
    }

    public abstract int f(State n);

    @Override
    public int compare(State n1, State n2) {
        return this.f(n1) - this.f(n2);
    }

    public static class AStar extends Heuristic {
        public AStar(State initialState) {
            super(initialState);
        }

        @Override
        public int f(State n) {
            return n.g() + this.h(n);
        }

        @Override
        public String toString() {
            return "A* evaluation";
        }
    }

    public static class WeightedAStar extends Heuristic {
        private int W;

        public WeightedAStar(State initialState, int W) {
            super(initialState);
            this.W = W;
        }

        @Override
        public int f(State n) {
            return n.g() + this.W * this.h(n);
        }

        @Override
        public String toString() {
            return String.format("WA*(%d) evaluation", this.W);
        }
    }

    public static class Greedy extends Heuristic {
        public Greedy(State initialState) {
            super(initialState);
        }

        @Override
        public int f(State n) {
            return this.h(n);
        }

        @Override
        public String toString() {
            return "Greedy evaluation";
        }
    }
}
