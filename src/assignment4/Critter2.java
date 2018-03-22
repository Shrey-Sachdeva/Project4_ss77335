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
    
    public static void runStats(java.util.List<Critter> twos) {
    	if(twos.size() == 0) {
    		System.out.println("0 total Critter2s");
    		return;
    	}
    	int minimumAge = ((Critter2)twos.get(0)).age;
    	int maximumAge = ((Critter2)twos.get(0)).age;
    	int averageAge = 0;
    	int averageEnergy = 0;
    	for(Object t : twos) {
    		Critter2 two = (Critter2) t;
    		averageAge += two.age;
    		if(two.age < minimumAge) {
    			minimumAge = two.age;
    		}
    		if(two.age > maximumAge) {
    			maximumAge = two.age;
    		}
    		averageEnergy += two.getEnergy();
    	}
    	averageAge /= twos.size();
    	averageEnergy /= twos.size();
    	System.out.println(twos.size() + " total Critter2s\tminimum age: " + minimumAge + "\tmaximum age:" + maximumAge + "\taverage age" + averageAge + "\taverage energy: "+averageEnergy);
    	
    	
    	
    }
}
