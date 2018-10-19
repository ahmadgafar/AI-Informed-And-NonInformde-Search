package ai_project;

import java.util.Collection;
import java.util.Random;
import java.util.Stack;

// This class is responsilbe for Visualization, printing map, and Generating Random maps
/**
This class is responsilbe for Visualization, printing map, and Generating Random maps
*/
public class MapGen {
    
    public static void pMap(Cell[][] map, int x, int y) {
/**
pMap prints the map that is given to it 
@param Cell[][],int,int
@return void

*/        
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                System.out.print(map[i][j] + "       ");
            }
            System.out.println("");
        }

        System.out.println("\n \n");
    }

    public static void Visualize(Cell[][] map, Node s) {
/**
Visualize prints the map after every action the agent do to reach the goal, input map and node
* and performs the actions from the root to  this current node 
@param Cell[][],Node
@return void

*/        
        
        if (s != null) {
            int sX = 0;
            int sY = 0;
            for (int ci = 0; ci < map.length; ci++) {
                for (int cj = 0; cj < map[ci].length; cj++) {
                    if (map[ci][cj] == Cell.snow) {
                        sX = ci;
                        sY = cj;
                    }
                }
            }
            Collection<Actions> act = new Stack<Actions>();
            while (s.getOperator() != null) {
                ((Stack<Actions>) act).push((Actions) s.getOperator());
                s = s.getParent();
            }

            while (!act.isEmpty()) {
                Actions a = ((Stack<Actions>) act).pop();
                if (a == Actions.moveDown) {
                    map = vHelper(map, a, sX, sY).clone();
                    sX++;
                }
                if (a == Actions.moveUp) {
                    map = vHelper(map, a, sX, sY).clone();
                    sX--;
                }
                if (a == Actions.moveRight) {
                    map = vHelper(map, a, sX, sY).clone();
                    sY++;
                }
                if (a == Actions.moveLeft) {
                    map = vHelper(map, a, sX, sY).clone();
                    sY--;
                }
                if (a == Actions.useDragonStone) {
                    map = vHelper(map, a, sX, sY).clone();
                }
                //if(a== Actions.pickDragonstone)
                //System.err.println("PickDragonStone");   
            }
        }

    }

    public static Cell[][] vHelper(Cell[][] mhelp, Actions a, int sX, int sY) {
/**
Visualize helper function , updates the map after an action have been done and print the map.
@param Cell[][],Actions,int,int
@return Cell[][]

*/            
        
        boolean s =false;
        if(a !=Actions.pickDragonstone || a!=Actions.useDragonStone){
            if(mhelp[sX][sY]==Cell.SnowInStore){
               mhelp[sX][sY]=Cell.dragonstone;
               s=true;
            }
        }
        if (a == Actions.moveDown) {
            if(mhelp[sX + 1][sY]==Cell.dragonstone)
            mhelp[sX + 1][sY] = Cell.SnowInStore;
            else
            mhelp[sX + 1][sY] = Cell.snow;
            if(!s)mhelp[sX][sY] = Cell.empty;
        }
        if (a == Actions.moveUp) {
            if(mhelp[sX - 1][sY]==Cell.dragonstone)
            mhelp[sX - 1][sY] = Cell.SnowInStore;
            else
            mhelp[sX - 1][sY] = Cell.snow;
            if(!s)mhelp[sX][sY] = Cell.empty;
        }

        if (a == Actions.moveRight) {
            if(mhelp[sX][sY+1]==Cell.dragonstone)
            mhelp[sX][sY + 1] = Cell.SnowInStore;
            else
            mhelp[sX][sY+1] = Cell.snow;
            if(!s)mhelp[sX][sY] = Cell.empty;
        }
        if (a == Actions.moveLeft) {
            if(mhelp[sX][sY-1]==Cell.dragonstone)
            mhelp[sX][sY - 1] = Cell.SnowInStore;
            else
            mhelp[sX][sY-1] = Cell.snow;
            if(!s)mhelp[sX][sY] = Cell.empty;
        }
        if (a == Actions.useDragonStone) {
            if (sX != 0) {
                if (mhelp[sX - 1][sY] == Cell.ww) {
                    mhelp[sX - 1][sY] = Cell.deadww;
                }
            }
            if (sY != 0) {
                if (mhelp[sX][sY - 1] == Cell.ww) {
                    mhelp[sX][sY - 1] = Cell.deadww;
                }
            }
            if (sX != mhelp.length - 1) {
                if (mhelp[sX + 1][sY] == Cell.ww) {
                    mhelp[sX + 1][sY] = Cell.deadww;
                }
            }
            if (sY != mhelp[sX].length - 1) {
                if (mhelp[sX][sY + 1] == Cell.ww) {
                    mhelp[sX][sY + 1] = Cell.deadww;
                }
            }
        }
        pMap(mhelp, mhelp.length, mhelp[sX].length);
        return mhelp;
    }

    public static Cell[][] GenGrid(int x_grid, int y_grid, int ww_number) {
/**
GenGrid is responsible for generating random grid with the dimensions x_grid*y_grid with ww_umber of WW 
@param int,int,int
@return void
*/    
        
        if (x_grid < 4 || y_grid < 4 || ww_number < 1) {
            return null;
        }
        Random rn = new Random();
        Cell[][] grid = new Cell[x_grid][y_grid];
        grid[x_grid - 1][y_grid - 1] = Cell.snow; // snow in bottom right Cell
        // for loop to generate ww in random cells in map
        for (int i = 0; i < ww_number;) {
            int x = rn.nextInt(x_grid);
            int y = rn.nextInt(y_grid);

            if (grid[x][y] == null) {
                grid[x][y] = Cell.ww;
                i++;
            }
        }
        // for loop to generate dragonstone in random Cell
        for (int i = 0; i < 1;) {
            int x = rn.nextInt(x_grid);
            int y = rn.nextInt(y_grid);
            if (grid[x][y] == null) {
                grid[x][y] = Cell.dragonstone;
                i++;
            }
        }
        // for loop to generate obstacle in random Cell
        for (int i = 0; i < 2;) {
            int x = rn.nextInt(x_grid);
            int y = rn.nextInt(y_grid);
            if (grid[x][y] == null) {
                grid[x][y] = Cell.obstacle;
                i++;
            }
        }
        // assign empty cells in remaining cells
        for (int i = x_grid - 1; i >= 0; i--) {
            for (int j = y_grid - 1; j >= 0; j--) {
                if (grid[i][j] == null) {
                    grid[i][j] = Cell.empty;
                }
            }
        }
        return grid;
    }
}
