package ui;

import java.util.ArrayList;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.util.Rotation;

import action.Action;
import data.DataManagement;

public class XYPlotChart {

	public static XYDataset createDataSet(int n, int offset){
	   XYSeriesCollection dataset = new XYSeriesCollection( );
		for(int i=0;i<n;i++){
			XYSeries series = new XYSeries(Action.to_action_type(i+offset).toString());   
			series.add( 0.0 , 0.0 );  
			dataset.addSeries( series );
		}
        return dataset;
	}
	
	public static XYDataset updateDataSet(ArrayList<ArrayList<Integer>> datas,int offset){
		XYSeriesCollection result = new XYSeriesCollection();
		
		for(int i=0;i<datas.size();i++){
			int j=0;
			XYSeries series = new XYSeries(Action.to_action_type(i+offset).toString());   
			while(j<DataManagement.datas.last_frame){
				if(datas.get(i).size()>0){
					series.add( j , datas.get(i).get(j));  
				}
				j++;
			}
			result.addSeries( series );
		}

		
        return result;
	}
	
    public static JFreeChart createChart(XYDataset dataset, String title, String xName,String yName) {

        JFreeChart chart = ChartFactory.createXYLineChart(
        		title, 
        		xName,
        		yName, 
        		dataset,
           PlotOrientation.VERTICAL, 
           true, true, false);
        

        return chart;

    }
	
}
