package data;
import java.util.ArrayList;

import agent.Human;
import agent.Tribe;
import terrain.Terrain;

public class DataManagement {

	public static final int TerrainGridX = 120;
	public static final int TerrainGridY = 120;
	public static final int FOOD_RESTITUTION = 1;
	public static final int RESSOURCE_RESTITUTION = 2;
	
	public static Terrain terrain = new Terrain();
	public static ArrayList<Tribe> tribes = new ArrayList<Tribe>();
	public static Frame_data frame_data = new Frame_data();
	public static Tracked_Datas datas= new Tracked_Datas();
	
	public static void killHuman(Human human){
		for(Tribe tribe:tribes){
			for(int i=tribe.living_humans.size()-1;i>=0;i--){
				if(tribe.living_humans.get(i).id == human.id){
					tribe.living_humans.remove(i);
					return;
				}
			}
		}
	}

	public static void update_datas() {
		datas.push_back(frame_data);
		frame_data.reset();
	}
	
}
