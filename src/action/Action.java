package action;

import agent.Human;
import terrain.Case;
import agent.Sex;

public class Action{
	private static final int cooperation_overflow = 5;
	private static final int cooperation_give = 100;
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
	
	public static void execute_action(Type action,Human perpetrator, Human interlocutor, Case current_case){
		switch(action){
		case COLLECT_FOOD:
			switch(perpetrator.sex){
			case CHILDREN_1:
				break;
			case CHILDREN_2:
				break;
			case S1:
			case S2:
				perpetrator.food += current_case.gatherFood();
				break;
			default:
				break;
			}
			break;
		case COLLECT_RESSOURCE:
			perpetrator.ressource += current_case.gatherRessource();
			break;
		case GIVE_FOOD:
			if(perpetrator.food > 0){
				perpetrator.food -= cooperation_give;
				interlocutor.food += cooperation_give + cooperation_overflow;
			}
			break;
		case GIVE_RESSOURCE:
			if(perpetrator.ressource > 0){
				perpetrator.ressource -= cooperation_give;
				interlocutor.ressource += cooperation_give + cooperation_overflow;
			}
			break;
		case HURT:
			interlocutor.energy -= global.Global_variables.hurt_energy_deplete;
			break;
		case MOVE:
			break;
		case REPRODUCE:
			//TODO
			//perpetrator.reproduce(interlocutor);
			break;
		case SETTLEMENT:
			
			break;
		default:
			break;
		
		}
	}
}
