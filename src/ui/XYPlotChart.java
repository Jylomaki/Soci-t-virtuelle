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
	
	public static ArrayList<XYSeries> initSeries(int n, int offset){
		ArrayList<XYSeries> series = new ArrayList<XYSeries>();
		for(int i=0;i<n;i++){
			XYSeries serie = new XYSeries(Action.to_action_type(i+offset).toString());   
			serie.add( 0.0 , 0.0 );  
			series.add(serie);
		}
		return series;
	}
	
	public static XYDataset createDataSet(String serieName){
		   XYSeriesCollection dataset = new XYSeriesCollection( );
			
			XYSeries series = new XYSeries(serieName);   
			series.add( 0.0 , 0.0 );  
			dataset.addSeries( series );
			
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
	
	public static XYDataset updateDataSet(ArrayList<Integer> datas,String serieName){
		XYSeriesCollection result = new XYSeriesCollection();
		
		int j = 0;
		XYSeries series = new XYSeries(serieName);   
		while(j<DataManagement.datas.last_frame){
			
				series.add( j , datas.get(j));  
			
			j++;
		}
		result.addSeries( series );
	

	
        return result;
	}
	
	public static void addSeries(int data,XYSeries serie){
		serie.add(DataManagement.datas.last_frame,data);
	}
	
	
	public static void addSeries(ArrayList<ArrayList<Integer>> datas,ArrayList<XYSeries> series){
		for(int i=0;i<datas.size();i++)
			series.get(i).add(DataManagement.datas.last_frame,datas.get(i).get(DataManagement.datas.last_frame));
	}
	
	public static void setSerie(XYSeriesCollection dataset,XYSeries series){
		dataset.addSeries( series );
	}
	

	public static void setSeries(XYSeriesCollection dataset,ArrayList<XYSeries> series){
		for(XYSeries serie:series)
			dataset.addSeries( serie );
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
