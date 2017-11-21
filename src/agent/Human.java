package agent;

import genome.*;
import global.Randomized;
import global.Vector;
import global.local_random;
import terrain.Case;

import java.util.ArrayList;

import action.Collect_Food;
import action.ParentAction;
import data.DataManagement;

public class Human extends Randomized{
	static final int MUST_EAT = 200;
	static final int ENERGY_RESTITUTION = 200;
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
	
	public int x,y;
	public Vector dir;
	
	ParentAction currentAction;
	
	public Human(){
		dir = new Vector(100,100);
		id = id_count++;
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
		if(energy<=MUST_EAT && food>0){
			food--;
			energy += ENERGY_RESTITUTION;
		}
		if(energy<=0){//DIE
			DataManagement.killHuman(this);
		}
		wiggle();
		x = (int) ((x+(dir.x/100))%DataManagement.TerrainGridX);
		y = (int) ((y+(dir.y/100))%DataManagement.TerrainGridY);
		if(x<0)
			x = DataManagement.TerrainGridX-1;
		if(y<0)
			y = DataManagement.TerrainGridY-1;
	}
	
	public void wiggle(){
		int tr = local_random.nextInt(50);
		int tl = -local_random.nextInt(50);
		dir.rotate(tr);
		dir.rotate(tl);
	}

	public boolean does_like_interlocutor() {
		if(this.liked_list.contains(this.interlocutor_id))
			return true;
		// TODO access to check the culture distance
		return false;
	}
}
