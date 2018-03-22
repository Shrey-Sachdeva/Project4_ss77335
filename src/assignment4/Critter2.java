package assignment4;

public class Critter2 extends Critter {
    private int age;

    public Critter2() {
        age = 0;
    }

    @Override
    public String toString() {
        return "2";
    }

    @Override
    public void doTimeStep() {
        if(age < 3) {
            Critter2 offspring = new Critter2();
            int up = getRandomInt(4);
            reproduce(offspring, up);
            offspring = new Critter2();
            int down = getRandomInt(4) + 4;
            reproduce(offspring, down);
        }
        age++;
    }

    @Override
    // If it is a new fungus, it will fight
    public boolean fight(String opponent) {
        if(age < 3) {
            return true;
        }
        else {
            return false;
        }
    }
}
