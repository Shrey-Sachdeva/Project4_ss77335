package assignment4;

public class Critter4 extends Critter {
	
	private int kids = 0;
	private int fights = 0;
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
    	fights++;
        while (this.getEnergy() >= Params.min_reproduce_energy) {
            Critter4 offspring = new Critter4();
            reproduce(offspring, getRandomInt(8));
            kids++;
        }
        if (opponent.equals("@")) {
            return true;
        }
        return false;
    }
    
    public static void runStats(java.util.List<Critter> fours) {
    	if(fours.size() == 0) {
    		System.out.println("0 total Critter4s");
    		return;
    	}
    	
    	int averageKids = 0;
    	int averageFights = 0;
    	double averageKidsPerFight = 0;
    	int averageEnergy = 0;
    	
    	for(Object f : fours) {
    		Critter4 four = (Critter4) f;
    		averageKids += four.kids;
    		averageFights += four.fights;
    		averageEnergy += four.getEnergy();
    		
    	}
    	averageEnergy /= fours.size();
    	averageKids /= fours.size();
    	averageFights /= fours.size();
    	averageKidsPerFight = averageKids/((double)averageFights);
    	
    	System.out.println(fours.size() + " total Critter4s birthing babies\taverage #kids: " + averageKids + "\taverage #fights: "+averageFights + "\taverage kids per fight: "+ averageKidsPerFight + "\taverage energy: "+averageEnergy);
    }
}
