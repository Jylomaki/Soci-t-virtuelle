package genome.transition;

import java.util.Random;

import terrain.Terrain_Value;
import agent.Agent_Value;
import agent.Human;
import global.Mutable;
import global.Randomized;

public class Expr extends Randomized implements Mutable{
	private enum Type{
		OP_ADD,
		OP_SUB,
		OP_MULT,
		OP_DIV,
		VALUE_INTEGER,
		VALUE_AGENT,
		VALUE_TERRAIN,
	}
	static int type_count=7;
	
	private boolean has_mutated;
	
	Type type;
	Expr expr1, expr2;
	Agent_Value agent_Value;
	Terrain_Value terrain_Value;
	int value;
	
	public Expr(){
		type = this.next_Type();
		if(type == Type.VALUE_INTEGER)
			value = random.nextInt();
		this.generate_Adequate();
	}
	
	public int evaluate(Human agent) {
		switch(type){
		case VALUE_INTEGER:
			return value;
		case OP_ADD:
			return expr1.evaluate(agent) + expr2.evaluate(agent);
		case OP_DIV:
			return expr1.evaluate(agent) / expr2.evaluate(agent);
		case OP_MULT:
			return expr1.evaluate(agent) * expr2.evaluate(agent);
		case OP_SUB:
			return expr1.evaluate(agent) - expr2.evaluate(agent);
		//TODO
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
		has_mutated= false;
		
		if(random.nextInt(maxR) < treshold){
			//change type
			type = next_Type(type);
			has_mutated = true;
		}
		generate_Adequate();
		if(!is_static(type)){
			has_mutated |= expr2.mutate(treshold, maxR);
			has_mutated |= expr1.mutate(treshold, maxR);
		}
		
		else if(type == Type.VALUE_INTEGER){
			if(random.nextInt(maxR) < treshold){
				value = random.nextInt();
				has_mutated = true;
			}
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
	
	private boolean is_static(Type t){
		switch(t){
		case VALUE_AGENT:
		case VALUE_TERRAIN:
		case VALUE_INTEGER:
			return true;
		default:
			return false;
		}
	}
	
	private void generate_Adequate() {
		if(! is_static(this.type)){
			if(expr1 == null){
				expr1 = new Expr();
				expr2 = new Expr();
			}
		}
	}

}
