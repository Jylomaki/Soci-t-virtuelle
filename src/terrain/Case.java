package terrain;

import java.awt.Color;
import java.util.Random;

public class Case {

	enum Type{
		EMPTY,
		FOOD,
		RESSOURCE,
		FR
	};
	
	private Color color;
	private Type type;
	
	static final int percentageFood = 5;
	static final int percentageRessource = 5;
	static final int percentageFR = 1;
	
	public Case(){
		Random random = new Random();
		int rand = random.nextInt(100);
		if(rand<=percentageFood){
			color = new Color(0,100,0);
			type = Type.FOOD;
		}else if(rand<=percentageFood+percentageRessource){
			color = new Color(0,0,100);
			type = Type.RESSOURCE;
		}else if(rand<=+percentageFood+percentageRessource+percentageFR){
			color = new Color(0,100,100);
			type = Type.FR;
		}else{
			color = Color.black;
			type = Type.EMPTY;
		}
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}
	
	
	
}
