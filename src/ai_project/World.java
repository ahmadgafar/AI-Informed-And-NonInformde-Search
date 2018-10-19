package ai_project;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
// this class have all the methods that we in the problem (Generating Grid & checking possible moves)

public class World {

    public int max_stones; // max number of stones can carry
    public int x_grid; // x for grid size
    public int y_grid; // y for grid size
    public int x_position;// snow's x position
    public int y_position; // snow's y position
    public int x_dragonstone = -1;
    public int y_dragonstone = -1;
    public int ww_number; // number of ww in map
    public Cell[][] grid; // grid initialization
    public Point[] ww_positions;

    public World(int x_grid, int y_grid,int num_ww) {
        Random rn = new Random();
        this.x_grid = x_grid;
        this.y_grid = y_grid;
        this.x_position = x_grid - 1;
        this.y_position = y_grid - 1;
        this.max_stones = rn.nextInt(5) + 1; // can hold up to 5 stones
        this.ww_number = num_ww;
    }

    // function to generate game and map
    public void GenGrid() {
        Random rn = new Random();
        grid = new Cell[x_grid][y_grid];
        ww_positions = new Point[ww_number];
        grid[x_grid - 1][y_grid - 1] = Cell.snow; // snow in bottom right Cell
        // for loop to generate ww in random cells in map
        for (int i = 0; i < ww_number;) {
            int x = rn.nextInt(x_grid);
            int y = rn.nextInt(y_grid);

            if (grid[x][y] == null) {
                grid[x][y] = Cell.ww;
                ww_positions[i] = new Point(x, y);
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
                x_dragonstone = x;
                y_dragonstone = y;
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

    }

    // function to print map
    public void printMap() {

        for (int i = 0; i < x_grid; i++) {
            for (int j = 0; j < y_grid; j++) {
                System.out.print(grid[i][j] + "       ");
            }
            System.out.println("");
        }

        System.out.println("\n \n");
    }

    public Cell checkCurrent(int x, int y) {
        return grid[x][y];
    }

    // function to check what is in the Cell on the right
    public Cell checkRight(int x, int y) {
        if (y == y_grid - 1) {
            //System.out.println("error can't move right");
            return null;
        } else {
            return grid[x][y + 1];
        }
    }

    // function to check what is in Cell on left
    public Cell checkLeft(int x, int y) {
        if (y == 0) {
            //System.out.println("error can't move left");
            return null;
        } else {
            return grid[x][y - 1];
        }
    }

    // function to check what is in the Cell on the up
    public Cell checkUp(int x, int y) {
        if (x == 0) {
            //System.out.println("error can't move up");
            return null;
        } else {
            return grid[x - 1][y];
        }
    }

    // function to check what is in Cell on down
    public Cell checkDown(int x, int y) {
        if (x == x_grid - 1) {
            //System.out.println("error can't move down");
            return null;
        } else {
            return grid[x + 1][y];
        }
    }
    // function to check the number of surrouding WW

    public int checkSurroundingWW(int x, int y) {
        int number = 0;
        if (checkUp(x, y) == Cell.ww) {
            number++;
        }
        if (checkDown(x, y) == Cell.ww) {
            number++;
        }
        if (checkRight(x, y) == Cell.ww) {
            number++;
        }
        if (checkLeft(x, y) == Cell.ww) {
            number++;
        }
        return number;
    }

    public void killSurroundingWW(int x, int y) {
        if (checkUp(x, y) == Cell.ww) {
            grid[x - 1][y] = Cell.empty;
        }
        if (checkDown(x, y) == Cell.ww) {
            grid[x + 1][y] = Cell.empty;
        }
        if (checkRight(x, y) == Cell.ww) {
            grid[x][y + 1] = Cell.empty;
        }
        if (checkLeft(x, y) == Cell.ww) {
            grid[x][y - 1] = Cell.empty;
        }
    }

    public Point[] ScanMap(Cell[][] newGrid) {
        this.grid = newGrid.clone();
        this.ww_number=0;
        
        int i;
        int j;
        List<Point> ww = new ArrayList<Point>( );
        for (i = 0; i < grid.length; i++) {
            for (j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == Cell.ww) {
                    ww.add(new Point(i, j));
                    this.ww_number++;
                }
                if (grid[i][j] == Cell.dragonstone) {
                    x_dragonstone = i;
                    y_dragonstone = j;
                }
            }
            this.x_grid = grid.length;
            this.y_grid = j;
            this.x_position = x_grid - 1;
            this.y_position = y_grid - 1;
        }
        Point[] pointsWW = new Point[ww.size()];
        for(i=0;i<ww.size();i++)
        {
            pointsWW[i]=ww.get(i);
        }
        return pointsWW;
    }

    public Cell[][] getGrid() {
        return grid;
    }
}
