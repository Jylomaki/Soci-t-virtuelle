package ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import data.*;
import global.Global_variables;
import terrain.Renderer2D;


import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.util.Rotation;

import action.Action;

public class MainWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7218898975087861888L;
	private final int WIDTH = 1440;
	private final int HEIGHT = 720;
	private Renderer2D renderer;
	private boolean simulationHaveBeenRun = false;
	ChartPanel chartPanelAllAction;
	ChartPanel chartPanelActionSolo;
	ChartPanel chartPanelActionInteraction;
	
	public MainWindow(){
		setSize(WIDTH, HEIGHT+50);
		setTitle("Societe Virtuelle");
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		
		
		/*Set up Sliders*/
		JLabel tribe_count_slider_label = new JLabel("Tribe Count :");
		JLabel tribe_count_value_label = new JLabel(String.valueOf(Global_variables.tribe_count));
		JSlider tribe_count_slider = new JSlider(JSlider.HORIZONTAL,
                Global_variables.TRIBE_COUNT_MIN, Global_variables.TRIBE_COUNT_MAX, Global_variables.tribe_count);
		tribe_count_slider.addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider)e.getSource();
		        if (!source.getValueIsAdjusting()) {
		        	Global_variables.tribe_count = (int)source.getValue();
		        	tribe_count_value_label.setText(String.valueOf(Global_variables.tribe_count));
		        } 
			}
			
		});
		
		JLabel tribe_min_size_slider_label = new JLabel("Tribe Min Size : ");
		JLabel tribe_min_size_value_label = new JLabel(String.valueOf(Global_variables.tribe_min_size));
		JSlider tribe_min_size_slider = new JSlider(JSlider.HORIZONTAL,
				Global_variables.TRIBE_MIN_SIZE_MIN,Global_variables.TRIBE_MIN_SIZE_MAX, Global_variables.tribe_min_size);
		tribe_min_size_slider.addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider)e.getSource();
		        if (!source.getValueIsAdjusting()) {
		        	Global_variables.tribe_min_size = (int)source.getValue();
		        	tribe_min_size_value_label.setText(String.valueOf(Global_variables.tribe_min_size));
		        } 
			}
			
		});
		
		JLabel tribe_max_size_slider_label = new JLabel("Tribe Max Size : ");
		JLabel tribe_max_size_value_label = new JLabel(String.valueOf(Global_variables.tribe_max_size));
		JSlider tribe_max_size_slider = new JSlider(JSlider.HORIZONTAL,
				Global_variables.TRIBE_MAX_SIZE_MIN,Global_variables.TRIBE_MAX_SIZE_MAX, Global_variables.tribe_max_size);
		tribe_max_size_slider.addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider)e.getSource();
		        if (!source.getValueIsAdjusting()) {
		        	Global_variables.tribe_max_size = (int)source.getValue();
		        	tribe_max_size_value_label.setText(String.valueOf(Global_variables.tribe_max_size));
		        } 
			}
			
		});
		
		JLabel def_treshold_slider_label = new JLabel("Def Treshold : ");
		JLabel def_treshold_value_label = new JLabel(String.valueOf(Global_variables.def_treshold));
		JSlider def_treshold_size_slider = new JSlider(JSlider.HORIZONTAL,
				Global_variables.DEF_TRESHOLD_MIN,Global_variables.DEF_TRESHOLD_MAX, Global_variables.def_treshold);
		def_treshold_size_slider.addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider)e.getSource();
		        if (!source.getValueIsAdjusting()) {
		        	Global_variables.def_treshold = (int)source.getValue();
		        	def_treshold_value_label.setText(String.valueOf(Global_variables.def_treshold));
		        } 
			}
			
		});
		
		JLabel def_maxR_slider_label = new JLabel("Def MaxR : ");
		JLabel def_maxR_value_label = new JLabel(String.valueOf(Global_variables.def_maxR));
		JSlider def_maxR_size_slider = new JSlider(JSlider.HORIZONTAL,
                Global_variables.DEF_MAXR_MIN,Global_variables.DEF_MAXR_MAX, Global_variables.def_maxR);
		def_maxR_size_slider.addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider)e.getSource();
		        if (!source.getValueIsAdjusting()) {
		        	Global_variables.def_maxR = (int)source.getValue();
		        	def_maxR_value_label.setText(String.valueOf(Global_variables.def_maxR));
		        } 
			}
			
		});

		JLabel hurt_energy_deplete_label = new JLabel("Hurt Energy Deplete : ");
		JLabel hurt_energy_deplete_value_label = new JLabel(String.valueOf(Global_variables.hurt_energy_deplete));
		JSlider hurt_energy_deplete_slider = new JSlider(JSlider.HORIZONTAL,
				Global_variables.HURT_ENERGY_DEPLETE_MIN,Global_variables.HURT_ENERGY_DEPLETE_MAX, Global_variables.hurt_energy_deplete);
		hurt_energy_deplete_slider.addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider)e.getSource();
		        if (!source.getValueIsAdjusting()) {
		        	Global_variables.hurt_energy_deplete = (int)source.getValue();
		        	hurt_energy_deplete_value_label.setText(String.valueOf(Global_variables.hurt_energy_deplete));
		        } 
			}
			
		});
		
		JLabel settlement_decay_rate_label = new JLabel("Settlement Decay Rate : ");
		JLabel settlement_decay_rate_value_label = new JLabel(String.valueOf(Global_variables.settlement_decay_rate));
		JSlider settlement_decay_rate_slider = new JSlider(JSlider.HORIZONTAL,
				Global_variables.SETTLEMENT_DECAY_RATE_MIN,Global_variables.SETTLEMENT_DECAY_RATE_MAX, Global_variables.settlement_decay_rate);
		settlement_decay_rate_slider.addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider)e.getSource();
		        if (!source.getValueIsAdjusting()) {
		        	Global_variables.settlement_decay_rate = (int)source.getValue();
		        	settlement_decay_rate_value_label.setText(String.valueOf(Global_variables.settlement_decay_rate));
		        } 
			}
			
		});
		
		JLabel like_max_cultural_distance_label = new JLabel("Like Max Cultural Distance : ");
		JLabel like_max_cultural_distance_value_label = new JLabel(String.valueOf(Global_variables.like_max_cultural_distance));
		JSlider like_max_cultural_distance_slider = new JSlider(JSlider.HORIZONTAL,
				Global_variables.LIKE_MAX_CULTURAL_DISTANCE_MIN,Global_variables.LIKE_MAX_CULTURAL_DISTANCE_MAX, Global_variables.like_max_cultural_distance);
		like_max_cultural_distance_slider.addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider)e.getSource();
		        if (!source.getValueIsAdjusting()) {
		        	Global_variables.like_max_cultural_distance = (int)source.getValue();
		        	like_max_cultural_distance_value_label.setText(String.valueOf(Global_variables.like_max_cultural_distance));
		        } 
			}
			
		});
		
		JLabel interaction_max_label = new JLabel("Interaction Max : ");
		JLabel interaction_max_value_label = new JLabel(String.valueOf(Global_variables.interaction_max));
		JSlider interaction_max_slider = new JSlider(JSlider.HORIZONTAL,
				Global_variables.INTERACTION_MAX_MIN,Global_variables.INTERACTION_MAX_MAX, Global_variables.interaction_max);
		interaction_max_slider.addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider)e.getSource();
		        if (!source.getValueIsAdjusting()) {
		        	Global_variables.interaction_max = (int)source.getValue();
		        	interaction_max_value_label.setText(String.valueOf(Global_variables.interaction_max));
		        } 
			}
			
		});
		
		JLabel corpse_decay_rate_label = new JLabel("Corpse Decay Rate : ");
		JLabel corpse_decay_rate_value_label = new JLabel(String.valueOf(Global_variables.corpse_decay_rate));
		JSlider corpse_decay_rate_slider = new JSlider(JSlider.HORIZONTAL,
				Global_variables.CORPSE_DECAY_RATE_MIN,Global_variables.CORPSE_DECAY_RATE_MAX, Global_variables.corpse_decay_rate);
		corpse_decay_rate_slider.addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider)e.getSource();
		        if (!source.getValueIsAdjusting()) {
		        	Global_variables.corpse_decay_rate = (int)source.getValue();
		        	corpse_decay_rate_value_label.setText(String.valueOf(Global_variables.corpse_decay_rate));
		        } 
			}
			
		});
		
		JLabel cooperation_give_advantage_label = new JLabel("Cooperation Give Advantage : ");
		JLabel cooperation_give_advantage_value_label = new JLabel(String.valueOf(Global_variables.cooperation_give_advantage));
		JSlider cooperation_give_advantage_slider = new JSlider(JSlider.HORIZONTAL,
				Global_variables.COOPERATION_GIVE_ADVANTAGE_MIN,Global_variables.COOPERATION_GIVE_ADVANTAGE_MAX, Global_variables.cooperation_give_advantage);
		cooperation_give_advantage_slider.addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider)e.getSource();
		        if (!source.getValueIsAdjusting()) {
		        	Global_variables.cooperation_give_advantage = (int)source.getValue();
		        	cooperation_give_advantage_value_label.setText(String.valueOf(Global_variables.cooperation_give_advantage));
		        } 
			}
			
		});
		
		JLabel percentageFood_label = new JLabel("Percentage Food : ");
		JLabel percentageFood_value_label = new JLabel(String.valueOf(Global_variables.percentageFood));
		JSlider percentageFood_slider = new JSlider(JSlider.HORIZONTAL,
                0,100, Global_variables.percentageFood);
		percentageFood_slider.addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider)e.getSource();
		        if (!source.getValueIsAdjusting()) {
		        	Global_variables.percentageFood = (int)source.getValue();
		        	percentageFood_value_label.setText(String.valueOf(Global_variables.percentageFood));
		        } 
			}
			
		});
		
		JLabel percentageRessource_label = new JLabel("Percentage Ressource : ");
		JLabel percentageRessource_value_label = new JLabel(String.valueOf(Global_variables.percentageRessource));
		JSlider percentageRessource_slider = new JSlider(JSlider.HORIZONTAL,
                0,100, Global_variables.percentageRessource);
		percentageRessource_slider.addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider)e.getSource();
		        if (!source.getValueIsAdjusting()) {
		        	Global_variables.percentageRessource = (int)source.getValue();
		        	percentageRessource_value_label.setText(String.valueOf(Global_variables.percentageRessource));
		        } 
			}
			
		});
		
		JLabel percentageBoth_label = new JLabel("Percentage Both : ");
		JLabel percentageBoth_value_label = new JLabel(String.valueOf(Global_variables.percentageBoth));
		JSlider percentageBoth_slider = new JSlider(JSlider.HORIZONTAL,
                0,100, Global_variables.percentageBoth);
		percentageBoth_slider.addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider)e.getSource();
		        if (!source.getValueIsAdjusting()) {
		        	Global_variables.percentageBoth = (int)source.getValue();
		        	percentageBoth_value_label.setText(String.valueOf(Global_variables.percentageBoth));
		        } 
			}
			
		});
		
		renderer = new Renderer2D(WIDTH/2,WIDTH/2,DataManagement.terrain);
		this.addComponentListener(new ComponentListener() {
			@Override
			public void componentHidden(ComponentEvent arg0) {}

			@Override
			public void componentMoved(ComponentEvent arg0) {}

			@Override
			public void componentResized(ComponentEvent arg0) {
					renderer.setSize(getWidth()/2,getWidth()/2);
			}

			@Override
			public void componentShown(ComponentEvent arg0) {}
		});
		JPanel panelControler = new JPanel();
		panelControler.setLayout(new BoxLayout(panelControler,BoxLayout.Y_AXIS));
		
		JButton setupButton = new JButton("Setup");
		setupButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				simulationHaveBeenRun = false;
				DataManagement.terrain.regenerate();
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
		
		JPanel panel_tribe_count = new JPanel();
		panel_tribe_count.add(tribe_count_slider_label);
		panel_tribe_count.add(tribe_count_value_label);
		panelControler.add(panel_tribe_count);
		panelControler.add(tribe_count_slider);
		
		JPanel panel_min_size = new JPanel();
		panel_min_size.add(tribe_min_size_slider_label);
		panel_min_size.add(tribe_min_size_value_label);
		panelControler.add(panel_min_size);
		panelControler.add(tribe_min_size_slider);
		
		JPanel panel_max_size = new JPanel();
		panel_max_size.add(tribe_max_size_slider_label);
		panel_max_size.add(tribe_max_size_value_label);
		panelControler.add(panel_max_size);
		panelControler.add(tribe_max_size_slider);
		
		JPanel def_treshold= new JPanel();
		def_treshold.add(def_treshold_slider_label);
		def_treshold.add(def_treshold_value_label);
		panelControler.add(def_treshold);
		panelControler.add(def_treshold_size_slider);
		
		JPanel def_maxR = new JPanel();
		def_maxR.add(def_maxR_slider_label);
		def_maxR.add(def_maxR_value_label);
		panelControler.add(def_maxR);
		panelControler.add(def_maxR_size_slider);
		
		JPanel hurt_energy_deplete = new JPanel();
		hurt_energy_deplete.add(hurt_energy_deplete_label);
		hurt_energy_deplete.add(hurt_energy_deplete_value_label);
		panelControler.add(hurt_energy_deplete);
		panelControler.add(hurt_energy_deplete_slider);
		
		JPanel settlement_decay_rate = new JPanel();
		settlement_decay_rate.add(settlement_decay_rate_label);
		settlement_decay_rate.add(settlement_decay_rate_value_label);
		panelControler.add(settlement_decay_rate);
		panelControler.add(settlement_decay_rate_slider);
		
		JPanel like_max_cultural_distance = new JPanel();
		like_max_cultural_distance.add(like_max_cultural_distance_label);
		like_max_cultural_distance.add(like_max_cultural_distance_value_label);
		panelControler.add(like_max_cultural_distance);
		panelControler.add(like_max_cultural_distance_slider);
		
		JPanel interaction_max = new JPanel();
		interaction_max.add(interaction_max_label);
		interaction_max.add(interaction_max_value_label);
		panelControler.add(interaction_max);
		panelControler.add(interaction_max_slider);
		
		JPanel corpse_decay_rate = new JPanel();
		corpse_decay_rate.add(corpse_decay_rate_label);
		corpse_decay_rate.add(corpse_decay_rate_value_label);
		panelControler.add(corpse_decay_rate);
		panelControler.add(corpse_decay_rate_slider);
		
		JPanel cooperation_give_advantage = new JPanel();
		cooperation_give_advantage.add(cooperation_give_advantage_label);
		cooperation_give_advantage.add(cooperation_give_advantage_value_label);
		panelControler.add(cooperation_give_advantage);
		panelControler.add(cooperation_give_advantage_slider);
		
		JPanel percentageFood = new JPanel();
		percentageFood.add(percentageFood_label);
		percentageFood.add(percentageFood_value_label);
		panelControler.add(percentageFood);
		panelControler.add(percentageFood_slider);
		
		JPanel percentageRessource = new JPanel();
		percentageRessource.add(percentageRessource_label);
		percentageRessource.add(percentageRessource_value_label);
		panelControler.add(percentageRessource);
		panelControler.add(percentageRessource_slider);
		
		JPanel percentageBoth = new JPanel();
		percentageBoth.add(percentageBoth_label);
		percentageBoth.add(percentageBoth_value_label);
		panelControler.add(percentageBoth);
		panelControler.add(percentageBoth_slider);
		
		add(panelControler,BorderLayout.WEST);
		
		add(renderer,BorderLayout.CENTER);
		
		
		JPanel trackPanel = new JPanel();
		trackPanel.setLayout(new BoxLayout(trackPanel,BoxLayout.Y_AXIS));
		XYDataset dataset = XYPlotChart.createDataSet(Action.all_action_max,0);
        JFreeChart chart = XYPlotChart.createChart(dataset, "Action Performed","time","percentage");
        chartPanelAllAction = new ChartPanel(chart);
        chartPanelAllAction.setPreferredSize(new java.awt.Dimension(500, 270));

        
        XYDataset datasetActionSolo = XYPlotChart.createDataSet(Action.solo_action_max,0);
        JFreeChart chartSoloAction = XYPlotChart.createChart(datasetActionSolo, "Solo Action Performed","time","percentage");
        chartPanelActionSolo = new ChartPanel(chartSoloAction);
        chartPanelActionSolo.setPreferredSize(new java.awt.Dimension(500, 270));

        XYDataset datasetActionInteraction = XYPlotChart.createDataSet(Action.interaction_max,4);
        
        JFreeChart chartSoloInteraction = XYPlotChart.createChart(datasetActionInteraction, "Interaction Action Performed","time","percentage");
        chartPanelActionInteraction = new ChartPanel(chartSoloInteraction);
        chartPanelActionInteraction.setPreferredSize(new java.awt.Dimension(500, 270));
        
        trackPanel.add(chartPanelAllAction);
        trackPanel.add(chartPanelActionSolo);
        trackPanel.add(chartPanelActionInteraction);
        
        add(trackPanel,BorderLayout.EAST);
		
		int delay = 60;
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
		XYPlot plot  = (XYPlot) chartPanelAllAction.getChart().getXYPlot();
		plot.setDataset(XYPlotChart.updateDataSet(DataManagement.datas.actions_performed,0));
		chartPanelAllAction.repaint();
		
		XYPlot plot2  = (XYPlot) chartPanelActionSolo.getChart().getXYPlot();
		plot2.setDataset(XYPlotChart.updateDataSet(DataManagement.datas.soloaction_performed,0));
		chartPanelActionSolo.repaint();
		
		XYPlot plot3  = (XYPlot) chartPanelActionInteraction.getChart().getXYPlot();
		plot3.setDataset(XYPlotChart.updateDataSet(DataManagement.datas.interaction_performed,4));
		chartPanelActionInteraction.repaint();
	}
	
}
