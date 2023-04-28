package Services;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;

import java.awt.BorderLayout;
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
import java.util.ArrayList;

import Etities.Manager;
import Etities.Employee;
import Etities.Client;
import Etities.Employee.Position;
import Etities.Kozmetic;
import Etities.Recepcionist;
import au.com.bytecode.opencsv.CSVWriter;
import net.miginfocom.swing.MigLayout;

public class ManagerService {
	private List<Object> allEntities;
	
	JFrame frame;
	
	JDialog dialog;
    JPanel panel;
    JLabel userLabel;
    JTextField userText;
    JLabel passLabel;
    JPasswordField passText;
    JTextField passwordText;
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
    JLabel spremaLabel;
    JTextField spremaText;
    JLabel stazLabel;
    JTextField stazText;
    JLabel tipTretmanaLabel;
    JTextField tipTretmanaText;
    JLabel plataLabel;
    JTextField plataText;
    
    JButton btn;
    String user;
    String pass;
    boolean check;
    String ime;
    String prezime;
    String pol;
    String fon;
    String adresa;
    String sprema;
    int staz;
    String tipTretmana;
    int plata;
	
    BufferedReader reader;
    
    protected JToolBar mainToolbar;
   	protected JButton btnAdd = new JButton();
   	protected JButton btnEdit = new JButton();
   	protected JButton btnDelete = new JButton();
   	protected JButton btnEdit_user = new JButton();
   	protected JTable table;
	protected TableRowSorter<AbstractTableModel> tableSorter = new TableRowSorter<AbstractTableModel>();
	
	public ManagerService() {
		EntitiesFromCSV();
	}
	
	public List<Object> GetAllEntities() {
		return allEntities;
	}
	
	public void SetAllEntities(List<Object> allEntities_) {
		allEntities = allEntities_;
	}

	private void EntitiesFromCSV() {
		allEntities = new ArrayList<Object>();
		
		BufferedReader reader = null;
		String line = "";
		
		try {
			reader = new BufferedReader(new FileReader("src\\Data\\Users.csv"));
			while((line = reader.readLine()) != null) {
				String[] nextRow = line.split(",");
				switch(nextRow[2]) {	//m - manager, k - kozmetic, r - receprionist, c - client
				case "m":
					Manager manager = new Manager(nextRow[3], nextRow[4], nextRow[0], nextRow[1], nextRow[5], nextRow[6], nextRow[7], Integer.parseInt(nextRow[8]));
					System.out.println(manager.toString());
					allEntities.add(manager);
					break;
				case "k":
					String[] treatmentType = nextRow[8].split(";");
					Kozmetic kozmetic = new Kozmetic(nextRow[3], nextRow[4], nextRow[0], nextRow[1], nextRow[5], nextRow[6], nextRow[7], treatmentType, nextRow[9], Integer.parseInt(nextRow[10]), Integer.parseInt(nextRow[11]));
					System.out.println(kozmetic.toString());
					allEntities.add(kozmetic);
					break;
				case "r":
					Recepcionist recepcionist = new Recepcionist(nextRow[3], nextRow[4], nextRow[0], nextRow[1], nextRow[5], nextRow[6], nextRow[7], nextRow[8], Integer.parseInt(nextRow[9]), Integer.parseInt(nextRow[10]));
					System.out.println(recepcionist.toString());
					allEntities.add(recepcionist);
					break;
				case "c":
					Client client = new Client(nextRow[3], nextRow[4], nextRow[0], nextRow[1], nextRow[5], nextRow[6], nextRow[7], Integer.parseInt(nextRow[8]));
					System.out.println(client.toString());
					allEntities.add(client);
					break;
				default:
					break;
				}				
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void WriteToCSV() {
		
	}
	
	public void PrintAllallEntities() {
		for(Object entity : allEntities) {
			System.out.println(entity.toString());
		}
	}
	
	public void AddEmployee() {
		dialog = new JDialog();
		panel = new JPanel();
		
		dialog.setSize(400, 1000);
		
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (int) ((dimension.getWidth() - dialog.getWidth()) / 2);
	    int y = (int) ((dimension.getHeight() - dialog.getHeight()) / 2);
	    dialog.setLocation(x, y);
		
	    dialog.setTitle("Registracija radnika");
	    //CLOSING EVENT
	    dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
	    dialog.addWindowListener(new WindowAdapter() {
	    	@Override
	    	public void windowClosing(WindowEvent e) {
	    		int opt = JOptionPane.showConfirmDialog(null, "Do you want to close this window?", "close", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
	    		if (opt == JOptionPane.YES_OPTION) {
	    			e.getWindow().dispose();
	    		}
	    	}
	    });
		dialog.add(panel);
		
		panel.setLayout(new MigLayout("wrap, insets 20, fill", "[center]20[center]20[center]20[center]20[center]", "[center]20[center]")); //arg 2 broj col, arg 3 br redova

		//BITNE INFORMACIJE
		String[] optionsToChoose = {"r", "k", "m"};
		final JComboBox<String> jComboBox = new JComboBox<>(optionsToChoose);
        jComboBox.setBounds(80, 50, 140, 20);
        panel.add(jComboBox, "wrap, left");
		
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
        
        //OSTALE INFORMACIJE
		imeLabel = new JLabel();
		
		imeLabel.setText("Ime");
		imeLabel.setBounds(10, 20, 80, 25);
		panel.add(imeLabel);
		
		imeText = new JTextField(20); //20 je duzina textfields aka 20 karaktera
		imeText.setBounds(100, 20, 165, 25);
		panel.add(imeText, "wrap, left");

		prezimeLabel = new JLabel();
		
		prezimeLabel.setText("Prezime");
		prezimeLabel.setBounds(10, 20, 80, 25);
		panel.add(prezimeLabel);
		
		prezimeText = new JTextField(20); //20 je duzina textfields aka 20 karaktera
		prezimeText.setBounds(100, 20, 165, 25);
		panel.add(prezimeText, "wrap, left");
		
		polLabel = new JLabel();
		
		polLabel.setText("Pol");
		polLabel.setBounds(10, 20, 80, 25);
		panel.add(polLabel);
		
		polText = new JTextField(20); //20 je duzina textfields aka 20 karaktera
		polText.setBounds(100, 20, 165, 25);
		panel.add(polText, "wrap, left");
		
		fonLabel = new JLabel();
		
		fonLabel.setText("Broj telefona");
		fonLabel.setBounds(10, 20, 80, 25);
		panel.add(fonLabel);
		
		fonText = new JTextField(20); //20 je duzina textfields aka 20 karaktera
		fonText.setBounds(100, 20, 165, 25);
		panel.add(fonText, "wrap, left");
		
		adresaLabel = new JLabel();
		
		adresaLabel.setText("Adresa");
		adresaLabel.setBounds(10, 20, 80, 25);
		panel.add(adresaLabel);
		
		adresaText = new JTextField(20); //20 je duzina textfields aka 20 karaktera
		adresaText.setBounds(100, 20, 165, 25);
		panel.add(adresaText, "wrap, left");
		
		spremaLabel = new JLabel();
		
		spremaLabel.setText("Sprema");
		spremaLabel.setBounds(10, 20, 80, 25);
		panel.add(spremaLabel);
		
		spremaText = new JTextField(20); //20 je duzina textfields aka 20 karaktera
		spremaText.setBounds(100, 20, 165, 25);
		panel.add(spremaText, "wrap, left");
		
		stazLabel = new JLabel();
		
		stazLabel.setText("Staz");
		stazLabel.setBounds(10, 20, 80, 25);
		panel.add(stazLabel);
		
		stazText = new JTextField(20); //20 je duzina textfields aka 20 karaktera
		stazText.setBounds(100, 20, 165, 25);
		panel.add(stazText, "wrap, left");
		
		
		tipTretmanaLabel = new JLabel();
		
		tipTretmanaLabel.setText("Tipovi tretmana(tip1;tip2;tip3)");
		tipTretmanaLabel.setBounds(10, 20, 80, 25);
		panel.add(tipTretmanaLabel);
		
		tipTretmanaText = new JTextField(20); //20 je duzina textfields aka 20 karaktera
		tipTretmanaText.setBounds(100, 20, 165, 25);
		tipTretmanaText.setEnabled(false);
		panel.add(tipTretmanaText, "wrap, left");
		
		jComboBox.addActionListener(new ActionListener() {
        	@Override
			public void actionPerformed(ActionEvent e) {
        		if (jComboBox.getItemAt(jComboBox.getSelectedIndex()).equals("k")) {
        			tipTretmanaText.setEnabled(true);
        		}
        		else {
        			tipTretmanaText.setEnabled(false);
        			tipTretmanaText.setText("");
        			if (jComboBox.getItemAt(jComboBox.getSelectedIndex()).equals("m")) {
        				spremaText.setEnabled(false);
        				stazText.setEnabled(false);
        			}
        			else {
        				spremaText.setEnabled(true);
        				stazText.setEnabled(true);
        			}
        		}
        	}
        });
		
		//WRITE
		btn = new JButton("Register user");
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
					if (!jComboBox.getItemAt(jComboBox.getSelectedIndex()).equals("m")) {
						sprema = spremaText.getText();
						staz = Integer.parseInt(stazText.getText());
						tipTretmana = tipTretmanaText.getText();
					}
									
					//REGEX PROVERA FONA
			        Pattern pattern_fon = Pattern.compile("^(\\d{3}[- .]?){2}\\d{4}$"); //555-123-4567
			        Matcher matcher_fon = pattern_fon.matcher(fon);
			        boolean found2 = matcher_fon.find();
					
			        if (found2 == false) {
						JFrame optFrame = new JFrame();
						JOptionPane.showMessageDialog(optFrame, "Wrong input!", "Input Error Message",JOptionPane.OK_OPTION);
					}
					else if (found2 == true) {
			        
						if(!jComboBox.getItemAt(jComboBox.getSelectedIndex()).equals("m")) {
							if (sprema.equals("niska") && staz < 10) {
								plata = 400;	
							}
							else if (sprema.equals("niska") && staz > 10) {
								plata = 600;
							}
							else if (sprema.equals("srednja") && staz < 10) {
								plata = 500;
							}
							else if (sprema.equals("srednja") && staz > 10) {
								plata = 700;
							}
							else if (sprema.equals("visoka") && staz < 10) {
								plata = 600;
							}
							else if (sprema.equals("visoka") && staz > 10) {
								plata = 800;
							}
						}
						
						if (pass.equals("") || user.equals("")) {
							JFrame optFrame = new JFrame();
							JOptionPane.showMessageDialog(optFrame, "Wrong input!", "Input Error Message", JOptionPane.OK_OPTION);
						}
						else {
							check = false;
							
							for(Object entity : allEntities) {
								if(user.equals(((Employee) entity).GetUsername()) && pass.equals(((Employee) entity).GetPassword())) {
									check = true;
									break;
								}
							}
							if (check == true) {
								JFrame optFrame = new JFrame();
								JOptionPane.showMessageDialog(optFrame, "User already exists!", "Input Error Message", JOptionPane.OK_OPTION);
							}
							else {
								FileWriter writer;
								try {
									writer = new FileWriter("src\\Data\\Users.csv", true);
									String ispis = "";
									if (jComboBox.getItemAt(jComboBox.getSelectedIndex()).equals("r")) {							
										ispis = user + "," + pass + "," + jComboBox.getItemAt(jComboBox.getSelectedIndex()) + "," + ime + "," + prezime + "," + pol + "," + fon + "," + adresa + "," + sprema + "," + staz + "," + plata + "\n";
										Recepcionist newRecepcionist = new Recepcionist(ime, prezime, user, pass, pol, fon, adresa, sprema, staz, plata);
										allEntities.add(newRecepcionist);
									}
									else if (jComboBox.getItemAt(jComboBox.getSelectedIndex()).equals("k")) {
										ispis = user + "," + pass + "," + jComboBox.getItemAt(jComboBox.getSelectedIndex()) + "," + ime + "," + prezime + "," + pol + "," + fon + "," + adresa + "," + tipTretmana + "," + sprema + "," + staz + "," + plata + "\n";
										String[] tipTretmanaNiz = tipTretmana.split(";");
										Kozmetic newKozmetic = new Kozmetic(ime, prezime, user, pass, pol, fon, adresa, tipTretmanaNiz, sprema, staz, plata);
										allEntities.add(newKozmetic);
									}
									else if (jComboBox.getItemAt(jComboBox.getSelectedIndex()).equals("m")) {
										ispis = user + "," + pass + "," + jComboBox.getItemAt(jComboBox.getSelectedIndex()) + "," + ime + "," + prezime + "," + pol + "," + fon + "," + adresa + ",0\n"; //MYB DOESNT NEED TO EXIST
										Manager newManager = new Manager(ime, prezime, user, pass, pol, fon, adresa, 100000);
										allEntities.add(newManager);
									}
									writer.write(ispis);
									writer.close();
									
									JOptionPane.showMessageDialog(null, "Korisnik kreiran!", "Information message", JOptionPane.INFORMATION_MESSAGE);
									dialog.dispose();
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

	public void AllEntitiesTableView() {
		frame = new JFrame();
    	panel = new JPanel();
    	
    	frame.setSize(700, 300);
    	Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    final int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
	    final int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
	    frame.setLocation(x, y);
	    
	    frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	    frame.addWindowListener(new WindowAdapter() {
	    	@Override
	    	public void windowClosing(WindowEvent e) {
	    		int opt = JOptionPane.showConfirmDialog(null, "Do you want to close this window?", "close", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
	    		if (opt == JOptionPane.YES_OPTION) {
	    			e.getWindow().dispose();
	    		}
	    	}
	    });
	    
	    //TABELA
		int lines = allEntities.size();

		Object[][] data = new Object[lines][2];
		int i = 0;
		for (Object entity : allEntities) {
			data[i][0] = ((Employee) entity).GetUsername();
			if (((Employee) entity).GetPosition().equals(Position.MANAGER)) {
				data[i][1] = "manager";
			}
			else if (((Employee) entity).GetPosition().equals(Position.RECEPCIONIST)) {
				data[i][1] = "recepcionist";
			}
			else if (((Employee) entity).GetPosition().equals(Position.KOZMETIC)) {
				data[i][1] = "kozmetic";
			}
			else if (((Employee) entity).GetPosition().equals(Position.CLIENT)) {
				data[i][1] = "client";
			}
			i++;
		}
		
		mainToolbar = new JToolBar();
		ImageIcon deleteIcon = new ImageIcon("src\\img\\remove.gif");
		btnDelete.setIcon(deleteIcon);
		btnDelete.setText("Delete user");
		mainToolbar.add(btnDelete);		
		mainToolbar.setFloatable(false);		
		frame.add(mainToolbar, BorderLayout.NORTH);
		
		ImageIcon editIcon = new ImageIcon("src\\img\\edit.gif");
		btnEdit_user.setIcon(editIcon);
		btnEdit_user.setText("See stats");
		mainToolbar.add(btnEdit_user);		
		mainToolbar.setFloatable(false);		
		frame.add(mainToolbar, BorderLayout.NORTH);
		
		String[] collNames = {"Korisnicko ime", "Pozicija"};
		
		final JTable table = new JTable(data, collNames);
		table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getTableHeader().setReorderingAllowed(false);
		table.setPreferredScrollableViewportSize(new Dimension(500, 150));
		table.setFillsViewportHeight(true);
		
		tableSorter.setModel((AbstractTableModel) table.getModel());
		table.setRowSorter(tableSorter);
		
		JScrollPane scrollPane = new JScrollPane(table);
		panel.add(scrollPane);
		
		btnDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int red = table.getSelectedRow();
				if(red == -1) {
					JOptionPane.showMessageDialog(null, "Morate odabrati red u tabeli.", "Greska", JOptionPane.WARNING_MESSAGE);
				}
				else {
					String entitet_za_brisanje = table.getValueAt(red, 0).toString();
					
					if(entitet_za_brisanje != null) {
						int izbor = JOptionPane.showConfirmDialog(null,"Da li ste sigurni da zelite da obrisete entitet?", 
								entitet_za_brisanje + " - Potvrda brisanja", JOptionPane.YES_NO_OPTION);
						if(izbor == JOptionPane.YES_OPTION) {					
							for (Object entity : allEntities) {
								if (((Employee) entity).GetUsername().equals(entitet_za_brisanje)) {
									allEntities.remove(entity);
									break;
								}
							}
							
							try {			     
								WriteEntitiesToCSV();
								
								JOptionPane.showMessageDialog(null, "Entitet uklonjen!", "Information message", JOptionPane.INFORMATION_MESSAGE);
								frame.dispose();
							} catch (IOException e1) {
								e1.printStackTrace();
							}
						}
					}
					else {
						JOptionPane.showMessageDialog(null, "Nije moguce pronaci entitet!", "Greska", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		
		btnEdit_user.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int red = table.getSelectedRow();
				if(red == -1) {
					JOptionPane.showMessageDialog(null, "Morate odabrati red u tabeli.", "Greska", JOptionPane.WARNING_MESSAGE);
				}
				else {
					String username_izabranog = table.getValueAt(red, 0).toString();
					
					for (Object entity : allEntities) {							
						if (username_izabranog.equals(((Employee) entity).GetUsername())) {
							if (((Employee) entity).GetPosition().equals(Position.KOZMETIC))
								Detaljan_pregled(((Employee) entity).GetPosition(), ((Employee) entity).GetName(), ((Employee) entity).GetSurame(), ((Employee) entity).GetUsername(), ((Employee) entity).GetPassword(), ((Employee) entity).GetAddress(), ((Employee) entity).GetPhone(), ((Employee) entity).GetSex(), ((Employee) entity).GetSprema(), ((Employee) entity).GetStaz(), ((Employee) entity).GetPay(), ((Kozmetic) entity).GettreatmentTypeInStringFormat());
							else
								Detaljan_pregled(((Employee) entity).GetPosition(), ((Employee) entity).GetName(), ((Employee) entity).GetSurame(), ((Employee) entity).GetUsername(), ((Employee) entity).GetPassword(), ((Employee) entity).GetAddress(), ((Employee) entity).GetPhone(), ((Employee) entity).GetSex(), ((Employee) entity).GetSprema(), ((Employee) entity).GetStaz(), ((Employee) entity).GetPay(), "");
						}
					}
				}
			}
		});
		
		frame.add(panel);
		frame.setTitle("Entity table");
		frame.setVisible(true);
	}
	
	public void Detaljan_pregled(Position zvanje_, String ime_, String prezime_, String username_, String pass_, String adress_, String phone_, String pol_, String sprema_, int staz_, int plata_, String tipTretmana_) {
		JFrame detaljan = new JFrame("Detaljan pregled");
		JPanel detaljan_panel = new JPanel();
		MigLayout ml = new MigLayout("wrap 3", "[][][]", "[]10[]10[]10[]20[]");
		detaljan_panel.setLayout(ml);
		
		detaljan.setSize(500, 500);
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (int) ((dimension.getWidth() - detaljan.getWidth()) / 2);
	    int y = (int) ((dimension.getHeight() - detaljan.getHeight()) / 2);
	    detaljan.setLocation(x, y);
		
	    detaljan.setTitle("Izmena radnika");
	    //CLOSING EVENT
	    detaljan.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
	    detaljan.addWindowListener(new WindowAdapter() {
	    	@Override
	    	public void windowClosing(WindowEvent e) {
	    		int opt = JOptionPane.showConfirmDialog(null, "Do you want to close this window?", "close", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
	    		if (opt == JOptionPane.YES_OPTION) {
	    			e.getWindow().dispose();
	    		}
	    	}
	    });
		
		userLabel = new JLabel();
		
		userLabel.setText("Username");
		userLabel.setBounds(10, 20, 80, 25);
		detaljan_panel.add(userLabel);
		
		userText = new JTextField(20); //20 je duzina textfields aka 20 karaktera
		userText.setBounds(100, 20, 165, 25);
		userText.setText(username_);
		detaljan_panel.add(userText, "wrap, left");

		passLabel = new JLabel();

		passLabel.setText("Password");
		passLabel.setBounds(10, 50, 80, 25);
		detaljan_panel.add(passLabel);
		
		passwordText = new JTextField(20); //20 je duzina textfields aka 20 karaktera
		passwordText.setBounds(100, 50, 165, 25);
		passwordText.setText(pass_);
		detaljan_panel.add(passwordText, "wrap, left");				
        
        //OSTALE INFORMACIJE
		imeLabel = new JLabel();
		
		imeLabel.setText("Ime");
		imeLabel.setBounds(10, 20, 80, 25);
		detaljan_panel.add(imeLabel);
		
		imeText = new JTextField(20); //20 je duzina textfields aka 20 karaktera
		imeText.setBounds(100, 20, 165, 25);
		imeText.setText(ime_);
		detaljan_panel.add(imeText, "wrap, left");

		prezimeLabel = new JLabel();
		
		prezimeLabel.setText("Prezime");
		prezimeLabel.setBounds(10, 20, 80, 25);
		detaljan_panel.add(prezimeLabel);
		
		prezimeText = new JTextField(20); //20 je duzina textfields aka 20 karaktera
		prezimeText.setBounds(100, 20, 165, 25);
		prezimeText.setText(prezime_);
		detaljan_panel.add(prezimeText, "wrap, left");
		
		polLabel = new JLabel();
		
		polLabel.setText("Pol");
		polLabel.setBounds(10, 20, 80, 25);
		detaljan_panel.add(polLabel);
		
		polText = new JTextField(20); //20 je duzina textfields aka 20 karaktera
		polText.setBounds(100, 20, 165, 25);
		polText.setText(pol_);
		detaljan_panel.add(polText, "wrap, left");
        
		fonLabel = new JLabel();
		
		fonLabel.setText("Broj telefona");
		fonLabel.setBounds(10, 20, 80, 25);
		detaljan_panel.add(fonLabel);
		
		fonText = new JTextField(20); //20 je duzina textfields aka 20 karaktera
		fonText.setBounds(100, 20, 165, 25);
		fonText.setText(phone_);
		detaljan_panel.add(fonText, "wrap, left");
		
		adresaLabel = new JLabel();
		
		adresaLabel.setText("Adresa");
		adresaLabel.setBounds(10, 20, 80, 25);		
		detaljan_panel.add(adresaLabel);
		
		adresaText = new JTextField(20); //20 je duzina textfields aka 20 karaktera
		adresaText.setBounds(100, 20, 165, 25);
		adresaText.setText(adress_);
		detaljan_panel.add(adresaText, "wrap, left");
		
		if (zvanje_.equals(Position.RECEPCIONIST) || zvanje_.equals(Position.KOZMETIC)) {
			spremaLabel = new JLabel();
			
			spremaLabel.setText("Sprema");
			spremaLabel.setBounds(10, 20, 80, 25);
			detaljan_panel.add(spremaLabel);
			
			spremaText = new JTextField(20); //20 je duzina textfields aka 20 karaktera
			spremaText.setBounds(100, 20, 165, 25);
			spremaText.setText(sprema_);
			detaljan_panel.add(spremaText, "wrap, left");
			
			stazLabel = new JLabel();
			
			stazLabel.setText("Staz");
			stazLabel.setBounds(10, 20, 80, 25);
			detaljan_panel.add(stazLabel);
			
			stazText = new JTextField(20); //20 je duzina textfields aka 20 karaktera
			stazText.setBounds(100, 20, 165, 25);
			stazText.setText(((Integer) staz_).toString());
			detaljan_panel.add(stazText, "wrap, left");
			
			plataLabel = new JLabel();
			
			plataLabel.setText("Plata");
			plataLabel.setBounds(10, 20, 80, 25);
			detaljan_panel.add(plataLabel);
			
			plataText = new JTextField(20); //20 je duzina textfields aka 20 karaktera
			plataText.setBounds(100, 20, 165, 25);
			plataText.setText(((Integer) plata_).toString());
			detaljan_panel.add(plataText, "wrap, left");
			
			if (zvanje_ == Position.KOZMETIC) {
				tipTretmanaLabel = new JLabel();
				
				tipTretmanaLabel.setText("Tipovi tretmana(tip1;tip2;tip3)");
				tipTretmanaLabel.setBounds(10, 20, 80, 25);
				detaljan_panel.add(tipTretmanaLabel);
				
				tipTretmanaText = new JTextField(20); //20 je duzina textfields aka 20 karaktera
				tipTretmanaText.setBounds(100, 20, 165, 25);
				tipTretmanaText.setText(tipTretmana_);
				detaljan_panel.add(tipTretmanaText, "wrap, left");
			}
		}
		
		btn = new JButton("Save update");
		btn.setBounds(10, 80, 80, 25);
		btn.addActionListener(new ActionListener() { 
			@Override
			public void actionPerformed(ActionEvent e) {
				user = userText.getText();			
				pass = passwordText.getText();
				ime = imeText.getText();
				prezime = prezimeText.getText();
				pol = polText.getText();				
				fon = fonText.getText();
				adresa = adresaText.getText();
				if (zvanje_ == Position.KOZMETIC || zvanje_ == Position.RECEPCIONIST) {
					sprema = spremaText.getText();
					staz = Integer.parseInt(stazText.getText());				
					plata = Integer.parseInt(plataText.getText());
					if (zvanje_ == Position.KOZMETIC)
						tipTretmana = tipTretmanaText.getText();
				}
				
				for (Object entity : allEntities) {
					if (((Employee) entity).GetUsername().equals(user)) {
						((Employee) entity).SetName(ime);
						((Employee) entity).SetSurame(prezime);
						((Employee) entity).SetUsername(user);
						((Employee) entity).SetPassword(pass);
						((Employee) entity).SetSex(pol);
						((Employee) entity).SetPhone(fon);
						((Employee) entity).SetAddress(adresa);
						if (zvanje_ == Position.KOZMETIC || zvanje_ == Position.RECEPCIONIST) {
							((Employee) entity).SetSprema(sprema);
							((Employee) entity).SetStaz(staz);
							((Employee) entity).SetPay(plata);
							if (zvanje_ == Position.KOZMETIC)
								((Kozmetic) entity).SetTreatmentType(tipTretmana);
						}
						System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------");
						System.out.println(entity.toString());				
						System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------");
					}
				}
				
				try {
					WriteEntitiesToCSV();
					
					JOptionPane.showMessageDialog(null, "Entitet promenjen!", "Information message", JOptionPane.INFORMATION_MESSAGE);
					detaljan.dispose();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		detaljan_panel.add(btn);
		
		detaljan.add(detaljan_panel);
		detaljan.setVisible(true);
	}
	
	public void WriteEntitiesToCSV() throws IOException {
		CSVWriter writer = new CSVWriter(new FileWriter("src\\Data\\Users.csv"), ',', CSVWriter.NO_QUOTE_CHARACTER);
		//ISPROBAVANJE WRITE FAJLOVA
		List<String[]> allEntitiesCSVFormat = new ArrayList<String[]>();
		String[] csvFormatedEntity = new String[] {};
		for(Object entity : allEntities) {
			System.out.println(entity.toString());
			if (((Employee) entity).GetPosition().equals(Position.MANAGER))
				csvFormatedEntity = new String[] { ((Employee) entity).GetUsername(), ((Employee) entity).GetPassword(), "m", ((Employee) entity).GetName(), ((Employee) entity).GetSurame(), ((Employee) entity).GetSex(), ((Employee) entity).GetPhone(), ((Employee) entity).GetAddress(), ((Integer) ((Employee) entity).GetPay()).toString() };
			else if (((Employee) entity).GetPosition().equals(Position.RECEPCIONIST))
				csvFormatedEntity = new String[] { ((Employee) entity).GetUsername(), ((Employee) entity).GetPassword(), "r", ((Employee) entity).GetName(), ((Employee) entity).GetSurame(), ((Employee) entity).GetSex(), ((Employee) entity).GetPhone(), ((Employee) entity).GetAddress(), ((Employee) entity).GetSprema(), ((Integer) ((Employee) entity).GetStaz()).toString(), ((Integer) ((Employee) entity).GetPay()).toString() };
			else if (((Employee) entity).GetPosition().equals(Position.KOZMETIC))
				csvFormatedEntity = new String[] { ((Employee) entity).GetUsername(), ((Employee) entity).GetPassword(), "k", ((Employee) entity).GetName(), ((Employee) entity).GetSurame(), ((Employee) entity).GetSex(), ((Employee) entity).GetPhone(), ((Employee) entity).GetAddress(), ((Kozmetic) entity).GettreatmentTypeInStringFormat(), ((Employee) entity).GetSprema(), ((Integer) ((Employee) entity).GetStaz()).toString(), ((Integer) ((Employee) entity).GetPay()).toString() };
			else
				csvFormatedEntity = new String[] { ((Employee) entity).GetUsername(), ((Employee) entity).GetPassword(), "c", ((Employee) entity).GetName(), ((Employee) entity).GetSurame(), ((Employee) entity).GetSex(), ((Employee) entity).GetPhone(), ((Employee) entity).GetAddress(), ((Integer) ((Employee) entity).GetPay()).toString() };
			allEntitiesCSVFormat.add(csvFormatedEntity);
		}
		writer.writeAll(allEntitiesCSVFormat);
		writer.close();
	}
}
