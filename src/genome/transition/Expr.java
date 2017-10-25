package genome.transition;

import java.util.Random;

import terrain.Terrain_Value;
import agent.Agent_Value;
import agent.Human;
import global.Mutable;
import global.Randomized;

public class Expr extends Randomized implements Mutable{
	enum Type{
		OP_ADD,
		OP_SUB,
		OP_MULT,
		OP_DIV,
		VALUE_INTEGER,
		VALUE_AGENT,
		VALUE_TERRAIN,
	}
	
	Type type;
	Expr expr1, expr2;
	Agent_Value agent_Value;
	Terrain_Value terrain_Value;
	int value;
	
	
	
	public int evaluate(Human agent) {
		// TODO Auto-generated method stub
		switch(type){
		case VALUE_INTEGER:
			return value;
		case OP_ADD:
			return expr1.evaluate(agent) + expr2.evaluate(agent);
		case OP_DIV:
			break;
		case OP_MULT:
			break;
		case OP_SUB:
			break;
		case VALUE_AGENT:
			break;
		case VALUE_TERRAIN:
			break;
		default:
			break;
		}
		return 0;
	}



	@Override
	public boolean mutate(int treshold, int maxR) {
		// TODO Auto-generated method stub
		if(random.nextInt(maxR) < treshold){
			//change type
		}
		//mutate cond1/expr1
		//mutate cond2/expr2
		
		return false;
	}

}
