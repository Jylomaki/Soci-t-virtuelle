package agent;

import java.awt.Color;
import java.util.ArrayList;

import global.Randomized;

public class Tribe extends Randomized {
	int size;
	int fitness_mult;
	int fitness_score;
	Color color;
	int spawnX,spawnY;
	ArrayList<Human> back_up_humans;
	//TODO:
	// vhen size reach max tribu size, back up the live human of this tribe
	
	public Tribe(){
		back_up_humans = new ArrayList<Human>();
	}
	
	public int getSize() {
		return size;
	}
	
	public void setSize(int size) {
		this.size = size;
	}
	public int getFitness_mult() {
		return fitness_mult;
	}
	
	public void setFitness_mult(int fitness_mult) {
		this.fitness_mult = fitness_mult;
	}
	public int getFitness_score() {
		return fitness_score;
	}
	
	public void setFitness_score(int fitness_score) {
		this.fitness_score = fitness_score;
	}
	
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	
	public int getSpawnX() {
		return spawnX;
	}
	public void setSpawnX(int spawnX) {
		this.spawnX = spawnX;
	}
	
	public int getSpawnY() {
		return spawnY;
	}
	public void setSpawnY(int spawnY) {
		this.spawnY = spawnY;
	}
	
	public ArrayList<Human> getBack_up_humans() {
		return back_up_humans;
	}
	public void setBack_up_humans(ArrayList<Human> back_up_humans) {
		this.back_up_humans = back_up_humans;
	}
	
	
	
}
