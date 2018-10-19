package ai_project;

import static ai_project.SaveWesteros.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class GreedyTreeSearch extends AbstractTreeSearch {

    boolean heuristic = true;

    public GreedyTreeSearch(boolean heuristic) {
        this.heuristic = heuristic;
    }

    @Override
    public Collection<Node> nodeCollection() {
        return new ArrayList<Node>();

    }

    @Override
    public Node chooseLeafNode(Collection<Node> nodeCollection, Problem problem) {
        ArrayList nodes = (ArrayList) nodeCollection;
        nodes.sort(nodeCompare);
        Node popped = (Node) nodes.get(0);
        int i = 0;
        nodeCollection.remove(popped);
        return popped;
    }

    public static Comparator<Node> nodeCompare
            = new Comparator<Node>() {

        public int compare(Node node1, Node node2) {

            int node1path = node1.getPath_cost();
            int node2path = node2.getPath_cost();
            //ascending order
            return ((node1path > node2path) ? node2path : node1path);

        }

    };

    public Collection<Node> expand(Node node, Problem problem) {
        // creating a empty list of nodes to put in it the children of the expanded input node
        Collection<Node> nodes = new ArrayList<Node>();
        // getting all possible actions that can be performed from this node or State
        Collection<Object> actions = problem.getActions(node.getState());
        RepeatedState.add((State) node.getState());
        for (Object action : actions) {
            // next is node that is produced by preforming action to current node 
            Object next = problem.getNextState(node.getState(), action);
            // nextState is the State of the next node we use it to ensure 
            //that the agent do not go to the State he was perviously in.
            State nextState = (State) next;
            if (State.repeatCheck(nextState, RepeatedState)) {
                //Do nothing
                //System.out.println("Repeated");
            } else { // add the node with State next, parent node, the depth of parent node+1, 
                //the cost of the going to this node from root.
                if (heuristic) {
                    nodes.add(new Node(next, node, action, node.getDepth() + 1, node.getPath_cost()+hueristic1(next)));
                } else {
                    nodes.add(new Node(next, node, action, node.getDepth() + 1, node.getPath_cost()+hueristic2(next)));
                }
            }
        }
        return nodes;
    }

}
