package agent;

import genome.*;
import global.Randomized;
import global.Vector;
import global.local_random;
import terrain.Case;
import agent.Sex;

import java.util.ArrayList;

import data.DataManagement;

public class Human extends Randomized{
	//static information
	static final int MUST_EAT = 200;
	static final int ENERGY_RESTITUTION = 200;
	static final int FOOD_EAT = 1;
	static final int adult_age = 1000;
	static final int birth_energy = 200;
	static final int settlement_energy_loss_advantage = 15;

	
	//comportement information
	public Tribe tribe;// reference to tribe to access the shared automatas
	public Behaviour_Automata behaviour_automata;
	public Communication_Automata communication_automata;
	public Genomal_Variables genomalVariables;
	
	static long id_count=0;
	
	//Status datas
	public long id;
	public int energy, age, food, ressource;
	public Sex sex;
	public int culture;
	public Case currentCase;
	
	//Communication used datas
	public Human interlocutor;
	public ArrayList<Integer> liked_list;
	public enum Communication_Status{//SET THIS VAR AT THE BEGINING OF A COM
		BEGIN,
		HURTED,
		GIVEN_FOOD,
		GIVEN_RESSOURCE
	}
	public Communication_Status comStatus;
	
	//spatial information
	public int x,y;
	public int dst_x, dst_y;
	public Vector dir;
	
	
	public Human(){
		this.sex = Sex.values()[local_random.nextInt(Sex.values().length)];
		dir = new Vector(100,100);
		this.age = 0;
		this.energy = birth_energy;
		this.genomalVariables = new Genomal_Variables();
		id = id_count++;
	}
	
	public Human(int food, int ressource, Sex sex, int culture) {
		this();
		this.food = food;
		this.ressource = ressource;
		this.sex = sex;
		this.culture = culture;
	}
	
	public Human offspring() {
		System.out.println("HALLELUYA A CHILD IS BORN");
		Human offspring = new Human();
		//TODO add mutation factor on culture
		offspring.culture = this.culture;
		
		offspring.tribe = this.tribe;
		this.tribe.living_humans.add(offspring);
		
		offspring.currentCase = this.currentCase;
		currentCase.humans.add(offspring);
		this.tribe.mutate_autos();
		return offspring;
	}
	
	//Reflexes actions and update Attribute
	public void update(){
		age++;
		energy_update();
		update_genre();
		update_tribe_frame_datas();
	}
	
	private void energy_update(){
		//System.out.println(energy);
		if(this.currentCase.settlement_present())
			energy += settlement_energy_loss_advantage;
		energy -= (age/100);
		while(energy<=MUST_EAT && food>0){
			food-= FOOD_EAT;
			energy += ENERGY_RESTITUTION;
		}
		if(energy<=0){//DIE
			DataManagement.killHuman(this);
			if(this.food > 0)
				DataManagement.terrain.getCase(x, y).corpse_food += this.food;
			if(this.ressource > 0)
				DataManagement.terrain.getCase(x, y).corpse_ressource += this.ressource;
		}
	}
	
	public void move(){
		this.currentCase.humans.remove(this);
		wiggle();
		x = (int) ((x+(dir.x/100))%DataManagement.TerrainGridX);
		y = (int) ((y+(dir.y/100))%DataManagement.TerrainGridY);
		if(x<0)
			x = DataManagement.TerrainGridX-1;
		if(y<0)
			y = DataManagement.TerrainGridY-1;
		this.currentCase = DataManagement.terrain.getCase(x, y);
		this.currentCase.humans.add(this);
	}
	
	
	private void update_tribe_frame_datas() {
		if(this.energy<=0) {
			this.tribe.currentFrame.food += this.food;
			this.tribe.currentFrame.ressource += this.ressource;
			this.tribe.currentFrame.tribus_size ++;
		}
	}

	private void update_genre(){
		switch(this.sex){
		case CHILDREN_1:
			if(this.age > adult_age)
				this.sex = Sex.S1;
			break;
		case CHILDREN_2:
			if(this.age > adult_age)
				this.sex = Sex.S2;
			break;
		case S1:
			if(this.age < adult_age)
				this.sex = Sex.CHILDREN_1;
			break;
		case S2:
			if(this.age < adult_age)
				this.sex = Sex.CHILDREN_2;
		default:
			break;
		
		}
	}
	
	public void wiggle(){
		int tr = local_random.nextInt(50);
		int tl = -local_random.nextInt(50);
		dir.rotate(tr);
		dir.rotate(tl);
	}
	
	public boolean parenting_ok(Human h){
		switch(this.sex){
		case S1:
			if(h.sex == Sex.S2)
				return true;
		case S2:
			if(h.sex == Sex.S1)
				return true;
		case CHILDREN_1:
		case CHILDREN_2:
		default:
			return false;
		
		}
	}
	
	public void reproduce(Human h){
		if(this.parenting_ok(h))
			this.offspring();
	}

	public boolean does_like_interlocutor() {
		return Math.abs(this.culture - this.interlocutor.culture) <= global.Global_variables.like_max_cultural_distance; 
	}

	public void collecte_corpse(int corpse_food, int corpse_ressource) {
		this.food+= corpse_food;
		this.ressource += corpse_ressource;
		
	}
}
