package AssEX3;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.AbstractTableModel;

class MyFrame extends JPanel implements ActionListener{
	
	JPanel upperPanel = new JPanel();
	JButton openBtn = new JButton("Open");
	JTextField titleField = new JTextField(20);
	JButton quitBtn = new JButton("Quit");
	
	/*JComponent bondChart = new JComponent() {
	};*/
	
	JPanel bontChartPanel = new JPanel();
	
	JPanel lowerPanel = new JPanel();
	JTextField xAxisTf = new JTextField(10);
	JTextField yAxisTf = new JTextField(10);
	JTextArea commentField = new JTextArea(3, 20);
	
	String[] columnHeader = { "YIELD", "DAYS_TO_MATURITY", "AMOUNT_CHF"};
	JComboBox<String> xAixsCombBx = new JComboBox<String>(columnHeader);
	JComboBox<String> yAixsCombBx = new JComboBox<String>(columnHeader);
	
	JFrame frame = new JFrame();
	//Create Layout Manager
	
	List<BondData> csvBondDataList;
	
	public MyFrame(String title){
		
		frame.setTitle(title);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		openBtn.addActionListener(this);
		quitBtn.addActionListener(this);

		upperPanel.add(openBtn);
		upperPanel.add(titleField);
		upperPanel.add(quitBtn);
		
		lowerPanel.add(xAixsCombBx);
		lowerPanel.add(yAixsCombBx);
		lowerPanel.add(commentField);
		
		Container container = frame.getContentPane();
		
		container.add(upperPanel,BorderLayout.NORTH);
//		container.add(bondChart,BorderLayout.CENTER);
		container.add(bontChartPanel,BorderLayout.CENTER);
		container.add(lowerPanel,BorderLayout.SOUTH);
		
		/*Scatterplot scatterplot = new Scatterplot();
		scatterplot.createAndShowGui(bontChartPanel);*/
		createAndShowGui(bontChartPanel);
		
		frame.setSize(500, 600);
		
		frame.setVisible(true);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e){
		Object obj = e.getSource();    
		
		if((JButton)obj == openBtn){
			
			JFileChooser chooser = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter(".csv file only", "csv");
			
			chooser.addChoosableFileFilter(filter);
			chooser.setFileFilter(filter);
			
			//get current directory
			File getPath = new File(".");
			String currentPath = getPath.getAbsolutePath();
			
			chooser.setCurrentDirectory(new File(currentPath));
			int ret = chooser.showDialog(null, "Choose file");
			if(ret!=JFileChooser.APPROVE_OPTION){
				JOptionPane.showMessageDialog(null, "A file is not chosen", "alert", JOptionPane.WARNING_MESSAGE);
				return;
			} else if (ret == JFileChooser.APPROVE_OPTION){
				File file = chooser.getSelectedFile();
				file.getAbsoluteFile();
				
				
				csvBondDataList = new ArrayList<BondData>();
				try {
					BufferedReader br = new BufferedReader(new FileReader(file));
					ArrayList<String[]> bondArr = new ArrayList<String[]>();
					
					
					String line = "";
					int i = 0, row =0;
					while((line = br.readLine()) != null){
						
						
						String[] lineData = line.split(",");
						bondArr.add(lineData);
						System.out.println( i+"번째 arr : " + bondArr.get(i)[0]);
						
						
						BondModel bModel = new BondModel();
						
						BondData bondData = new BondData(i ,line.split(",")[0], line.split(",")[1], line.split(",")[2], "");
						csvBondDataList.add(bondData);
						
						//bModel.addRow(i ,line.split(",")[0], line.split(",")[1], line.split(",")[2], "");

						//System.out.println(line);
						i++;
						
					}
					
					System.out.println("csvBondDataList.size() : " + csvBondDataList.size());
					
					for(int j=0; j<csvBondDataList.size(); j++){
						System.out.println("csvBondDataList.get(j).getYield() : " + csvBondDataList.get(j).getYield());
					}
					
					br.close();
					
				} catch (IOException ex) {
					ex.printStackTrace();
				}
				
			}
			
			String filePath = chooser.getSelectedFile().getPath();
			String fileName = chooser.getSelectedFile().getName();
			
			titleField.setText(fileName);
			
		} else if((JButton)obj == quitBtn){
			frame.dispose();
		}
	}
	
	// **** Model ****//
		class BondModel extends AbstractTableModel {

			List<BondData> dataRow;
			String[] columnHeader = { "YIELD", "DAYS_TO_MATURITY", "AMOUNT_CHF(000)","DETAIL"};
			int id = 0;

			public BondModel() {
				dataRow = new ArrayList<BondData>();
			}

			@Override
			public String getColumnName(int column) {
				return columnHeader[column];
			}

			@Override
			public int getColumnCount() {
				return columnHeader.length;
			}

			@Override
			public int getRowCount() {
				return dataRow.size();
			}

			@Override
			public Object getValueAt(int rowIndex, int columnIndex) {
				BondData bondData = dataRow.get(rowIndex);
				switch (columnIndex) {
				case 0:
					return bondData.getYield();
				case 1:
					return bondData.getDays_to_maturity();
				case 2:
					return bondData.getAmount_chf();
				case 3:
					return bondData.getDetail();
				default:
					return null;
				}
			}

			public void addRow(int dataNo, String yield, String days_to_maturity, String amount_chf, String detail) {
				dataRow.add(new BondData(dataNo, yield, days_to_maturity, amount_chf, detail));
				int rowCount = getRowCount();
				fireTableRowsInserted(rowCount, rowCount);
			}

		}
		
		
		/////////////////////////////////////////
		 
		    private int width = 500;
		    private int heigth = 450;
		    private int padding = 25;
		    private int labelPadding = 25;
		    private Color lineColor = new Color(44, 102, 230, 180);
		    private Color pointColor = new Color(100, 100, 100, 180);
		    private Color gridColor = new Color(200, 200, 200, 200);
		    private static final Stroke GRAPH_STROKE = new BasicStroke(2f);
		    private int pointWidth = 4;
		    private int numberYDivisions = 10;
		    private List<Double> scores;
		    
		    public void Scatterplot(List<Double> scores) {
		    	this.scores = scores;
		    }
		    
		    public void createAndShowGui(JPanel bontChartPanel) {
		        List<Double> scores = new ArrayList<>();
		        Random random = new Random();
		        int maxDataPoints = 40;
		        int maxScore = 10;
		        for (int i = 0; i < maxDataPoints; i++) {
		            scores.add((double) random.nextDouble() * maxScore);
//		            scores.add((double) i);
		        }
		        Scatterplot mainPanel = new Scatterplot(scores);
		        mainPanel.setPreferredSize(new Dimension(500, 450));
		        /*JFrame frame = new JFrame("DrawGraph");
		        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		        frame.getContentPane().add(mainPanel);
		        
		        frame.pack();
		        frame.setLocationRelativeTo(null);
		        frame.setVisible(true);
		        */
		        
		        bontChartPanel.add(mainPanel);
		    }
		    
		    //////////////
		    @Override
		    protected void paintComponent(Graphics g) {
		        super.paintComponent(g);
		        Graphics2D g2 = (Graphics2D) g;
		        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		        double xScale = ((double) getWidth() - (2 * padding) - labelPadding) / (scores.size() - 1);
		        double yScale = ((double) getHeight() - 2 * padding - labelPadding) / (getMaxScore() - getMinScore());

		        List<Point> graphPoints = new ArrayList<>();
		        for (int i = 0; i < scores.size(); i++) {
		            int x1 = (int) (i * xScale + padding + labelPadding);
		            int y1 = (int) ((getMaxScore() - scores.get(i)) * yScale + padding);
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
		            if (scores.size() > 0) {
		                g2.setColor(gridColor);
		                g2.drawLine(padding + labelPadding + 1 + pointWidth, y0, getWidth() - padding, y1);
		                g2.setColor(Color.BLACK);
		                String yLabel = ((int) ((getMinScore() + (getMaxScore() - getMinScore()) * ((i * 1.0) / numberYDivisions)) * 100)) / 100.0 + "";
		                FontMetrics metrics = g2.getFontMetrics();
		                int labelWidth = metrics.stringWidth(yLabel);
		                g2.drawString(yLabel, x0 - labelWidth - 5, y0 + (metrics.getHeight() / 2) - 3);
		            }
		            g2.drawLine(x0, y0, x1, y1);
		        }

		        // and for x axis
		        for (int i = 0; i < scores.size(); i++) {
		            if (scores.size() > 1) {
		                int x0 = i * (getWidth() - padding * 2 - labelPadding) / (scores.size() - 1) + padding + labelPadding;
		                int x1 = x0;
		                int y0 = getHeight() - padding - labelPadding;
		                int y1 = y0 - pointWidth;
		                if ((i % ((int) ((scores.size() / 20.0)) + 1)) == 0) {
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
		        }

		        // create x and y axes 
		        g2.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, padding + labelPadding, padding);
		        g2.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, getWidth() - padding, getHeight() - padding - labelPadding);

		        Stroke oldStroke = g2.getStroke();
		        g2.setColor(lineColor);
		        g2.setStroke(GRAPH_STROKE);
		        for (int i = 0; i < graphPoints.size() - 1; i++) {
		            int x1 = graphPoints.get(i).x;
		            int y1 = graphPoints.get(i).y;
		            int x2 = graphPoints.get(i + 1).x;
		            int y2 = graphPoints.get(i + 1).y;
		            g2.drawLine(x1, y1, x2, y2);
		        }

		        g2.setStroke(oldStroke);
		        g2.setColor(pointColor);
		        for (int i = 0; i < graphPoints.size(); i++) {
		            int x = graphPoints.get(i).x - pointWidth / 2;
		            int y = graphPoints.get(i).y - pointWidth / 2;
		            int ovalW = pointWidth;
		            int ovalH = pointWidth;
		            g2.fillOval(x, y, ovalW, ovalH);
		        }
		    }

		    private double getMinScore() {
		        double minScore = Double.MAX_VALUE;
		        for (Double score : scores) {
		            minScore = Math.min(minScore, score);
		        }
		        return minScore;
		    }

		    private double getMaxScore() {
		        double maxScore = Double.MIN_VALUE;
		        for (Double score : scores) {
		            maxScore = Math.max(maxScore, score);
		        }
		        return maxScore;
		    }

		    public void setScores(List<Double> scores) {
		        this.scores = scores;
		        invalidate();
		        this.repaint();
		    }

		    public List<Double> getScores() {
		        return scores;
		    }

	
}




public class bondChart {

	public static void main(String[] args) {
		
		new MyFrame("AE3");
		

	}

}























