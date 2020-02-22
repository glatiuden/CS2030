class Dog extends Animal {
    private String sound;
    private int count;

    Dog(String name, int appetite, String sound) {
        super(name, appetite);
        this.sound = sound;
        this.count = 1;
    }

    @Override
    void greet() {
        String output = this.toString() + " says ";
        for (int i = 0; i < count; i++) {
            output += this.sound;
        }
        this.count++;
        System.out.println(output);
    }

    @Override
    void eat(Food food) throws CannotEatException
    {
        if(food instanceof Chocolate)
        {
            throw new CannotEatException(this.toString() + " cannot eat " + food.toString());
        }
        else if(this.appetiteLevel >= this.appetite)
        {
            throw new CannotEatException(this.toString() + " cannot eat " + food.toString() + " as it is full");
        }
        else {
            System.out.println(this.name + " eats " + food.toString());
            super.appetiteLevel++;
        }
    }
}