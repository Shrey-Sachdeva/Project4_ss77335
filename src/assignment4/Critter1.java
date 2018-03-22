package assignment4;

public class Critter1 extends Critter {
    private int direction;

    public Critter1() {
        direction = 0;
    }

    @Override
    public String toString() {
        return "1";
    }

    @Override
    public void doTimeStep() {
    	run(direction);
        Critter1 offspring = new Critter1();
        reproduce(offspring, getRandomInt(8));
    }

    @Override
    public boolean fight(String opponent) {
       if(opponent.equals("@")) {// buffalo eat grass
           return true;
       }
       else if (opponent.equals("1")){
           walk(4);//move back in the herd
           return false;
       }
       return false;
    }
}
