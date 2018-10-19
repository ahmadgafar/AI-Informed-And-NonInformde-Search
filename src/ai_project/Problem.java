package ai_project;

import java.util.Collection;
// abstract search problem 
public abstract class Problem {
  
    public abstract Object getInitialState();
    public abstract boolean isGoal(Object state);
    public abstract Collection<Object> getActions(Object state);
    public abstract Object getNextState(Object state,Object action);
    public abstract int getStepCost(Object start, Object action, Object dest);  
}
