package genome;

import global.Mutable;

public class Communication_Automata extends Behaviour_Automata implements Mutable{

	public Communication_Automata(int treshold, int maxR) {
		super(treshold, maxR, true);
	}
	public Communication_Automata(int treshold, int maxR, boolean handle_communication) {
		super(treshold, maxR, true);
	}

	
	

}
