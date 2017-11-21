package genome;

import genome.transition.Transition;
import global.Mutable;
import global.Randomized;
import global.local_random;

import java.util.ArrayList;

import action.Action;
import agent.Human;

public class Behaviour_Automata extends Randomized implements Mutable {
	ArrayList<ArrayList<Transition>> automata;
	int maxNode; // The last node number
	int final_state_max;// last state that is final, all subsequent state vill be generated
	public boolean handle_communication;
	private boolean has_mutated;
	private int time_tried=0;
	private final int max_tries = 512;
	public Behaviour_Automata(int treshold, int maxR) {
		super();
		handle_communication=false;
		this.basic_setup(treshold, maxR);
		
		boolean valid = is_Valid();
		assert(valid);
		if(!valid)
			System.err.println("BEHAVIOUR_AUTOMATA CONSTRUCTOR ERROR: THE AUTOMATA IS NOT VALID");
	}


	public Behaviour_Automata(int treshold, int maxR, boolean handle_communication) {
		super();
		this.handle_communication=handle_communication;
		this.basic_setup(treshold, maxR);
		boolean valid = is_Valid();
		assert(valid);
		if(!valid)
			System.err.println("BEHAVIOUR_AUTOMATA CONSTRUCTOR ERROR: THE AUTOMATA IS NOT VALID");
	}


	public Behaviour_Automata(ArrayList<ArrayList<Transition>> automata, int maxNode, int final_state_max,
			boolean handle_communication) {
		super();
		this.automata = automata;
		this.maxNode = maxNode;
		this.final_state_max = final_state_max;
		this.handle_communication = handle_communication;
	}


	Action.Type evaluate(Human agent){
		int current_state=0;
		while(! is_final_state(current_state)) {
			current_state = this.execute_transition(current_state,agent);
		}
		//vhen in a non final state, check for outvard transition
		// if cond true, do transition
		// if no cond true, do id 0 of transition from state
		return Action.Type.values()[current_state];
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
	
	@Override
	public boolean mutate(int treshold, int maxR) {
		this.has_mutated = false;
		//TODO add remove state, and/or remove transition (degenerative mutations)
		
		//Mutate transition table
		for(int i=0; i< maxNode; i++) {
			if(!this.is_final_state(i))//not a final state
				for(int j=0; j< automata.get(i).size(); j++) {
					has_mutated |= automata.get(i).get(j).mutate(treshold, maxR, maxNode);
				}
		}
		
		//Remove state
		while(local_random.nextInt(maxR)<treshold) {
			int target_state = local_random.nextInt(this.maxNode-1)+1;
			if(!this.is_final_state(target_state)) {
				has_mutated = true;
				// chose state, by pass all transition that go into that state
				ArrayList<Transition> bypassing_trans = this.get_trans_to_state(target_state);
				for(int i=0; i<bypassing_trans.size(); i++) {
					// bypassing (in)transition get for ending state the ending state of a bypassed (out) transition
					int nevTargetID = local_random.nextInt(this.automata.get(target_state).size());
					bypassing_trans.get(i).ending_state = this.automata.get(target_state).get(nevTargetID).ending_state;
				}
				
				// re-index states
				for(int i=target_state+1; i<this.maxNode; i++) {
					ArrayList<Transition> reindexed_trans = this.get_trans_to_state(i);
					for(int j=0; j<reindexed_trans.size(); j++) {
						reindexed_trans.get(j).ending_state --;
					}
				}
				// remove state
				automata.remove(target_state);
				this.maxNode--;
			}
		}
		
		//Add states
		while(local_random.nextInt(maxR)<treshold) {
			has_mutated = true;
			this.add_state(treshold, maxR);
		}
			
		//Add transitions
		while(local_random.nextInt(maxR)<treshold) {
			has_mutated=true;
			this.add_transition(treshold, maxR);
		}
		assert(is_Valid());
		return has_mutated;
	}

	public Behaviour_Automata clone(){
		return new Behaviour_Automata(this.clone_transitions(), this.maxNode, this.final_state_max, this.handle_communication);
	}


	private ArrayList<Transition> get_trans_to_state(int ending_state){
		ArrayList<Transition> retour = new ArrayList<Transition>();
		ArrayList<Transition> all = this.get_all_trans();
		for(int i=0; i < all.size();i++ ) {
			Transition current = all.get(i);
			if(current.ending_state == ending_state)
				retour.add(current);
		}
		return retour;
	}
	
	private ArrayList<Transition> get_all_trans(){
		ArrayList<Transition> retour = new ArrayList<Transition>();
		for(int i=0; i<this.maxNode; i++) {
			if(!this.is_final_state(i)) {
				retour.addAll(this.automata.get(i));
			}
		}
		return retour;
		
	}
	private void basic_setup(int treshold, int maxR) {
		if(this.handle_communication)
			final_state_max = maxNode = Action.all_action_max+1;
		else
			final_state_max = maxNode = Action.solo_action_max+1;
		//add 1 to account for initial state
		
		automata = new ArrayList<ArrayList<Transition>>();
		
		//add 0 out transition array
		automata.add(new ArrayList<Transition>());
		
		//Add null to fill the gap
		for(int i=1; i< maxNode; i++)
			automata.add(null);
		
		//Add states
		while(local_random.nextInt(maxR)<treshold) 
			this.add_state(treshold, maxR);
					
		//Add transitions
		while(local_random.nextInt(maxR)<treshold)
			this.add_transition(treshold, maxR);
		
		//Add transitions from 0
		this.add_transition_from_state(0, treshold, maxR);
	}


	// Can a state create a loop vhile being loopless? No.
	//loopless and can reach final or is a final state
	private boolean is_valid_state(int begin_state) {
		if( 0 < begin_state && begin_state <= this.final_state_max) {// AKA is final state
			//System.out.println("Beha_auto: found a final state");
			return true;
		}
		ArrayList<Integer> current_states = new ArrayList<Integer>();
		int current_state;
		ArrayList<Integer> visited_states= new ArrayList<Integer>();
		
		current_states.add(begin_state);
		visited_states.add(begin_state);
		while(!current_states.isEmpty()) {
			current_state = current_states.remove(0);
			//System.out.println("Beha_Auto: Is valid: current state" + current_state);
			if(this.is_final_state(current_state))
				continue;
			// Add all non-visited states to current states
			for(int i = 0; i< this.automata.get(current_state).size(); i++) {
				Transition trans= automata.get(current_state).get(i);
				if(i == trans.starting_state ) {
					if(begin_state== trans.ending_state) {
						//System.out.println("Beha_auto: found a looping state");
						return false;//looping
					}
					if(!visited_states.contains(trans.ending_state)) {
						current_states.add(trans.ending_state);
						visited_states.add(trans.ending_state);
					}
					
				}			
			}
		}
		// does it reach a final state?
		for(int i=1; i<this.final_state_max; i++) {
			if(visited_states.contains(i)) {
				//System.out.println("Beha_auto: found a valid state");
				return true;
			}
		}
		//System.out.println("Beha_auto: found a unvalid state(can't reach final state)");
		//not reached a final state
		return false;
	}
	
	// automaticly render the last state valid by linking it to a final state
	private void add_state(int treshold, int maxR) {
		//DONE change this shit, a nev state could lead to any valid state other than 0
		automata.add( new ArrayList<Transition>());
		
		this.add_transition_from_state(maxNode, treshold, maxR);
		maxNode ++;
		assert(is_Valid());
		
	}
	
	private void add_transition_from_state(int begin_state, int treshold, int maxR) {
		//DONE
		int a,b;
		boolean validity = true;
		a = begin_state;
		b = local_random.nextInt(maxNode);
		this.time_tried=0;
		do {
			while(a==b) {// no transition can stay on one state
				b = local_random.nextInt(maxNode);
				//System.out.println("tried " + i++ +" time, still not have found a different state: ("+a+","+b+")");
			}
			automata.get(a).add(new Transition(a,b, treshold, maxR, handle_communication));
			//no adding transition shall lead this state to unvalid state
			validity = this.is_valid_state(a);
			if(!validity && time_tried < 10) {
				int size = automata.get(a).size();
				automata.get(a).remove(size-1);
			}
			else if(time_tried >= max_tries) {
				System.err.println("Tried 64 times, and no valid trans could be found");
				//this.print();
			}
		}while(!validity && time_tried++ < max_tries);
	}
	
	
	private void add_transition(int treshold, int maxR) {
		int begin_state,end_state;
		boolean validity = true;
		this.time_tried=0;
		do {
			begin_state = local_random.nextInt(maxNode);
			while( this.is_final_state(begin_state))//no transition shall begin on a final state
				begin_state = local_random.nextInt(maxNode);
			
			end_state = local_random.nextInt(maxNode);
			while(begin_state==end_state)// no transition can stay on one state
				end_state = local_random.nextInt(maxNode);
			
			automata.get(begin_state).add(new Transition(begin_state,end_state, treshold, maxR, handle_communication));
			
			//no adding transition shall lead this state to unvalid state
			validity =this.is_valid_state(begin_state); 
			if(!validity) {
				int size = automata.get(begin_state).size();
				automata.get(begin_state).remove(size-1);
			}
		}while(!validity && this.time_tried++<this.max_tries);
		if(this.time_tried >= this.max_tries) {
			System.err.println("Add transition: couldn't find valid transition");
			//this.print();
		}
		
	}
	

	private boolean is_final_state(int state) {
		return 0<state && state < this.final_state_max;
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

	private ArrayList<ArrayList<Transition>> clone_transitions() {
		ArrayList<ArrayList<Transition>> list = new ArrayList<ArrayList<Transition>>();
		for(int i=0; i<this.maxNode; i++){
			if(this.is_final_state(i)){
				list.add(null);
			}
			else{
				list.add(clone_transition(this.automata.get(i)));
			}
		}
		return list;
	}


	private  ArrayList<Transition> clone_transition(ArrayList<Transition> arrayList) {
		ArrayList<Transition> list = new ArrayList<Transition>();
		for(int i=0; i< arrayList.size(); i++){
			list.add(arrayList.get(i).clone());
		}
		return list;
	}


	@Override
	public void print() {
		ArrayList<Transition> all = this.get_all_trans();
		for(int i=0;i<all.size(); i++)
			all.get(i).cond.simplify();
		System.out.println("Max States: " + this.maxNode 
				+ " handle communication? : " + this.handle_communication);
		System.out.println("Transition table:");
		for(int i=0; i<this.maxNode; i++) {
			if(this.is_final_state(i))
				System.out.println("Final state: " + Action.Type.values()[i-1]);
			else {
				System.out.println("\t Transition from "+i+" state:");
				for(int j=0; j<this.automata.get(i).size(); j++) {
					this.automata.get(i).get(j).print();
				}
			}
			
		}
	}


	@Override
	@Deprecated
	public void print(String mise_forme) {
		// TODO Auto-generated method stub
		
	}
}
