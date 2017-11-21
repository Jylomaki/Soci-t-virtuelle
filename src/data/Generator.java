package data;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import agent.Human;
import agent.Tribe;

public class Generator{

	static final int NOMBER_TRIBE = 3;
	static final int MAX_TRIBE_SIZE = 10;
	
	public static ArrayList<Tribe> generateTribes(){
		Random random = new Random();
		ArrayList<Tribe> tribes = new ArrayList<Tribe>();
		for(int i=0 ;i<NOMBER_TRIBE;i++){
			tribes.add(new Tribe());
			float r = random.nextFloat()%0.9f+0.1f;
			float g = random.nextFloat()%0.9f+0.1f;
			float b = random.nextFloat()%0.9f+0.1f;
			tribes.get(i).setColor(new Color(r,g,b));
			tribes.get(i).setSpawnX(random.nextInt(DataManagement.TerrainGridX));
			tribes.get(i).setSpawnY(random.nextInt(DataManagement.TerrainGridY));
			int nomberHumanToGenerate = MAX_TRIBE_SIZE;
			for(int j=0;j<nomberHumanToGenerate;j++)
				tribes.get(i).getBack_up_humans().add(generateHuman(tribes.get(i)));
		}
		return tribes;
	}
	
	public static Human generateHuman(Tribe tribe){
		Random random = new Random();
		Human human = new Human();
		human.x = (tribe.getSpawnX() + random.nextInt(10))%DataManagement.TerrainGridX;
		human.y = (tribe.getSpawnY() + random.nextInt(10))%DataManagement.TerrainGridY;
		human.age = 0;
		human.energy = random.nextInt(200) + 600;
		human.culture = random.nextInt(10);
		human.food = 1;
		return human;
	}
	
}
