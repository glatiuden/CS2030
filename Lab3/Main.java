import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int[][][] grid = new int[6][3][3];
        Scanner sc = new Scanner(System.in);
        for (int k = 0; k < 6; k++)
            for (int i = 0; i < 3; i++)
                for (int j = 0; j < 3; j++)
                    grid[k][i][j] = sc.nextInt();

        Rubik rubik = new Rubik(grid);
        while (sc.hasNext()) {
            String instruction = sc.next();
            rubik = turn(rubik, instruction);
        }
        sc.close();
        System.out.println(rubik.toString());
    }

    private static Rubik turn(Rubik rubik, String direction) {
        Rubik newRubik = rubik.clone();
        switch (direction) {
        case "F":
            return newRubik.frontView().right();
        case "F'":
            return newRubik.frontView().left();
        case "F2":
            return newRubik.frontView().half();

        case "R":
            return new RubikRight(newRubik).right();
        case "R'":
            return new RubikRight(newRubik).left();
        case "R2":
            return new RubikRight(newRubik).half();

        case "U":
            return new RubikUp(newRubik).right();
        case "U'":
            return new RubikUp(newRubik).left();
        case "U2":
            return new RubikUp(newRubik).half();

        case "L":
            return new RubikLeft(newRubik).right();
        case "L'":
            return new RubikLeft(newRubik).left();
        case "L2":
            return new RubikLeft(newRubik).half();

        case "B":
            return new RubikBack(newRubik).right();
        case "B'":
            return new RubikBack(newRubik).left();
        case "B2":
            return new RubikBack(newRubik).half();

        case "D":
            return new RubikDown(newRubik).right();
        case "D'":
            return new RubikDown(newRubik).left();
        case "D2":
            return new RubikDown(newRubik).half();

        default:
            return newRubik;
        }
    }
}