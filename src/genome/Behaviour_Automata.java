package genome;

import genome.transition.Transition;
import global.Mutable;
import global.Randomized;

import java.util.ArrayList;

import action.Action;
import agent.Human;

public class Behaviour_Automata extends Randomized implements Mutable {
	ArrayList<ArrayList<Transition>> automata;
	int maxNode;
	private boolean has_mutated;
	
	
	/**
	 * 
	 */
	public Behaviour_Automata(int treshold, int maxR) {
		super();
		maxNode = Action.values().length+1;
		automata = new ArrayList<ArrayList<Transition>>();
		
		//Add states
		while(random.nextInt(maxR)<treshold) 
			this.add_state(treshold, maxR);
			
		//Add transitions
		while(random.nextInt(maxR)<treshold)
			this.add_transition(treshold, maxR);
		
		//Add transitions from 0
		int next_state = random.nextInt(maxNode);
		while(next_state == 0)
			next_state = random.nextInt(maxNode);
		
		automata.get(0).add(new Transition(0,random.nextInt(maxNode), treshold, maxR));
		
		//Add transition until the automata become valid should be 0, as any state should be valid, and ve added a outvard transition from begin state
		boolean valid = is_Valid();
		assert(valid);
		if(!valid)
			System.err.println("BEHAVIOUR_AUTOMATA CONSTRUCTOR ERROR: THE AUTOMATA IS NOT VALID");
	}
	
	//check for loops, and well-states
	// all reachable states must be able to reach a end-state
	// no state must be able to reach itself
	public boolean is_Valid(){
		//Automata is valid if all states are valid. (even non reachable state, in order to preserve okayness vhen adding transitions)
		for(int i=0; i< maxNode; i++)
			if(! is_valid_state(i))
				return false;
		return true;
	}
	
	// Can a state create a loop vhile being loopless? No.
	//loopless and can reach final or is a final state
	private boolean is_valid_state(int begin_state) {
		if( 0 < begin_state && begin_state <= Action.values().length + 1)// AKA is final state
			return true;
		
		ArrayList<Integer> current_states = new ArrayList<Integer>();
		int current_state;
		ArrayList<Integer> visited_states= new ArrayList<Integer>();
		
		current_states.add(begin_state);
		visited_states.add(begin_state);
		while(!current_states.isEmpty()) {
			current_state = current_states.remove(0);
			// Add all non-visited states to current states
			for(int i = 0; i< this.automata.get(current_state).size(); i++) {
				Transition trans= automata.get(current_state).get(i);
				if(i == trans.starting_state ) {
					if(begin_state== trans.ending_state)
						return false;//looping
					if(!visited_states.contains(trans.ending_state)) {
						current_states.add(trans.ending_state);
						visited_states.add(trans.ending_state);
					}
					
				}			
			}
		}
		// does it reach a final state?
		for(int i=1; i<Action.values().length; i++) {
			if(visited_states.contains(i))
				return true;
		}
		//not reached a final state
		return false;
	}
	
	// automaticly render the last state valid by linking it to a final state
	private void add_state(int treshold, int maxR) {
		//DONE change this shit, a nev state could lead to any valid state other than 0
		automata.set(maxNode, new ArrayList<Transition>());
		
		this.add_transition_from_state(maxNode, treshold, maxR);
		maxNode ++;
		assert(is_Valid());
		
	}
	
	private void add_transition_from_state(int begin_state, int treshold, int maxR) {
		//DONE
		int a,b;
		a = begin_state;
		b = random.nextInt(maxNode);
		while(a==b)// no transition can stay on one state
			b = random.nextInt(maxNode);
		if(automata.get(a)== null)
			automata.set(a, new ArrayList<Transition>());
		automata.get(a).add(new Transition(a,b, treshold, maxR));
		//no adding transition shall lead this state to unvalid state
		if(!this.is_valid_state(a)) {
			int size = automata.get(a).size();
			automata.get(a).remove(size-1);
			this.add_transition(treshold, maxR);
		}
		assert(is_Valid());
	}
	
	
	private void add_transition(int treshold, int maxR) {
		int a,b;
		a = random.nextInt(maxNode);
		while( 0< a && a<= Action.values().length+1)//no transition shall begin on a final state
			a = random.nextInt(maxNode);
		b = random.nextInt(maxNode);
		while(a==b)// no transition can stay on one state
			b = random.nextInt(maxNode);
		if(automata.get(a)== null)
			automata.set(a, new ArrayList<Transition>());
		automata.get(a).add(new Transition(a,b, treshold, maxR));
		//no adding transition shall lead this state to unvalid state
		if(!this.is_valid_state(a)) {
			int size = automata.get(a).size();
			automata.get(a).remove(size-1);
			this.add_transition(treshold, maxR);
		}
		
	}
	

	Action evaluate(Human agent){
		int current_state=0;
		while(current_state ==0 || current_state > Action.values().length) {
			current_state = this.execute_transition(current_state,agent);
		}
		//vhen in a non final state, check for outvard transition
		// if cond true, do transition
		// if no cond true, do id 0 of transition from state
		return Action.values()[current_state-1];
	}

	private int execute_transition(int begin_state, Human agent) {
		for(int i=0; i< automata.get(begin_state).size(); i++) {
			if(automata.get(begin_state).get(i).cond.evaluate(agent)) {
				automata.get(begin_state).get(i).modifications.execute(agent);
				return automata.get(begin_state).get(i).ending_state;
			}
		}
		//return the next state, don't execute modification
		return automata.get(begin_state).get(0).ending_state;
	}

	@Override
	public boolean mutate(int treshold, int maxR) {
		this.has_mutated = false;
		//TODO had remove state, and/or remove transition
		//Mutate transition table
		for(int i=0; i< maxNode; i++) {
			if(i==0 || i> Action.values().length)//not a final state
				for(int j=0; j< automata.get(i).size(); j++) {
					has_mutated |= automata.get(i).get(j).mutate(treshold, maxR, maxNode);
				}
		}
		//Add states
		while(random.nextInt(maxR)<treshold) {
			has_mutated = true;
			this.add_state(treshold, maxR);
		}
			
		//Add transitions
		while(random.nextInt(maxR)<treshold) {
			has_mutated=true;
			this.add_transition(treshold, maxR);
		}
		assert(is_Valid());
		return has_mutated;
	}
}
