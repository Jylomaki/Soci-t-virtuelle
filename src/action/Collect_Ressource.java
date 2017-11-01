package action;

import agent.Human;
import data.DataManagement;
import terrain.Case.TypeCase;

public class Collect_Ressource extends ParentAction {

	@Override
	public void execute(Human human) {
		if(DataManagement.terrain.getCase(human.x, human.y).getType() == TypeCase.RESSOURCE){
			DataManagement.terrain.getCase(human.x, human.y).setType(TypeCase.EMPTY);
			human.food += DataManagement.RESSOURCE_RESTITUTION;
		}else if(DataManagement.terrain.getCase(human.x, human.y).getType() == TypeCase.FR){
			DataManagement.terrain.getCase(human.x, human.y).setType(TypeCase.FOOD);
			human.food += DataManagement.RESSOURCE_RESTITUTION;
		}
	}

}
