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
						//System.out.println( i+"번째 arr : " + bondArr.get(i)[0]);
						
						
						BondModel bModel = new BondModel();
						
						BondData bondData = new BondData(i ,line.split(",")[0], line.split(",")[1], line.split(",")[2], "");
						csvBondDataList.add(bondData);
						
						//bModel.addRow(i ,line.split(",")[0], line.split(",")[1], line.split(",")[2], "");

						//System.out.println(line);
						i++;
						
					}
					
					System.out.println("csvBondDataList.size() : " + csvBondDataList.size());
					
					for(int j=0; j<csvBondDataList.size(); j++){
						System.out.println("csvBondDataList.get(j).getYield() : " + csvBondDataList.get(0).getYield());
						System.out.println("csvBondDataList.get(j).getYield() : " + csvBondDataList.get(0).getAmount_chf());
						System.out.println("csvBondDataList.get(j).getYield() : " + csvBondDataList.get(0).getDays_to_maturity());
						
					}
					
					br.close();
					List<Double> scores = null;
					Scatterplot scatterplot = new Scatterplot(scores, (String)xAixsCombBx.getSelectedItem(), (String)yAixsCombBx.getSelectedItem());
					
					scatterplot.createAndShowGui(bontChartPanel, scores, (String)xAixsCombBx.getSelectedItem(), (String)yAixsCombBx.getSelectedItem());
					frame.setVisible(true);
					
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
					return bondData.getComment();
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
	
	
}




public class bondChart {

	public static void main(String[] args) {
		
		new MyFrame("AE3");
		

	}

}























