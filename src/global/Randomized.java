package global;

import java.util.Random;


public class Randomized {
	public static local_random random;
	public static int generated = 0;
	
	public Randomized(){
		//random = new Random();
		random = new  local_random();
	}
	
	public static void init_rand(int seed){
		local_random.init_seed(seed);
	}
	
	public static void init_rand(){
		local_random.init();
	}
}
