package AE3;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

class BondChart extends JPanel implements ActionListener{
	
	private static final long serialVersionUID = 1L;
	JPanel upperPanel = new JPanel();
	JButton openBtn = new JButton("Open");
	JTextField titleField = new JTextField(20);
	JButton quitBtn = new JButton("Quit");
	
	/*JComponent bondChart = new JComponent() {
	};*/
	
	JPanel bondChartPanel = new JPanel();
	
	JPanel lowerPanel = new JPanel();
	JTextField xAxisTf = new JTextField(10);
	JTextField yAxisTf = new JTextField(10);
	JTextArea commentField = new JTextArea(3, 20);
	
	String[] columnHeader = { "YIELD", "DAYS_TO_MATURITY", "AMOUNT_CHF"};
	JComboBox<String> xAixsCombBx = new JComboBox<String>(columnHeader);
	JComboBox<String> yAixsCombBx = new JComboBox<String>(columnHeader);
	
	JFrame frame = new JFrame();
	//Create Layout Manager
	
	List<BondData> csvBondDataList = new ArrayList<BondData>();
	
	public BondChart(String title){
		
		frame.setTitle(title);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		ActionListener btnActionListener =  new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {}
		};

			
			//set combobox actionListner
			btnActionListener = new ActionListener() {//add actionlistner to listen for change
	            @Override
	            public void actionPerformed(ActionEvent e) {
	            	

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
	        					int i = 0;
	        					while((line = br.readLine()) != null){
	        						
	        						String[] lineData = line.split(",");
	        						bondArr.add(lineData);
	        						
	        						BondData bondData = new BondData(i ,line.split(",")[0], line.split(",")[1], line.split(",")[2], "");
	        						csvBondDataList.add(bondData);
	        						i++;
	        					}
	        					
	        					System.out.println("csvBondDataList.size() : " + csvBondDataList.size());
	        					
	        					br.close();
	        					/*Scatterplot scatterplot = new Scatterplot();
	        					
	        					scatterplot.createAndShowGui(bondChartPanel, csvBondDataList, (String)xAixsCombBx.getSelectedItem(), (String)yAixsCombBx.getSelectedItem());
	        					frame.setVisible(true);*/
	        					
	        					createScatterplot();
	        					
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
	        };
	        
        openBtn.addActionListener(btnActionListener);
		quitBtn.addActionListener(btnActionListener);
        xAixsCombBx.addActionListener(this);
        yAixsCombBx.addActionListener(this);
		
		upperPanel.add(openBtn);
		upperPanel.add(titleField);
		upperPanel.add(quitBtn);
		lowerPanel.add(xAixsCombBx);

		yAixsCombBx.setSelectedIndex(1);
		lowerPanel.add(yAixsCombBx);
		lowerPanel.add(commentField);
		
		Container container = frame.getContentPane();
		
		container.add(upperPanel,BorderLayout.NORTH);
		container.add(bondChartPanel,BorderLayout.CENTER);
		container.add(lowerPanel,BorderLayout.SOUTH);
		
		frame.setSize(700, 800);
		
		frame.setVisible(true);
		
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e){
		
		String s1 = (String) xAixsCombBx.getSelectedItem();//get the selected item
		String s2 = (String) yAixsCombBx.getSelectedItem();//get the selected item
		
		switch (s1) {//check for a match
        case "YIELD":
            System.out.println("YIELD selected, emailvalue:");
            createScatterplot();
            break;
        case "DAYS_TO_MATURITY":
            System.out.println("DAYS_TO_MATURITY selected, emailvalue:");
            createScatterplot();
            break;
        case "AMOUNT_CHF":
            System.out.println("AMOUNT_CHF selected, emailvalue:");
            createScatterplot();
            break;
		}
		
		switch (s2) {//check for a match
		case "YIELD":
			System.out.println("YIELD selected, emailvalue:");
			createScatterplot();
			break;
		case "DAYS_TO_MATURITY":
			System.out.println("DAYS_TO_MATURITY selected, emailvalue:");
			createScatterplot();
			break;
		case "AMOUNT_CHF":
			System.out.println("AMOUNT_CHF selected, emailvalue:");
			createScatterplot();
			break;
		}
		
	}
	
	public void createScatterplot() {
		bondChartPanel.removeAll();
		Scatterplot scatterplot = new Scatterplot();
		scatterplot.createAndShowGui(bondChartPanel, csvBondDataList, (String)xAixsCombBx.getSelectedItem(), (String)yAixsCombBx.getSelectedItem());
		frame.setVisible(true);
	}
	
	
	
}
























