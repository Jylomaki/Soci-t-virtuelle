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
	//public ArrayList<Corpse> corpses;
	public int food;
	public int ressource;
	
	
	
	static final int percentageFood = 2;
	static final int percentageRessource = 2;
	static final int percentageFR = 1;
	
	public Case(){
		Random random = new Random();
		int rand = random.nextInt(100);
		if(rand<=percentageFood){
			food = random.nextInt(100);
			color = new Color(0,food,0);
			type = TypeCase.FOOD;
		}else if(rand<=percentageFood+percentageRessource){
			ressource = random.nextInt(100);
			color = new Color(0,0,ressource);
			type = TypeCase.RESSOURCE;
		}else if(rand<=percentageFood+percentageRessource+percentageFR){
			food = random.nextInt(100);
			ressource = random.nextInt(100);
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
			if(food<100){
				food++;
				color = new Color(0,Math.max(food,0),0);
			}
		break;
		case RESSOURCE:
			if(ressource<100){
				ressource++;
				color = new Color(0,0,Math.max(ressource,0));
			}
		break;
		case FR:
			if(food<100){
				food++;
			}
			if(ressource<100){
				ressource++;
				
			}
			color = new Color(0,Math.max(food,0),Math.max(ressource,0));
		break;
		case EMPTY:
			
		break;
	}
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

	public void gatherFood(){
		food -= 100;
		color = new Color(0,Math.max(food,0),Math.max(ressource,0));
	}
	
	public void gatherRessource(){
		ressource -= 100;
		color = new Color(0,Math.max(food,0),Math.max(ressource,0));
	}
	
	public boolean settlement_present() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean ressource_present() {
		// TODO Auto-generated method stub
		return ressource > 0;

	}

	public boolean food_present() {
		// TODO Auto-generated method stub
		return food > 0;

	}

	public boolean corpse_present() {
		// TODO Auto-generated method stub
		return false;

	}
	
	
	
}
