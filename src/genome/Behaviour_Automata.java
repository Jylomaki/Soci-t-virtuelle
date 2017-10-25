package genome;

import genome.transition.Transition;
import global.Mutable;
import global.Randomized;

import java.util.ArrayList;

import agent.Action;
import agent.Human;

public class Behaviour_Automata extends Randomized implements Mutable {
	ArrayList<Transition> automata;
	int maxNode;
	
	
	/**
	 * 
	 */
	public Behaviour_Automata(int treshold, int maxR) {
		super();
		maxNode = Action.values().length+1;
		automata = new ArrayList<Transition>();
		while(random.nextInt(maxR)<treshold)
			maxNode++;
		automata.add(new Transition(0,random.nextInt(maxNode), treshold, maxR));
	}
	
	//check for loops, and well-states
	// all states must be able to reach a end-state
	// no state must be able to reach itself
	public boolean is_Valid(){
		//TODO
		ArrayList<Integer> currents_states= new ArrayList<Integer>();
		currents_states.add(0);
		return false;
	}
	
	private ArrayList<Integer> reachable_states(int begin_state){
		//TODO
		ArrayList<Integer> visited = new ArrayList<Integer>();
		
		return null;
	}

	Action evaluate(Human agent){
		return null;
	}

	@Override
	public boolean mutate(int treshold, int maxR) {
		// TODO Auto-generated method stub
		return false;
	}
}
