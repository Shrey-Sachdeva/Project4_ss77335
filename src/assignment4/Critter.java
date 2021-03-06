package assignment4;
/* CRITTERS Critter.java
 * EE422C Project 4 submission by
 * Shrey Sachdeva
 * ss77335
 * 15455
 * Kylar Osborne
 * kmo785
 * 15455
 * Slip days used: <0>
 * Spring 2018
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
	// Indicates whether a Critter has moved during the current time step
	private boolean hasMoved = false;
	// Indicates whether a Critter is moving to flee from a fight
	protected boolean tryingToFlee = false;
	// 2D array representing Critter locations
	private static ArrayList<Critter>[][] myWorld = new ArrayList[Params.world_width][Params.world_height];

	// Gets the package name.  This assumes that Critter and its subclasses are all in the same package.
	static {
		myPackage = Critter.class.getPackage().toString().split(" ")[1];
	}
	
	private static java.util.Random rand = new java.util.Random();

    /**
     * Returns a random int between 0 and max
     * @param max is the upper bound
     * @return integer
     */
	public static int getRandomInt(int max) {
		return rand.nextInt(max);
	}

    /**
     * Sets rand's seed to allow testing to be repeatable
     * @param new_seed is the seed
     */
	public static void setSeed(long new_seed) {
		rand = new java.util.Random(new_seed);
	}
	
	
	/* a one-character long string that visually depicts your critter in the ASCII interface */
	public String toString() { return ""; }

    private int energy = 0;

    /**
     * Returns a Critter's current energy level
     */
	protected int getEnergy() { return energy; }
	
	private int x_coord = -1;
	private int y_coord = -1;

    /**
     * Updates a critters location by moving it one space in the specified direction
     * @param direction is the direction in which the Critter should move
     */
	protected final void walk(int direction) {
	    // Check if a Critter has previously moved in a time step
		if(!hasMoved) {
		    // Check if the Critter is trying to flee
            if(!tryingToFlee) {
				myWorld[x_coord][y_coord].remove(this);
				x_coord += xMovement(direction);
				y_coord += yMovement(direction);
				x_coord %= Params.world_width;
				y_coord %= Params.world_height;
				x_coord = x_coord < 0 ? Params.world_width + x_coord : x_coord;
				y_coord = y_coord < 0 ? Params.world_height + y_coord : y_coord;
				if(myWorld[x_coord][y_coord] == null){
				    myWorld[x_coord][y_coord] = new ArrayList<Critter>();
                }
				myWorld[x_coord][y_coord].add(this);
			}
			else {
                // Check if fleeing Critter tries to move into an occupied location
            	int newX = x_coord + xMovement(direction);
            	int newY = y_coord + yMovement(direction);
				newX %= Params.world_width;
				newY %= Params.world_height;
				newX = newX < 0 ? Params.world_width + newX : newX;
				newY = newY < 0 ? Params.world_height + newY : newY;
				// Only allow the flee to be successful if there is no Critter already in the intended location
				if(myWorld[newX][newY].size() == 0) {
					myWorld[x_coord][y_coord].remove(this);
					myWorld[newX][newY].add(this);
					x_coord = newX;
					y_coord = newY;
				}
			}
        }
        energy -= Params.walk_energy_cost;
        hasMoved = true;
	}

    /**
     * Allows a Critter to move 2 spaces
     * @param direction is the direction in which to move
     */
	protected final void run(int direction) {
		if(!hasMoved) {
			if(!tryingToFlee) {
			    // Call walk twice to move normally
				walk(direction);
				hasMoved = false;
				walk(direction);
				energy += 2 * Params.walk_energy_cost;
			}
			else {
			    // Check if another Critter occupies a space in a fleeing situation
				int newX = x_coord + 2 * xMovement(direction);
				int newY = y_coord + 2 * yMovement(direction);
				newX %= Params.world_width;
				newY %= Params.world_height;
				newX = newX < 0 ? Params.world_width + newX : newX;
				newY = newY < 0 ? Params.world_height + newY : newY;
				if(myWorld[newX][newY] == null){
				    myWorld[newX][newY] = new ArrayList<>();
                }
				if(myWorld[newX][newY].size() == 0) {
					myWorld[x_coord][y_coord].remove(this);
					myWorld[newX][newY].add(this);
					x_coord = newX;
					y_coord = newY;
				}
			}
        }
        energy -= Params.run_energy_cost;
    }

    /**
     * Determine the change to the Critter's x_coord
     * @param direction is the direction in which to move
     * @return integer representing how the x_coord to should change
     */
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

    /**
     * Determine the change to the Critter's y_coord
     * @param direction is the direction in which to move
     * @return integer representing how the y_coord to should change
     */
    private static int yMovement(int direction) {
        if(direction == 1 || direction == 2 || direction == 3) {
            return -1;
        }
        else if(direction == 5 || direction == 6 || direction == 7) {
            return 1;
        }
        else {
            return 0;
        }
    }

    /**
     * Function through which Critters reproduce
     * @param offspring is a reference to the child
     * @param direction is the direction in which the offspring should spawn
     */
	protected final void reproduce(Critter offspring, int direction) {
	    // Ensure the parent has enough energy to reproduce
	    if(energy >= Params.min_reproduce_energy) {
	        offspring.energy = energy / 2;
	        energy -= offspring.energy;
	        offspring.x_coord = x_coord + xMovement(direction);
	        offspring.y_coord = y_coord + yMovement(direction);
	        offspring.x_coord %= Params.world_width;
	        offspring.y_coord %= Params.world_height;
	        offspring.x_coord = offspring.x_coord < 0 ? Params.world_width + offspring.x_coord : offspring.x_coord;
	        offspring.y_coord = offspring.y_coord < 0 ? Params.world_height + offspring.y_coord : offspring.y_coord;
            // Add the child to the babies list (added to population and myWorld at the end of the time step)
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
        if(!critter_class_name.contains("assignment4.")){
            critter_class_name = "assignment4."+critter_class_name;
        }
	    try {
            Class c = Class.forName(critter_class_name);
            Critter critter = (Critter) c.newInstance();
            int x = getRandomInt(Params.world_width);
            int y = getRandomInt(Params.world_height);
            critter.x_coord = x;
            critter.y_coord = y;
            critter.energy = Params.start_energy;
            population.add(critter);
            if(myWorld[x][y] == null){
                myWorld[x][y] = new ArrayList<Critter>();
            }
            myWorld[x][y].add(0, critter);
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
		if(!critter_class_name.contains("assignment4.")){
		    critter_class_name = "assignment4." + critter_class_name;
        }
	    List<Critter> result = new ArrayList<Critter>();
		try {
		    Class c = Class.forName(critter_class_name);
		    Critter critter1 = (Critter) c.newInstance();
		    for(Critter critter2 : population) {
		        if(critter2.getClass().equals(critter1.getClass())) {               // Does this use a subclass?
		            result.add(critter2);
                }
            }
        } catch(Exception e) {
		    throw new InvalidCritterException(critter_class_name);
        }
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
        /**
         * Sets the energy value indirectly
         * @param new_energy_value is the initial value for the energy
         */
	    protected void setEnergy(int new_energy_value) {
			super.energy = new_energy_value;
			if(new_energy_value < 1) {                              // Is this ok?
			    myWorld[getX_coord()][getY_coord()].remove(this);
			    population.remove(this);
            }
		}

        /**
         * Set x_coord indirectly
         * @param new_x_coord is the initial x position
         */
		protected void setX_coord(int new_x_coord) {
			new_x_coord %= Params.world_width;
			new_x_coord = new_x_coord < 0 ? Params.world_width + new_x_coord : new_x_coord;
			super.x_coord = new_x_coord;
			// Adds the critter to the general population if its x_coord and y_coord have been set
			if(getY_coord() != -1) {
			    if(myWorld[getX_coord()][getY_coord()] == null){
                    myWorld[getX_coord()][getY_coord()] = new ArrayList<>();
                }
			    myWorld[getX_coord()][getY_coord()].add(this);
			    population.add(this);
            }
		}

        /**
         * Set y_coord indirectly
         * @param new_y_coord is the initial y position
         */
		protected void setY_coord(int new_y_coord) {
			new_y_coord %= Params.world_height;
			new_y_coord = new_y_coord < 0 ? Params.world_height + new_y_coord : new_y_coord;
			super.y_coord = new_y_coord;
            // Adds the critter to the general population if its x_coord and y_coord have been set
            if(getX_coord() != -1) {
                if(myWorld[getX_coord()][getY_coord()] == null){
                    myWorld[getX_coord()][getY_coord()] = new ArrayList<>();
                }
                myWorld[getX_coord()][getY_coord()].add(this);
                population.add(this);
            }
		}

        /**
         * Returns current x_coord of a Critter
         * @return x_coord
         */
		protected int getX_coord() {
			return super.x_coord;
		}

        /**
         * Returns current y_coord of a Critter
         * @return y_coord
         */
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
        myWorld = new ArrayList[Params.world_width][Params.world_height];
        population = new ArrayList<>();
	}

    /**
     * Simulates passage of one unit of time in the world
     */
	public static void worldTimeStep() {
	    // Perform each Critter's time step for every Critter in the population
	    for(Critter c : population) {
	        c.doTimeStep();
        }
        // Determine locations where multiple Critters occupy the same position
        for(int i = 0; i < Params.world_width; i++) {
	        for(int j = 0; j < Params.world_height; j++) {
	            if(myWorld[i][j] == null){
	                myWorld[i][j] = new ArrayList<Critter>();
                }
                while (myWorld[i][j].size() > 1) {
                    Critter critter1 = myWorld[i][j].get(0);
                    Critter critter2 = myWorld[i][j].get(1);
                    // Resolve the encounter between the Critters
                    resolveEncounter(critter1, critter2);
                    // Remove the dead Critters from the population
                    if (critter1.energy <= 0) {
                        population.remove(critter1);
                        myWorld[critter1.x_coord][critter1.y_coord].remove(critter1);
                    }
                    if (critter2.energy <= 0) {
                        population.remove(critter2);
                        myWorld[critter2.x_coord][critter2.y_coord].remove(critter2);
                    }
                }
            }
        }
        // Subtract the rest energy cost from all Critters
        ArrayList<Critter> toRemove = new ArrayList<Critter>();
        for(Critter c : population) {
            c.energy -= Params.rest_energy_cost;
            if(c.energy < 1) {
                myWorld[c.x_coord][c.y_coord].remove(c);
                toRemove.add(c);
            }
            // Reset flags indicating movement for next time step
            c.hasMoved = false;
            c.tryingToFlee = false;
        }
        // Remove the Critters that died from the rest energy cost
        for(Critter c : toRemove) {
            population.remove(c);
        }
        // Add algae equal to the refresh_algae_count to the world randomly
        for(int i = 0; i < Params.refresh_algae_count; i++) {
        	try {
				makeCritter("assignment4.Algae");
			} catch (InvalidCritterException e) {
				e.printStackTrace();
			}
        }
        // Add all offspring to the world
        for(Critter c : babies) {
            myWorld[c.x_coord][c.y_coord].add(c);
            population.add(c);
        }
        babies.clear();
    }

    /**
     * Simulates an encounter between 2 Critters
     * @param critter1 is the first Critter involved in the encounter
     * @param critter2 is the second Critter involved in the encounter
     */
    private static void resolveEncounter(Critter critter1, Critter critter2) {
	    // Determine critter1's response
        boolean critter1Fights = critter1.fight(critter2.toString());
        // If critter1 moved away, the encounter is finished
	    if(myWorld[critter1.x_coord][critter1.y_coord].contains(critter2)) {
	        // Determine critter2's repsonse
            boolean critter2Fights = critter2.fight(critter1.toString());
            // Simulate the encounter further if both Critters are still in the same position
            if(critter1.x_coord == critter2.x_coord && critter1.y_coord == critter2.y_coord) {
                if (critter1.energy > 0 && critter2.energy > 0) {
                    // Simulate a fight if both Critters are still alive
                    int critter1Rolls = -1;
                    int critter2Rolls = -1;
                    if (critter1Fights) {
                        critter1Rolls = getRandomInt(critter1.energy);
                    }
                    if (critter2Fights) {
                        critter2Rolls = getRandomInt(critter2.energy);
                    }
                    // Determine winner and update winner's energy level
                    if (critter1Rolls >= critter2Rolls) {
                        critter1.energy += (critter2.energy / 2);
                        critter2.energy = 0;
                    } else {
                        critter2.energy += (critter1.energy / 2);
                        critter1.energy = 0;
                    }
                }
            }
        }
    }

    /**
     * Display world in response to command "show"
     */
    public static void displayWorld() {
		// Display world
	    printBounds();
	    System.out.println("");
	    for(int i = 0; i < Params.world_height; i++) {
	        System.out.print("|");
	    	for(int j = 0; j < Params.world_width; j++) {
	    	    if(myWorld[j][i] == null){
	    	        myWorld[j][i] = new ArrayList<Critter>();
                }
	            if(myWorld[j][i].size() == 0) {
	                System.out.print(' ');
                }
                else {
	                System.out.print(myWorld[j][i].get(0).toString());
                }
            }
            System.out.print("|\n");
        }
        printBounds();
        System.out.println("");
	}

    /**
     * Prints bounds of the world (top and bottom)
     */
	private static void printBounds() {
        System.out.print('+');
        for(int i = 0; i < Params.world_width; i++) {
            System.out.print('-');
        }
        System.out.print("+");
    }
}