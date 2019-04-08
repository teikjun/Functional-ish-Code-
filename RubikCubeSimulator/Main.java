import java.util.Scanner; 

class Main {
    public static void main(String[] args) { 
        Scanner sc = new Scanner(System.in); 
        int[][][] myGrid = new int[6][3][3]; 
        Rubik rb = null; 
        while (sc.hasNext()) {
            if (sc.hasNextInt()) {
                for (int f = 0; f < 6; f++) {
                    for (int r = 0; r < 3; r++) {
                        for (int c = 0; c < 3; c++) {
                            myGrid[f][r][c] = sc.nextInt();
                        }
                    }
                }
                rb = new Rubik(myGrid);
            } else {
                String command = sc.next();
                switch (command) {
                    case "F": 
                        rb = rb.frontfaceRight();
                        break;
                    case "R":
                        rb = rb.rightfaceRight();
                        break;
                    case "U":    
                        rb = rb.upfaceRight();
                        break;
                    case "L":
                        rb = rb.leftfaceRight();
                        break;
                    case "B":
                        rb = rb.backfaceRight();
                        break;
                    case "D":
                        rb = rb.downfaceRight();
                        break;
                    case "F'": 
                        rb = rb.frontfaceLeft();
                        break;
                    case "R'":
                        rb = rb.rightfaceLeft();
                        break;
                    case "U'":    
                        rb = rb.upfaceLeft();
                        break;
                    case "L'":
                        rb = rb.leftfaceLeft();
                        break;
                    case "B'":
                        rb = rb.backfaceLeft();
                        break;
                    case "D'":
                        rb = rb.downfaceLeft();
                        break;
                    case "F2": 
                        rb = rb.frontfaceHalf();
                        break;
                    case "R2":
                        rb = rb.rightfaceHalf();
                        break;
                    case "U2":    
                        rb = rb.upfaceHalf();
                        break;
                    case "L2":
                        rb = rb.leftfaceHalf();
                        break;
                    case "B2":
                        rb = rb.backfaceHalf();
                        break;
                    case "D2":
                        rb = rb.downfaceHalf();
                        break;
                    default:
                        break;
                }
            }
        }

        System.out.println(rb);
    }
}
