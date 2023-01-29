import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class HillClimbing{

	public static void main(String[] args) {//One mark for accepting k value from the user as a command line argument.
			
		if (2 < Integer.valueOf(args[0]) && Integer.valueOf(args[0]) < 10) {
			hillClimbingSearch(Integer.valueOf(args[0]));
		}
		
		else {
			System.out.println("Invalid input. Please pass through an integer between 3 and 9");
		}
		

		
	}
	
	public static void hillClimbingSearch(int k) {
		
		char[] state = {'b','o','o','o','r','r','j','j','j','j','j','j','j'};
		
		int[][] adjacencyMatrix = {
				{1,1,0,0,0,0,0,0,0,0,0,1,1},//BC
				{1,1,1,0,0,0,0,0,0,0,0,1,0},//AB
				{0,1,1,1,0,0,0,0,0,0,0,1,0},//SK
				{0,0,1,1,1,0,0,0,0,0,1,0,0},//MB
				{0,0,0,1,1,1,0,0,0,0,0,0,0},//ON
				{0,0,0,0,1,1,1,0,0,1,0,0,0},//QC
				{0,0,0,0,0,1,1,1,1,0,0,0,0},//NB
				{0,0,0,0,0,0,1,1,1,0,0,0,0},//NS
				{0,0,0,0,0,0,1,1,1,0,0,0,0},//PEI
				{0,0,0,0,0,1,0,0,0,1,0,0,0},//NL
				{0,0,0,1,0,0,0,0,0,0,1,1,0},//NU
				{1,1,1,0,0,0,0,0,0,0,1,1,1},//NT
				{1,0,0,0,0,0,0,0,0,0,0,1,1}//YT
		};
		
		char[] currState = Arrays.copyOf(state, state.length);
		
		int temperature = 3000; //I use simulated annealing to avoid local minimums, and to give the algorithm enough time to get the number of colours correct
		int currentTemperature = temperature;
		int randomTemp = temperature;
		
		Random t = new Random();
		
		while(goalReached(state, adjacencyMatrix, k) == false) { //One mark for writing the correct loop condition that checks if a Solution state is found.
			
			currentTemperature--;
			t.setSeed(System.nanoTime());
			randomTemp = t.nextInt(temperature);
			
			//we update the current state with a neighbouring state
			currState = neighbourState(Arrays.copyOf(state, state.length), k); // functions to calculate the successor (2)
						
			if(heuristic(state, adjacencyMatrix) > heuristic(currState, adjacencyMatrix) || randomTemp < currentTemperature) { //if the neighbouring state is better, we update our state
				
//				System.out.println(printRegions(state) + "\tHeuristic: " + heuristic(state,adjacencyMatrix) + "\tCost: " + cost(state) + "\tIs Solution?: " + goalReached(state, adjacencyMatrix, k) + "\tColours: " + variety(state));
				
				state = Arrays.copyOf(currState, currState.length);
				
//				try {
//					Thread.sleep(500);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
			}
	
		}
			
		//One mark for writing the code that prints out the solution in the correct order.
		System.out.println();
		System.out.println("Solution Found: -----------------------------------------------------------");
		System.out.println("Heuristic: " + heuristic(state,adjacencyMatrix) + "\tCost: " + cost(state) + "\tIs Solution?: " + goalReached(state, adjacencyMatrix, k) + "\tColours: " + variety(state));
		System.out.println(printRegions(state));
		System.out.println("BC\tAB\tSK\tMB\tON\tQC\tNB\tNS\tPEI\tNL\tNU\tNT\tYT\t");
		System.out.println();

	}
	
	/**
	 * neighbourState will randomly generate a neighbouring state by changing one character in the char array to another colour. 
	 * It returns that state to be used in the hill climbing algorithm
	 * @param regions
	 * @param k
	 * @return
	 */
	public static char[] neighbourState(char[] regions, int k) {
		Random r = new Random();
		r.setSeed(System.nanoTime());
		regions[r.nextInt(13)] = randomColour(k);
		return regions;
	}
	
	/**
	 * This method returns a random colour, in the form of a character. the input, k, refers to how many colours we are allowed. 
	 * @param k
	 * @return
	 */
	public static char randomColour(int k) {
		Random c = new Random();
		c.setSeed(System.nanoTime());
		int choice = c.nextInt(k);

		switch(choice) {
		
		case 0:
			return 'r'; //red
		case 1:
			return 'b'; //blue
		case 2:
			return 'o'; //orange
		case 3:
			return 'j'; //jungle
		case 4:
			return 'y'; //yellow
		case 5:
			return 'g'; //green
		case 6:
			return 'i'; //indigo
		case 7:
			return 'v'; //violet
		case 8:
			return 'p'; //pink
		default:
			return 'x';
		}
	}
	
	public static int variety(char[] regions) {
		int count = 0;
		int[] characterCounts = {0,0,0,0,0,0,0,0,0};
		
		for (int i = 0; i < regions.length; i++) {
			
			switch (regions[i]) {
			
			case 'r':
				characterCounts[0]++;
				break;
			case 'b':
				characterCounts[1]++;
				break;
			case 'o':
				characterCounts[2]++;
				break;
			case 'j':
				characterCounts[3]++;
				break;
			case 'y':
				characterCounts[4]++;
				break;
			case 'g':
				characterCounts[5]++;
				break;
			case 'i':
				characterCounts[6]++;
				break;
			case 'v':
				characterCounts[7]++;
				break;
			case 'p':
				characterCounts[8]++;
				break;
			default:
				characterCounts[9]++;
				break;
			}
		}
		
		for (int j = 0; j < characterCounts.length; j++) {
			if (characterCounts[j] != 0)
				count++;
		}
		
		return count;
	}
	
	/**
	 * This method returns a random colour, in the form of a character. the input, k, refers to how many colours we are allowed. 
	 * @param k
	 * @return
	 */
	public static char returnColour(int choice) {
		
		switch(choice) {
		
		case 0:
			return 'r'; //red
		case 1:
			return 'b'; //blue
		case 2:
			return 'o'; //orange
		case 3:
			return 'j'; //jungle
		case 4:
			return 'y'; //yellow
		case 5:
			return 'g'; //green
		case 6:
			return 'i'; //indigo
		case 7:
			return 'v'; //violet
		case 8:
			return 'p'; //pink
		default:
			return 'x';
		}
	}
	
	/**
	 * In this problem, each colour has a different cost. This method simply returns the cost of all of the colours used in the 13 regions.
	 * This method is used alongside the Heuristic method to compare neighbouring states, and determine which is closer to our goal
	 * @param regions
	 * @return
	 */
	public static int cost(char[] regions) { // cost (2)
		
		int cost = 0;
		
		for (int i = 0; i < regions.length; i++) {
			switch (regions[i]) {
			
			case 'r':
				cost = cost + 2;
				break;
			case 'b':
				cost = cost + 1;
				break;
			case 'o':
				cost = cost + 3;
				break;
			case 'j':
				cost = cost + 5;
				break;
			case 'y':
				cost = cost + 4;
				break;
			case 'g':
				cost = cost + 3;
				break;
			case 'i':
				cost = cost + 2;
				break;
			case 'v':
				cost = cost + 1;
				break;
			case 'p':
				cost = cost + 1;
				break;
			default:
				cost = 10000;
				break;
			}
		}
		
		return cost;
	}
	
	/**
	 * This method's purpose is to calculate how many adjacent regions share the same colour. The lower the value returned, the closer
	 * we are to the goal of no adjacent regions sharing the same colour. 
	 * @param regions
	 * @param adjacencyMatrix
	 * @return
	 */
	public static int heuristic(char[] regions, int[][] adjacencyMatrix) { //Heuristic (2).
		
		int count = 0;
		for (int i = 0; i < regions.length; i++) {
			for (int j = 0; j < regions.length; j++) {
				if (adjacencyMatrix[i][j] == 1 && regions[i] == regions[j] && i != j)
					count++;
			}
		}
		return count / 2;
	}
	
	/**
	 * This method will take inputs of the current region state, and the adjacency matrix. It will return true or false, depending on whether
	 * or not the goal is reached (false if 2 adjacent regions are the same colour, otherwise true)
	 * @param regions
	 * @param adjacencyMatrix
	 * @return
	 */
	public static boolean goalReached(char[] regions, int[][] adjacencyMatrix, int k) { //One mark for writing the code that checks if the current state is a goal state.
		for (int i = 0; i < regions.length; i++) {
			for (int j = 0; j < regions.length; j++) {
				if (adjacencyMatrix[i][j] == 1 && regions[i] == regions[j] && i != j) //if adjacent regions share the same colour, we immediately return false;
					return false;	
			}
		}
		
		if (variety(regions) == k) //Here I check if the number of colours is equal to k, so we can determine if it is a real solution.
			return true;
		else
			return false;
	}
	
	public static String printRegions(char[] regions) {
		
		String str = "";
		
		for (int i = 0; i < regions.length; i++) {
			str = str + regions[i] + "\t";
		}
		
		return str;
	}
}
