import java.util.List;
import java.util.ArrayList;

public class Order {
    private Menu menu;
    private int[] menuIds;
    private int totalAmt;

    public Order(Menu menu) {
        this.menu = menu;
        this.totalAmt = 0;
    }

    public Order(Menu menu, int[] menuIds, int totalAmt) {
        this.menu = menu;
        this.menuIds = menuIds;
        this.totalAmt = totalAmt;
    }

    public Order add(int[] itemsId) {
        int orderAmt = 0;
        for (int i = 0; i < itemsId.length; i++) {
            int orderFoodId = itemsId[i];
            FastFood selectedFood = MenuUtility.getItem(orderFoodId);
            orderAmt += selectedFood.getFoodPrice();
        }
        return new Order(this.menu, itemsId, orderAmt);

    }

    @Override
    public String toString() {
        String newOutput = "\n";
        for (int i = 0; i < menuIds.length; i++) {
            int orderFoodId = menuIds[i];
            FastFood selectedFood = MenuUtility.getItem(orderFoodId);
            newOutput += selectedFood.toString() + "\n";
        }
        newOutput += "Total: " + this.totalAmt;
        return newOutput;
    }
}
