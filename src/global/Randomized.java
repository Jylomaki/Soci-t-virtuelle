package global;

import java.util.Random;


public class Randomized {
	public static Random random;
	
	public Randomized(){
		random = new Random();
	}
	
	public void init_rand(int seed){
		random = new Random(seed);
	}
	
	public void init_rand(){
		random = new Random();
	}
}
