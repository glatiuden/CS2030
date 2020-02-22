public class Face implements Cloneable {
    private int[][] grid;

    public Face(int[][] grid) {
        // for (int i = 0; i < 3; i++) {
        // for (int j = 0; j < 3; j++) {
        // this.grid[i][j] = grid[i][j];
        // }
        // }
        this.grid = grid;
    }

    public Face right() {
        // Working
        // int[][] newGrid = new int[3][3];
        // for (int i = 0; i < 3; i++) {
        // for (int j = 2; j >= 0; j--) {
        // newGrid[i][2 - j] = this.grid[j][i];
        // }
        // }
        // return new Face(newGrid);

        Face newFace = this.clone();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                newFace.grid[i][j] = this.grid[2 - j][i];
            }
        }
        return newFace;
    }

    public Face left() {
        return (this.right().right().right());
    }

    public Face half() {
        return (this.right().right());
    }

    public int[][] toIntArray() {
        return this.grid.clone();
        // return this.clone().grid;
    }

    @Override
    public String toString() {
        String str = "";
        for (int i = 0; i < grid.length; i++) {
            str = str + "\n";
            int[] currentRow = grid[i];
            for (int j = 0; j < currentRow.length; j++) {
                int currentInt = currentRow[j];
                str = str + String.format("%02d", currentInt);
            }
        }
        str += "\n";
        return str;
    }

    @Override
    public Face clone() {
        int[][] newGrid = new int[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                newGrid[i][j] = this.grid[i][j];
            }
        }
        return new Face(newGrid);
    }

}
