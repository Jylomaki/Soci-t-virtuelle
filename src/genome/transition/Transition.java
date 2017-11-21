package genome.transition;

import global.Mutable;
import global.Randomized;
import global.local_random;

public class Transition extends Randomized implements Mutable{
	public int starting_state;
	public int ending_state;
	public Cond cond;
	public Modification modifications;
	//TODO extends so it can handle communication *done*
	private boolean has_mutated;
	
	public Transition(int begin, int end, int treshold, int maxR , boolean handle_communication){
		//DONE
		this.starting_state = begin;
		this.ending_state = end;
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
		
		if(local_random.nextInt(maxR)< treshold){
			this.ending_state = local_random.nextInt(maxState);
			has_mutated = true;
		}
		
		return has_mutated;
	}

	
	public Transition clone(){
		return new Transition(this.starting_state, this.ending_state, this.cond.clone(), this.modifications.clone());
	}
	
	public Transition(int starting_state, int ending_state, Cond cond, Modification modifications) {
		super();
		this.starting_state = starting_state;
		this.ending_state = ending_state;
		this.cond = cond;
		this.modifications = modifications;
	}

	@Override
	public void print() {
		System.out.println("FROM: " + this.starting_state + " TO: " + this.ending_state);
		System.out.println("On condition: ");
		this.cond.print();
		
	}

	@Override
	@Deprecated
	public void print(String mise_forme) {
		// TODO Auto-generated method stub
		
	}
	
	
}
