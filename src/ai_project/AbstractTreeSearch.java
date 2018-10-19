package ai_project;

import java.util.ArrayList;
import java.util.Collection;

public abstract class AbstractTreeSearch implements Search {
    int expansion_number = 0 ;
    Collection<Node> expansionSequence;
    ArrayList<State> RepeatedState = new ArrayList<State>();;

    public Node solve(Problem problem) {
        //System.out.println("Solving...");
        // creating empty sturcture of nodes
        expansionSequence = nodeCollection();
        // adding all the children of the root node
        expansionSequence.addAll(expand(new Node(problem.getInitialState()), problem));
        // increasing the number of expanded nodes
        expansion_number++; 
        //System.out.println("Starting expansion is " + expansionSequence);
        // done is flag to tell if we have reached a solution or failur or still having nodes to expand
        boolean done = false;
        // the soultion node is null before searching
        Node solution = null;
        while (!done) {
            // if there is no more nodes to expand and we did not reach solution
            if (expansionSequence.isEmpty())
            {
                System.out.println("No more nodes to expand => FAILURE");
                System.out.println("Expansion number is "+expansion_number);
                done = true;
            }
            // else expand the remaining nodes
            else 
            {
                // choose the node to expand according to the search method (BFS,DFS, IDS , ....)
                Node node = chooseLeafNode(expansionSequence, problem);
                // printing the exanded node information
                //System.out.println(node.getOperator());
                //System.out.println(((State)node.getState()).numOfWW);             
                //System.out.println(((State)node.getState()).dragonStones); 
                // checking if the current node is a goal State or not
                if (problem.isGoal(node.getState())) {
                    System.out.println("Goal node reached => SUCCESS");
                    System.out.println("Expansion number is "+expansion_number);
                    System.out.println("level number is "+node.getDepth());
                    solution = node;
                    done = true;
                } else {
                    // expand the current node and continue searching
                    expansionSequence.addAll(expand(node, problem));
                    // increment number of expanded nodes by one
                    expansion_number++; 
                }
            }
        }
        return solution;
    }

    public Collection<Node> expand(Node node, Problem problem) {
        // creating a empty list of nodes to put in it the children of the expanded input node
        Collection<Node> nodes = new ArrayList<Node>();
        // getting all possible actions that can be performed from this node or State
        Collection<Object> actions = problem.getActions(node.getState());
        RepeatedState.add((State)node.getState());
        for (Object action : actions) {
            // next is node that is produced by preforming action to current node 
            Object next = problem.getNextState(node.getState(), action);
            // nextState is the State of the next node we use it to ensure 
            //that the agent do not go to the State he was perviously in.
            State nextState = (State) next; 
            if ( State.repeatCheck(nextState, RepeatedState))
            {
                //Do nothing
                //System.out.println("Repeated");
            }
            else
            { // add the node with State next, parent node, the depth of parent node+1, 
              //the cost of the going to this node from root.
             nodes.add(new Node(next, node, action,node.getDepth()+1 , problem.getStepCost(node,action,next)));    
            }
        }   
        return nodes;
    }
    //abstract method inherited by seach stratgies to define the Collection
    //of nodes structure (Stack,Queue,ArrayList,...)
    public abstract Collection<Node> nodeCollection();
    //abstract method inherited by seach stratgies to define how to choose the node to expand
    public abstract Node chooseLeafNode(Collection<Node> nodeCollection, Problem problem);
}
