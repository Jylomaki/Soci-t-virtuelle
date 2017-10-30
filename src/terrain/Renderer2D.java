package terrain;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JComponent;

public class Renderer2D extends JComponent{

	private Graphics2D g2d;
	private int width,height;
	private Terrain terrain;
	
	public Renderer2D(int width,int height,Terrain terrain){
		super();
		this.width = width;
		this.height = height;
		this.terrain = terrain;
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
				g2d.setColor(terrain.getCase(i, j).getColor());
				g2d.fillRect(i*caseWidth,j*caseHeight,caseWidth,caseHeight);
			}
		}
		/*Settlement Rendering*/

		
		/*Human Rendering*/
		for(int i=0;i<0/*Humans.size*/;i++){
			/*g2d.setColor(Humans.get(i).getColor());
			g2d.drawOval(Humans.get(i).x * caseWidth,Humans.get(i).y * caseHeight, caseWidth, caseHeight);*/
		}	

		
	}
	
}
