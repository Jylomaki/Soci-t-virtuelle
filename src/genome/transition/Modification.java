package genome.transition;

import agent.Human;
import global.Mutable;
import global.Randomized;

public class Modification extends Randomized implements Mutable{
	Expr expr;
	Integer id;
	Modification next_mod;
	
	void execute(Human agent){
		agent.genomalVariables.set(id, expr.evaluate(agent));
		if( next_mod != null)
			next_mod.execute(agent);
	}

	@Override
	public boolean mutate(int treshold, int maxR) {
		// TODO Auto-generated method stub
		return false;
	}
}
