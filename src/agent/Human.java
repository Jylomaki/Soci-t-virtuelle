package agent;

import genome.*;
import global.Randomized;
import global.Vector;
import global.local_random;
import terrain.Case;
import agent.Sex;

import java.util.ArrayList;

import action.Collect_Food;
import action.ParentAction;
import data.DataManagement;

public class Human extends Randomized{
	//static information
	static final int MUST_EAT = 200;
	static final int ENERGY_RESTITUTION = 200;
	static final int adult_age = 1000;
	static final int birth_energy = 200;

	
	//comportement information
	public Tribe tribe;// reference to tribe to access the shared automatas
	public Behaviour_Automata behaviour_automata;
	public Communication_Automata communication_automata;
	public Genomal_Variables genomalVariables;
	
	static long id_count=0;
	
	//Status datas
	public long id, group_id;
	public int energy, age, food, ressource;
	public Sex sex;
	public int culture;
	public Case currentCase;
	
	//Communication used datas
	public int interlocutor_id;
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
	public Vector dir;
	
	ParentAction currentAction;
	
	public Human(){
		this.sex = Sex.values()[local_random.nextInt(Sex.values().length)];
		dir = new Vector(100,100);
		id = id_count++;
	}
	
	public Human(long group_id, int food, int ressource, Sex sex, int culture) {
		super();
		this.group_id = group_id;
		this.energy = birth_energy;
		this.age = 0;
		this.food = food;
		this.ressource = ressource;
		this.sex = sex;
		this.culture = culture;
	}

	public void take_action(){
		//TODO maybe switch over Action enum and set current Action
		currentAction = new Collect_Food();
	}
	
	public void executeAction(){
		take_action();
		currentAction.execute(this);
	}
	
	
	//Reflexes actions and update Attribute
	public void update(){
		age++;
		//System.out.println(energy);
		energy -= (age/100);
		while(energy<=MUST_EAT && food>0){
			food--;
			energy += ENERGY_RESTITUTION;
		}
		if(energy<=0){//DIE
			DataManagement.killHuman(this);
			if(this.food > 0)
				DataManagement.terrain.getCase(x, y).corpse_food += this.food;
			if(this.ressource > 0)
				DataManagement.terrain.getCase(x, y).corpse_ressource += this.ressource;
		}
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
		update_genre();
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
		case S2:
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
	
	public Human reproduce(Human h){
		if(this.parenting_ok(h))
		{
			return new Human();
		}
		return null;
	}

	public boolean does_like_interlocutor() {
		if(this.liked_list.contains(this.interlocutor_id))
			return true;
		// TODO access to check the culture distance
		return false;
	}
}
