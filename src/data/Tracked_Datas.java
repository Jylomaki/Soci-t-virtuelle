package data;
import java.util.ArrayList;

import action.Action;

public class Tracked_Datas {
	public ArrayList<ArrayList<Integer>> actions_performed;
	public ArrayList<ArrayList<Integer>> interaction_performed;
	public ArrayList<ArrayList<Integer>> soloaction_performed;
	
	public ArrayList<ArrayList<Integer>> actions_performed_per;
	public ArrayList<ArrayList<Integer>> interaction_performed_per;
	public ArrayList<ArrayList<Integer>> soloaction_performed_per;
	
	public ArrayList<Integer> ignorance_on_com;
	
	public ArrayList<Integer> nourriture;
	public ArrayList<Integer> ressource;
	public ArrayList<Integer> craft_and_settlement;
	public ArrayList<Integer> tribus_size;
	public ArrayList<Integer> fitness_score;
	
	
	public int last_frame=-1;
	
	public Tracked_Datas() {
		this.actions_performed = list_of_list_cons(Action.all_action_max);
		this.interaction_performed = list_of_list_cons(Action.interaction_max);
		this.soloaction_performed = list_of_list_cons(Action.solo_action_max);
		
		this.actions_performed_per = list_of_list_cons(Action.all_action_max);
		this.interaction_performed_per = list_of_list_cons(Action.interaction_max);
		this.soloaction_performed_per = list_of_list_cons(Action.solo_action_max);
		
		this.ignorance_on_com = list_cons();

		this.nourriture = list_cons();
		this.ressource = list_cons();
		this.craft_and_settlement = list_cons();
		this.tribus_size = list_cons();
		this.fitness_score = list_cons();

	}
	
	private static ArrayList<ArrayList<Integer>> list_of_list_cons(int sub_list_count){
		ArrayList<ArrayList<Integer>> retour = new ArrayList<ArrayList<Integer>>();
		for(int i=0; i<sub_list_count; i++)
			retour.add(new ArrayList<Integer>());
		return retour;
	}
	
	private static ArrayList<Integer> list_cons(){
		return new ArrayList<Integer>();
	}
	
	public void push_back(Frame_data frame) {
		int action_perf_sum=0;
		int inter_perf_sum=0;
		int solo_perf_sum=0;
		for(int i=0; i<Action.all_action_max; i++){
			this.actions_performed.get(i).add(frame.actions_performed[i]);
			action_perf_sum += frame.actions_performed[i];
		}
		for(int i=0; i<Action.interaction_max; i++){
			this.interaction_performed.get(i).add(frame.interaction_performed[i]);
			inter_perf_sum += frame.interaction_performed[i];
		}
		for(int i=0; i<Action.solo_action_max; i++){
			this.soloaction_performed.get(i).add(frame.soloaction_performed[i]);
			solo_perf_sum += frame.soloaction_performed[i];
		}
		
		for(int i=0; i<Action.all_action_max; i++){
			if(action_perf_sum !=0)
				this.actions_performed_per.get(i).add(frame.actions_performed[i]*1000/action_perf_sum);
			else
				this.actions_performed_per.get(i).add(0);
		}
	
		for(int i=0; i<Action.interaction_max; i++){
			if(inter_perf_sum !=0)
				this.interaction_performed_per.get(i).add(frame.interaction_performed[i]*1000/inter_perf_sum);
			else
				this.interaction_performed_per.get(i).add(0);

		}
		
		
		for(int i=0; i<Action.solo_action_max; i++){
			if(solo_perf_sum!=0)
				this.soloaction_performed_per.get(i).add(frame.soloaction_performed[i]*1000/solo_perf_sum);
			else
				this.soloaction_performed_per.get(i).add(0);
		}
		
		
		this.nourriture.add(frame.food);
		this.ressource.add(frame.ressource);
		this.tribus_size.add(frame.tribus_size);
		this.fitness_score.add(frame.fitness_score);
		this.craft_and_settlement.add(frame.craft_and_settlement);
		this.ignorance_on_com.add(frame.ignorance_on_com);
		this.last_frame++;
	}

	public void printLastFrame() {
		System.out.println("Action performed:");
		for(int i=0; i<Action.all_action_max; i++) {
			System.out.println("("+Action.to_action_type(i)+ 
					", " + this.actions_performed.get(i).get(this.last_frame)+ ")");
		}
	}
}


