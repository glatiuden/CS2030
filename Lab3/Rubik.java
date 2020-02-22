public class Rubik implements Cloneable, SideViewable {
    protected int[][][] grid;
    public static final int UP = 0;
    public static final int LEFT = 1;
    public static final int FRONT = 2;
    public static final int RIGHT = 3;
    public static final int DOWN = 4;
    public static final int BACK = 5;

    public Rubik(int[][][] grid) {
        this.grid = grid;
    }

    public Rubik(Rubik rubik) {
        this.grid = rubik.clone().grid;
    }

    @Override
    public Rubik clone() {
        int[][][] newGrid = new int[6][3][3];
        for (int k = 0; k < 6; k++) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    newGrid[k][i][j] = this.grid[k][i][j];
                }
            }
        }
        return new Rubik(newGrid);
    }

    @Override
    public String toString() {
        String str = "";
        String strTop = "";
        String strCenter1 = "";
        String strCenter2 = "";
        String strCenter3 = "";
        String strBottom = "";
        for (int i = 0; i < 6; i++) {
            if (i == 0 || i == 4 || i == 5) {
                for (int j = 0; j < 3; j++) {
                    String tempStr = "";
                    tempStr += "\n";
                    tempStr += "......";
                    int[] currentRow = grid[i][j];
                    for (int k = 0; k < currentRow.length; k++) {
                        int currentInt = currentRow[k];
                        tempStr += String.format("%02d", currentInt);
                    }
                    tempStr += "......";
                    if (i == 0)
                        strTop += tempStr;
                    else
                        strBottom += tempStr;
                }
            } else { // it is left front right
                int[] currentRow = grid[i][0];
                int[] currentRow2 = grid[i][1];
                int[] currentRow3 = grid[i][2];
                for (int k = 0; k < 3; k++) {
                    int currentInt = currentRow[k];
                    int currentInt2 = currentRow2[k];
                    int currentInt3 = currentRow3[k];
                    strCenter1 += String.format("%02d", currentInt);
                    strCenter2 += String.format("%02d", currentInt2);
                    strCenter3 += String.format("%02d", currentInt3);
                }
            }
        }
        str += strTop;
        str += "\n" + strCenter1 + "\n";
        str += strCenter2 + "\n";
        str += strCenter3;
        str += strBottom;
        str += "\n";
        return str;
    }

    public Rubik right() {
        int[][][] newGrid = this.clone().grid;
        int[][] newCenter = new Face(newGrid[2]).right().toIntArray();
        newGrid[2] = newCenter;

        // int[][] topFace = newGrid[0];
        // int[][] rightFace = newGrid[3];
        // int[][] bottomFace = newGrid[4];
        // int[][] leftFace = newGrid[1];

        // int tempRight1 = rightFace[0][0];
        // int tempRight2 = rightFace[1][0];
        // int tempRight3 = rightFace[2][0];

        // rightFace[0][0] = topFace[2][0];
        // rightFace[1][0] = topFace[2][1];
        // rightFace[2][0] = topFace[2][2];

        // int tempBot1 = bottomFace[0][0];
        // int tempBot2 = bottomFace[0][1];
        // int tempBot3 = bottomFace[0][2];

        // bottomFace[0][0] = tempRight3;
        // bottomFace[0][1] = tempRight2;
        // bottomFace[0][2] = tempRight1;

        // int tempLeft1 = leftFace[0][2];
        // int tempLeft2 = leftFace[1][2];
        // int tempLeft3 = leftFace[2][2];

        // leftFace[0][2] = tempBot1;
        // leftFace[1][2] = tempBot2;
        // leftFace[2][2] = tempBot3;

        // topFace[2][0] = tempLeft3;
        // topFace[2][1] = tempLeft2;
        // topFace[2][2] = tempLeft1;

        // newGrid[0] = (topFace);
        // newGrid[3] = (rightFace);
        // newGrid[4] = (bottomFace);
        // newGrid[1] = (leftFace);

        int tmp1 = newGrid[0][2][0];
        int tmp2 = newGrid[0][2][1];
        int tmp3 = newGrid[0][2][2];
        for (int i = 0; i < 3; i++) {
            newGrid[0][2][i] = newGrid[1][2 - i][2];
        }
        for (int i = 0; i < 3; i++) {
            newGrid[1][i][2] = newGrid[4][0][i];
        }
        for (int i = 0; i < 3; i++) {
            newGrid[4][0][i] = newGrid[3][2 - i][0];
        }
        newGrid[3][0][0] = tmp1;
        newGrid[3][1][0] = tmp2;
        newGrid[3][2][0] = tmp3;

        return new Rubik(newGrid);
    }

    public Rubik left() {
        return this.right().right().right();
    }

    public Rubik half() {
        return this.right().right();
    }

    @Override
    public Rubik rightView() {
        int[][][] newGrid = this.clone().grid;
        newGrid[UP] = new Face(this.grid[UP]).right().toIntArray();
        newGrid[LEFT] = this.grid[FRONT];
        newGrid[FRONT] = this.grid[RIGHT];
        newGrid[RIGHT] = new Face(this.grid[BACK]).half().toIntArray();
        newGrid[DOWN] = new Face(this.grid[DOWN]).left().toIntArray();
        newGrid[BACK] = new Face(this.grid[LEFT]).half().toIntArray();
        return new Rubik(newGrid);
    }

    @Override
    public Rubik leftView() {
        return rightView().rightView().rightView();
    }

    @Override
    public Rubik upView() {
        int[][][] newGrid = this.clone().grid;
        newGrid[UP] = this.grid[BACK];
        newGrid[LEFT] = new Face(this.grid[LEFT]).right().toIntArray();
        newGrid[FRONT] = this.grid[UP];
        newGrid[RIGHT] = new Face(this.grid[RIGHT]).left().toIntArray();
        newGrid[DOWN] = this.grid[FRONT];
        newGrid[BACK] = this.grid[DOWN];
        return new Rubik(newGrid);
    }

    @Override
    public Rubik downView() {
        return upView().upView().upView();

    }

    @Override
    public Rubik backView() {
        int[][][] newGrid = this.clone().grid;
        newGrid[UP] = new Face(this.grid[UP]).half().toIntArray();
        newGrid[LEFT] = this.grid[RIGHT];
        newGrid[FRONT] = new Face(this.grid[BACK]).half().toIntArray();
        newGrid[RIGHT] = this.grid[LEFT];
        newGrid[DOWN] = new Face(this.grid[DOWN]).half().toIntArray();
        newGrid[BACK] = new Face(this.grid[FRONT]).half().toIntArray();
        return new Rubik(newGrid);
    }

    @Override
    public Rubik frontView() {
        return this.clone();
    }

}
