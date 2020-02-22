import java.util.List;
import java.util.ArrayList;

public class MenuUtility {
    private static List<FastFood> menuList = new ArrayList<>();

    public static FastFood getItem(int foodId) {
        FastFood selectFood = new FastFood();
        for (FastFood food : menuList) {
            if (food.getFoodId() == foodId) {
                selectFood = food;
                break;
            }
        }
        return selectFood;
    }

    public static void addFood(FastFood food) {
        menuList.add(food);
    }

    public static int getSize() {
        return menuList.size();
    }

    public static int calculateTotal(List<Integer> inputItems) {
        int comboPrice = 0;
        for (int i = 0; i < inputItems.size(); i++) {
            FastFood tryGetFood = MenuUtility.getItem(inputItems.get(i));
            comboPrice += tryGetFood.getFoodPrice();
        }
        return comboPrice - 50;
    }

    public static String getComboOutputs(List<Integer> inputItems) {
        String newOutput = "";
        for (int i = 0; i < inputItems.size(); i++) {
            FastFood tryGetFood = MenuUtility.getItem(inputItems.get(i));
            newOutput += "\n   " + tryGetFood.toString();
        }
        return newOutput;
    }
}