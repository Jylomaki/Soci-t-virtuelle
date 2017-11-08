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
	
	private enum Meta_Type{
		BOOL2,
		BOOL1,
		BOOL0,
		CMP
	}
	
	Type type;
	Com_Type com_Type;
	Cond cond1,cond2;
	Expr expr1,expr2;
	private boolean has_mutated;
	private boolean handle_communication;
	
	
	public Cond(){
		type = this.next_Type();
		this.generate_Adequate();
	}
	
	public Cond(boolean communication) {
		this.handle_communication = communication;
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
			//TODO ADD COMMUNICATION COND
		case COMMUNICATION:
			switch(this.com_Type) {
			case HAS_BEEN_HURT:
				return agent.comStatus == agent.comStatus.HURTED;
			case HAS_RECEIVED_FOOD:
				return agent.comStatus == agent.comStatus.GIVEN_FOOD;
			case HAS_RECEIVED_RESSOURCE:
				return agent.comStatus == agent.comStatus.GIVEN_RESSOURCE;
			case IS_FIRST:
				return agent.comStatus == agent.comStatus.BEGIN;
			case LIKE:
				return agent.does_like_interlocutor();
			default:
				break;
			
			}
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
		if(this.handle_communication)
			return Type.values()[random.nextInt(Type.values().length)];
		return Type.values()[random.nextInt(Type.values().length)-1];
	}
	
	private Type next_Type(Type t) {
		Type next = next_Type();
		while(next == t)
			next = next_Type();
		return next;
	}
	
	private Meta_Type meta_Type(Type t){
		switch(t){
		case LOWER_OR_EQ:
		case HIGHER:
			return Meta_Type.CMP;
		case TRUE:
		case FALSE:
		case COMMUNICATION: //simple check on communication state
			return Meta_Type.BOOL0;
		case NOT:
			return Meta_Type.BOOL1;
		default:
			return Meta_Type.BOOL2;
		}
	}
	
	private void generate_Adequate(){
		if(type == Type.COMMUNICATION)
			this.com_Type = Com_Type.values()[random.nextInt(Com_Type.values().length)];
		else {
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

	public Cond clone(){
		switch(this.meta_Type(this.type)){
		case BOOL0:
			return new Cond(this.type, this.com_Type, null, null, null, null, this.handle_communication);
		case BOOL1:
			return new Cond(this.type, this.com_Type, this.cond1.clone(), null, null, null, this.handle_communication);
		case BOOL2:
			return new Cond(this.type, this.com_Type, this.cond1.clone(), this.cond2.clone(), null, null, this.handle_communication);
		case CMP:
			return new Cond(this.type, this.com_Type, null, null, this.expr1.clone(), this.expr2.clone(), this.handle_communication);
		default:
			break;
		
		}
		System.err.println("Cond clone: couldn't clone");
		return null;
	}
	public Cond(Type type, Com_Type com_Type, Cond cond1, Cond cond2, Expr expr1, Expr expr2,
			boolean handle_communication) {
		super();
		this.type = type;
		this.com_Type = com_Type;
		this.cond1 = cond1;
		this.cond2 = cond2;
		this.expr1 = expr1;
		this.expr2 = expr2;
		this.handle_communication = handle_communication;
	}
	
	
}
