public class Rubik { 
    private final int[][][] grid = new int[6][3][3];  
    private final int FRONT = 2;
    private final int BACK = 5;
    private final int LEFT = 1;
    private final int RIGHT = 3; 
    private final int TOP = 0;
    private final int DOWN = 4;
    //constructor
    Rubik(int[][][] grid) {
        gridCopy(grid, this.grid);
    }
    //Makes a copy of the grid array, facilitates deep copy of Rubik object
    public int[][][] gridCopy(int[][][] original , int[][][] copy) {
        for (int f = 0; f < 6; f++) {
            for (int r = 0; r < 3; r++) {
                for (int c = 0; c < 3; c++) {
                    copy[f][r][c] = original[f][r][c];
                }
            }
        }
        return copy; 
    }
    //getter
    public int[][][] getGrid() { 
        int[][][] temp = new int[6][3][3];
        return gridCopy(this.grid, temp);
    }

    //CHANGE ORIENTATION
    //rotates a face of the grid
    public int[][] rotateGridFace(int[][] gridFace, String direction) {
        Face f = new Face(gridFace);
        switch (direction) {   
            case "right":
                return f.rotateRight().getGrid();
            case "left": 
                return f.rotateLeft().getGrid();
            case "half": 
                return f.rotateHalf().getGrid();
            default: 
                break; 
        }
        return null; 
    }
    //orientate the cube to view right side, returns new Rubik
    public Rubik viewRight() {
        int[][][] temp = new int[6][3][3];
        temp[FRONT] = grid[RIGHT];
        temp[LEFT] = grid[FRONT];
        temp[RIGHT] = rotateGridFace(grid[BACK], "half");
        temp[TOP] = rotateGridFace(grid[TOP], "right");
        temp[DOWN] = rotateGridFace(grid[DOWN],"left"); 
        temp[BACK] = rotateGridFace(grid[LEFT], "half");
        Rubik r = new Rubik(temp); 
        return r;
    }

    public Rubik viewLeft() {
        return viewRight().viewRight().viewRight();
    }
    public Rubik viewUp() {
        int[][][] temp = new int[6][3][3]; 
        temp[FRONT] = grid[TOP];
        temp[LEFT] = rotateGridFace(grid[LEFT], "right");
        temp[RIGHT] = rotateGridFace(grid[RIGHT], "left");
        temp[TOP] = grid[BACK];
        temp[DOWN] = grid[FRONT]; 
        temp[BACK] = grid[DOWN];
        Rubik r = new Rubik(temp);
        return r;
    } 
    public Rubik viewDown() {
        return viewUp().viewUp().viewUp();
    }
    public Rubik viewBack() {
        return viewUp().viewUp();
    }
    public Rubik frontfaceRight() {
        int[][][] temp = new int[6][3][3];
        gridCopy(this.grid, temp);
        //rotate front face
        temp[FRONT] = rotateGridFace(grid[FRONT], "right");
        //rotate elements connected to front face
        //top to right
        temp[RIGHT] = rotateGridFace(temp[RIGHT], "left");
        temp[RIGHT][2] = grid[TOP][2];
        temp[RIGHT] = rotateGridFace(temp[RIGHT], "right");
        //left to top
        temp[TOP][2] = rotateGridFace(temp[LEFT], "right")[2];
        //down to left
        temp[LEFT] = rotateGridFace(temp[LEFT], "left");
        temp[LEFT][0] = grid[DOWN][0];
        temp[LEFT] = rotateGridFace(temp[LEFT], "right");
        //right to down
        temp[DOWN][0] = rotateGridFace(grid[RIGHT], "right")[0];
        Rubik r = new Rubik(temp);
        return r;
    }

    
    public Rubik frontfaceLeft() { // turns the front face anti-clockwise and returns the new Rubik object
        return frontfaceRight().frontfaceRight().frontfaceRight();
    }
    public Rubik frontfaceHalf() {  //turns the front face half revolution and returns the new Rubik object
        return frontfaceRight().frontfaceRight();
    } 
    public Rubik rightfaceRight() { // turns the right face clockwise and returns the new Rubik object
        return viewRight().frontfaceRight().viewLeft();  
    }
    public Rubik rightfaceLeft() { // turns the right face anti-clockwise and returns the new Rubik object 
        return viewRight().frontfaceRight().frontfaceRight().frontfaceRight().viewLeft();
    }
    public Rubik rightfaceHalf() { // turns the right face half revolution and returns the new Rubik object 
        return viewRight().frontfaceRight().frontfaceRight().viewLeft();
    }    
    public Rubik leftfaceRight() {
        return viewLeft().frontfaceRight().viewRight();  
    }
    public Rubik leftfaceLeft() {
        return viewLeft().frontfaceRight().frontfaceRight().frontfaceRight().viewRight();
    }
    public Rubik leftfaceHalf() {
        return viewLeft().frontfaceRight().frontfaceRight().viewRight();
    }
    public Rubik upfaceRight() {
        return viewUp().frontfaceRight().viewDown();  
    }
    public Rubik upfaceLeft() {
        return viewUp().frontfaceRight().frontfaceRight().frontfaceRight().viewDown();
    }
    public Rubik upfaceHalf() {
        return viewUp().frontfaceRight().frontfaceRight().viewDown();
    }
    public Rubik downfaceRight() {
        return viewDown().frontfaceRight().viewUp();  
    }
    public Rubik downfaceLeft() {
        return viewDown().frontfaceRight().frontfaceRight().frontfaceRight().viewUp();
    }
    public Rubik downfaceHalf() {
        return viewDown().frontfaceRight().frontfaceRight().viewUp();
    }
    public Rubik backfaceRight() {
        return viewBack().frontfaceRight().viewBack();  
    }
    public Rubik backfaceLeft() {
        return viewBack().frontfaceRight().frontfaceRight().frontfaceRight().viewBack();
    }
    public Rubik backfaceHalf() {
        return viewBack().frontfaceRight().frontfaceRight().viewBack();
    }



    // PRINTING
    // reorders a 3 dimensional array for printing and
    // flattens the 3 dimensional array into a 1 dimensional array
    public int[] flattenGrid(int[][][] myGrid) { 
        int[][][] temp = new int[6][3][3];
        int[] flatGrid = new int[54];
        int count = 0;
        //reorder
        gridCopy(myGrid, temp);
        temp[1][1] = myGrid[2][0];
        temp[2][0] = myGrid[1][1];
        temp[1][2] = myGrid[3][0];
        temp[3][0] = myGrid[1][2];
        temp[2][2] = myGrid[3][1];
        temp[3][1] = myGrid[2][2];
        //flatten
        for (int f = 0; f < 6; f++) {
            for (int r = 0; r < 3; r++) {
                for (int c = 0; c < 3; c++) {
                    flatGrid[count] = temp[f][r][c];
                    count++; 
                }
            }
        } 
        return flatGrid;
    }
    // calls flattenGrid, for each position to be printed:
    // if check returns true, add the grid to the result string, else add ".." 
    @Override
    public String toString() { 
        int[] flatGrid = flattenGrid(grid);
        int count = 0;
        String result = "";
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 9; j++) {
                boolean check = (j > 2 && j < 6) || (i > 2 && i < 6); 
                if (check) {
                    result += String.format("%02d", flatGrid[count]);
                    count++; 
                } else {
                    result += "..";
                }
            }
            result += "\n";
        }
        return result;
    }
    
}
