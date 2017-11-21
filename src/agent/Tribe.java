package agent;

import java.awt.Color;
import java.util.ArrayList;

import global.Randomized;
import global.Tracked_Datas;

public class Tribe extends Randomized {
	int size;
	int fitness_score;
	Color color;
	int spawnX,spawnY;
	ArrayList<Human> back_up_humans;
	ArrayList<Human> living_humans;
	Tracked_Datas tracked_datas;
	//TODO:
	// vhen size reach max instansiation tribu size, back up the live human of this tribe
	
	public Tribe(){
		back_up_humans = new ArrayList<Human>();
	}
	
	public int getSize() {
		return size;
	}
	
	public void setSize(int size) {
		this.size = size;
	}
	
	public void update_fitness() {
		this.fitness_score += size;
		if(this.size==global.Global_variables.tribe_max_size) {
			backup_humans();
		}
	}
	
	private void backup_humans() {
		this.back_up_humans= new ArrayList<Human>();
		for(int i=0; i<global.Global_variables.tribe_max_size; i++)
			this.back_up_humans.add(this.living_humans.get(i));
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
