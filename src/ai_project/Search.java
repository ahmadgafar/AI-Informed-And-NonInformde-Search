package ai_project;

import java.util.Collection;
// This interface is inharited by the AbstractTreeSearch
public interface Search {
    public Node solve(Problem problem);
    public Collection<Node> expand(Node node, Problem problem);    
}
