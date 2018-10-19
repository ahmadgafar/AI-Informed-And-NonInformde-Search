package ai_project;

import java.util.Collection;
import java.util.LinkedList;

public class BreadthTreeSearch extends AbstractTreeSearch {

    @Override
    public Collection<Node> nodeCollection() {
        return new  LinkedList <Node>();
    }

    @Override
    public Node chooseLeafNode(Collection<Node> nodeCollection, Problem problem) {
        return ((LinkedList<Node>) nodeCollection).pop();
    }
    
}
