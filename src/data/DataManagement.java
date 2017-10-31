package data;
import java.util.ArrayList;

import agent.Human;
import agent.Tribe;
import terrain.Terrain;

public class DataManagement {

	public static ArrayList<Tribe> tribes = new ArrayList<Tribe>();
	public static final int TerrainGridX = 120;
	public static final int TerrainGridY = 120;
	public static Terrain terrain = new Terrain();
	public static final int FOOD_RESTITUTION = 1;
	public static final int RESSOURCE_RESTITUTION = 2;
	
	public static void killHuman(Human human){
		for(Tribe tribe:tribes){
			for(int i=tribe.getBack_up_humans().size()-1;i>=0;i--){
				if(tribe.getBack_up_humans().get(i).id == human.id){
					tribe.getBack_up_humans().remove(i);
					return;
				}
			}
		}
	}
	
}
