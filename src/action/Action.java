package action;

import agent.Human;
import terrain.Case;

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
	public final static int interaction_max = all_action_max - solo_action_max;
	
	public static void execute_action(Type action,Human perpetrator, Human interlocutor, Case current_case){
		if(interlocutor == null && !Action.actionIgnoring(action)){
			System.err.println("Action Execute action: Calling for a interaction whilst interlocutor is null:Action:" + action);
			return;
		}

		perpetrator.tribe.currentFrame.actions_performed[Action.to_int(action)] ++;
		if(interlocutor != null)
			if(Action.actionIgnoring(action))
				perpetrator.tribe.currentFrame.ignorance_on_com++;
			else
				perpetrator.tribe.currentFrame.interaction_performed[Action.to_int(action)-Action.solo_action_max]++;
		else
			perpetrator.tribe.currentFrame.soloaction_performed[Action.to_int(action)]++;
			
		switch(action){
		case COLLECT_FOOD:
			switch(perpetrator.sex){
			case CHILDREN_1:
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
	public static Human.Communication_Status correspondingStatus(Action.Type a){
		switch(a){
		case COLLECT_FOOD:
			break;
		case COLLECT_RESSOURCE:
			break;
		case GIVE_FOOD:
			return Human.Communication_Status.GIVEN_FOOD;
		case GIVE_RESSOURCE:
			return Human.Communication_Status.GIVEN_RESSOURCE;
		case HURT:
			return Human.Communication_Status.HURTED;
		case MOVE:
			break;
		case REPRODUCE:
			break;
		case SETTLEMENT:
			break;
		default:
			break;
		}
		return Human.Communication_Status.BEGIN;
	}
	
	public static boolean actionIgnoring(Action.Type a){
		switch(a){
		case COLLECT_FOOD:
		case COLLECT_RESSOURCE:
		case MOVE:
		case SETTLEMENT:
			return true;
		case GIVE_FOOD:
		case GIVE_RESSOURCE:
		case HURT:
		case REPRODUCE:
			return false;
		default:
			break;
		}
		return true;
	}
	
	public static Action.Type to_action_type(int i) {
		switch(i) {
		case 0:
			return Action.Type.COLLECT_RESSOURCE;
		case 1:
			return Action.Type.COLLECT_FOOD;
		case 2:
			return Action.Type.MOVE;
		case 3:
			return Action.Type.SETTLEMENT;
		case 4:
			return Action.Type.GIVE_FOOD;
		case 5:
			return Action.Type.GIVE_RESSOURCE;
		case 6:
			return Action.Type.HURT;
		case 7:
			return Action.Type.REPRODUCE;
		}
		return Action.Type.MOVE;
	}
	
	public static int to_int(Action.Type t) {
		switch(t) {
		case COLLECT_RESSOURCE:
			return 0;
		case COLLECT_FOOD:
			return 1;
		case MOVE:
			return 2;
		case SETTLEMENT:
			return 3;
		case GIVE_FOOD:
			return 4;
		case GIVE_RESSOURCE:
			return 5;
		case HURT:
			return 6;
		case REPRODUCE:
			return 7;
		}
		return -1;
	}
	
}
