package terrain;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import action.Action;
import agent.Human;

public class Case {

	public enum TypeCase{
		EMPTY,
		FOOD,
		RESSOURCE,
		FR
	};
	
	private Color color;
	private TypeCase type;
	
	public ArrayList<Human> humans;
	public int food;
	public int ressource;
	public int settlement;
	public int corpse_food;
	public int corpse_ressource;
	public boolean corpse_presence;
	
	private static int foodMax = 100;
	private static int ressourceMax = 100;
	private static int presence_threshold = 5;
	private static int childrenGatherRate = 5;

	
	
	
	static final int percentageFood = global.Global_variables.percentageFood;
	static final int percentageRessource = global.Global_variables.percentageRessource;
	static final int percentageFR = global.Global_variables.percentageBoth;
	
	public Case(){
		Random random = new Random();
		int rand = random.nextInt(foodMax);
		if(rand<=percentageFood){
			food = random.nextInt(foodMax);
			color = new Color(0,food,0);
			type = TypeCase.FOOD;
		}else if(rand<=percentageFood+percentageRessource){
			ressource = random.nextInt(ressourceMax);
			color = new Color(0,0,ressource);
			type = TypeCase.RESSOURCE;
		}else if(rand<=percentageFood+percentageRessource+percentageFR){
			food = random.nextInt(foodMax);
			ressource = random.nextInt(ressourceMax);
			color = new Color(0,food,ressource);
			type = TypeCase.FR;
		}else{
			color = Color.black;
			food = 0;
			ressource = 0;
			type = TypeCase.EMPTY;
		}
		this.humans = new ArrayList<Human>();
	}

	public void update(){
		this.update_corpse_presence();
		switch(type){
		case FOOD:
			if(food<foodMax){
				food++;
				color = new Color(0,Math.max(food,0),0);
			}
			break;
		case RESSOURCE:
			if(ressource<ressourceMax){
				ressource++;
				color = new Color(0,0,Math.max(ressource,0));
			}
			break;
		case FR:
			if(food<foodMax){
				food++;
			}
			if(ressource<ressourceMax){
				ressource++;
				
			}
			color = new Color(0,Math.max(food,0),Math.max(ressource,0));
			break;
		case EMPTY:
			break;
		}
		if(this.settlement_present()){
			this.settlement -= global.Global_variables.settlement_decay_rate;
		}
		this.execute_human_actions();
	}
	
	public void execute_human_actions(){
		if(this.humans == null){
			System.out.println("Case:execute_human_action: Human list null: passing by");
			return;
		}
		int i=0;
		for(int j=0; j<humans.size(); j++){
			//System.out.println("CASE: Updated a human, human on case:" + humans.size());
			humans.get(j).update();
		}
		if(humans.size()>0)
			collect_corpse();
		
		while(i< this.humans.size()) {
			if(this.humans.size()-i >=2) {
				// make humans i and i+1 interact
				//Varning! be careful of vhen human moves from case to cases.
				//System.out.println("Case: executing a human interaction");
				Human h1 = this.humans.get(i);
				h1.comStatus = Human.Communication_Status.BEGIN;
				Human h2 = this.humans.get(i);
				h1.interlocutor = h2;
				h2.interlocutor = h1;
				boolean ignored_both;
				int interaction_count = 0;
				do{
					Action.Type a1 = h1.tribe.get_adequate_Automata(h1, true).evaluate(h1);
					Action.execute_action(a1, h1, h2, this);
					h2.comStatus = Action.correspondingStatus(a1);
					
					Action.Type a2 = h2.tribe.get_adequate_Automata(h2, true).evaluate(h2);
					Action.execute_action(a2, h2, h1, this);
					h1.comStatus = Action.correspondingStatus(a2);

					ignored_both= Action.actionIgnoring(a1) && Action.actionIgnoring(a2);
					
				}while(!ignored_both && interaction_count++ < global.Global_variables.interaction_max);
				//then next
				i+=2;
			}
			else {
				// make human do solo action
				//System.out.println("Case: executing a human solo action");
				Human h = this.humans.get(i);
				Action.Type a = h.tribe.get_adequate_Automata(h, false).evaluate(h);
				Action.execute_action(a, h, null, this);
				i++;
			}
			//System.out.println("Case: executing human_action: done i:" + i);
		}
	}
	
	private void collect_corpse(){
		humans.get(0).collecte_corpse(this.corpse_food, this.corpse_ressource);
		this.corpse_food=0;
		this.corpse_ressource=0;
	}
	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public TypeCase getType() {
		return type;
	}

	public void setType(TypeCase type) {
		this.type = type;
		switch(type){
			case FOOD:
				color = new Color(0,100,0);
			break;
			case RESSOURCE:
				color = new Color(0,0,100);
			break;
			case FR:
				color = new Color(0,100,100);
			break;
			case EMPTY:
				color = Color.black;
			break;
		}
	}

	public int gatherFood(){
		if(food > 0){
			int retour = food;
			if(this.settlement_present())
				;//retour += settlement_food_gather_advantage;
			food -= foodMax;
			color = new Color(0,Math.max(food,0),Math.max(ressource,0));
			return retour;
		}
		return 0;
	}
	
	public int childGatherFood(){
		if(food > 0){
			int retour = childrenGatherRate;
			food -= childrenGatherRate;
			color = new Color(0,Math.max(food,0),Math.max(ressource,0));
			return retour;
		}
		return 0;
	}
	
	public int gatherRessource(){
		if(ressource>0){
			int retour = ressource;
			ressource -= ressourceMax;
			color = new Color(0,Math.max(food,0),Math.max(ressource,0));
			return retour;
		}
		return 0;
	}
	
	public int childGatherRessource(){
		if(ressource>0){
			int retour = childrenGatherRate;
			ressource -= childrenGatherRate;
			color = new Color(0,Math.max(food,0),Math.max(ressource,0));
			return retour;
		}
		return 0;
	}
	
	
	public boolean settlement_present() {
		return this.settlement>presence_threshold;
	}

	public boolean ressource_present() {
		return ressource > presence_threshold;

	}

	public boolean food_present() {
		return food > presence_threshold;

	}

	public void update_corpse_presence(){
		if(this.corpse_presence){
			this.corpse_food = Math.min(this.corpse_food-global.Global_variables.corpse_decay_rate, 0);
			this.corpse_ressource = Math.min(this.corpse_ressource-global.Global_variables.corpse_decay_rate, 0);
		}
		this.corpse_presence = this.corpse_food>0 || this.corpse_ressource>0;
	}
	
	public boolean human_presence(){
		return this.humans.size()>=2;
	}
	public boolean corpse_present() {
		return this.corpse_presence;

	}
	
	
	
}
