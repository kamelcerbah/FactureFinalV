import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.awt.print.PrinterException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.Date;
import java.util.EventObject;
import java.util.StringJoiner;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.*;

import com.spire.pdf.PdfDocument;
import com.spire.pdf.PdfPageBase;
import com.spire.pdf.graphics.PdfPen;
import com.spire.pdf.graphics.PdfRGBColor;
import com.spire.pdf.tables.BeginRowLayoutEventArgs;
import com.spire.pdf.tables.BeginRowLayoutEventHandler;
import com.spire.pdf.tables.PdfCellStyle;
import com.spire.pdf.tables.PdfTable;
import com.spire.pdf.tables.PdfTableStyle;


public class Traitement extends JFrame{
	
	Container content;
	String[] colNames = {"Référence","Produit", "Quantité", "Prix","Montant"};
	JTable table,client;
	DefaultTableModel model,modelclient;
	JScrollPane ScrollPane,ScrollPaneClient;
	Object[] rowData = {"","Total"};
    JLabel lab = new JLabel("La Facture");
    JLabel lab1 = new JLabel("<html><center><u>Entreprise National Des Pieces</u><br>15 zone industrielle Rouiba<br>Algerie</center></html>");
    JLabel vide = new JLabel("");
    JButton Supp = new JButton("Suprrimer");
    JComboBox<Integer> com = new JComboBox<Integer>();
    JButton Ajout = new JButton("Ajouter");
    JTextField text = new JTextField();
    JButton save = new JButton("Sauvgarder");
    //public String facture="";
    String filepath = "save\\";
    File test = new File(filepath);
    PdfDocument doc = new PdfDocument();
    
    
    public Traitement(int a) throws IOException {
    	
		this.setTitle("Facture");
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		content=this.getContentPane();
		content.setLayout(new GridLayout(5,2,10,10));
		
		model = new DefaultTableModel(colNames, a) {
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
                if (col == 4 & row == a) {
                    double sum = 0;
                    for (int i = 0; i < a; i++) {
                        sum += ((Double) getValueAt(i, 4)).doubleValue();
                    }
                    return sum;
                }
                if (col == 4 & row != a) {
                    Integer i = (Integer) getValueAt(row, 2);
                    Double d = (Double) getValueAt(row, 3);
                    if (i != null && d != null) {
                        return i * d;
                    } else {
                        return 0d;
                    }
                }
                //System.out.println(row);
                //System.out.println(col);
                return super.getValueAt(row, col);
            }

            @Override
            public void setValueAt(Object aValue, int row, int col) {
                super.setValueAt(aValue, row, col);
                fireTableDataChanged();
            }

            @Override
            public boolean isCellEditable(int row, int col) {
            	return ((col != 4)&&(row != a));
            }
        };
        modelclient = new DefaultTableModel() {
        	
        	@Override
        	public boolean isCellEditable(int row, int col) {
            	return (col == 1 && ((row == 2 )||(row == 3 )||(row == 4 )));
            }
        };
        table = new JTable(model);
        model.addRow(rowData);
        TableColumn nameColumn = table.getColumnModel().getColumn(0);
        ScrollPane = new JScrollPane(table);
        client = new JTable(modelclient);
        modelclient.addColumn("constant");//pour Edition il faut un nom pour les  collones
        modelclient.addColumn("echangeable");
        Date date = new Date();
        int num = getNum(test);
        String [] row1= {" Numéro",""+num};
        String [] row2= {" Date",date.toString()};
        String [] row3= {" Nom de Client",""};
        String [] row4= {" Prenom de Client",""};
        String [] row5= {"Mot De Passe",""}; 
        modelclient.addRow(row1);
        modelclient.addRow(row2);
        modelclient.addRow(row3);
        modelclient.addRow(row4);
        modelclient.addRow(row5);
        for(int n=0; n<modelclient.getRowCount();n++) {
        	if( n == 4 ) {
    			JPasswordField pass = new JPasswordField();
        		CellEditor cell = new DefaultCellEditor(pass);
        		client.getColumnModel().getColumn(1).setCellEditor((TableCellEditor) cell);
        		
    		}
        }
        ScrollPaneClient = new JScrollPane(client);
        int size = 50;
        lab.setFont(new Font("Bold",Font.PLAIN,size));
        lab1.setFont(new Font("Bold",Font.CENTER_BASELINE,15));
        for(int i=0;i<a;i++) {
        	com.addItem(i+1);
        }
        Supp.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				int b = (int) com.getSelectedItem();
				visible(false);
				try {
					new Traitement(a-b);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
        Ajout.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int b = Integer.parseInt(text.getText().toString().trim());
				visible(false);
				try {
					new Traitement(a+b);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}			
			}
		});

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
        
        this.setBounds(0, 0, 1366, 768);;
        
        save.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//read(client);
				//save(client);
				filepath="save\\client"+num+".txt";
				//read(client);
				save(client);
				filepath="save\\facture"+num+".txt";
				//read(table);
				save(table);
				//client.print();
				//table.print();
				//content.print(null);
				try {
					savePDF();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
				
			}
		});
    }
    public void save(JTable table) {
    	//read(table);
    	try (BufferedWriter bw = new BufferedWriter(new FileWriter(new File(filepath)))) {
    	    StringJoiner joiner = new StringJoiner(",");
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
    	//facture="";
    	}
    /*public void read(JTable table) {
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
    }*/
    public int getNum(File file) throws IOException {
    	return (((file.list().length)/3)+1) ;
    }
    
    public void visible(boolean test) {
    	this.setVisible(test);
    }
    public void savePDF() throws IOException {

    	tabletostring(client, modelclient);
    	tabletostring(table, model);
    	int num = getNum(test);
		saves(doc,num);
		
    }
    public void tabletostring(JTable table,DefaultTableModel model){
		
		int x = model.getRowCount();
		int y = model.getColumnCount();
		String[][] retour = new String[x][y];
		for(int i=0;i<x;i++) {
			for(int j=0;j<y;j++) {
				
				if(model.getValueAt(i, j)!=null) {
					retour[i][j]= model.getValueAt(i, j).toString();
				}else {
					retour[i][j]="";
				}
			}
		}
		
		tabletopdf(retour);
		
	}
	 
	public void tabletopdf(String[][] dataSource) {
        PdfPageBase page = doc.getPages().add();
        //Create a pdf table
        PdfTable table = new PdfTable();
        //Set data source of the pdf table
        table.setDataSource(dataSource);
        //Set the color of table border
        PdfTableStyle style = new PdfTableStyle();
        style.setCellPadding(2);
        style.setBorderPen(new PdfPen(new PdfRGBColor(new Color(128,128,128)), 1f));
        table.setStyle(style);
        //Add custom method to beginRowLayout event
        table.beginRowLayout.add((new BeginRowLayoutEventHandler() {
            @Override
            public void invoke(Object sender, BeginRowLayoutEventArgs args) {
                table_BeginRowLayout(sender, args);
            }
        }));
        //Draw the pdf table into pdf document
        table.draw(page, new Point2D.Float(50, 100));
	
	}
	static void table_BeginRowLayout(Object sender, BeginRowLayoutEventArgs args) {
        //Set the color of table cell border
        PdfCellStyle cellStyle = new PdfCellStyle();
        cellStyle.setBorderPen(new PdfPen(new PdfRGBColor(new Color(173, 216, 230)), 0.9));
        args.setCellStyle(cellStyle);
    }
	public void saves(PdfDocument doc,int num) {
		doc.saveToFile("save\\facture"+num+".pdf");
        doc.close();
	}
}
