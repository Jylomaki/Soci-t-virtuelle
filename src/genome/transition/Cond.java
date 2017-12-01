package genome.transition;

import agent.Human;
import agent.Human.Communication_Status;
import global.Mutable;
import global.Randomized;
import global.local_random;
import terrain.Terrain_Value;

public class Cond extends Randomized implements Mutable{
	private enum Type{
		LOWER_OR_EQ,
		HIGHER,
		AND,
		OR,
		NOT,
		TRUE,
		FALSE,
		COMMUNICATION,
		TERRAIN_VALUE
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
	
	static int depth=0;
	Type type;
	Terrain_Value terrain_value;
	Com_Type com_Type;
	Cond cond1,cond2;
	Expr expr1,expr2;
	private boolean has_mutated;
	private boolean handle_communication;
	
	public Cond(){
		//System.out.println("Cond generated: " + Randomized.generated++);
		type = this.next_Type();
		this.generate_Adequate();
	}
	
	public Cond(boolean communication) {
		this.handle_communication = communication;
		type = this.next_Type();
		this.generate_Adequate();
			
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
			// done
		case COMMUNICATION:
			switch(this.com_Type) {
			case HAS_BEEN_HURT:
				return agent.comStatus == Communication_Status.HURTED;
			case HAS_RECEIVED_FOOD:
				return agent.comStatus == Communication_Status.GIVEN_FOOD;
			case HAS_RECEIVED_RESSOURCE:
				return agent.comStatus == Communication_Status.GIVEN_RESSOURCE;
			case IS_FIRST:
				return agent.comStatus == Communication_Status.BEGIN;
			case LIKE:
				return agent.does_like_interlocutor();			
			}
		case TERRAIN_VALUE:
			switch(this.terrain_value){
			case CORPSE:
				return agent.currentCase.corpse_present();
			case FOOD:
				return agent.currentCase.food_present();
			case RESSOURCE:
				return agent.currentCase.ressource_present();
			case SETTLEMENT:
				return agent.currentCase.settlement_present();
			case OTHER_HUMAN:
				return agent.currentCase.human_presence();
			}
		}
		System.err.println("Cond: could not resolve type");
		return false;
	}

	@Override
	public boolean mutate(int treshold, int maxR) {
		has_mutated= false;
		if(local_random.nextInt(maxR) < treshold){
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
		this.simplify();
		return has_mutated;
	}
	
	public void simplify() {
		if(cond1 != null)
			this.cond1.simplify();
		if(this.cond2 !=null)
			this.cond2.simplify();	
		
		switch(this.type) {
		case AND:
			if(this.cond1.type == Type.FALSE || this.cond2.type == Type.FALSE)
				this.type = Type.FALSE;
			break;
		case HIGHER:
			break;
		case LOWER_OR_EQ:
			break;
		case NOT:
			if(this.cond1.type == Type.FALSE){
				this.type = Type.TRUE;
			}
			else if(this.cond1.type == Type.TRUE) {
				this.type = Type.FALSE;
			}
			break;
		case OR:
			if(this.cond1.type == Type.TRUE || this.cond2.type == Type.TRUE)
				this.type = Type.TRUE;
			break;
		default:
			break;
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

	private Type next_Type(){
		int currentType =0;
		int permil = local_random.nextInt(1000);
		while(permil > this.typePermil(Type.values()[currentType])) {
			permil -= this.typePermil(Type.values()[currentType]);
			currentType++;
		}
		Type r = Type.values()[currentType];
		return r;
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
			this.com_Type = Com_Type.values()[local_random.nextInt(Com_Type.values().length)];
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
	
	private int typePermil(Type t) {
		switch(this.meta_Type(t)) {
		case BOOL0:
			return 500;
		case BOOL1:
			return 200;
		case BOOL2:
			return 150;
		case CMP:
			return 150;
		default:
			return 1000;

		}
	}
	
	public void print() {
		switch(type) {
		case AND:
			System.out.println("(AND");
			this.cond1.print("#->");
			this.cond2.print("#->");
			System.out.println(")");
			break;
		case COMMUNICATION:
			System.out.println(this.com_Type);
			break;
		case FALSE:
			System.out.println("FALSE");
			break;
		case HIGHER:
			System.out.println("( HIGHER");
			this.expr1.print("#->");
			this.expr2.print("#->");
			System.out.println(")");
			break;
		case LOWER_OR_EQ:
			System.out.println("( LOWER_OR_EQ");
			this.expr1.print("#->");
			this.expr2.print("#->");
			System.out.println(")");
			break;
		case NOT:
			System.out.println("( NOT");
			this.cond1.print("#->");
			System.out.println(")");
			break;
		case OR:
			System.out.println("( OR");
			this.cond1.print("#->");
			this.cond2.print("#->");
			System.out.println(")");
			break;
		case TERRAIN_VALUE:
			System.out.println("(" + this.terrain_value + ")");
			break;
		case TRUE:
			System.out.println("( TRUE )");
			break;
		default:
			break;
		}
	}
	
	public void print(String s) {
		switch(type) {
		case AND:
			System.out.println(s+" AND");
			this.cond1.print("#--"+s);
			this.cond2.print("#--"+s);
			break;
		case COMMUNICATION:
			System.out.print(s+" " +this.com_Type.toString());
			break;
		case FALSE:
			System.out.println(s+" FALSE");
			break;
		case HIGHER:
			System.out.println(s+" HIGHER");
			this.expr1.print("#--"+s);
			this.expr2.print("#--"+s);
			break;
		case LOWER_OR_EQ:
			System.out.println(s+" LOWER_OR_EQ");
			this.expr1.print("#--"+s);
			this.expr2.print("#--"+s);
			break;
		case NOT:
			System.out.println(s+" NOT");
			this.cond1.print("#--"+s);
			break;
		case OR:
			System.out.println(s+" OR");
			this.cond1.print("#--"+s);
			this.cond2.print("#--"+s);
			break;
		case TERRAIN_VALUE:
			System.out.println(s+" " + this.terrain_value.toString());
			break;
		case TRUE:
			System.out.println(s+" TRUE");
			break;
		default:
			break;
		}
	}
}
