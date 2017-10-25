package genome;

import global.Mutable;
import global.Randomized;

import java.util.ArrayList;

public class Genomal_Variables extends Randomized implements Mutable {
	public int maxVar=64;
	ArrayList<Integer> vars;
	
	
	@Override
	public boolean mutate(int treshold, int maxR) {
		// TODO Auto-generated method stub
		return false;
	}

	
	/**
	 * 
	 * @param id
	 * @param evaluate
	 */
	public void set(Integer id, int evaluate) {
		vars.set(id,evaluate);
	}
	
	public int get(int id){
		return vars.get(id);
	}
	
}
