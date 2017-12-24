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
	public static final int birth_energy = 200;
	static final int settlement_energy_loss_advantage = 15;

	
	//comportement information
	public Tribe tribe;// reference to tribe to access the shared automatas
	public Behaviour_Automata behaviour_automata;
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
		this.dst_x=-1;
		this.dst_y=-1;
		}
	
	public Human(int food, int ressource, Sex sex, int culture) {
		this();
		this.food = food;
		this.ressource = ressource;
		this.sex = sex;
		this.culture = culture;
	}
	
	public Human offspring() {
		//System.out.println("birth" + this.tribe.getSize());
		Human offspring = new Human();
		//TODO add mutation factor on culture
		offspring.culture = this.culture;
		
		offspring.tribe = this.tribe;
		this.tribe.living_humans.add(offspring);
		
		offspring.currentCase = this.currentCase;
		currentCase.humans.add(offspring);
		//this.tribe.mutate_autos();
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
		this.dieCheck();
		if(this.currentCase.settlement_present())
			energy += settlement_energy_loss_advantage;
		energy -= (age/100);
		while(energy<=MUST_EAT && food>0){
			food-= FOOD_EAT;
			energy += ENERGY_RESTITUTION;
		}
		this.dieCheck();
	}
	
	private void dieCheck() {
		if(energy<=0){//DIE
			DataManagement.killHuman(this);
			this.currentCase.humans.remove(this);
			if(this.food > 0)
				DataManagement.terrain.getCase(x, y).corpse_food += this.food;
			this.food=0;
			if(this.ressource > 0)
				DataManagement.terrain.getCase(x, y).corpse_ressource += this.ressource;
			this.tribe.living_humans.remove(this);
		}
	}
	
	public void move(){
		boolean random = this.random_destination();
		this.currentCase.humans.remove(this);
		Vector v = this.find_shortest_path_to_dst();
		//System.out.println("Human: Moving: actual ("+ this.x + "," + this.y +") ");
		//System.out.println("DST: ("+ this.dst_x +","+ this.dst_y +",RNG?" + random +") ");
		//System.out.println("DirVec:(" + v.x + "," + v.y +")" );
		this.x = (int)v.x+this.x;
		this.y = (int)v.y+this.y;
		//System.out.println("Human moving: new pos: (" + this.x +", "+ this.y +")" );
		if(x<0)
			x = DataManagement.TerrainGridX-1;
		else if(x == DataManagement.TerrainGridX)
			x=0;
		if(y<0)
			y = DataManagement.TerrainGridY-1;
		else if(y == DataManagement.TerrainGridX)
			y=0;
		this.currentCase = DataManagement.terrain.getCase(x, y);
		this.currentCase.humans.add(this);
		if(random)
			this.reset_random_dst();
	}
	
	private Vector find_shortest_path_to_dst() {
		Vector r = new Vector();
		int dX_direct = this.dst_x - this.x;
		int dX_reverse = this.dst_x - (this.x + DataManagement.TerrainGridX);
		if(Math.abs(dX_direct) < Math.abs(dX_reverse))
			r.x = dX_direct;
		else
			r.x = dX_reverse;
		int dy_direct = this.dst_y - this.y;
		int dy_reverse = this.dst_y - (this.y + DataManagement.TerrainGridY);
		if(Math.abs(dy_direct) < Math.abs(dy_reverse))
			r.y = dy_direct;
		else
			r.y = dy_reverse;
		
		if(r.x<0)
			r.x = -1;
		else
			r.x = 1;
		
		if(r.y<0)
			r.y=-1;
		else
			r.y=1;
		/*System.out.println("POS: ( " + this.x + "," + this.y + ")");
		System.out.println("DST: (" + this.dst_x + "," + this.dst_y + "(");
		System.out.println("Deltas: direct: ( "+ dX_direct + "," + dy_direct + ")");
		System.out.println("Deltas: reverse: ("+ dX_reverse + "," + dy_reverse +")");
		System.out.println("R: (" + r.x + "," + r.y +")");*/
		return r;
	}
	
	private void update_tribe_frame_datas() {
		if(this.energy>=0) {
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
	
	private boolean random_destination() {
		if(this.dst_x == -1 && this.dst_y ==-1)
		{
			this.dst_x = local_random.nextInt(DataManagement.TerrainGridX);
			this.dst_y = local_random.nextInt(DataManagement.TerrainGridY);
			return true;
		}
		return false;
	}
	
	private void reset_random_dst() {
		this.dst_x=-1;
		this.dst_y=-1;
	}
	public boolean parenting_ok(Human h){
		if(this.currentCase.humans.size()> 10)
			return false;
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
