package ai_project;

import static ai_project.Point.isDeadWWinArray;
import static ai_project.MapGen.*;
import ai_project.AbstractTreeSearch;
import java.util.ArrayList;
import java.util.Collection;

public class SaveWesteros extends Problem {

    // generating grid 5*5
    public static World main = new World(5, 5, 4);
    // defining intial State of the problem with 4,4 postion of the agent , 0 dragonstones, 4 WhiteWalkers
    public State initialState = new State(main.x_position, main.y_position, 0, main.ww_number);

    public SaveWesteros(int xG, int yG, int WW) {
        initialState = new State(xG, yG, 0, WW);
    }

    public static void main(String[] args) {
        Cell[][] map1 = {
            {Cell.empty, Cell.empty, Cell.empty, Cell.empty, Cell.empty},
            {Cell.obstacle, Cell.empty, Cell.ww, Cell.obstacle, Cell.empty},
            {Cell.empty, Cell.empty, Cell.dragonstone, Cell.empty, Cell.ww},
            {Cell.empty, Cell.empty, Cell.empty, Cell.ww, Cell.empty},
            {Cell.ww, Cell.empty, Cell.empty, Cell.empty, Cell.snow}};

        Cell[][] map2 = {{Cell.empty, Cell.empty, Cell.empty, Cell.empty, Cell.empty, Cell.empty},
        {Cell.empty, Cell.empty, Cell.empty, Cell.dragonstone, Cell.empty, Cell.ww},
        {Cell.ww, Cell.empty, Cell.ww, Cell.empty, Cell.empty, Cell.empty},
        {Cell.empty, Cell.empty, Cell.empty, Cell.empty, Cell.ww, Cell.empty},
        {Cell.ww, Cell.empty, Cell.obstacle, Cell.empty, Cell.empty, Cell.empty},
        {Cell.empty, Cell.ww, Cell.empty, Cell.empty, Cell.obstacle, Cell.snow}};
        Cell[][] mapR = MapGen.GenGrid(5, 5, 4).clone();
        MapGen.pMap(mapR, 5, 5);
        Search(mapR, "AS1", false);
        Search(mapR, "AS2", false);
        Search(mapR, "GR1", false);
        Search(mapR, "GR2", false);
        Search(mapR, "BF", false);
        Search(mapR, "DF", false);
        Search(mapR, "ID", false);
        Search(mapR, "UC", false);
    }

    public static Object[] Search(Cell[][] grid, String strategy, boolean visualize) {
        int en = 0;
        Object[] result = new Object[3];
        SaveWesteros saveWesteros = new SaveWesteros(main.x_position, main.y_position, main.ww_number);
        Node solution = null;
        if (grid != null) {
            main.ww_positions = main.ScanMap(grid).clone();
            saveWesteros = new SaveWesteros(main.x_position, main.y_position, main.ww_number);
        }

        switch (strategy) {
            case "BF":
                System.out.println("Expansion Technique : Breadth First");
                BreadthTreeSearch bft = new BreadthTreeSearch();
                solution = bft.solve(saveWesteros);
                en = bft.expansion_number;
                break;
            case "DF":
                System.out.println("Expansion Technique : Depth First");
                DepthFirstTreeSearch dft = new DepthFirstTreeSearch();
                solution = dft.solve(saveWesteros);
                en = dft.expansion_number;
                break;
            case "ID":
                System.out.println("Expansion Technique : Iterative");
                IterativeTreeSearch ift = new IterativeTreeSearch();
                solution = ift.solve(saveWesteros);
                en = ift.expansion_number;
                break;
            case "UC":
                System.out.println("Expansion Technique : Uniform Cost");
                UniformTreeSearch uft = new UniformTreeSearch();
                solution = uft.solve(saveWesteros);
                en = uft.expansion_number;
                break;
            case "GR1":
                System.out.println("Expansion Technique : Greedy search using Manhattan heuristic function");
                GreedyTreeSearch gft1 = new GreedyTreeSearch(true);
                solution = gft1.solve(saveWesteros);
                en = gft1.expansion_number;
                break;
            case "GR2":
                System.out.println("Expansion Technique : Greedy search using Euclidean heuristic function");
                GreedyTreeSearch gft2 = new GreedyTreeSearch(false);
                solution = gft2.solve(saveWesteros);
                en = gft2.expansion_number;
                break;
            case "AS1":
                System.out.println("Expansion Technique : A* search using Manhattan heuristic function");
                AStarTreeSearch aft1 = new AStarTreeSearch(true);
                solution = aft1.solve(saveWesteros);
                en = aft1.expansion_number;
                break;
            case "AS2":
                System.out.println("Expansion Technique : A* search using Euclidean heuristic function");
                AStarTreeSearch aft2 = new AStarTreeSearch(true);
                solution = aft2.solve(saveWesteros);
                en = aft2.expansion_number;
                break;

            default:
                System.out.println("using breadth as default case");
                bft = new BreadthTreeSearch();
                solution = bft.solve(saveWesteros);
                en = bft.expansion_number;
                break;
        }

        result[0] = printSolution(solution);
        result[1] = solution.getPath_cost();
        result[2] = en;
        System.out.println("Cost to reach goal => " + solution.getPath_cost());
        if (visualize) {
            MapGen.Visualize(main.grid, solution);
        }
        return result;
    }

    @Override
    public Object getInitialState() {
        return initialState;
    }

    @Override
    public boolean isGoal(Object state) {
        State fs = (State) state;
        // the goal is to kill all the whitewalkers in the grid
        if (fs.numOfWW == 0) {
            return true;
        }
        return false;
    }

    @Override
    public Collection<Object> getActions(Object state) {

        State current_state = (State) state;
        int x = current_state.x;
        int y = current_state.y;
        int stones = current_state.dragonStones;
        Point[] killedWW = current_state.killedWW;
        ArrayList<Object> possible_actions = new ArrayList<Object>();
        //System.out.println(x + " " + y);
        boolean fL = false;
        boolean fR = false;
        boolean fU = false;
        boolean fD = false;
        if (x != 0) {
            Point pU = new Point(x - 1, y);
            fU = pU.inArray(killedWW);
        }
        if (x != main.x_grid) {
            Point pD = new Point(x + 1, y);
            fD = pD.inArray(killedWW);
        }
        if (y != 0) {
            Point pL = new Point(x, y - 1);
            fL = pL.inArray(killedWW);
        }
        if (y != main.y_grid) {
            Point pR = new Point(x, y + 1);
            fR = pR.inArray(killedWW);
        }

        // to check for possible movement
        if (main.checkLeft(x, y) == Cell.empty || main.checkLeft(x, y) == Cell.dragonstone || fL) {
            possible_actions.add(Actions.moveLeft);
        }
        if (main.checkUp(x, y) == Cell.empty || main.checkUp(x, y) == Cell.dragonstone || fU) {
            possible_actions.add(Actions.moveUp);
        }
        if (main.checkRight(x, y) == Cell.empty || main.checkRight(x, y) == Cell.dragonstone || fR) {
            possible_actions.add(Actions.moveRight);
        }
        if (main.checkDown(x, y) == Cell.empty || main.checkDown(x, y) == Cell.dragonstone || fD) {
            possible_actions.add(Actions.moveDown);
        }

        // to check for if in dragonstone store Cell
        if (main.checkCurrent(x, y) == Cell.dragonstone && stones < main.max_stones) {
            possible_actions.add(Actions.pickDragonstone);
        }

        // to check for possible any WhiteWalker to Kill
        if (main.checkSurroundingWW(x, y) != 0 && stones != 0) {
            possible_actions.add(Actions.useDragonStone);
        }

        return possible_actions;
    }

    @Override
    public Object getNextState(Object state, Object action) {
        State current_state = (State) state;
        int x = current_state.x;
        int y = current_state.y;
        int stones = current_state.dragonStones;
        int ww = current_state.numOfWW;
        Point[] killedWW = current_state.killedWW;

        Actions current_action = (Actions) action;
        if (current_action == Actions.pickDragonstone) {
            // new State with agent having full bag of dragonstones after performing pick action
            return new State(x, y, main.max_stones, ww, killedWW);
        }
        if (current_action == Actions.useDragonStone) {
            // new State with agent decrease stones by one after using dragonstone and if there
            // is any WhiteWalkers dead reduce the number of WhiteWalkers in the grid
            stones--;
            //there is problem that we do not delet the WW after killing him so the agent kills
            //can kill him again

            // checkSurrounding return how many Adjcent WhiteWalkers are.
            int deadWW = 0;
            if (main.checkUp(x, y) == Cell.ww) {
                Point p = new Point(x - 1, y);
                if (!p.inArray(killedWW)) {
                    deadWW++;
                    killedWW = p.addInArray(killedWW);
                }
            }
            if (main.checkDown(x, y) == Cell.ww) {
                Point p = new Point(x + 1, y);
                if (!p.inArray(killedWW)) {
                    deadWW++;
                    killedWW = p.addInArray(killedWW);
                }
            }
            if (main.checkRight(x, y) == Cell.ww) {
                Point p = new Point(x, y + 1);
                if (!p.inArray(killedWW)) {
                    deadWW++;
                    killedWW = p.addInArray(killedWW);
                }
            }
            if (main.checkLeft(x, y) == Cell.ww) {
                Point p = new Point(x, y - 1);
                if (!p.inArray(killedWW)) {
                    deadWW++;
                    killedWW = p.addInArray(killedWW);
                }
            }
            int newWW = ww - deadWW;
            //main.printMap();
            //System.out.println(ww+" after kill :"+ newWW);
            return new State(x, y, stones, newWW, killedWW);
        }

        // return new State with the updated postion based on the move direction performed.
        if (current_action == Actions.moveDown) {
            return new State(x + 1, y, stones, ww, killedWW);
        }
        if (current_action == Actions.moveUp) {
            return new State(x - 1, y, stones, ww, killedWW);
        }
        if (current_action == Actions.moveRight) {
            return new State(x, y + 1, stones, ww, killedWW);
        }
        if (current_action == Actions.moveLeft) {
            return new State(x, y - 1, stones, ww, killedWW);
        }
        return null;

    }

    public int getStepCost(Object start, Object action, Object dest) {
        Actions current_action = (Actions) action;
        int cost = ((Node) start).getPath_cost();
        // assigning the cost to all Actions cost of moving and picking up stone and using stone 
        if (current_action == Actions.moveDown || current_action == Actions.moveLeft
                || current_action == Actions.moveRight || current_action == Actions.moveUp) {
            cost += 10;
        }
        if (current_action == Actions.pickDragonstone) {
            cost += 2;
        }
        if (current_action == Actions.useDragonStone) {
            cost += 1;
        }

        return cost;

    }

    public static int hueristic1(Object start) {
        State current_state = ((State) start);
        int x = current_state.x;
        int y = current_state.y;
        int stones = current_state.dragonStones;
        int ww = current_state.numOfWW;
        Point[] killedWW = current_state.killedWW;
        boolean foundWW = false;

        if (stones == 0) {
            int temp = main.x_dragonstone;
            int diff_x = Math.abs(temp - x);
            temp = main.y_dragonstone;
            int diff_y = Math.abs(temp - y);
            return (diff_x + diff_y);
        } else {
            int value = 100;
            int diff_x = 0;
            int diff_y = 0;
            for (int i = 0; i < main.ww_positions.length; i++) {
                if (killedWW != null && main.ww_positions[i] != null && !isDeadWWinArray(killedWW, main.ww_positions[i])) {
                    diff_x = Math.abs(x - ((Point) main.ww_positions[i]).x);
                    diff_y = Math.abs(y - ((Point) main.ww_positions[i]).y);
                }
                if (value > (diff_x + diff_y)) {
                    value = (diff_x + diff_y);
                }

            }
            return value;

        }
    }

    public static int hueristic2(Object start) {
        State current_state = (State) start;
        int x = current_state.x;
        int y = current_state.y;
        int stones = current_state.dragonStones;
        int ww = current_state.numOfWW;
        Point[] killedWW = current_state.killedWW;
        boolean foundWW = false;

        if (stones == 0) {
            int temp = main.x_dragonstone;
            int diff_x = Math.abs(temp - x);
            temp = main.y_dragonstone;
            int diff_y = Math.abs(temp - y);
            return (int) Math.sqrt(diff_x * diff_x + diff_y * diff_x);
        } else {
            int value = 100;
            int diff_x = 0;
            int diff_y = 0;
            for (int i = 0; i < main.ww_positions.length; i++) {
                if (killedWW != null && main.ww_positions[i] != null && !isDeadWWinArray(killedWW, main.ww_positions[i])) {
                    diff_x = Math.abs(x - ((Point) main.ww_positions[i]).x);
                    diff_y = Math.abs(y - ((Point) main.ww_positions[i]).y);
                }
                int temp = (int) Math.sqrt(diff_x ^ 2 + diff_y ^ 2);
                if (value > temp) {
                    value = temp;
                }

            }
            return value;

        }
    }

    public static String printSolution(Node solution) {
        String actStr = "";
        if (solution != null) {
            boolean flag = false;
            actStr = "";

            Point[] temp = ((State) solution.getState()).killedWW;

            Object a = null;
            while (!flag) {
                if (solution != null) {
                    a = solution.getOperator();
                    if (a != null) {
                        actStr = a + " " + actStr;
                    }
                    solution = solution.getParent();
                } else {
                    flag = true;
                    //System.out.println(actStr);
                }
            }
            // to print ww positions

//        for (int i = 0; i < temp.length; i++) {
//            System.out.println(temp[i].toString());
//
//        }
        }
        return actStr;
    }

}
