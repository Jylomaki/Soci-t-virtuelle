package action;

import javax.sound.sampled.AudioFileFormat.Type;

import agent.Human;
import data.DataManagement;
import terrain.Case.TypeCase;

public class Collect_Food extends ParentAction {

	@Override
	public void execute(Human human) {
		if(DataManagement.terrain.getCase(human.x, human.y).getType() == TypeCase.FOOD){
			DataManagement.terrain.getCase(human.x, human.y).setType(TypeCase.EMPTY);
			human.food += DataManagement.FOOD_RESTITUTION;
		}else if(DataManagement.terrain.getCase(human.x, human.y).getType() == TypeCase.FR){
			DataManagement.terrain.getCase(human.x, human.y).setType(TypeCase.RESSOURCE);
			human.food += DataManagement.FOOD_RESTITUTION;
		}
	}

}
