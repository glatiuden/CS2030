class Cat extends Animal {
    private String colour;
    private boolean isLazy; 

    Cat(String name, int appetite, String colour) {
        super(name, appetite);
        this.colour = colour;
        this.isLazy = false;
    }

    @Override
    public String toString() {
        return String.format("%s(%s)", super.toString(), this.colour);
    }

    @Override
    void greet()
    {
        if(!this.isLazy)
        {
            System.out.println(this.toString() + " says meow and gets lazy");
            this.isLazy = true;
        }
        else {
            System.out.println(this.toString() + " is lazy");
            this.isLazy = false;
        }
    }

    @Override
    void eat(Food food) throws CannotEatException
    {
        if(food instanceof Cheese)
        {
            throw new CannotEatException(this.toString() + " cannot eat " + food.toString());
        }
        else if(this.appetiteLevel >= this.appetite)
        {
            throw new CannotEatException(this.toString() + " cannot eat " + food.toString() + " as it is full");
        }
        else {
            System.out.println(this.toString() + " eats " + food.toString());
            super.appetiteLevel++;
        }
    }
}