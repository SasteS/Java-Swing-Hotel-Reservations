package Frame;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Users.Admin;

public class Logged_User_frame {
	private JFrame frame;
	private JPanel panel;
	private JLabel label;
	private JButton btn;
	
//	private String user;
//	private String pozicija;
	
	public Logged_User_frame(String username, String position) throws IOException {
		frame = new JFrame();
		panel = new JPanel();
		
		frame.setSize(650, 500);
		
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
	    int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
	    frame.setLocation(x, y);
		
	    //CLOSING EVENT
	    frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	    frame.addWindowListener(new WindowAdapter() {
	    	@Override
	    	public void windowClosing(WindowEvent e) {
	    		int opt = JOptionPane.showConfirmDialog(null, "Do you want to close the app?", "close", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
	    		if (opt == JOptionPane.YES_OPTION) {
	    			e.getWindow().dispose();
	    		}
	    	}
	    });
	    
	    
		frame.add(panel);
	    
		//DEO KOJI TREBA DA RADI
		if(position.equals("a")) {
			frame.setTitle("Adnimistrator manager");
			label = new JLabel("Welcome " + username + " na poziciji " + position);
			panel.add(label);
			label = new JLabel("Lista entiteta:");
			panel.add(label);
	
			btn = new JButton("Dodaj zaposlenog");
			panel.add(btn);
			
			Admin admin = new Admin();
			admin.PrikazSvihEndtiteta(this.panel);
		}
		else if(position.equals("r")) {
			frame.setTitle("Recepcionist manager");
			label = new JLabel("Welcome " + username + " na poziciji " + position);
			panel.add(label);
		}
		else if(position.equals("s")) {
			frame.setTitle("Maid manager");
			label = new JLabel("Welcome " + username + " na poziciji " + position);
			panel.add(label);
		}
		else {
			frame.setTitle("Guest manager");
		}
		frame.setVisible(true);
	}
}
