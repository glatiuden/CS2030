import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Collections;

class Farm {
    List<Animal> animals;
    List<Food> foods;

    Farm() {
        animals = new ArrayList<>();
        foods = new ArrayList<>();
    }

    void runInstruction(String inst) throws IllegalInstructionException {
        Scanner sc = new Scanner(inst);
        String cmd = sc.next();
        if (cmd.equalsIgnoreCase("new")) {
            String animalType = sc.next();
            if (animalType.equalsIgnoreCase("cat") || animalType.equalsIgnoreCase("dog")) {
                String name = "", last = "";
                int appetite = 0;
                if (sc.hasNext()) {
                    name = sc.next();
                } else {
                    throw new IllegalInstructionException("Too few arguments");
                }
                if (sc.hasNextInt()) {
                    appetite = sc.nextInt();
                } else {
                    throw new IllegalInstructionException("Too few arguments");
                }
                if (sc.hasNext()) {
                    last = sc.next();
                } else {
                    throw new IllegalInstructionException("Too few arguments");
                }
                Animal animal;
                if (animalType.equalsIgnoreCase("cat")) {
                    animal = new Cat(name, appetite, last);
                } else if (animalType.equalsIgnoreCase("dog")) {
                    animal = new Dog(name, appetite, last);
                } else {
                    throw new IllegalInstructionException(animalType + " is not a valid species");
                }
                animals.add(animal);
                System.out.println(animal.toString() + " was created");
            } else {
                throw new IllegalInstructionException(animalType + " is not a valid species");
            }
        } else if (cmd.equalsIgnoreCase("add")) {
            String foodType = sc.next();
            if (foodType.equalsIgnoreCase("chocolate") || foodType.equalsIgnoreCase("tuna")
                    || foodType.equalsIgnoreCase("cheese")) {
                String brand = "";
                if (sc.hasNext()) {
                    brand = sc.next();
                } else {
                    throw new IllegalInstructionException("Too few arguments");
                }
                Food food;
                if (foodType.equalsIgnoreCase("chocolate")) {
                    if (sc.hasNext()) {
                        String type = sc.next();
                        food = new Chocolate(brand, type);
                    } else {
                        throw new IllegalInstructionException("Too few arguments");
                    }
                } else if (foodType.equalsIgnoreCase("tuna")) {
                    food = new Tuna(brand);
                } else if (foodType.equalsIgnoreCase("cheese")) {
                    food = new Cheese(brand);
                } else {
                    throw new IllegalInstructionException(foodType + " is not a valid food type");
                }
                foods.add(food);
                System.out.println(food.toString() + " was added");
            } else {
                throw new IllegalInstructionException(foodType + " is not a valid food type");
            }
        } else if (cmd.equalsIgnoreCase("eat")) {
            Collections.sort(animals);
            int i = 0;
            while (i < animals.size()) {
                Animal a = animals.get(i);
                Iterator<Food> foodsIt = foods.iterator();
                if (!a.isFull()) {
                    while (foodsIt.hasNext()) {
                        try {
                            a.eat(foodsIt.next());
                            foodsIt.remove();
                        } catch (CannotEatException e) {
                            i++;
                        }
                    }
                } else {
                    i++;
                }
            }
        } else if (cmd.equalsIgnoreCase("greet")) {
            Collections.sort(animals);
            for (Animal a : animals) {
                a.greet();
            }
        } else {
            throw new IllegalInstructionException(cmd + " is not a valid instruction");
        }
    }

    @Override
    public String toString() {
        Collections.sort(animals);
        String output = "Animals:\n";
        for (Animal a : animals) {
            output += a.toString() + "\n";
        }

        output += "\nFood:\n";
        for (int i = 0; i < foods.size(); i++) {
            Food f = foods.get(i);
            output += f.toString() + (i == foods.size() - 1 ? "" : "\n");
        }

        return output;
    }
}
