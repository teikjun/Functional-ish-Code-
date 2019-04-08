public class Face {
    private final int[][] grid = new int[3][3];
    //constructor
    Face(int[][] grid) {
        int[][] temp;
        gridCopy(grid, this.grid);
    }
    //getter
    public int[][] getGrid() {
        int[][] temp = new int[3][3];
        return gridCopy(this.grid, temp);
    }
    //creates a new copy of the grid array, used to facilitate deep copy of Face object
    public int[][] gridCopy(int[][] original, int[][] copy) { 
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                copy[r][c] = original[r][c];
            }
        }
        return copy;
    }   

    public Face rotateRight() {
        int[][] temp = new int[3][3];  
        for (int r = 0; r < 3; r++) {
            for(int c = 0; c < 3; c++) {
                temp[r][c] = grid[2 - c][r];
            }
        }
        Face f = new Face(temp);
        return f;
    }

    public Face rotateLeft() {
        return rotateRight().rotateRight().rotateRight();
    }

    public Face rotateHalf() {
        return rotateRight().rotateRight();
    }
 
    @Override 
    public String toString() {
        String result = "";
        //for each row
        for (int r = 0; r < 3; r++) {
            // return all columns
            for (int c = 0; c < 3; c++) {
                result = result + String.format("%02d", grid[r][c]);
            } 
            result = result + "\n";
        }
        return result;
    }
}
