package ui;

import javax.swing.JFrame;

import terrain.Renderer2D;
import terrain.Terrain;

public class MainWindow extends JFrame {

	private final int WIDTH = 800;
	private final int HEIGHT = 600;
	
	
	public MainWindow(){
		setSize(WIDTH, HEIGHT);
		setTitle("Société Virtuelle");
		setVisible(true);
		
		Terrain terrain = new Terrain(60,60);
		
		Renderer2D renderer = new Renderer2D(HEIGHT,HEIGHT,terrain);
		
		add(renderer);
		renderer.repaint();
		
	}
	
}
