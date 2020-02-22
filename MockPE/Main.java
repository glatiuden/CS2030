import java.util.Scanner;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.function.Function;

class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Menu menu = new Menu();

        while (sc.next().equals("add")) {
            String foodType = sc.next();
            if (foodType.equals("Combo")) {
                String foodDesc = sc.next();
                List<Integer> comboInputs = new ArrayList<>();
                while (sc.hasNextInt()) {
                    comboInputs.add(sc.nextInt());
                }
                menu.add(foodType, foodDesc, comboInputs);
            } else {
                menu.add(foodType, sc.next(), sc.nextInt());
            }
        }
        menu.print();
        List<Integer> orders = new ArrayList<>();
        while (sc.hasNextInt()) {
            orders.add(sc.nextInt());
        }
        System.out.println("\n--- Order ---");
        Order order = new Order(menu).add(orders.stream().mapToInt(Integer::intValue).toArray());
        System.out.println(order.toString());
        
        sc.close();
    }

    private static int[] convertIntegers(List<Integer> integers) {
        int[] inputs = new int[integers.size()];
        for (int i = 0; i < inputs.length; i++) {
            inputs[i] = integers.get(i).intValue();
        }
        return inputs;
    }
}
