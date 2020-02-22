public class FastFood {
    protected int foodId;
    protected String foodType;
    protected String foodDesc;
    protected int foodPrice;

    public FastFood(int foodId, String foodType, String foodDesc, int foodPrice) {
        this.foodId = foodId;
        this.foodType = foodType;
        this.foodDesc = foodDesc;
        this.foodPrice = foodPrice;
    }

    public FastFood(int foodId, String foodType, String foodDesc) {
        this.foodId = foodId;
        this.foodType = foodType;
        this.foodDesc = foodDesc;
    }

    public FastFood() {
    }

    // GETTER METHODS
    public int getFoodId() {
        return this.foodId;
    }

    public String getFoodType() {
        return this.foodType;
    }

    public int getFoodPrice() {
        return this.foodPrice;
    }
}