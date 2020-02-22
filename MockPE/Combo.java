import java.util.List;
import java.util.ArrayList;

public class Combo extends FastFood {
    private List<Integer> foodItems;

    public Combo() {

    }

    public Combo(int foodId, String foodType, String foodDesc, List<Integer> foodItems) {
        super(foodId, foodType, foodDesc);
        super.foodPrice = MenuUtility.calculateTotal(foodItems);
        this.foodItems = foodItems;
    }

    // private int calculateTotal(List<Integer> inputItems) {
    // int comboPrice = 0;
    // for (int i = 0; i < inputItems.size(); i++) {
    // FastFood tryGetFood = MenuUtility.getItem(inputItems.get(i));
    // comboPrice += tryGetFood.getFoodPrice();
    // }
    // return comboPrice - 50;
    // }

    // @Override
    // public String toString() {
    // String newString = String.format("#%d %s: %s (%d)", super.foodId,
    // super.foodType, super.foodDesc,
    // super.foodPrice);
    // for (int i = 0; i < this.foodItems.size(); i++) {
    // FastFood tryGetFood = MenuUtility.getItem(this.foodItems.get(i));
    // newString += "\n " + tryGetFood.toString();
    // }
    // return newString;
    // }

    @Override
    public String toString() {
        String newString = String.format("#%d %s: %s (%d)", super.foodId, super.foodType, super.foodDesc,
                super.foodPrice) + MenuUtility.getComboOutputs(this.foodItems);
        return newString;
    }
}
