package ai_project;

import java.util.Collection;
import java.util.Stack;

public class DepthFirstTreeSearch extends AbstractTreeSearch {

    public Collection<Node> nodeCollection() {
        return new Stack<Node>();
    }

    public Node chooseLeafNode(Collection<Node> nodeCollection, Problem problem) {
        return ((Stack<Node>) nodeCollection).pop();
    }
}
