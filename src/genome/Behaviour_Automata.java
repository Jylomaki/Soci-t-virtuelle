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
	
	Action evaluate(Human agent){
		return null;
	}

	@Override
	public boolean mutate(int treshold, int maxR) {
		// TODO Auto-generated method stub
		return false;
	}
}
