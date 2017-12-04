package terrain;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JComponent;

import data.DataManagement;
import data.Generator;

public class Renderer2D extends JComponent{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9009545833418986547L;
	private Graphics2D g2d;
	private int width,height;
	private Terrain terrain;
	private int frame_count;
	private static final int frame_checkup = 100;
	int xPoints[];
	int yPoints[];
	
	public Renderer2D(int width,int height,Terrain terrain){
		super();
		this.width = width;
		this.height = height;
		this.terrain = terrain;
		xPoints = new int[3];
		yPoints = new int[3];
	}
	
	public void setSize(int width,int height){
		this.width = width;
		this.height = height;
	}
	
	private void cleanUp(){
		g2d.setColor(Color.white);
		g2d.fillRect(0,0,width,height);
	}
	
	public void paintComponent(Graphics g) {
		g2d = (Graphics2D)g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		cleanUp();
		
		/*Terrain Rendering*/
		int caseWidth = width/terrain.getWidth();
		int caseHeight = height/terrain.getHeight();
		for(int i=0;i<terrain.getWidth();i++){
			for(int j=0;j<terrain.getHeight();j++){
				terrain.getCase(i, j).update();
				g2d.setColor(terrain.getCase(i, j).getColor());
				g2d.fillRect(i*caseWidth,j*caseHeight,caseWidth,caseHeight);				
			}
		}
		for(int i=0;i<terrain.getWidth();i++){
			for(int j=0;j<terrain.getHeight();j++){
				int Hcount = terrain.getCase(i, j).humans.size();
				if(Hcount>0){
					int circle_size = (int)Hcount*caseWidth;
					g2d.setColor(terrain.getCase(i, j).humans.get(0).tribe.getColor());
					int x=i * caseWidth-circle_size/2 + caseWidth/2;
					int y=j * caseHeight-circle_size/2 + caseHeight/2;
					g2d.fillOval(x,y,circle_size, circle_size);
				}
				//Settlement print
				if(terrain.getCase(i, j).settlement_present()){
					g2d.setColor(Color.WHITE);
					xPoints[0] = i*caseWidth;
					xPoints[1] = i*caseWidth+caseWidth/2;
					xPoints[2] = i*caseWidth+caseWidth;
					yPoints[0] = j*caseHeight+caseHeight;
					yPoints[1] = j*caseHeight;
					yPoints[2] = j*caseHeight+caseHeight;
					g2d.fillPolygon(xPoints, yPoints, 3);
				}
				//Corpse print
				if(terrain.getCase(i, j).corpse_present()){
					g2d.setColor(Color.RED);
					g2d.drawLine(i*caseWidth, j*caseHeight, i*caseWidth+caseWidth, j*caseHeight+caseHeight);
					g2d.drawLine(i*caseWidth, j*caseHeight+caseHeight, i*caseWidth+caseWidth, j*caseHeight);
				}
				
			}
		}
		
		
		
		/*Human Rendering*/
		boolean extinction=true;
		for(int i=0;i<DataManagement.tribes.size();i++){
			DataManagement.frame_data.add(DataManagement.tribes.get(i).currentFrame);
			DataManagement.tribes.get(i).update_datas();
			extinction &= DataManagement.tribes.get(i).getSize()== 0;
		}
		execution_check_up();
		DataManagement.update_datas();
		if(extinction) {
			Generator.reinstanciate();
			DataManagement.update_datas();
			this.frame_count=0;
		}
		
		
		
	}
	private void execution_check_up() {
		this.frame_count++;
		if(frame_count% frame_checkup==0) {
			System.out.println(frame_count+" frames passed, Humans:"+DataManagement.frame_data.tribus_size +" Total food:" + DataManagement.frame_data.food);
		}
	}
}
