package genome.transition;

import agent.Human;
import global.Mutable;
import global.Randomized;

public class Cond extends Randomized implements Mutable{
	enum Type{
		AND,
		OR,
		NOT,
		LOWER_OR_EQ,
		HIGHER
	}
	
	Type type;
	Cond cond1,cond2;
	Expr expr1,expr2;
	
	boolean evaluate(Human agent){
		//TODO
		switch(type){
		case NOT:
			return !(cond1.evaluate(agent));
		case LOWER_OR_EQ:
			return expr1.evaluate(agent) <= expr2.evaluate(agent);
		case AND:
			break;
		case HIGHER:
			break;
		case OR:
			break;
		default:
			break;
		}
		return false;
	}

	@Override
	public boolean mutate(int treshold, int maxR) {
		// TODO Auto-generated method stub
		return false;
	}
}
