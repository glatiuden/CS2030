abstract class Animal implements Comparable<Animal> {
    protected String name;
    protected int appetite;
    protected int appetiteLevel;

    protected Animal(String name, int appetite) {
        this.name = name;
        this.appetite = appetite;
        this.appetiteLevel = 0;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public int compareTo(Animal a2)
    {
        return this.name.compareToIgnoreCase(a2.name);
    }

    public boolean isFull()
    {
        return this.appetiteLevel >= this.appetite;
    }

    abstract void greet();
    abstract void eat(Food food) throws CannotEatException;
}