package Frame;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import Etities.Employee;
import Services.ManagerService;
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
	
	private boolean check;
	private String user;
	private String pass;
	private String position;
	
	JFrame frame;
	JLabel imeLabel;
	JTextField imeText;
	JLabel prezimeLabel;
	JTextField prezimeText;
	JLabel polLabel;
	JTextField polText;
	JLabel fonLabel;
	JTextField fonText;
	JLabel adresaLabel;
	JTextField adresaText;
	
	String ime;
	String prezime;
	String pol;
	String fon;
	String adresa;
	
	public Frame_Setup() {
		RegisterLoginDialog();
	}
	
	public void RegisterLoginDialog() {
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
		
		JButton register = new JButton("Register");
		JButton login = new JButton("Log in");
		
		register.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e1) {
				try {
	    			dialog.dispose();
	    			SetFrameRegister();
				}
				catch(Exception e2) {
					System.out.println("error during login.");
				}
			}
		});
		panel.add(register);
		
		login.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e1) {
				try {
	    			dialog.dispose();
	    			SetFrameLogin();
				}
				catch(Exception e2) {
					System.out.println("error during login.");
				}
			}
		});
		panel.add(login);
		
		dialog.setVisible(true);
	}
	
	public void SetFrameRegister() {
		dialog = new JDialog();
		panel = new JPanel();

		dialog.setSize(400, 1000);

		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - dialog.getWidth()) / 2);
		int y = (int) ((dimension.getHeight() - dialog.getHeight()) / 2);
		dialog.setLocation(x, y);

		dialog.setTitle("Registracija gostiju");
		// CLOSING EVENT
		dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		dialog.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				int opt = JOptionPane.showConfirmDialog(null, "Do you want to close this window?", "close",
						JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
				if (opt == JOptionPane.YES_OPTION) {
					e.getWindow().dispose();
				}
			}
		});
		dialog.add(panel);

		panel.setLayout(new MigLayout("wrap, insets 20, fill", "[center]20[center]20[center]20[center]20[center]",
				"[center]20[center]")); // arg 2 broj col, arg 3 br redova
		// pogledati tutorijal za detaljnije layout

		// BITNE INFORMACIJE
		userLabel = new JLabel();

		userLabel.setText("Username(email)");
		userLabel.setBounds(10, 20, 80, 25);
		panel.add(userLabel);

		userText = new JTextField(20); // 20 je duzina textfields aka 20 karaktera
		userText.setBounds(100, 20, 165, 25);
		panel.add(userText, "wrap, left");

		passLabel = new JLabel();

		passLabel.setText("Password");
		passLabel.setBounds(10, 50, 80, 25);
		panel.add(passLabel);

		passText = new JPasswordField(20); // 20 je duzina textfields aka 20 karaktera
		passText.setBounds(100, 50, 165, 25);
		panel.add(passText, "wrap, left");

		// OSTALE INFORMACIJE
		imeLabel = new JLabel();

		imeLabel.setText("Ime");
		imeLabel.setBounds(10, 20, 80, 25);
		panel.add(imeLabel);

		imeText = new JTextField(20); // 20 je duzina textfields aka 20 karaktera
		imeText.setBounds(100, 20, 165, 25);
		panel.add(imeText, "wrap, left");

		prezimeLabel = new JLabel();

		prezimeLabel.setText("Prezime");
		prezimeLabel.setBounds(10, 20, 80, 25);
		panel.add(prezimeLabel);

		prezimeText = new JTextField(20); // 20 je duzina textfields aka 20 karaktera
		prezimeText.setBounds(100, 20, 165, 25);
		panel.add(prezimeText, "wrap, left");

		polLabel = new JLabel();

		polLabel.setText("Pol");
		polLabel.setBounds(10, 20, 80, 25);
		panel.add(polLabel);

		polText = new JTextField(20); // 20 je duzina textfields aka 20 karaktera
		polText.setBounds(100, 20, 165, 25);
		panel.add(polText, "wrap, left");

		fonLabel = new JLabel();

		fonLabel.setText("Broj telefona");
		fonLabel.setBounds(10, 20, 80, 25);
		panel.add(fonLabel);

		fonText = new JTextField(20); // 20 je duzina textfields aka 20 karaktera
		fonText.setBounds(100, 20, 165, 25);
		panel.add(fonText, "wrap, left");

		adresaLabel = new JLabel();

		adresaLabel.setText("Adresa");
		adresaLabel.setBounds(10, 20, 80, 25);
		panel.add(adresaLabel);

		adresaText = new JTextField(20); // 20 je duzina textfields aka 20 karaktera
		adresaText.setBounds(100, 20, 165, 25);
		panel.add(adresaText, "wrap, left");

		// WRITE
		btn = new JButton("Register guest");
		btn.setBounds(10, 80, 80, 25);
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					user = userText.getText();
					char[] passCH = passText.getPassword();
					pass = new String(passCH);
					ime = imeText.getText();
					prezime = prezimeText.getText();
					pol = polText.getText();
					fon = fonText.getText();
					adresa = adresaText.getText();
					
					//REGEX PROVERA MAIL UNOSA
					Pattern pattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
					Matcher matcher = pattern.matcher(user);
			        boolean found = matcher.find();
			        
			        //REGEX PROVERA FONA
			        Pattern pattern_fon = Pattern.compile("^(\\d{3}[- .]?){2}\\d{4}$");
			        Matcher matcher_fon = pattern_fon.matcher(fon);
			        boolean found2 = matcher_fon.find();
			        
					if (found == false || found2 == false) {
						JFrame optFrame = new JFrame();
						JOptionPane.showMessageDialog(optFrame, "Wrong input!", "Input Error Message",JOptionPane.OK_OPTION);
					}
					else if (found == true && found2 == true) {
						if (pass.equals("") || user.equals("")) {
							JFrame optFrame = new JFrame();
							JOptionPane.showMessageDialog(optFrame, "Wrong input!", "Input Error Message",
									JOptionPane.OK_OPTION);
						} else {
							check = false;
							ManagerService managerService = new ManagerService();
							List<Object> allEntities = managerService.GetAllEntities();
	
							for (Object entity : allEntities) {
								if (user.equals(((Employee) entity).GetUsername()) && pass.equals(((Employee) entity).GetPassword())) {
									check = true;
									break;
								}
							}
							if (check == true) {
								JFrame optFrame = new JFrame();
								JOptionPane.showMessageDialog(optFrame, "User already exists!", "Input Error Message",
										JOptionPane.OK_OPTION);
							} else {
								FileWriter writer;
								try {
									writer = new FileWriter("src\\Data\\Users.csv", true);
									String ispis = user + "," + pass + ",c," + ime + "," + prezime + "," + pol + "," + fon + "," + adresa + ",100000\n";
									writer.write(ispis);
									writer.close();
									JOptionPane.showMessageDialog(null, "Korisnik kreiran!", "Information message",
											JOptionPane.INFORMATION_MESSAGE);
									dialog.dispose();
									SetFrameLogin();
								} catch (IOException e1) {
									e1.printStackTrace();
								}
							}
						}
					}
				}
				catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "Popuniti sva polja!", "Information message", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		panel.add(btn);

		dialog.setVisible(true);
	}
	
	public void SetFrameLogin() {
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
			if(user.equals(item[0]) && pass.equals(item[1])) {
				this.check = true;
				this.position = item[2];
				dialog.dispose();
				try {
					Logged_User_frame luf = new Logged_User_frame(this.user, this.position);
				} catch (IOException | ParseException e1) {
					e1.printStackTrace();
				}
			}
		}
		if(this.check == false) {
			JFrame optFrame = new JFrame();
			JOptionPane.showMessageDialog(optFrame, "Wrong input!", "Input Error Message", JOptionPane.OK_OPTION);
		}
	}
	
	private Collection<String[]> IscitajCSV() {
		BufferedReader reader = null;
		String line = "";
		
		try {
			reader = new BufferedReader(new FileReader("src\\Data\\Users.csv"));
			
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
