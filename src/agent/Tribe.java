package agent;

import java.awt.Color;
import java.util.ArrayList;

import genome.Behaviour_Automata;
import genome.Communication_Automata;
import global.Randomized;
import global.Tracked_Datas;

public class Tribe extends Randomized {
	int size;
	int fitness_score;
	Color color;
	int spawnX,spawnY;
	public ArrayList<Human> living_humans;
	Tracked_Datas tracked_datas;
	// vhen size reach max instansiation tribu size, back up the live human of this tribe
	// Nape, not with the same 
	
	//Automatas shared by humans:
	public Behaviour_Automata A_S1, A_ChildS1, A_S2, A_ChildS2;
	public Communication_Automata cA_S1, cA_S2, cA_childS1, cA_childS2;
	
	public Tribe(){
		this.living_humans = new ArrayList<Human>();
		do{
			this.A_S1 = new Behaviour_Automata(false);
		}while(!this.A_S1.is_Valid());
		do{
			this.A_ChildS1 = new Behaviour_Automata(false);
		}while(!this.A_S1.is_Valid());
		do{
			this.A_S2 = new Behaviour_Automata(false);
		}while(!this.A_S1.is_Valid());
		do{
			this.A_ChildS2 = new Behaviour_Automata(false);
		}while(!this.A_S1.is_Valid());
		
		do{
			this.cA_childS1 = new Communication_Automata();
		}while(!this.A_S1.is_Valid());
		do{
			this.cA_childS2 = new Communication_Automata();
		}while(!this.A_S1.is_Valid());
		do{
			this.cA_S1 = new Communication_Automata();
		}while(!this.A_S1.is_Valid());
		do{
			this.cA_S2 = new Communication_Automata();
		}while(!this.A_S1.is_Valid());
	}
	
	public Behaviour_Automata get_adequate_Automata(Human h, boolean handle_com){
		switch(h.sex){
		case CHILDREN_1:
			if(handle_com)
				return this.cA_childS1;
			return this.A_ChildS1;
		case CHILDREN_2:
			if(handle_com)
				return this.cA_childS2;
			return this.A_ChildS2;
		case S1:
			if(handle_com)
				return this.cA_S1;
			return this.A_S1;
		case S2:
			if(handle_com)
				return this.cA_S2;
			return this.A_S2;
		default:
			System.err.println("Tribe: get adequate automate: could not resovle sex");
			return null;
		}
	}
	
	public int getSize() {
		return size;
	}
	
	public void setSize(int size) {
		this.size = size;
	}
	
	public void update_fitness() {
		this.fitness_score += size;
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
	
	
	
}
