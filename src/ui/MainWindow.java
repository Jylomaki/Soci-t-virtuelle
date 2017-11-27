package ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import agent.Human;
import agent.Tribe;
import data.*;
import terrain.Renderer2D;
import terrain.Terrain;

public class MainWindow extends JFrame {

	private final int WIDTH = 1200;
	private final int HEIGHT = 720;
	private Renderer2D renderer;
	private boolean simulationHaveBeenRun = false;
	
	public MainWindow(){
		setSize(WIDTH, HEIGHT);
		setTitle("Societe Virtuelle");
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		
		
		renderer = new Renderer2D(HEIGHT,HEIGHT,DataManagement.terrain);
		
		JPanel panelControler = new JPanel();
		panelControler.setLayout(new BoxLayout(panelControler,BoxLayout.Y_AXIS));
		
		JButton setupButton = new JButton("Setup");
		setupButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				simulationHaveBeenRun = false;
				DataManagement.tribes = Generator.generateTribes();
				renderer.repaint();
			}
	    	 
	     });

		JButton launchButton = new JButton("GO");
		launchButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				simulationHaveBeenRun = true;
			}
	    	 
	     });
		
		panelControler.add(setupButton);
		panelControler.add(launchButton);
		
		add(panelControler,BorderLayout.WEST);
		
		add(renderer,BorderLayout.CENTER);
		
		int delay = 16;
		ActionListener taskPerformer = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				if(simulationHaveBeenRun)
					loop();
			}
		};
		
		Timer timer = new Timer(delay,taskPerformer);
		timer.start();
		renderer.repaint();
		
		
	}
	
	public void loop(){
		renderer.repaint();
	}
	
}
