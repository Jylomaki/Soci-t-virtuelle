package agent;

import genome.*;
import global.Randomized;
import global.Vector;

import java.awt.Point;
import java.util.ArrayList;

import data.DataManagement;

public class Human extends Randomized{
	static final int MUST_EAT = 200;
	static final int ENERGY_RESTITUTION = 200;
	public Behaviour_Automata behaviour_automata;
	public Communication_Automata communication_automata;
	public Genomal_Variables genomalVariables;
	
	static long id_count=0;
	
	public long id, group_id;
	public int energy, age, food, ressource;
	public Sex sex;
	public int culture;
	public ArrayList<Integer> liked_list;
	
	public int x,y;
	public Vector dir;
	
	public Human(){
		dir = new Vector(100,100);
		id = id_count++;
	}
	
	public void take_action(){
		//TODO
	}
	
	//Reflexes actions and update Attribute
	public void update(){
		age++;
		//System.out.println(energy);
		energy--;
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
			x = DataManagement.TerrainGridX;
		if(y<0)
			y = DataManagement.TerrainGridY;
	}
	
	public void wiggle(){
		int tr = random.nextInt(50);
		int tl = -random.nextInt(50);
		dir.rotate(tr);
		dir.rotate(tl);
	}
}
