public class Food extends FastFood {

    public Food(int foodId, String foodType, String foodDesc, int foodPrice) {
        super(foodId, foodType, foodDesc, foodPrice);
    }

    @Override
    public String toString() {
        return String.format("#%d %s: %s (%d)", this.foodId, this.foodType, this.foodDesc, this.foodPrice);
    }
}
