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
        else {
           tryingToFlee = true;
           walk(4);//move back in the herd
           return false;
       }
    }
    
    public static void runStats(java.util.List<Critter> ones) {
    	if(ones.size() == 0) {
    		System.out.println("0 total Critter1s, how sad");
    		return;
    	}
    	
    	int averageEnergy  = 0;
    	for(Object o : ones) {
    		Critter1 one = (Critter1) o;
    		averageEnergy += one.getEnergy();
    	}
    	averageEnergy /= ones.size();
    	System.out.println(ones.size() + " total Critter1s running to the right with average energy: " + averageEnergy);
    	
    }
    
}
