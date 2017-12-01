package global;

import java.util.Random;

public class local_random {
	public static Random r;
	
	public static void init() {
		r = new Random();
	}
	public static void init_seed(int seed) {
		r = new Random(seed);
	}

	public static int nextInt(int i) {
		return r.nextInt(i);
		//return 0;
	}
	
	public static int nextInt() {
		return r.nextInt(global.Global_variables.default_max_int);
		//return 0;
	}
	public static float nextFloat() {
		return r.nextFloat();
	}

}
