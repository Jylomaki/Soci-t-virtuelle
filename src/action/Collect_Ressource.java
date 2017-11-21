package action;

import agent.Human;
import data.DataManagement;
import terrain.Case.TypeCase;

public class Collect_Ressource extends ParentAction {

	@Override
	public void execute(Human human) {
		if(DataManagement.terrain.getCase(human.x, human.y).ressource_present()){
			DataManagement.terrain.getCase(human.x, human.y).gatherRessource();
			human.food += DataManagement.RESSOURCE_RESTITUTION;
		}
	}

}
