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
	
	public Genomal_Variables clone(){
		return new Genomal_Variables();
	}
	
	public Genomal_Variables() {
		super();
		vars = new ArrayList<Integer>();
	}


	public int get(int id){
		return vars.get(id);
	}


	@Override
	@Deprecated
	public void print() {
		// TODO Auto-generated method stub
		
	}


	@Override
	@Deprecated
	public void print(String mise_forme) {
		// TODO Auto-generated method stub
		
	}
	
}
