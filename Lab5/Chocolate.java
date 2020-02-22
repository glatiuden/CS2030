class Chocolate extends Food
{
    private String type;
    public Chocolate(String brand, String type)
    {
        super(brand);
        this.type = type;
    }

    @Override
    public String toString()
    {
        return this.brand + " " + this.type + " " + this.getClass().getSimpleName().toLowerCase();
    }
}