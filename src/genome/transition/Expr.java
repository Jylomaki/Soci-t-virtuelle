package genome.transition;

import java.util.Random;

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
	}
	static int type_count=7;
	
	private boolean has_mutated;
	
	Type type;
	Expr expr1, expr2;
	Agent_Value agent_Value;
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
		case VALUE_AGENT:
			switch(this.agent_Value){
			case AGE:
				return agent.age;
			case ENERGY:
				return agent.energy;
			case FOOD:
				return agent.food;
			case RESSOURCE:
				return agent.ressource;
			case SEXE:
				switch(agent.sex){
				case CHILDREN_1:
					return 3;
				case CHILDREN_2:
					return 4;
				case S1:
					return 1;
				case S2:
					return 2;
				default:
					break;
				
				}
			default:
				break;
			
			}
		default:
			break;
		}
		System.err.println("Expr: could not resolve type");
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
	
	public Expr clone(){
		switch(this.type){
		case OP_ADD:
		case OP_DIV:
		case OP_MULT:
		case OP_SUB:
			return new Expr(this.type, this.expr1.clone(), this.expr2.clone(), null, 0);
		case VALUE_AGENT:
			return new Expr(this.type, null,null,this.agent_Value, 0);
		case VALUE_INTEGER:
			return new Expr(this.type, null, null, null, this.value);
		default:
			return null;
		
		}
	}

	public Expr(Type type, Expr expr1, Expr expr2, Agent_Value agent_Value, int value) {
		super();
		this.type = type;
		this.expr1 = expr1;
		this.expr2 = expr2;
		this.agent_Value = agent_Value;
		this.value = value;
	}

}
