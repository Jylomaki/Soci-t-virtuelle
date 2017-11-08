package terrain;

import java.awt.Color;
import java.util.Random;

public class Case {

	public enum TypeCase{
		EMPTY,
		FOOD,
		RESSOURCE,
		FR
	};
	
	private Color color;
	private TypeCase type;
	
	static final int percentageFood = 5;
	static final int percentageRessource = 5;
	static final int percentageFR = 1;
	
	public Case(){
		Random random = new Random();
		int rand = random.nextInt(100);
		if(rand<=percentageFood){
			color = new Color(0,100,0);
			type = TypeCase.FOOD;
		}else if(rand<=percentageFood+percentageRessource){
			color = new Color(0,0,100);
			type = TypeCase.RESSOURCE;
		}else if(rand<=+percentageFood+percentageRessource+percentageFR){
			color = new Color(0,100,100);
			type = TypeCase.FR;
		}else{
			color = Color.black;
			type = TypeCase.EMPTY;
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

	public boolean settlement_present() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean ressource_present() {
		// TODO Auto-generated method stub
		return false;

	}

	public boolean food_present() {
		// TODO Auto-generated method stub
		return false;

	}

	public boolean corpse_present() {
		// TODO Auto-generated method stub
		return false;

	}
	
	
	
}
