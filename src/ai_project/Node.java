package ai_project;

public class Node {  
    private Object state;
    private Node parent;
    private Object operator;
    private int depth;
    public int path_cost;
    
    // Constractor for any node
    public Node(Object s,Node p,Object o,int d,int cost){
        this.state = s;
        this.parent = p;
        this.operator = o;
        this.depth = d;
        this.path_cost = cost;    
    }
    // Constractor for root node
    public Node(Object s){
        this.state = s;
        this.parent = null;
        this.operator = null;
        this.depth = 0;
        this.path_cost = 0;    
    }

    public Object getState() {
        return state;
    }

    public Node getParent() {
        return parent;
    }

    public Object getOperator() {
        return operator;
    }

    public int getDepth() {
        return depth;
    }

    public int getPath_cost() {
        return path_cost;
    }
    
}
