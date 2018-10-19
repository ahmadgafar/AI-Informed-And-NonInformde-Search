package ai_project;




public class Point {
    int x;
    int y;

    public Point() {
    }
    
    
    public static void main(String[] args) {
        Point p0 = new Point(2, 1);
        Point p1 = new Point(2, 1);
        Point p2 = new Point(0,0);
        Point[][] L = {{p0},{p1}};  
        Point[][] X = L.clone();
        X[0][0]=p2;
        
        System.out.println("X[0]: "+X[0][0]);
        System.out.println("X[1]: "+X[1][0]);
        
    }
    
    public static boolean isDeadWWinArray(Point [] deadWW, Point storedWW ){
        
        for (int i = 0; i < deadWW.length; i++) {
            if(storedWW != null && deadWW[i]!=null && storedWW.x == deadWW[i].x && storedWW.y == deadWW[i].y)
            return true;
        }
        
        return false;
    }

    public Point(int x,int y){
        this.x=x;
        this.y=y;
    }
    
    public boolean inArray(Point [] array){
        boolean b = false;
        for (int i = 0; i < array.length ; i++) {
            if (array[i] != null)
                if (this.x == array[i].x && this.y == array[i].y)
                    b=true;
        }
        
        return b;
    }
    public Point [] addInArray(Point [] array){
        Point [] array2 = new Point[array.length];
        
        for (int i = 0; i < array.length; i++) {
            array2[i] = array[i];
            if(array[i] == null){
                array2[i] = new Point(this.x,this.y);
                break;}
        } 
        return array2;
    }

    @Override
    public String toString() {
        return "Point{" + "x=" + x + ", y=" + y + '}';
    }
    
 


}
