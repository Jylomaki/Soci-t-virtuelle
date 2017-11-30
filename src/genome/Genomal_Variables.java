package genome;

import global.Mutable;
import global.Randomized;

public class Genomal_Variables extends Randomized implements Mutable {
	public int maxVar=64;
	int vars[];
	
	
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
		vars[id]=evaluate;
	}
	
	public Genomal_Variables clone(){
		return new Genomal_Variables();
	}
	
	public Genomal_Variables() {
		super();
		vars = new int[maxVar];
	}


	public int get(int id){
		return vars[id];
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
