package metadata;

import java.io.Serializable;
/**
 * A actual sokoban level JSON serialized model.
 * @author Isan Rivkin
 *
 */
public class LevelModel implements Serializable  
{
	private char[][] map;
	private int min_steps;
    private int current_steps;
    private int height;
    private int width;
    private boolean alreadyWon;
    private int numBoxes,numTargets,numFloors,numPlayers,numWalls;
    
	public LevelModel() 
	{

	}
	public LevelModel(char[][] map, int min_steps, int current_steps, int height, int width, boolean alreadyWon,
			int numBoxes, int numTargets, int numFloors, int numPlayers, int numWalls) {
		super();
		this.map = map;
		this.min_steps = min_steps;
		this.current_steps = current_steps;
		this.height = height;
		this.width = width;
		this.alreadyWon = alreadyWon;
		this.numBoxes = numBoxes;
		this.numTargets = numTargets;
		this.numFloors = numFloors;
		this.numPlayers = numPlayers;
		this.numWalls = numWalls;
	}

	public char[][] getMap() {
		return map;
	}

	public void setMap(char[][] map) {
		this.map = map;
	}

	public int getMin_steps() {
		return min_steps;
	}

	public void setMin_steps(int min_steps) {
		this.min_steps = min_steps;
	}

	public int getCurrent_steps() {
		return current_steps;
	}

	public void setCurrent_steps(int current_steps) {
		this.current_steps = current_steps;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public boolean isAlreadyWon() {
		return alreadyWon;
	}

	public void setAlreadyWon(boolean alreadyWon) {
		this.alreadyWon = alreadyWon;
	}

	public int getNumBoxes() {
		return numBoxes;
	}

	public void setNumBoxes(int numBoxes) {
		this.numBoxes = numBoxes;
	}

	public int getNumTargets() {
		return numTargets;
	}

	public void setNumTargets(int numTargets) {
		this.numTargets = numTargets;
	}

	public int getNumFloors() {
		return numFloors;
	}

	public void setNumFloors(int numFloors) {
		this.numFloors = numFloors;
	}

	public int getNumPlayers() {
		return numPlayers;
	}

	public void setNumPlayers(int numPlayers) {
		this.numPlayers = numPlayers;
	}

	public int getNumWalls() {
		return numWalls;
	}

	public void setNumWalls(int numWalls) {
		this.numWalls = numWalls;
	}
	
}
