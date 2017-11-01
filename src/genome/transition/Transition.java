package genome.transition;

import global.Mutable;
import global.Randomized;

public class Transition extends Randomized implements Mutable{
	public int starting_state;
	public int ending_state;
	public Cond cond;
	public Modification modifications;
	//TODO extends so it can handle communication
	private boolean has_mutated;
	
	public Transition(int begin, int end, int treshold, int maxR , boolean handle_communication){
		//DONE
		cond = new Cond();
		modifications = new Modification(treshold, maxR);
	}
	@Override
	@Deprecated
	public boolean mutate(int treshold, int maxR) {
		has_mutated = false;
		has_mutated |= cond.mutate(treshold, maxR);
		has_mutated |= modifications.mutate(treshold, maxR);
		return has_mutated;
	}
	
	
	public boolean mutate(int treshold, int maxR, int maxState) {
		has_mutated = false;
		has_mutated |= cond.mutate(treshold, maxR);
		has_mutated |= modifications.mutate(treshold, maxR);
		
		if(random.nextInt(maxR)< treshold){
			this.ending_state = random.nextInt(maxState);
			has_mutated = true;
		}
		
		return has_mutated;
	}
}
