package action;


public class Action{
	public enum Type{
		//solo actions
		COLLECT_FOOD,
		COLLECT_RESSOURCE,
		SETTLEMENT,
		MOVE,
		//interactions
		GIVE_FOOD,
		GIVE_RESSOURCE,
		HURT,
		REPRODUCE
	}
	
	public Type type;
	public final static int solo_action_max = 4;
	public final static int all_action_max = 8;
}
