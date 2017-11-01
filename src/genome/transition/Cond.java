package genome.transition;

import agent.Human;
import global.Mutable;
import global.Randomized;

public class Cond extends Randomized implements Mutable{
	private enum Type{
		AND,
		OR,
		NOT,
		LOWER_OR_EQ,
		HIGHER,
		TRUE,
		FALSE,
		COMMUNICATION
	}
	
	private enum Com_Type{
		HAS_BEEN_HURT,
		HAS_RECEIVED_FOOD,
		HAS_RECEIVED_RESSOURCE,
		IS_FIRST,
		LIKE
	}
	//TODO extends so it can handle communication or non-communication as in ignoring
	private enum Meta_Type{
		BOOL2,
		BOOL1,
		BOOL0,
		CMP
	}
	
	Type type;
	Cond cond1,cond2;
	Expr expr1,expr2;
	private boolean has_mutated;
	private static int type_count= 5;
	
	
	public Cond(){
		type = this.next_Type();
		this.generate_Adequate();
	}
	
	public boolean evaluate(Human agent){
		switch(type){
		case NOT:
			return !(cond1.evaluate(agent));
		case LOWER_OR_EQ:
			return expr1.evaluate(agent) <= expr2.evaluate(agent);
		case AND:
			return cond1.evaluate(agent) && cond2.evaluate(agent);
		case HIGHER:
			return expr1.evaluate(agent) > expr2.evaluate(agent);
		case OR:
			return cond1.evaluate(agent) || cond2.evaluate(agent);
		case FALSE:
			return false;
		case TRUE:
			return true;
		default:
			break;
		}
		return false;
	}

	@Override
	public boolean mutate(int treshold, int maxR) {
		has_mutated= false;
		if(random.nextInt(maxR) < treshold){
			//change type
			type = next_Type(type);
			has_mutated = true;
		}
		generate_Adequate();
		switch( meta_Type(type)){
		case BOOL1:
			return this.has_mutated || cond1.mutate(treshold, maxR);
		case BOOL2:
			return this.has_mutated || cond1.mutate(treshold, maxR) || cond2.mutate(treshold, maxR);
		case CMP:
			return this.has_mutated || expr1.mutate(treshold, maxR) || expr2.mutate(treshold, maxR);
		case BOOL0:
			break;
		default:
			break;
			
		}
		return has_mutated;
	}
	
	private Type next_Type(){
		return Type.values()[random.nextInt(type_count)];
	}
	
	private Type next_Type(Type t){
		Type next = Type.values()[random.nextInt(type_count)];
		while(next == t)
			next = Type.values()[random.nextInt(type_count)];
		return next;
	}
	
	private Meta_Type meta_Type(Type t){
		switch(t){
		case LOWER_OR_EQ:
		case HIGHER:
			return Meta_Type.CMP;
		case TRUE:
		case FALSE:
			return Meta_Type.BOOL0;
		case NOT:
			return Meta_Type.BOOL1;
		default:
			return Meta_Type.BOOL2;
		}
	}
	
	private void generate_Adequate(){
		switch( meta_Type(type)){
		case BOOL1:
			if(cond1 == null);
				cond1 = new Cond();
			break;
		case BOOL2:
			if(cond1 == null);
				cond1 = new Cond();
			if(cond2 == null);
				cond2 = new Cond();
			break;
		case CMP:
			if(expr1 == null);
				expr1 = new Expr();
			if(expr2 == null);
				expr2 = new Expr();
			break;
		case BOOL0:
			break;
		default:
			break;
			
		}
	}
}
