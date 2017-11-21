package action;

import javax.sound.sampled.AudioFileFormat.Type;

import agent.Human;
import data.DataManagement;
import terrain.Case.TypeCase;

public class Collect_Food extends ParentAction {

	@Override
	public void execute(Human human) {
		if(DataManagement.terrain.getCase(human.x, human.y).food_present()){
			DataManagement.terrain.getCase(human.x, human.y).gatherFood();
			human.food += DataManagement.FOOD_RESTITUTION;
		}
	}

}
