package ui;

import java.util.ArrayList;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;

import action.Action;
import data.DataManagement;

public class PieChart {

	public static PieDataset createDataSet(int n, int offset){
		DefaultPieDataset result = new DefaultPieDataset();
		for(int i=0;i<n;i++)
			result.setValue(Action.to_action_type(i+offset).toString(), 0);
        return result;
	}
	
	public static PieDataset updateDataSet(ArrayList<ArrayList<Integer>> datas,int offset){
		DefaultPieDataset result = new DefaultPieDataset();
		for(int i=0;i<datas.size();i++){
			if(datas.get(i).size()>0)
				result.setValue(Action.to_action_type(i+offset).toString(), datas.get(i).get(DataManagement.datas.last_frame));
		}
        return result;
	}
	
    public static JFreeChart createChart(PieDataset dataset, String title) {

        JFreeChart chart = ChartFactory.createPieChart3D(
            title,                  // chart title
            dataset,                // data
            true,                   // include legend
            true,
            false
        );

        PiePlot3D plot = (PiePlot3D) chart.getPlot();
        plot.setStartAngle(290);
        plot.setDirection(Rotation.CLOCKWISE);
        plot.setForegroundAlpha(0.5f);
        return chart;

    }
	
}
