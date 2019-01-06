package AE3;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;


/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Rodrigo
 */
public class Scatterplot extends JPanel {

    private int width = 500;
    private int heigth = 450;
    private int padding = 25;
    private int labelPadding = 25;
    private Color pointColor = new Color(255, 0, 0, 180);
    private Color gridColor = new Color(200, 200, 200, 200);
    private int pointWidth = 4;
    private int numberYDivisions = 15;
    private int numberXDivisions = 15;
    private List<Double> scores;
	
	private ArrayList<Double> selectedXaxis = new ArrayList<Double>();
	private ArrayList<Double> selectedYaxis = new ArrayList<Double>();
	private int maxXaxisValue = 0;
	private int minXaxisValue = 0;
	private int maxYaxisValue = 0;
	private int minYaxisValue = 0;
	private List<BondData> csvBondDataList = new ArrayList<BondData>();
	
	private String xAxisTitle = "";
	private String yAxisTitle = "";

    public Scatterplot() {
    	
    }
    
    public Scatterplot(List<BondData> csvBondDataList, ArrayList<Double> selectedXaxis, ArrayList<Double> selectedYaxis, int maxXaxisValue, int minXaxisValue, int maxYaxisValue, int minYaxisValue) {
    	this.csvBondDataList = csvBondDataList;
    	this.selectedXaxis = selectedXaxis;
    	this.selectedYaxis = selectedYaxis;
    	this.maxXaxisValue = maxXaxisValue;
    	this.minXaxisValue = minXaxisValue;
    	this.maxYaxisValue = maxYaxisValue;
    	this.minYaxisValue = minYaxisValue;
    }
    
    
//    public void createAndShowGui(JPanel bontChartPanel, List<Double> scores, String xAxis, String yAxis) {
	public void createAndShowGui(JPanel bontChartPanel, List<BondData> csvBondDataList, String xAxisTitle, String yAxisTitle) {
		//set into global variable
		this.xAxisTitle = xAxisTitle;
		this.yAxisTitle = yAxisTitle;
		this.csvBondDataList = csvBondDataList;
		
		//csvBondDataList.get(0) is column title
    	for(int i=1; i<this.csvBondDataList.size(); i++){
    		//set xAxis
    		if("YIELD".equals(xAxisTitle)){
    			this.selectedXaxis.add(Double.parseDouble(csvBondDataList.get(i).getYield()));
    			
    		} else if("DAYS_TO_MATURITY".equals(xAxisTitle)){
    			this.selectedXaxis.add(Double.parseDouble(csvBondDataList.get(i).getDays_to_maturity()));
    			
    		} else if("AMOUNT_CHF".equals(xAxisTitle)){
    			this.selectedXaxis.add(Double.parseDouble(csvBondDataList.get(i).getAmount_chf()));
    		}
    		
    		//set yAxis
    		if("YIELD".equals(yAxisTitle)){
    			this.selectedYaxis.add(Double.parseDouble(csvBondDataList.get(i).getYield()));
    			
    		} else if("DAYS_TO_MATURITY".equals(yAxisTitle)){
    			this.selectedYaxis.add(Double.parseDouble(csvBondDataList.get(i).getDays_to_maturity()));
    			
    		} else if("AMOUNT_CHF".equals(yAxisTitle)){
    			this.selectedYaxis.add(Double.parseDouble(csvBondDataList.get(i).getAmount_chf()));
    		}
    	}
    	
    	this.maxXaxisValue = (int) getMaxValue(selectedXaxis);
    	this.minXaxisValue = (int) getMinValue(selectedXaxis);
    	this.maxYaxisValue = (int) getMaxValue(selectedYaxis);
    	this.minYaxisValue = (int) getMinValue(selectedYaxis);
    	
    	System.out.println("maxXaxisValue : " + this.maxXaxisValue);
    	System.out.println("minXaxisValue : " + this.minXaxisValue);
    	System.out.println("maxYaxisValue : " + this.maxYaxisValue);
    	System.out.println("minYaxisValue : " + this.minYaxisValue);
    	
        
        //scores, xAxis, yAxis
//        Scatterplot mainPanel = new Scatterplot();
        Scatterplot mainPanel = new Scatterplot(this.csvBondDataList, selectedXaxis,  selectedYaxis, maxXaxisValue, minXaxisValue, maxYaxisValue, minYaxisValue);
        
        mainPanel.setPreferredSize(new Dimension(500, 450));
        
        bontChartPanel.add(mainPanel);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        double xScale = ((double) getWidth() - (2 * padding) - labelPadding) / (maxXaxisValue - minXaxisValue);
        double yScale = ((double) getHeight() - 2 * padding - labelPadding) / (maxYaxisValue - minYaxisValue);
        
        System.out.println("paintComponent() xScale : " + xScale);
        System.out.println("paintComponent() yScale : " + yScale);
        
        List<Point> graphPoints = new ArrayList<>();
        System.out.println("line 134 csvBondDataList.size() : " + csvBondDataList.size());
        for (int i = 0; i < selectedXaxis.size(); i++) {
        	int x1 = (int) ((maxXaxisValue - selectedXaxis.get(i).intValue()) * xScale + padding);
            int y1 = (int) ((maxYaxisValue - selectedYaxis.get(i).intValue()) * yScale + padding);
//            System.out.println("graphPoints x"+i+" : "+ x1);
//            System.out.println("graphPoints y"+i+" : "+ y1);
            graphPoints.add(new Point(x1, y1));
        }

        // draw white background
        g2.setColor(Color.WHITE);
        g2.fillRect(padding + labelPadding, padding, getWidth() - (2 * padding) - labelPadding, getHeight() - 2 * padding - labelPadding);
        g2.setColor(Color.BLACK);

        // create hatch marks and grid lines for y axis.
        for (int i = 0; i < numberYDivisions + 1; i++) {
            int x0 = padding + labelPadding;
            int x1 = pointWidth + padding + labelPadding;
            int y0 = getHeight() - ((i * (getHeight() - padding * 2 - labelPadding)) / numberYDivisions + padding + labelPadding);
            int y1 = y0;
            if (selectedYaxis.size() > 0) {
                g2.setColor(gridColor);
                g2.drawLine(padding + labelPadding + 1 + pointWidth, y0, getWidth() - padding, y1);
                g2.setColor(Color.BLACK);
                String yLabel = ((int) ((minYaxisValue + (maxYaxisValue - minYaxisValue) * ((i * 1.0) / numberYDivisions)) * 100)) / 100.0 + "";
                FontMetrics metrics = g2.getFontMetrics();
                int labelWidth = metrics.stringWidth(yLabel);
                g2.drawString(yLabel, x0 - labelWidth - 5, y0 + (metrics.getHeight() / 2) - 3);
            }
            g2.drawLine(x0, y0, x1, y1);
        }
        
        
        // and for x axis
        for (int i = 0; i < numberXDivisions+1; i++) {
        	int x0 = i * (getWidth() - padding * 2 - labelPadding) / (numberXDivisions - 1) + padding + labelPadding;
            int x1 = x0;
            int y0 = getHeight() - padding - labelPadding;
            int y1 = y0 - pointWidth;
            System.out.println(i + "|| x0 " + x0  + "|| x1 " +  x1  + "|| y0 " +  y0  + "|| y1 " + y1);
        	if (selectedXaxis.size() > 0) {
        		g2.setColor(gridColor);
        		g2.drawLine(x0, getHeight() - padding - labelPadding - 1 - pointWidth, x1, padding);
        		g2.setColor(Color.BLACK);
        		String xLabel = ((int) ((minXaxisValue + (maxXaxisValue - minXaxisValue) * ((i * 1.0) / numberXDivisions)) * 100)) / 100.0 + "";
        		FontMetrics metrics = g2.getFontMetrics();
        		
        		int labelWidth = metrics.stringWidth(xLabel);
        		g2.drawString(xLabel, x0 - labelWidth / 2, y0 + metrics.getHeight() + 3); //draw interval values
        		
        		System.out.println("yLabel : " + xLabel + "|| x0 - labelWidth - 5 : "+(x0 - labelWidth - 5) + "|| y0 + (metrics.getHeight() / 2) - 3 : " + ( y0 + (metrics.getHeight() / 2) - 3)+"\n");
        	}
        	g2.drawLine(x0, y0, x1, y1);
        }

        
/*        // and for x axis
        for (int i = 0; i < numberXDivisions; i++) {
            if (selectedXaxis.size() > 1) {
                int x0 = i * (getWidth() - padding * 2 - labelPadding) / (selectedXaxis.size() - 1) + padding + labelPadding;
                int x1 = x0;
                int y0 = getHeight() - padding - labelPadding;
                int y1 = y0 - pointWidth;
                if ((i % ((int) ((selectedXaxis.size() / 20.0)) + 1)) == 0) {
                    g2.setColor(gridColor);
                    g2.drawLine(x0, getHeight() - padding - labelPadding - 1 - pointWidth, x1, padding);
                    g2.setColor(Color.BLACK);
                    String xLabel = i + "";
                    FontMetrics metrics = g2.getFontMetrics();
                    int labelWidth = metrics.stringWidth(xLabel);
                    g2.drawString(xLabel, x0 - labelWidth / 2, y0 + metrics.getHeight() + 3);
                }
                g2.drawLine(x0, y0, x1, y1);
            }
        }*/
        
        // create x and y axes 
        g2.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, padding + labelPadding, padding);
        g2.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, getWidth() - padding, getHeight() - padding - labelPadding);

        g2.setColor(pointColor);
        for (int i = 0; i < graphPoints.size(); i++) {
            int x = graphPoints.get(i).x - pointWidth / 2;
            int y = graphPoints.get(i).y - pointWidth / 2;
            int ovalW = pointWidth;
            int ovalH = pointWidth;
            g2.fillOval(x, y, ovalW, ovalH);
        }
    }
    
    
    private double getMaxValue(ArrayList<Double> list) {
    	double maxValue = Double.MIN_VALUE;
    	for (Double value : list) {
    		maxValue = Math.max(maxValue, value);
    	}
    	return maxValue;
    }
    
    private double getMinValue(ArrayList<Double> list) {
    	double minValue = Double.MAX_VALUE;
    	for (Double value : list) {
    		minValue = Math.min(minValue, value);
    	}
    	return minValue;
    }
    

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

}