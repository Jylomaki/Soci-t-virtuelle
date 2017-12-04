package data;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import agent.Human;
import agent.Tribe;
import global.Randomized;
import global.local_random;

public class Generator extends Randomized{

	static final int NUMBER_TRIBE = 12;
	static final int MAX_TRIBE_SIZE = 5;
	static int generation =0;
	
	public static ArrayList<Tribe> generateTribes(){
		ArrayList<Tribe> tribes = new ArrayList<Tribe>();
		for(int i=0 ;i<global.Global_variables.tribe_count;i++){
			Tribe added = new Tribe();
			set_tribe_attributes(added);
			tribes.add(added);
			
			for(int j=0;j<global.Global_variables.tribe_max_size;j++)
				generateHuman(tribes.get(i));
		}
		return tribes;
	}
	
	public static Tribe generateTribe() {
		Tribe t = new Tribe();
		set_tribe_attributes(t);
		return t;
	}
	
	private static void set_tribe_attributes(Tribe t) {
		float r = global.local_random.nextFloat()%0.9f+0.1f;
		float g = global.local_random.nextFloat()%0.9f+0.1f;
		float b = global.local_random.nextFloat()%0.9f+0.1f;
		t.setColor(new Color(r,g,b));
		t.setSpawnX(global.local_random.nextInt(DataManagement.TerrainGridX));
		t.setSpawnY(global.local_random.nextInt(DataManagement.TerrainGridY));
	}

	public static void reinstanciate() {
		if(DataManagement.tribes.size() > 0) {
			ArrayList<Integer> fitness = new ArrayList<Integer>();
			for(Tribe t: DataManagement.tribes) {
				fitness.add(t.getFitness_score());
				t.setFitness_score(1);
			}
			Collections.sort(fitness);
			System.out.println("Render: Extinction detected, beginning reinstanciation. Generation:" + generation++);
			System.out.println("Best fitness:" + fitness.get(fitness.size()-1)+ "  tribe count: "+ DataManagement.tribes.size());
			DataManagement.datas.printLastFrame();
			int median_fitness = fitness.get(DataManagement.tribes.size()/2);
			int quartile_fitness = fitness.get(DataManagement.tribes.size()*3/4);
			
			int i=0;
			int initial_size = DataManagement.tribes.size();
			while(i<initial_size) {
				Tribe t = DataManagement.tribes.get(i);
				if(t.getFitness_score() >= median_fitness) {
					t.mutate_autos();
					if(t.getFitness_score() >= quartile_fitness) {
						Tribe nouv = t.clone();
						set_tribe_attributes(nouv);
						DataManagement.tribes.add(nouv);
					}
					i++;
				}
				else {
					DataManagement.tribes.remove(i);
					Tribe add= generateTribe();
					DataManagement.tribes.add(add);
					initial_size--;
				}
			}
			
			for(Tribe t: DataManagement.tribes) {
				for(int j=0; j<global.Global_variables.tribe_max_size; j++) {
					generateHuman(t);
				}
			}
		}
	}
	
	public static Human generateHuman(Tribe tribe){
		
		Human human = new Human();
		human.x = (tribe.getSpawnX() + local_random.nextInt(10))%DataManagement.TerrainGridX;
		human.y = (tribe.getSpawnY() + local_random.nextInt(10))%DataManagement.TerrainGridY;
		human.age = 0;
		human.energy = local_random.nextInt(200) + 600;
		human.culture = local_random.nextInt(10);
		human.food = 1;
		human.ressource = 5;
		human.tribe=tribe;
		tribe.living_humans.add(human);
		human.currentCase = DataManagement.terrain.getCase(human.x, human.y);
		DataManagement.terrain.getCase(human.x, human.y).humans.add(human);
		tribe.living_humans.add(human);
		return human;
	}
	
}
