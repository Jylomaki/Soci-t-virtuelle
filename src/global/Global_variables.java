package global;

public class Global_variables {
	public static int default_max_int = 1024;
	
	
	public static final int TRIBE_COUNT_MIN = 1;
	public static final int TRIBE_COUNT_MAX = 40;
	public static int tribe_count=3;
	
	public static final int TRIBE_MIN_SIZE_MIN = 1;
	public static final int TRIBE_MIN_SIZE_MAX = 40;
	public static final int TRIBE_MAX_SIZE_MIN = 1;
	public static final int TRIBE_MAX_SIZE_MAX = 40;
	public static int tribe_min_size=5, tribe_max_size=15;
	
	public static final int DEF_TRESHOLD_MIN = 1;
	public static final int DEF_TRESHOLD_MAX = 100;
	public static final int DEF_MAXR_MIN = 1;
	public static final int DEF_MAXR_MAX = 200;
	public static int def_treshold=1, def_maxR=100;
	
	public static final int HURT_ENERGY_DEPLETE_MIN = 1;
	public static final int HURT_ENERGY_DEPLETE_MAX = 600;
	public static int hurt_energy_deplete = 150;
	
	public static final int SETTLEMENT_DECAY_RATE_MIN = 1;
	public static final int SETTLEMENT_DECAY_RATE_MAX = 40;
	public static int settlement_decay_rate = 1;
	
	public static final int LIKE_MAX_CULTURAL_DISTANCE_MIN = 1;
	public static final int LIKE_MAX_CULTURAL_DISTANCE_MAX = 100;
	public static int like_max_cultural_distance = 50;
	
	public static final int INTERACTION_MAX_MIN = 1;
	public static final int INTERACTION_MAX_MAX = 40;
	public static int interaction_max=5;
	
	public static final int CORPSE_DECAY_RATE_MIN = 1;
	public static final int CORPSE_DECAY_RATE_MAX = 40;
	public static int corpse_decay_rate = 5;
	
	public static final int COOPERATION_GIVE_ADVANTAGE_MIN = 0;
	public static final int COOPERATION_GIVE_ADVANTAGE_MAX = 40;
	public static int cooperation_give_advantage=0;
		
	public static int percentageFood=10;
	public static int percentageRessource=5;
	public static int percentageBoth=5;
	
	public static int lastBestFitness;
	public static int lastMedianFitness;
	public static int refreshRate=10;
}
