package terrain;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

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
	
	private static int foodMax = 100;
	private static int ressourceMax = 100;
	private static int presence_threshold = 5;
	private static int childrenGatherRate = 5;

	
	
	
	static final int percentageFood = 2;
	static final int percentageRessource = 2;
	static final int percentageFR = 1;
	
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
	}

	public void update(){
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
	}
	
	public void execute_human_actions(){
		
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

	public boolean corpse_present() {
		return this.corpse_food>0 || this.corpse_ressource>0;

	}
	
	
	
}
