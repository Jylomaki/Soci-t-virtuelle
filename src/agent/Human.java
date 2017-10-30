package agent;

import genome.*;

import java.util.ArrayList;

public class Human {
	public Behaviour_Automata behaviour_automata;
	public Communication_Automata communication_automata;
	public Genomal_Variables genomalVariables;
	
	public int id, group_id;
	public int energy, age, food, ressource;
	public Sex sex;
	public int culture;
	public ArrayList<Integer> liked_list;
	
	public int x,y;
	
	public void take_action(){
		//TODO
	}
}
