package data;

import action.Action;

public class Frame_data {
	public int actions_performed[];
	public int interaction_performed[];
	public int soloaction_performed[];
	
	public int ignorance_on_com;
	
	public int food;
	public int ressource;
	public int craft_and_settlement;
	public int tribus_size;
	
	public Frame_data(){
		this.actions_performed = new int[Action.all_action_max];
		this.interaction_performed = new int[Action.interaction_max];
		this.soloaction_performed = new int[Action.solo_action_max];
	}
	
	public void reset() {
		this.food=0;
		this.ressource=0;
		this.craft_and_settlement=0;
		this.tribus_size=0;
		this.ignorance_on_com=0;
		
		for(int i=0; i<Action.all_action_max; i++)
			this.actions_performed[i]=0;
		
		for(int i=0; i<Action.solo_action_max; i++)
			this.soloaction_performed[i]=0;
		
		for(int i=0; i<Action.interaction_max; i++)
			this.interaction_performed[i]=0;
	}
}
