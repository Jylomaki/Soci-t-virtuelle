package terrain;

import java.util.ArrayList;

import data.DataManagement;

public class Terrain {

	private int width,height;
	private ArrayList<ArrayList<Case>> grid;
	
	public Terrain(){
		this.width = DataManagement.TerrainGridX;
		this.height = DataManagement.TerrainGridY;
		grid = new ArrayList<ArrayList<Case>>();
		for(int i=0;i<DataManagement.TerrainGridX;i++){
			grid.add(new ArrayList<Case>());
			for(int j=0;j<DataManagement.TerrainGridY;j++){
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
