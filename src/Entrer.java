import java.awt.Container;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.IOException;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;


public class Entrer extends JFrame{
	
	Container content;
	JLabel label = new JLabel("<html><center>Pour Creer une Facture <br>Entrer Le Nombre D'objet</center></html>");
	JLabel label2 = new JLabel("<html><center>Pour Editer une Facture<br>Choisi Le Numéro de client</center></html>");
	JTextField text = new JTextField();
	JButton creer = new JButton("Créer");
	JButton edit = new JButton("Editer");
	JComboBox<Integer> combo = new JComboBox<Integer>();
	String path="save\\";
	File file = new File(path);
	JMenuBar menu = new JMenuBar();
	JMenuItem help = new JMenuItem("Aide");
	JMenuItem author = new JMenuItem("A propros");
	
	public Entrer() {
		this.setBounds(0, 0, 480, 200);
		this.setTitle("Entrer");
		this.setVisible(true);
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		content=this.getContentPane();
		content.setLayout(null);
		
		content.add(label);
		content.add(text);
		content.add(creer);
		content.add(combo);
		content.add(label2);
		content.add(edit);
		content.add(menu);
		menu.add(help);
		menu.add(author);
		this.setJMenuBar(menu);
		
		
		label.setBounds(40, 10, 200, 30);
		text.setBounds(10, 50, 200, 30);
		creer.setBounds(70, 100, 100, 30);
		label2.setBounds(260, 10, 200, 30);
		combo.setBounds(240, 50, 200, 30);
		edit.setBounds(280, 100, 100, 30);
		
		int fileCount = (file.list().length)/2;
		for (int i=0;i<fileCount;i++) {
			combo.addItem(i+1);
		}
		help.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String url = "Aide.html";
				File htmlFile = new File(url);
				try {
					Desktop.getDesktop().browse(htmlFile.toURI());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		author.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JOptionPane.showMessageDialog(null, "<html><center><u><strong>Edité par :</strong></u><br>RAHMANI ABD EL KADER<br>SEIF EL ISLEM<br> Groupe 1 Section 1</center></html>", "A propros", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		creer.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				int a = Integer.parseInt(text.getText().toString().trim());
				try {
					new Traitement(a);
					visible(false);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		edit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int b = (int) combo.getSelectedItem();
				//JOptionPane.showMessageDialog(content, "N'est Pas Complet", "A maintenance",JOptionPane.INFORMATION_MESSAGE);
				SwingUtilities.invokeLater(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						try {
							new Verification(b);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
				}
		});
		
		
	}
	public void visible(boolean test) {
    	this.setVisible(test);
    }
	

}
