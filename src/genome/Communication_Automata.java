package genome;

import global.Mutable;
@Deprecated
public class Communication_Automata extends Behaviour_Automata implements Mutable{
	public Communication_Automata(){
		super(true);
	}
	
	public Communication_Automata(int treshold, int maxR) {
		super(treshold, maxR, true);
	}
	public Communication_Automata(int treshold, int maxR, boolean handle_communication) {
		super(treshold, maxR, true);
	}

	public Communication_Automata clone() {
		Communication_Automata r = (Communication_Automata) super.clone();
		return r;
	}
}
