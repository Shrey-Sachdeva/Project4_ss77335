package assignment4;

public class Critter1 extends Critter.TestCritter {

    @Override
    public String toString() {
        return "1";
    }

    @Override
    public void doTimeStep() {
        run(getRandomInt(8));

        Critter1 offspring = new Critter1();
        reproduce(offspring, getRandomInt(8));
    }

    @Override
    // If this has more energy than its opponent, it will fight
    public boolean fight(String oponent) {
        try {
            Class c = Class.forName(oponent);
            Critter opponent = (Critter) c.newInstance();
            return (getEnergy() >= opponent.getEnergy());
        } catch(Exception e) {
            // TODO: 3/13/2018 FIX 
            //throw new InvalidCritterException(oponent);
            return false;
        }
    }
}
