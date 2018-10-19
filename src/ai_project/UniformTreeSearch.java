package ai_project;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class UniformTreeSearch extends AbstractTreeSearch {

    @Override
    public Collection<Node> nodeCollection() {
            return new  ArrayList<Node>();
    }

    @Override
    public Node chooseLeafNode(Collection<Node> nodeCollection, Problem problem) {
        ArrayList nodes = (ArrayList) nodeCollection;
        Collections.sort(nodes,nodeCompare);
        Node popped = (Node)nodes.get(0);
        int i = 0 ;
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
    
}
