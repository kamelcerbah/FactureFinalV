import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

public class Verification extends JFrame{
	
	Container content;
	JLabel lab = new JLabel("Entrer Le Mot De Passe");
	JPasswordField pass = new JPasswordField();
	JButton connect = new JButton("Connect");
	
	public void visible(boolean test) {
    	this.setVisible(test);
    }
	
	public Verification(int a) throws IOException {
		// TODO Auto-generated constructor stub
		String filepath = "save\\client"+a+".txt";
		File file = new File(filepath);
		int f=a;
		connect.addActionListener(new ActionListener() {
			
			BufferedReader test = new BufferedReader(new FileReader(file));
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				try {
					for(int i=0;i<5;i++) {
						String j =test.readLine();
					}
							String[] motvrai = test.readLine().split("\t,");
							String k = motvrai[1];
							System.out.print(k);
							String motentrer = String.valueOf(pass.getText());
							if(motentrer.equals(k)) {
								JOptionPane.showMessageDialog(null, "Connection avec succés", "Connection", JOptionPane.INFORMATION_MESSAGE);
								new Edit(f);
							}else {
								JOptionPane.showMessageDialog(null, "Mot De Passe entrer est Faux\n Vous avez ressai a nouveau", "Erreur", JOptionPane.YES_OPTION);
								System.exit(ERROR);
							}
					visible(false);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		
		this.setTitle("Facture");
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setBounds(30, 50, 600, 250);
		
		content=this.getContentPane();
		content.setLayout(null);
		content.add(pass);
		content.add(connect);
		content.add(lab);
		
		lab.setBounds(20, 0, 300, 50);
		pass.setBounds(50, 60, 300, 50);
		connect.setBounds(100, 120, 90, 40);
		
	}
		
}
