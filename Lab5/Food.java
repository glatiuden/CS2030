abstract class Food implements Comparable<Food>
{
    protected String brand;
    protected Food(String brand)
    {
        this.brand = brand;
    }

    @Override
    public String toString()
    {
        return this.brand + " " + this.getClass().getSimpleName().toLowerCase();
    }

    @Override
    public int compareTo(Food f2)
    {
        return this.brand.compareToIgnoreCase(f2.brand);
    }
}