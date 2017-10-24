package genome.transition;

import global.Mutable;
import global.Randomized;

public class Transition extends Randomized implements Mutable{
	int starting_state;
	int ending_state;
	Cond cond;
	Modification modifications;
	@Override
	public boolean mutate(int treshold, int maxR) {
		// TODO Auto-generated method stub
		return false;
	}
}
