package Frame;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

public class Frame_Setup implements ActionListener {	
	private JDialog dialog;
	private JPanel panel;
	private JLabel label;
	private JLabel userLabel;
	private JTextField userText;
	private JLabel passLabel;
	private JPasswordField passText;
	private JButton btn;
	private JLabel success;
	
	private boolean check;
	private String user;
	private String pass;
	private String position;
	
	public Frame_Setup() {
		this.SetFrame();
	}
	
	public void SetFrame() {
		dialog = new JDialog();
		panel = new JPanel();
		
		dialog.setSize(400, 220);
		
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (int) ((dimension.getWidth() - dialog.getWidth()) / 2);
	    int y = (int) ((dimension.getHeight() - dialog.getHeight()) / 2);
	    dialog.setLocation(x, y);
		
	    dialog.setTitle("Prijava");
	    //CLOSING EVENT
	    dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
	    dialog.addWindowListener(new WindowAdapter() {
	    	@Override
	    	public void windowClosing(WindowEvent e) {
	    		int opt = JOptionPane.showConfirmDialog(null, "Do you want to close the app?", "close", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
	    		if (opt == JOptionPane.YES_OPTION) {
	    			e.getWindow().dispose();
	    		}
	    	}
	    });
		dialog.add(panel);
		
		panel.setLayout(new MigLayout("wrap, insets 20, fill", "[center]20[center]", "[center]20[center]")); //arg 2 broj col, arg 3 br redova
		//pogledati tutorijal za detaljnije layout
		
		label = new JLabel("Unesite korisnicko ime i lozinku.");
		panel.add(label, "wrap");

		userLabel = new JLabel();
		
		userLabel.setText("Username");
		userLabel.setBounds(10, 20, 80, 25);
		panel.add(userLabel);
		
		userText = new JTextField(20); //20 je duzina textfields aka 20 karaktera
		userText.setBounds(100, 20, 165, 25);
		panel.add(userText, "wrap, left");
		
		
		passLabel = new JLabel();

		passLabel.setText("Password");
		passLabel.setBounds(10, 50, 80, 25);
		panel.add(passLabel);
		
		passText = new JPasswordField(20); //20 je duzina textfields aka 20 karaktera
		passText.setBounds(100, 50, 165, 25);
		panel.add(passText, "wrap, left");
		
		
		btn = new JButton("Login");
		btn.setBounds(10, 80, 80, 25);
		btn.addActionListener(this);
		panel.add(btn);
		
		success = new JLabel("");
		success.setBounds(10, 110, 300, 25);
		panel.add(success);
		
		dialog.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		this.user = userText.getText();
		char[] passCH = passText.getPassword();
		this.pass = new String(passCH);
		
		this.check = false;
		Collection<String[]> coll = IscitajCSV();
		
		for(String[] item : coll) {
		//	for(String index2 : index1) {
		//		System.out.println(index2);
		//	}
		//	System.out.println();
			if(user.equals(item[0]) && pass.equals(item[1])) {
				success.setText("Login successful");
				this.check = true;
				this.position = item[2];
				dialog.dispose();
				try {
					Logged_User_frame luf = new Logged_User_frame(this.user, this.position);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		if(this.check == false) {
			success.setText("Wrong input!");
		}
	}
	
	private Collection<String[]> IscitajCSV() {
		BufferedReader reader = null;
		String line = "";
		
		try {
			reader = new BufferedReader(new FileReader("src\\Users.csv"));
			
			Collection<String[]> coll = new ArrayList<String[]>();
			while((line = reader.readLine()) != null) {
				String[] red = line.split(",");
				
				coll.add(red);							
			}
			return coll;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;	
	}
	
	public boolean Get_check() { //potencijalno treba
		return this.check;
	}
	
	public String Get_user() { //potencijalno treba
		return this.user;
	}
	
	public String Get_position() { //potencijalno treba
		return this.position;
	}
}
