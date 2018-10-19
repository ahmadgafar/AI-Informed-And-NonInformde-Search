package ai_project;
//Class defining how we represent the State for the SaveWastors.

import com.sun.accessibility.internal.resources.accessibility;
import java.util.ArrayList;
import java.util.Collection;

public class State {

    public int x, y; // x and y position
    public int dragonStones; // dragon stones 
    public int numOfWW; //# of white walkers
    public Point[] killedWW = null;

    public State(int x, int y, int dragonStones, int numOfWW, Point[] killedWW) {
        this.x = x;
        this.y = y;
        this.dragonStones = dragonStones;
        this.numOfWW = numOfWW;
        this.killedWW = killedWW.clone();
    }

    public static void main(String[] args) {
        Point pa = new Point(1,2);
        Point pb = new Point(1,2);
        Point[] aps= {pa,pa,pb,null};
        Point[] bps= {pa,pa,pb,pb};
        
        State a = new State(12, 12, 12, 12,aps);
        State b = new State(12, 12, 12, 12,bps);
        
        System.out.println(compareState(a, b));
    }

    public State(int x, int y, int dragonStones, int numOfWW) {
        this.x = x;
        this.y = y;
        this.dragonStones = dragonStones;
        this.numOfWW = numOfWW;
        this.killedWW = new Point[numOfWW];
    }

    public State(int numOfWW) {
        this.numOfWW = numOfWW;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getDragonStones() {
        return dragonStones;
    }

    public int getNumOfWW() {
        return numOfWW;
    }

    public Point[] getKilledWW() {
        return killedWW;
    }

    @Override
    public String toString() {
        return "state{" + "x=" + x + ", y=" + y + ", dragonStones=" + dragonStones + ", numOfWW=" + numOfWW + ", killedWW=" + killedWW + '}';
    }

    public static boolean repeatCheck(State s, ArrayList<State> List) {
        boolean found = false;
        int i = 0;
        while (!found && i < List.size()) {
            found = compareState(s, List.get(i));
            i++;
        }
        return found;
    }

    public static boolean compareState(State s1, State s2) {
        int len;
        boolean equal = false;
        if (s1 != null && s2 != null) {
            if (s1.x == s2.x && s1.y == s2.y && s1.dragonStones == s2.dragonStones && s1.numOfWW == s2.numOfWW) {
                equal = true;
                if (s1.killedWW == null && s2.killedWW == null) {
                    return true;
                } else {
                    if ((s1.killedWW != null && s2.killedWW == null) || (s1.killedWW == null && s2.killedWW != null)) {
                        return false;
                    } else {
                        len = Math.min(s1.killedWW.length, s2.killedWW.length);
                        for (int i = 0; i < len; i++) {
                            if (s1.killedWW[i] != null && s2.killedWW[i] != null) {
                                if (s1.killedWW[i].x != s2.killedWW[i].x && s1.killedWW[i].y != s2.killedWW[i].y) {
                                    equal = false;
                                    return equal;
                                }
                            } else {
                                if ((s1.killedWW[i] != null && s2.killedWW[i] == null) || (s1.killedWW[i] == null && s2.killedWW[i] != null)) {
                                    return false;
                                }
                            }

                        }
                    }
                }
            }
        }
        return equal;
    }
}
