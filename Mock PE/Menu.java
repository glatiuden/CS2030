import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class Menu {
    private static int nextId = 0;
    private List<String> typeList;

    public Menu() {
        this.typeList = new ArrayList<>();
    }

    public Food add(String foodType, String foodDesc, int foodPrice) {

        Food newFood = new Food(nextId, foodType, foodDesc, foodPrice);
        MenuUtility.addFood(newFood);
        nextId++;

        if (this.typeList.isEmpty() || !this.typeList.contains(foodType)) {
            this.typeList.add(foodType);
        }

        return newFood;
    }

    public Combo add(String comboType, String comboDesc, List<Integer> comboItems) {
        Combo newCombo = new Combo(nextId, comboType, comboDesc, comboItems);
        MenuUtility.addFood(newCombo);
        nextId++;

        if (this.typeList.isEmpty() || !this.typeList.contains(comboType)) {
            this.typeList.add(comboType);
        }
        return newCombo;
    }

    public void print() {
        // Put combo to print last
        // if (this.typeList.contains("Combo")) {
        // this.typeList.remove("Combo");
        // this.typeList.add("Combo");
        // }
        Collections.swap(this.typeList, typeList.indexOf("Combo"), typeList.size() - 1);
        for (int i = 0; i < this.typeList.size(); i++) {
            String currentType = this.typeList.get(i);
            for (int j = 0; j < MenuUtility.getSize(); j++) {
                FastFood currentFood = MenuUtility.getItem(j);
                if (currentFood.getFoodType().equals(currentType)) {
                    System.out.println(currentFood.toString());
                }
            }
        }
        // for (int k = 0; k < this.comboList.size(); k++) {
        // Combo currentCombo = this.comboList.get(k);
        // System.out.println(currentCombo.toString());
        // }
    }
}
