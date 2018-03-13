package assignment4;
/* CRITTERS Critter.java
 * EE422C Project 4 submission by
 * Replace <...> with your actual data.
 * <Student1 Name>
 * <Student1 EID>
 * <Student1 5-digit Unique No.>
 * <Student2 Name>
 * <Student2 EID>
 * <Student2 5-digit Unique No.>
 * Slip days used: <0>
 * Fall 2016
 */


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* see the PDF for descriptions of the methods and fields in this class
 * you may add fields, methods or inner classes to Critter ONLY if you make your additions private
 * no new public, protected or default-package code or data can be added to Critter
 */


public abstract class Critter {
	private static String myPackage;
	private	static List<Critter> population = new java.util.ArrayList<Critter>();
	private static List<Critter> babies = new java.util.ArrayList<Critter>();

	//private static ArrayList<Critter> critters = new ArrayList<>();
	private static ArrayList<Critter>[][] myWorld = new ArrayList[Params.world_width][Params.world_height];

	// Gets the package name.  This assumes that Critter and its subclasses are all in the same package.
	static {
		myPackage = Critter.class.getPackage().toString().split(" ")[1];
	}
	
	private static java.util.Random rand = new java.util.Random();
	public static int getRandomInt(int max) {
		return rand.nextInt(max);
	}
	
	public static void setSeed(long new_seed) {
		rand = new java.util.Random(new_seed);
	}
	
	
	/* a one-character long string that visually depicts your critter in the ASCII interface */
	public String toString() { return ""; }
	
	private int energy = 0;
	protected int getEnergy() { return energy; }
	
	private int x_coord = -1;
	private int y_coord = -1;
	
	protected final void walk(int direction) {
	    myWorld[x_coord][y_coord].remove(this);
	    x_coord += xMovement(direction);
        y_coord += yMovement(direction);
        if(x_coord == Params.world_width) {
            x_coord = 0;
        }
        else if(x_coord < 0) {
            x_coord = Params.world_width - 1;
        }
	    if(y_coord == Params.world_height) {
            y_coord = 0;
        }
        else if(y_coord < 0) {
            y_coord = Params.world_height - 1;
        }
        myWorld[x_coord][y_coord].add(this);
        energy -= Params.walk_energy_cost;
	}
	
	protected final void run(int direction) {
		walk(direction);
		walk(direction);
        energy = energy + 2 * Params.walk_energy_cost - Params.run_energy_cost;
    }

    private static int xMovement(int direction) {
        if(direction == 0 || direction == 1 || direction == 7) {
            return 1;
        }
        else if(direction == 3 || direction == 4 || direction == 5) {
            return -1;
        }
        else {
            return 0;
        }
    }

    private static int yMovement(int direction) {
        if(direction == 1 || direction == 2 || direction == 3) {
            return 1;
        }
        else if(direction == 5 || direction == 6 || direction == 7) {
            return -1;
        }
        else {
            return 0;
        }
    }
	
	protected final void reproduce(Critter offspring, int direction) {
	    if(energy >= Params.min_reproduce_energy) {
	        offspring.energy = energy / 2;
	        energy -= offspring.energy;
	        offspring.x_coord = x_coord + xMovement(direction);
	        offspring.y_coord = y_coord + yMovement(direction);
            if(x_coord == Params.world_width) {
                x_coord = 0;
            }
            else if(x_coord < 0) {
                x_coord = Params.world_width - 1;
            }
            if(y_coord == Params.world_height) {
                y_coord = 0;
            }
            else if(y_coord < 0) {
                y_coord = Params.world_height - 1;
            }
            babies.add(offspring);
        }
	}

	public abstract void doTimeStep();
	public abstract boolean fight(String oponent);
	
	/**
	 * create and initialize a Critter subclass.
	 * critter_class_name must be the unqualified name of a concrete subclass of Critter, if not,
	 * an InvalidCritterException must be thrown.
	 * (Java weirdness: Exception throwing does not work properly if the parameter has lower-case instead of
	 * upper. For example, if craig is supplied instead of Craig, an error is thrown instead of
	 * an Exception.)
	 * @param critter_class_name
	 * @throws InvalidCritterException
	 */
	public static void makeCritter(String critter_class_name) throws InvalidCritterException {
        try {
            Class c = Class.forName(critter_class_name);
            Critter critter = (Critter) c.newInstance();
            int x = getRandomInt(Params.world_width);
            int y = getRandomInt(Params.world_height);
            critter.x_coord = x;
            critter.y_coord = y;
            critter.energy = Params.start_energy;
            population.add(critter);
            myWorld[x][y].add(critter);
        } catch(Exception e) {
            throw new InvalidCritterException(critter_class_name);
        }
	}
	
	/**
	 * Gets a list of critters of a specific type.
	 * @param critter_class_name What kind of Critter is to be listed.  Unqualified class name.
	 * @return List of Critters.
	 * @throws InvalidCritterException
	 */
	public static List<Critter> getInstances(String critter_class_name) throws InvalidCritterException {
		List<Critter> result = new java.util.ArrayList<Critter>();
	
		return result;
	}
	
	/**
	 * Prints out how many Critters of each type there are on the board.
	 * @param critters List of Critters.
	 */
	public static void runStats(List<Critter> critters) {
		System.out.print("" + critters.size() + " critters as follows -- ");
		java.util.Map<String, Integer> critter_count = new java.util.HashMap<String, Integer>();
		for (Critter crit : critters) {
			String crit_string = crit.toString();
			Integer old_count = critter_count.get(crit_string);
			if (old_count == null) {
				critter_count.put(crit_string,  1);
			} else {
				critter_count.put(crit_string, old_count.intValue() + 1);
			}
		}
		String prefix = "";
		for (String s : critter_count.keySet()) {
			System.out.print(prefix + s + ":" + critter_count.get(s));
			prefix = ", ";
		}
		System.out.println();		
	}
	
	/* the TestCritter class allows some critters to "cheat". If you want to 
	 * create tests of your Critter model, you can create subclasses of this class
	 * and then use the setter functions contained here. 
	 * 
	 * NOTE: you must make sure that the setter functions work with your implementation
	 * of Critter. That means, if you're recording the positions of your critters
	 * using some sort of external grid or some other data structure in addition
	 * to the x_coord and y_coord functions, then you MUST update these setter functions
	 * so that they correctly update your grid/data structure.
	 */
	static abstract class TestCritter extends Critter {
		protected void setEnergy(int new_energy_value) {
			super.energy = new_energy_value;
			if(new_energy_value < 1) {                              // Is this ok?
			    myWorld[getX_coord()][getY_coord()].remove(this);
            }
		}
		
		protected void setX_coord(int new_x_coord) {
			super.x_coord = new_x_coord;
			if(getY_coord() != -1) {                                // Is this ok?
			    myWorld[getX_coord()][getY_coord()].add(this);
            }
		}
		
		protected void setY_coord(int new_y_coord) {
			super.y_coord = new_y_coord;
            if(getX_coord() != -1) {                                // Is this ok?
                myWorld[getX_coord()][getY_coord()].add(this);
            }
		}
		
		protected int getX_coord() {
			return super.x_coord;
		}
		
		protected int getY_coord() {
			return super.y_coord;
		}
		

		/*
		 * This method getPopulation has to be modified by you if you are not using the population
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.
		 */
		protected static List<Critter> getPopulation() {
			return population;
		}
		
		/*
		 * This method getBabies has to be modified by you if you are not using the babies
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.  Babies should be added to the general population 
		 * at either the beginning OR the end of every timestep.
		 */
		protected static List<Critter> getBabies() {
			return babies;
		}
	}

	/**
	 * Clear the world of all critters, dead and alive
	 */
	public static void clearWorld() {
		// Complete this method.
        myWorld = new ArrayList[Params.world_width][Params.world_height];
        population = new ArrayList<>();
	}
	
	public static void worldTimeStep() {
	    for(Critter c : population) {
	        c.doTimeStep();
        }
        for(int i = 0; i < Params.world_width; i++) {
	        for(int j = 0; j < Params.world_height; j++) {
                while (myWorld[i][j].size() > 1) {
                    Critter critter1 = myWorld[i][j].get(0);
                    Critter critter2 = myWorld[i][j].get(1);
                    resolveEncounter(critter1, critter2);
                    if (critter1.energy == 0) {
                        population.remove(critter1);
                        myWorld[critter1.x_coord][critter1.y_coord].remove(critter1);
                    }
                    if (critter2.energy == 0) {
                        population.remove(critter2);
                        myWorld[critter2.x_coord][critter2.y_coord].remove(critter2);
                    }
                }
            }
        }
        for(int i = 0; i < Params.refresh_algae_count; i++) {
	        Algae newAlgae = new Algae();
	        newAlgae.setX_coord(getRandomInt(Params.world_width));
	        newAlgae.setY_coord(getRandomInt(Params.world_height));
	        newAlgae.setEnergy(Params.start_energy);
	        babies.add(newAlgae);
        }
    }
	
    private static void resolveEncounter(Critter critter1, Critter critter2) {
	    boolean critter1Fights = critter1.fight(critter2.toString());
	    boolean critter2Fights = critter2.fight(critter1.toString());
	    if(critter1.energy > 0 && critter2.energy > 0) {
	        int critter1Rolls = 0;
	        int critter2Rolls = 0;
	        if(critter1Fights) {
	            critter1Rolls = getRandomInt(critter1.energy);
            }
            if(critter2Fights) {
	            critter2Rolls = getRandomInt(critter2.energy);
            }
            if(critter1Rolls >= critter2Rolls) {                    // =?
	            critter1.energy += (critter2.energy / 2);
	            critter2.energy = 0;
            }
            else if(critter1Rolls < critter2Rolls) {
	            critter2.energy += (critter1.energy / 2);
	            critter1.energy = 0;
            }
        }
    }
    
	public static void displayWorld() {
	    printBounds();
	    for(int i = 0; i < Params.world_height; i++) {
	        System.out.print("|");
	        for(int j = 0; j < Params.world_width; j++) {
	            if(myWorld[j][i].size() == 0) {
	                System.out.print(' ');
                }
                else {
	                System.out.print(myWorld[j][i].get(0).toString());
                }
            }
            System.out.print("|");
        }
        printBounds();
	}

	private static void printBounds() {
        System.out.print('+');
        for(int i = 0; i < Params.world_width; i++) {
            System.out.print('-');
        }
        System.out.print('+');
    }
}