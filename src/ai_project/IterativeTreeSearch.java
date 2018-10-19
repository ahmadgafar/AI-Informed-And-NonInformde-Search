package ai_project;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Stack;

public class IterativeTreeSearch extends AbstractTreeSearch {

    public int level;
    public int max = 500;

    @Override
    public Collection<Node> nodeCollection() {
        return new Stack<Node>();
    }

    @Override
    public Node chooseLeafNode(Collection<Node> nodeCollection, Problem problem) {
        return ((Stack<Node>) nodeCollection).pop();
    }


    public Node solve(Problem problem) {
        level = 0;
        Node Initial = new Node(problem.getInitialState());
        Node n ;
        expansionSequence = nodeCollection();
        expansionSequence.addAll(expand(Initial, problem));
        expansion_number++;

        while (true) {
            if (expansionSequence.isEmpty()) {
                System.out.println("No more nodes in nodeCollection => FAILURE");
                System.out.println("Expansion number is " + expansion_number);
                return null;
            } else {
                while (!expansionSequence.isEmpty()) {
                    n = chooseLeafNode(expansionSequence, problem);
                    if (problem.isGoal(n.getState())) {
                        System.out.println("Goal node reached => SUCCESS");
                        System.out.println("Expansion number is " + expansion_number);
                        System.out.println("level number is "+n.getDepth());
                        return n;
                    }
                    if (level >= n.getDepth()) {
                        expansionSequence.addAll(expand(n, problem));
                        expansion_number++;
                    }
                }
                if (expansionSequence.isEmpty()) {
                    level++;
                    RepeatedState.clear();
                    expansionSequence.addAll(this.expand(Initial, problem));
                }
            }
        }
    }

}
