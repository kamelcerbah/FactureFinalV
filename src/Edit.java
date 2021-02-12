import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.print.PrinterException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringJoiner;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.AbstractTableModel.*;

public class Edit extends JFrame{
	
	Container content = new JPanel();
	String[] colNames = {"Référence","Produit", "Quantité", "Prix","Montant"};
	JScrollPane ScrollPane,ScrollPaneClient;
	Object[] rowData = {"","Total"};
    JLabel lab = new JLabel("La Facture");
    JLabel lab1 = new JLabel("<html><body><center><u>Entreprise National Des Pieces</u><br>15 zone industrielle Rouiba<br>Algerie</center></body></html>");
    JLabel vide = new JLabel("");
    JButton Supp = new JButton("Suprrimer");
    JComboBox<Integer> com = new JComboBox<Integer>();
    JButton Ajout = new JButton("Ajouter");
    JTextField text = new JTextField();
    JButton save = new JButton("Sauvgarder");
    public String facture="";
    String filepath="";
    File test = new File(filepath);
    DefaultTableModel modelf = null,modelclient = null;
    
	public Edit(int b) throws IOException {
		
		this.setTitle("Facture");
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		content=this.getContentPane();
		content.setLayout(new GridLayout(5,2,10,10));
		
		JTable table,client;
		
		filepath = "save\\facture"+b+".txt";
		table = new JTable(modelf);
	    File file = new File(filepath);
	    String [][] imodel = mod(file);
	    
	    int a = imodel.length;
	    /*System.out.println(a);
	    for(int k=0 ; k<a;k++) {
	    	for(int j=0; j<5;j++) {
	    		System.out.println("["+k+"]["+j+"] = "+imodel[k][j]);
	    	}
	    }*/
	    Object [][] emodel = new Object[a][5];
	    for(int i = 0;i<a;i++) {
	    	emodel[i][0] = imodel[i][0];
	    	emodel[i][1] = imodel[i][1];
	    	emodel[i][2] = Integer.parseInt((String)(imodel[i][2]).trim());
	    	emodel[i][3] = Double.parseDouble((String)(imodel[i][3]).trim());
	    	emodel[i][4] = Double.parseDouble((String)(imodel[i][4]).trim());
	    }
	    DefaultTableModel model = new DefaultTableModel(emodel,colNames){
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                switch (columnIndex) {
                    case 0:
                        return String.class;
                    case 1:
                        return String.class;
                    case 2:
                        return Integer.class;
                    case 3:
                        return Double.class;
                    case 4:
                        return Double.class;
                }
                return super.getColumnClass(columnIndex);
            }

            @Override
            public Object getValueAt(int row, int col) {
                if (col == 4 & row == a-1) {
                    double sum = 0;
                    for (int i = 0; i < a-1; i++) {
                        sum += ((Double) getValueAt(i, 4)).doubleValue();
                    }
                    return sum;
                }
                if (col == 4 & row != a-1) {
                    Integer i = (Integer) getValueAt(row, 2);
                    Double d = (Double) getValueAt(row, 3);
                    if (i != null && d != null) {
                        return i * d;
                    } else {
                        return 0d;
                    }
                }
                return super.getValueAt(row, col);
            }

            @Override
            public void setValueAt(Object aValue, int row, int col) {
                super.setValueAt(aValue, row, col);
                fireTableDataChanged();
            }

            @Override
            public boolean isCellEditable(int row, int col) {
            	return ((col != 4)&&(row != a-1));
            }
        };
        table.setModel(model);
        Supp.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				JTextField ent = new JTextField();
				Object[] message = {"référence",ent};
				int Deloption = JOptionPane.showConfirmDialog(null, message, "Entrer La Référence", JOptionPane.OK_CANCEL_OPTION);
				int x = model.getRowCount()-1;
				for(int i = 0;i<x;i++) {
					String test = ent.getText();
					if(test.equals(model.getValueAt(i, 0))) {
						model.removeRow(i);
					}else {
						vide= new JLabel("La référence est non reconnu");
					}
				}
				com.removeAllItems();
				int y = model.getRowCount()-1;
				for(int i=0;i<y;i++) {
		        	com.addItem(i+1);
		        }
				
				
			}
		});
        Ajout.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int t = Integer.parseInt(text.getText().toString().trim());
				Object [] row = {"","",0,0.0,0.0};
				int v=t+a-1;
					modelf = new DefaultTableModel(emodel,colNames){
						@Override
			            public Class<?> getColumnClass(int columnIndex) {
			                switch (columnIndex) {
			                    case 0:
			                        return String.class;
			                    case 1:
			                        return String.class;
			                    case 2:
			                        return Integer.class;
			                    case 3:
			                        return Double.class;
			                    case 4:
			                        return Double.class;
			                }
			                return super.getColumnClass(columnIndex);
			            }

			            @Override
			            public Object getValueAt(int row, int col) {
			                if (col == 4 & row == v) {
			                    double sum = 0;
			                    for (int i = 0; i < v; i++) {
			                        sum += ((Double) getValueAt(i, 4)).doubleValue();
			                    }
			                    return sum;
			                }
			                if (col == 4 & row != v) {
			                    Integer i = (Integer) getValueAt(row, 2);
			                    Double d = (Double) getValueAt(row, 3);
			                    if (i != null && d != null) {
			                        return i * d;
			                    } else {
			                        return 0d;
			                    }
			                }
			                return super.getValueAt(row, col);
			            }

			            @Override
			            public void setValueAt(Object aValue, int row, int col) {
			                super.setValueAt(aValue, row, col);
			                fireTableDataChanged();
			            }

			            @Override
			            public boolean isCellEditable(int row, int col) {
			            	return ((col != 4)&&(row != v));
			            }
			        };
			        modelf.removeRow(a-1);
			        for(int i = 0;i < t;i++) {
						modelf.addRow(row);
					}
			        modelf.addRow(rowData);
			        table.setModel(modelf);
			        com.removeAllItems();
			        int y = model.getRowCount()-1;
					for(int i=0;i<y;i++) {
			        	com.addItem(i+1);
			        }
			        
				}
				
		});

	    //model = createModel(file);
	    //int a = model.getRowCount();
	    table.repaint();
//	    System.out.println(a);
	    filepath = "save\\client"+b+".txt";
	    client = new JTable(modelclient);
	    file = new File(filepath);
	    modelclient = createModel(file);
	    client.setModel(modelclient);
	    ScrollPane = new JScrollPane(table);
	    ScrollPaneClient = new JScrollPane(client);
        int size = 50;
        lab.setFont(new Font("Bold",Font.PLAIN,size));
        lab1.setFont(new Font("Bold",Font.CENTER_BASELINE,15));
        for(int i=0;i<a-1;i++) {
        	com.addItem(i+1);
        }
        lab1.setPreferredSize(new Dimension(250,100));
        lab.setPreferredSize(new Dimension(100,100));
        ScrollPane.setPreferredSize(new Dimension(1000,300));
        ScrollPaneClient.setPreferredSize(new Dimension(500,100));
        Ajout.setPreferredSize(new Dimension(100,50));
        Supp.setPreferredSize(new Dimension(100,50));
        com.setPreferredSize(new Dimension(100,50));
        text.setPreferredSize(new Dimension(100,50));
        

        content.add(lab1);
        content.add(save);
        content.add(lab);
        content.add(ScrollPaneClient);
        content.add(vide);
        content.add(ScrollPane);
        content.add(Supp);
        content.add(Ajout);
        content.add(com);
        content.add(text);
        this.setVisible(true);
        this.setBounds(0, 0, 1366, 768);;
        
        save.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//read(client);
				//save(client);
				filepath="save\\client"+b+".txt";
				//read(client);
				save(client);
				filepath="save\\facture"+b+".txt";
				//read(table);
				save(table);
				try {
					client.print();
					table.print();
				} catch (PrinterException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
        



				
	}
	public String[][] mod(File file) throws IOException {
		try {
			BufferedReader txtReader = new BufferedReader(new FileReader(file));
			String header = txtReader.readLine();
			int f=0;
			while(txtReader.readLine() != null) {
				f++;
			}
			BufferedReader read = new BufferedReader(new FileReader(file));
			header = read.readLine();
			String[][] mod = new String[f][5] ;
			String [] row = new String[5];
			int i =0;
			String line;
			while((line = read.readLine()) != null) {
				row = line.split("\t,");
				for(int j = 0;j < 5; j++ ) {
					if(row[j] != null) {
						mod[i][j] = row[j];
					}else {
						mod[i][j] = "0";
					}
										
				}
				i++;
			}
			return mod;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;		
		}
	private DefaultTableModel createModel(File file) {
	       DefaultTableModel model = null;
	       try {
	           BufferedReader txtReader = new BufferedReader(
	                   new FileReader(file));
	           String header = txtReader.readLine();
	           String line;
	           
        	   model = new DefaultTableModel(header.split(","), 0){
               	@Override
            	public boolean isCellEditable(int row, int col) {
                	return (col == 1 && ((row == 2 )||(row == 3 )));
                }
            };
        	   while ((line = txtReader.readLine()) != null) {
	               model.addRow(line.split("\t,"));
	           }
	       } catch (IOException ex) {
	           ex.printStackTrace();
	       }
	       return model;
	   }
    public void save(JTable table) {
    	//read(table);
    	try (BufferedWriter bw = new BufferedWriter(new FileWriter(new File(filepath)))) {
    	    StringJoiner joiner = new StringJoiner(",");
    	    bw.write(facture);
    	    for (int col = 0; col < table.getColumnCount(); col++) {
    	        joiner.add(table.getColumnName(col));
    	    }
    	    //System.out.println(joiner.toString());
    	    bw.write(joiner.toString());
    	    bw.newLine();
    	    for (int row = 0; row < table.getRowCount(); row++) {
    	        joiner = new StringJoiner("\t,");
    	        for (int col = 0; col < table.getColumnCount(); col++) {
    	            Object obj = table.getValueAt(row, col);
    	            String value = obj == null ? "0" : obj.toString();
    	            joiner.add(value);
    	        }
    	        //System.out.println(joiner.toString());
    	        bw.write(joiner.toString());
    	        bw.newLine();
    	    }
    	} catch (IOException exp) {
    	    exp.printStackTrace();
    	}
    	facture="";
    	}
    public void read(JTable table) {
    	try {
			BufferedReader br = new BufferedReader(new FileReader(new File(filepath)));
			for(int i=0;i<br.read();i++) {
				facture+=String.valueOf(br.readLine())+"\n";
				//System.out.println(facture);
				
			}
		} catch (IOException exp) {
			// TODO: handle exception
			exp.printStackTrace();
		}
    }
    public int getNum(File file) throws IOException {
    	int s = 0;
    	try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String t="Numéro";
			int f = 0;
			while (f<=br.read()) {
				String line = br.readLine();
				if(line.contains(t)) {
					String digits="";
					for (int i = 0; i < line.length(); i++) {
		                char chrs = line.charAt(i);              
		                if (Character.isDigit(chrs))
		                    digits = digits+chrs;
		            }
					int num = Integer.parseInt(digits.trim());
					return (num+1);
				}
				f++;
			}
			return 1;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 1;
		}
    }
    
    public void visible(boolean test) {
    	this.setVisible(test);
    }

}