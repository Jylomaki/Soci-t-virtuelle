package agent;

import java.awt.Color;
import java.util.ArrayList;

import data.Frame_data;
import data.Tracked_Datas;
import genome.Behaviour_Automata;
import genome.Communication_Automata;
import global.Randomized;

public class Tribe extends Randomized {
	int size;
	int fitness_score;
	Color color;
	int spawnX,spawnY;
	public ArrayList<Human> living_humans;
	public Tracked_Datas tracked_datas;
	public Frame_data currentFrame;
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
		this.tracked_datas = new Tracked_Datas();
		this.currentFrame = new Frame_data();
	}
	
	public Behaviour_Automata get_adequate_Automata(Human h, boolean handle_com){
		if(handle_com)
			return this.cA_S1;
		return this.A_S1;
		/*
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
		}*/
	}
	
	
	public void update_datas() {
		this.tracked_datas.push_back(this.currentFrame);
		this.currentFrame.reset();
		this.update_fitness();
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
	
	
	public void mutate_autos() {
		this.mutate_autos(global.Global_variables.def_treshold, global.Global_variables.def_maxR);
	}
	public void mutate_autos(int treshold, int maxR) {
		this.A_ChildS1.mutate(treshold, maxR);
		this.A_ChildS2.mutate(treshold, maxR);
		this.A_S1.mutate(treshold, maxR);
		this.A_S2.mutate(treshold, maxR);
		this.cA_childS1.mutate(treshold, maxR);
		this.cA_childS2.mutate(treshold, maxR);
		this.cA_S1.mutate(treshold, maxR);
		this.cA_S2.mutate(treshold, maxR);
	}
}
