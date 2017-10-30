package terrain;

import java.util.ArrayList;

public class Terrain {

	private int width,height;
	private ArrayList<ArrayList<Case>> grid;
	
	public Terrain(int width,int height){
		this.width = width;
		this.height = height;
		grid = new ArrayList<ArrayList<Case>>();
		for(int i=0;i<width;i++){
			grid.add(new ArrayList<Case>());
			for(int j=0;j<height;j++){
				grid.get(i).add(new Case());
			}
		}
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public ArrayList<ArrayList<Case>> getGrid() {
		return grid;
	}
	
	public Case getCase(int i,int j){
		return grid.get(i).get(j);
	}
	
}
