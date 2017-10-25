package genome.transition;

import agent.Human;
import global.Mutable;
import global.Randomized;

public class Modification extends Randomized implements Mutable{
	private enum Type{
		EQ,
		INCR
	}
	
	private static int max_Id=64;
	
	Type type;
	Expr expr;
	Integer id;
	Modification next_mod;
	
	boolean has_mutated;
	
	public Modification (int treshold, int maxR){
		if(random.nextInt(maxR)< treshold){
			id = random.nextInt(max_Id);
			expr = new Expr();
			next_mod = new Modification(treshold, maxR);
		}
		else
			id = -1;
	}
	
	public void execute(Human agent){
		if(id>=0){
			agent.genomalVariables.set(id, expr.evaluate(agent));
			next_mod.execute(agent);
		}
	}

	@Override
	public boolean mutate(int treshold, int maxR) {
		has_mutated = false;
		if(random.nextInt(maxR)< treshold){
			id = random.nextInt(max_Id);
			has_mutated = true;
		}
		
		if(random.nextInt(maxR)< treshold){
			switch(type){
			case EQ:
				type = Type.INCR;
				break;
			case INCR:
				type = Type.EQ;
				break;
			default:
				break;
			
			}
			has_mutated = true;
		}
		
		return has_mutated || expr.mutate(treshold, maxR);
	}
}
