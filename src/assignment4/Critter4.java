package assignment4;

public class Critter4 extends Critter {
    public Critter4() {

    }

    @Override
    public String toString() {
        return "4";
    }

    @Override
    public void doTimeStep() {

    }

    @Override
    public boolean fight(String opponent) {
        while (this.getEnergy() >= Params.min_reproduce_energy) {
            Critter4 offspring = new Critter4();
            reproduce(offspring, getRandomInt(8));
        }
        if (getRandomInt(8) > 4) {
            return true;
        }
        return false;
    }
}
