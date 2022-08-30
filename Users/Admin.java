package Users;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
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
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;

import au.com.bytecode.opencsv.CSVWriter;
import net.miginfocom.swing.MigLayout;

public class Admin extends Korisnik {
	JFrame lista_entiteta;
    JPanel lista_entiteta_panel;
    
    JDialog dialog;
    JPanel panel;
    JLabel userLabel;
    JTextField userText;
    JLabel passLabel;
    JPasswordField passText;
    JLabel imeLabel;
    JTextField imeText;
    JLabel prezimeLabel;
    JTextField prezimeText;
    JLabel polLabel;
    JTextField polText;
    JLabel dateLabel;
    //JTextField dateText;
    JLabel fonLabel;
    JTextField fonText;
    JLabel adresaLabel;
    JTextField adresaText;
    JLabel spremaLabel;
    JTextField spremaText;
    JLabel stazLabel;
    JTextField stazText;
    
    JButton btn;
    String user;
    String pass;
    boolean check;
    String ime;
    String prezime;
    String pol;
    String date;
    String fon;
    String adresa;
    String sprema;
    int staz;
    int plata;
    
    BufferedReader reader;
	
    protected JToolBar mainToolbar;
	protected JButton btnAdd = new JButton();
	protected JButton btnEdit = new JButton();
	protected JButton btnDelete = new JButton();
	protected JButton btnEdit_user = new JButton();
	protected JTextField tfSearch = new JTextField(20);
	protected JTable table;
	protected TableRowSorter<AbstractTableModel> tableSorter = new TableRowSorter<AbstractTableModel>();
    
	JFrame cenovnik_teble;
	JPanel cenovnik_teble_panel;
	JButton btnEdit_cene  = new JButton();
	
	JTextField txt_jednosobna;
	JTextField txt_dvokrevetna;
	JTextField txt_dvokrevetna2;
	JTextField txt_trokrevetna;
	JTextField txt_cetvorokrevetna;
	JTextField txt_dorucak;
	JTextField txt_rucak;
	JTextField txt_vecera;
	JTextField txt_pocetak;
	JTextField txt_kraj;
	
	protected List<Zaposleni> recepcioneri = new ArrayList<Zaposleni>();
	protected List<Zaposleni> sobarice = new ArrayList<Zaposleni>();
	List<String[]> all_users = new ArrayList<String[]>();
	
	public Admin() throws IOException {
		Ucitaj_Zaposlene();
	}	
	
	//metode
	public void Ucitaj_Zaposlene() throws IOException {
		//UCITAVANJE RECEPCIONARA
		BufferedReader reader = new BufferedReader(new FileReader("src\\Users.csv"));
		List<String[]> arr = new ArrayList<String[]>();
		String line = "";
		while((line = reader.readLine()) != null) {
			String[] red = line.split(",");			
			if (red[2].equals("r"))
				arr.add(red);
		}
		
		for (String[] s : arr) {
			Zaposleni recepcioner = new Zaposleni();
			recepcioner.username = s[0];
			recepcioner.password = s[1];
			recepcioner.ime	= s[3];
			recepcioner.prezime = s[4];
			recepcioner.pol = s[5];
			recepcioner.datumRodjenja = LocalDate.parse(s[6]);
			recepcioner.telefon = s[7];
			recepcioner.adresa = s[8];
			recepcioner.sprema = s[9];
			recepcioner.staz = Integer.parseInt(s[10]);
			recepcioner.plata = Integer.parseInt(s[11]);
			System.out.println(recepcioner.username);
			System.out.println(recepcioner.password);
			System.out.println(recepcioner.ime);
			System.out.println(recepcioner.prezime);
			System.out.println(recepcioner.pol);
			System.out.println(recepcioner.datumRodjenja);
			System.out.println(recepcioner.telefon);
			System.out.println(recepcioner.adresa);
			System.out.println(recepcioner.sprema);
			System.out.println(recepcioner.staz);
			System.out.println(recepcioner.plata);
			System.out.println();
			recepcioneri.add(recepcioner);
		}
		
		//UCITAVANJE SOBARICA
		reader = new BufferedReader(new FileReader("src\\Users.csv"));
		arr.clear();
		line = "";
		while((line = reader.readLine()) != null) {
			String[] red = line.split(",");			
			if (red[2].equals("s"))
				arr.add(red);
		}
		
		for (String[] s : arr) {
			Zaposleni sobarica = new Zaposleni();
			sobarica.username = s[0];
			sobarica.password = s[1];
			sobarica.ime	= s[3];
			sobarica.prezime = s[4];
			sobarica.pol = s[5];
			sobarica.datumRodjenja = LocalDate.parse(s[6]);
			sobarica.telefon = s[7];
			sobarica.adresa = s[8];
			sobarica.sprema = s[9];
			sobarica.staz = Integer.parseInt(s[10]);
			sobarica.plata = Integer.parseInt(s[11]);
			System.out.println(sobarica.username);
			System.out.println(sobarica.password);
			System.out.println(sobarica.ime);
			System.out.println(sobarica.prezime);
			System.out.println(sobarica.pol);
			System.out.println(sobarica.datumRodjenja);
			System.out.println(sobarica.telefon);
			System.out.println(sobarica.adresa);
			System.out.println(sobarica.sprema);
			System.out.println(sobarica.staz);
			System.out.println(sobarica.plata);
			System.out.println();
			sobarice.add(sobarica);
		}
	}
	
	public void DodajZaposlenog() {//registruje novog zaposlenog
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
		//pogledati tutorijal za detaljnije layout

		//BITNE INFORMACIJE
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
        
		dateLabel = new JLabel();
		
		dateLabel.setText("Datum Rodjenja");
		dateLabel.setBounds(10, 20, 80, 25);
		panel.add(dateLabel);
		
		DateFormat df = new SimpleDateFormat("dd.MM.yyyy.");
		final JFormattedTextField txtDate = new JFormattedTextField(df);
		txtDate.setBounds(100, 20, 165, 25);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
		LocalDateTime now = LocalDateTime.now();
		txtDate.setText(dtf.format(now));
		panel.add(txtDate, "wrap, left");
		
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
		
		String[] optionsToChoose = {"r", "s"};
		final JComboBox<String> jComboBox = new JComboBox<>(optionsToChoose);
        jComboBox.setBounds(80, 50, 140, 20);
        panel.add(jComboBox);
		
		//WRITE
		btn = new JButton("Register user");
		btn.setBounds(10, 80, 80, 25);		
		btn.addActionListener(new ActionListener() { 
			@Override
			public void actionPerformed(ActionEvent e) {
				user = userText.getText();
				char[] passCH = passText.getPassword();
				pass = new String(passCH);
				ime = imeText.getText();
				prezime = prezimeText.getText();
				pol = polText.getText();
				date = txtDate.getText();//dateText.getText();
				fon = fonText.getText();
				adresa = adresaText.getText();
				sprema = spremaText.getText();
				staz = Integer.parseInt(stazText.getText());
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
				
				if (pass.equals("") || user.equals("")) {
					JFrame optFrame = new JFrame();
					JOptionPane.showMessageDialog(optFrame, "Wrong input!", "Input Error Message", JOptionPane.OK_OPTION);
				}
				else {
					check = false;
					List<String[]> coll = IscitajCSV();
					
					for(String[] item : coll) {
						if(user.equals(item[0]) && pass.equals(item[1])) {
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
							writer = new FileWriter("src\\Users.csv", true);
							String ispis = "";
							if (jComboBox.getItemAt(jComboBox.getSelectedIndex()).equals("r") || jComboBox.getItemAt(jComboBox.getSelectedIndex()).equals("s")) {
								ispis = user + "," + pass + "," + jComboBox.getItemAt(jComboBox.getSelectedIndex()) + "," + ime + "," + prezime + "," + pol + "," + date + "," + fon + "," + adresa + "," + sprema + "," + staz + "," + plata + "\n";
							}
							else {
								ispis = user + "," + pass + "," + jComboBox.getItemAt(jComboBox.getSelectedIndex()) + "," + ime + "," + prezime + "," + pol + "," + date + "," + fon + "," + adresa + ",100000\n";
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
		});
		panel.add(btn);
				
		dialog.setVisible(true);
	}
	
	private List<String[]> IscitajCSV() {
		BufferedReader reader = null;
		String line = "";
		
		try {
			reader = new BufferedReader(new FileReader("src\\Users.csv"));
			
			List<String[]> coll = new ArrayList<String[]>();
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
				e.printStackTrace();
			}
		}
		return null;	
	}
	
	public void PrikazSvihEndtiteta() throws IOException {//tabela entiteta
		lista_entiteta = new JFrame();
    	lista_entiteta_panel = new JPanel();
    	
    	lista_entiteta.setSize(700, 300);
    	Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    final int x = (int) ((dimension.getWidth() - lista_entiteta.getWidth()) / 2);
	    final int y = (int) ((dimension.getHeight() - lista_entiteta.getHeight()) / 2);
	    lista_entiteta.setLocation(x, y);
	    
	    lista_entiteta.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	    lista_entiteta.addWindowListener(new WindowAdapter() {
	    	@Override
	    	public void windowClosing(WindowEvent e) {
	    		int opt = JOptionPane.showConfirmDialog(null, "Do you want to close this window?", "close", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
	    		if (opt == JOptionPane.YES_OPTION) {
	    			e.getWindow().dispose();
	    		}
	    	}
	    });
	    
	    //TABELA
		String line = "";
		
	    reader = new BufferedReader(new FileReader("src\\Users.csv"));
		
		int lines = 0;
		while((line = reader.readLine()) != null) {
			lines++;
		}
		reader.close();

		reader = new BufferedReader(new FileReader("src\\Users.csv"));
		
		Object[][] data = new Object[lines][2];
		//Collection<String[]> coll = new ArrayList<String[]>();
		int i = 0;
		while((line = reader.readLine()) != null) {
			String[] red = line.split(",");
			
			data[i][0] = red[0];
			if (red[2].equals("a")) {
				data[i][1] = "admin";	
			}
			else if (red[2].equals("r")) {
				data[i][1] = "recepcioner";
			}
			else if (red[2].equals("s")) {
				data[i][1] = "sobarica";
			}
			else if (red[2].equals("g")) {
				data[i][1] = "gost";
			}
			all_users.add(red);
			i++;
			//coll.add(red);
		}
		reader.close();
		//System.out.println(i);	
		mainToolbar = new JToolBar();
		ImageIcon deleteIcon = new ImageIcon("src\\img\\remove.gif");
		btnDelete.setIcon(deleteIcon);
		btnDelete.setText("Delete user");
		mainToolbar.add(btnDelete);		
		mainToolbar.setFloatable(false);		
		lista_entiteta.add(mainToolbar, BorderLayout.NORTH);
		
		ImageIcon editIcon = new ImageIcon("src\\img\\edit.gif");
		btnEdit_user.setIcon(editIcon);
		btnEdit_user.setText("See stats");
		mainToolbar.add(btnEdit_user);		
		mainToolbar.setFloatable(false);		
		lista_entiteta.add(mainToolbar, BorderLayout.NORTH);
		
		String[] collNames = {"Korisničko ime", "Pozicija"};
		
		final JTable table = new JTable(data, collNames);
		table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getTableHeader().setReorderingAllowed(false);
		table.setPreferredScrollableViewportSize(new Dimension(500, 150));
		table.setFillsViewportHeight(true);
		
		tableSorter.setModel((AbstractTableModel) table.getModel());
		table.setRowSorter(tableSorter);
		
		JScrollPane scrollPane = new JScrollPane(table);
		lista_entiteta_panel.add(scrollPane);
		
		btnDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int red = table.getSelectedRow();
				if(red == -1) {
					JOptionPane.showMessageDialog(null, "Morate odabrati red u tabeli.", "Greska", JOptionPane.WARNING_MESSAGE);
				}
				else {
					String entitet_za_brisanje = table.getValueAt(red, 0).toString();
					List<String[]> kolekcija = IscitajCSV();
					
					if(entitet_za_brisanje != null) {
						int izbor = JOptionPane.showConfirmDialog(null,"Da li ste sigurni da zelite da obrisete entitet?", 
								entitet_za_brisanje + " - Potvrda brisanja", JOptionPane.YES_NO_OPTION);
						if(izbor == JOptionPane.YES_OPTION) {
							//SAD BRISE U KOLEKCIJI ODGOVARAJUCI ENTITET I ZATIM WRITE NA CSV FILE DA BI OVERWRITOVAO CEO DOKUMENT S NOVOM DATOM
							for (String[] niz : kolekcija) {
								if (niz[0].equals(entitet_za_brisanje)) {
									kolekcija.remove(niz);
									break;
								}
							}
							
							//FileWriter writer;
							try {
								CSVWriter writer = new CSVWriter(new FileWriter("src\\Users.csv"), ',', CSVWriter.NO_QUOTE_CHARACTER);
								//ISPROBAVANJE WRITE FAJLOVA
								for(String[] item : kolekcija) {
									System.out.println(Arrays.toString(item));
								}
								writer.writeAll(kolekcija);
								writer.close();						     
								
								JOptionPane.showMessageDialog(null, "Entitet uklonjen!", "Information message", JOptionPane.INFORMATION_MESSAGE);
								lista_entiteta.dispose();
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
					String ime_izabranog = table.getValueAt(red, 0).toString();
					
					for (String[] s : all_users) {
						if (ime_izabranog.equals(s[0])) {
							if (s[2].equals("r") || s[2].equals("s")) {
								Detaljan_pregled(s[2], s[3], s[4], s[0], s[1], s[8], s[7], s[5], s[6], s[9], s[10], s[11]);
							}
							else if (s[2].equals("g")) {
								Detaljan_pregled(s[2], s[3], s[4], s[0], s[1], s[8], s[7], s[5], s[6], "", "", "");
							}
						}
					}
				}
			}
		});
		
	    lista_entiteta.add(lista_entiteta_panel);
	    lista_entiteta.setTitle("Entity table");
	    lista_entiteta.setVisible(true);
	}
	
	public void Detaljan_pregled(String zvanje, String ime, String prezime, String username, String pass, String adress, String phone, String pol, String date, String sprema, String staz, String plata) {
		JFrame detaljan = new JFrame("Detaljan pregled");
		JPanel detaljan_panel = new JPanel();
		MigLayout ml = new MigLayout("wrap 3", "[][][]", "[]10[]10[]10[]20[]");
		detaljan_panel.setLayout(ml);
		
		detaljan.setSize(500, 500);
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (int) ((dimension.getWidth() - detaljan.getWidth()) / 2);
	    int y = (int) ((dimension.getHeight() - detaljan.getHeight()) / 2);
	    detaljan.setLocation(x, y);
		
	    detaljan.setTitle("Registracija radnika");
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
		userText.setText(username);
		detaljan_panel.add(userText, "wrap, left");

		passLabel = new JLabel();

		passLabel.setText("Password");
		passLabel.setBounds(10, 50, 80, 25);
		detaljan_panel.add(passLabel);
		
		JTextField passwordText = new JTextField(20); //20 je duzina textfields aka 20 karaktera
		passwordText.setBounds(100, 50, 165, 25);
		passwordText.setText(pass);
		detaljan_panel.add(passwordText, "wrap, left");				
        
        //OSTALE INFORMACIJE
		imeLabel = new JLabel();
		
		imeLabel.setText("Ime");
		imeLabel.setBounds(10, 20, 80, 25);
		detaljan_panel.add(imeLabel);
		
		imeText = new JTextField(20); //20 je duzina textfields aka 20 karaktera
		imeText.setBounds(100, 20, 165, 25);
		imeText.setText(ime);
		detaljan_panel.add(imeText, "wrap, left");

		prezimeLabel = new JLabel();
		
		prezimeLabel.setText("Prezime");
		prezimeLabel.setBounds(10, 20, 80, 25);
		detaljan_panel.add(prezimeLabel);
		
		prezimeText = new JTextField(20); //20 je duzina textfields aka 20 karaktera
		prezimeText.setBounds(100, 20, 165, 25);
		prezimeText.setText(prezime);
		detaljan_panel.add(prezimeText, "wrap, left");
		
		polLabel = new JLabel();
		
		polLabel.setText("Pol");
		polLabel.setBounds(10, 20, 80, 25);
		detaljan_panel.add(polLabel);
		
		polText = new JTextField(20); //20 je duzina textfields aka 20 karaktera
		polText.setBounds(100, 20, 165, 25);
		polText.setText(pol);
		detaljan_panel.add(polText, "wrap, left");
        
		dateLabel = new JLabel();
		
		dateLabel.setText("Datum Rodjenja");
		dateLabel.setBounds(10, 20, 80, 25);
		detaljan_panel.add(dateLabel);
		
		JTextField txtDate = new JTextField(20);
		txtDate.setBounds(100, 20, 165, 25);		
		txtDate.setText(date);
		detaljan_panel.add(txtDate, "wrap, left");
		
		fonLabel = new JLabel();
		
		fonLabel.setText("Broj telefona");
		fonLabel.setBounds(10, 20, 80, 25);
		detaljan_panel.add(fonLabel);
		
		fonText = new JTextField(20); //20 je duzina textfields aka 20 karaktera
		fonText.setBounds(100, 20, 165, 25);
		fonText.setText(phone);
		detaljan_panel.add(fonText, "wrap, left");
		
		adresaLabel = new JLabel();
		
		adresaLabel.setText("Adresa");
		adresaLabel.setBounds(10, 20, 80, 25);		
		detaljan_panel.add(adresaLabel);
		
		adresaText = new JTextField(20); //20 je duzina textfields aka 20 karaktera
		adresaText.setBounds(100, 20, 165, 25);
		adresaText.setText(adress);
		detaljan_panel.add(adresaText, "wrap, left");
		
		if (zvanje.equals("r") || zvanje.equals("s")) {		
			spremaLabel = new JLabel();
			
			spremaLabel.setText("Sprema");
			spremaLabel.setBounds(10, 20, 80, 25);
			detaljan_panel.add(spremaLabel);
			
			spremaText = new JTextField(20); //20 je duzina textfields aka 20 karaktera
			spremaText.setBounds(100, 20, 165, 25);
			spremaText.setText(sprema);
			detaljan_panel.add(spremaText, "wrap, left");
			
			stazLabel = new JLabel();
			
			stazLabel.setText("Staz");
			stazLabel.setBounds(10, 20, 80, 25);
			detaljan_panel.add(stazLabel);
			
			stazText = new JTextField(20); //20 je duzina textfields aka 20 karaktera
			stazText.setBounds(100, 20, 165, 25);
			stazText.setText(staz);
			detaljan_panel.add(stazText, "wrap, left");
			
			stazLabel = new JLabel();
			
			stazLabel.setText("Plata");
			stazLabel.setBounds(10, 20, 80, 25);
			detaljan_panel.add(stazLabel);
			
			JTextField plataText = new JTextField(20); //20 je duzina textfields aka 20 karaktera
			plataText.setBounds(100, 20, 165, 25);
			plataText.setText(plata);
			detaljan_panel.add(plataText, "wrap, left");
		}
		
		detaljan.add(detaljan_panel);
		detaljan.setVisible(true);
	}
	
	public void View_Cenovink() throws IOException {
		cenovnik_teble = new JFrame("Cenovnik");
		cenovnik_teble_panel = new JPanel();
    	
		cenovnik_teble.setSize(700, 300);
    	Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    final int x = (int) ((dimension.getWidth() - cenovnik_teble.getWidth()) / 2);
	    final int y = (int) ((dimension.getHeight() - cenovnik_teble.getHeight()) / 2);
	    cenovnik_teble.setLocation(x, y);
	    
	    cenovnik_teble.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	    cenovnik_teble.addWindowListener(new WindowAdapter() {
	    	@Override
	    	public void windowClosing(WindowEvent e) {
	    		int opt = JOptionPane.showConfirmDialog(null, "Do you want to close this window?", "close", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
	    		if (opt == JOptionPane.YES_OPTION) {
	    			e.getWindow().dispose();
	    		}
	    	}
	    });	    
	    cenovnik_teble.add(cenovnik_teble_panel);
	    
	    //TABELA
	    String line = "";
		
	    reader = new BufferedReader(new FileReader("src\\Cenovnik.csv"));
		List<String[]> arr = new ArrayList<String[]>();
		List<String[]> arr2 = new ArrayList<String[]>();
		int lines = 0;
		while((line = reader.readLine()) != null) {
			String[] temp = line.split(",");
			String[] temp2 = temp[1].split(";");
			arr.add(temp);
			arr2.add(temp2);
			lines++;
		}
		reader.close();
		
	    String[] collNames = {"Naziv", "Predsezona", "Sezona", "Postsezona",};
	    Object[][] data = new Object[lines][4];
	    
	    int i = 0;
	    for (String[] s : arr) {	    	  		    	
	    	if (s[0].equals("ONE")) {
	    		data[i][0] = "jednokrevetna";
	    	}
	    	else if (s[0].equals("TWO")) {
	    		data[i][0] = "dvokrevetna - jedan ležaj";
	    	}
	    	else if (s[0].equals("ONE_ONE")) {
	    		data[i][0] = "dvokrevetna - dva ležaja";
	    	}
	    	else if (s[0].equals("TWO_ONE")) {
	    		data[i][0] = "trokrevetna";
	    	}
	    	else if (s[0].equals("TWO_TWO")) {
	    		data[i][0] = "četvorokrevetna";
	    	}
	    	else {
	    		data[i][0] = s[0];
	    	}
	    	String[] temp = arr2.get(i);
	    	
	    	
	    	if (s[0].equals("ONE") || s[0].equals("ONE_ONE") || s[0].equals("TWO") || s[0].equals("TWO_ONE") || s[0].equals("TWO_TWO")) {
		    	int j = 1;
		    	for (String str : temp) {		    		
		    		data[i][j] = str;	
		    		j++;
		    	}
	    	}
	    	else {
	    		data[i][1] = temp[0];
	    		data[i][2] = temp[0];
	    		data[i][3] = temp[0];
	    	}
	    	i++;	    	
	    }
	    
		final JTable table = new JTable(data, collNames);
		table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getTableHeader().setReorderingAllowed(false);
		table.setPreferredScrollableViewportSize(new Dimension(500, 150));
		table.setFillsViewportHeight(true);
		
		tableSorter.setModel((AbstractTableModel) table.getModel());
		table.setRowSorter(tableSorter);
		
		JScrollPane scrollPane = new JScrollPane(table);
		cenovnik_teble_panel.add(scrollPane);
	    
		mainToolbar = new JToolBar();
		ImageIcon deleteIcon = new ImageIcon("src\\img\\edit.gif");
		btnEdit_cene.setIcon(deleteIcon);
		btnEdit_cene.setText("Izmeni cene");
		mainToolbar.add(btnEdit_cene);		
		mainToolbar.setFloatable(false);		
		cenovnik_teble.add(mainToolbar, BorderLayout.NORTH);
		
		btnEdit_cene.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {					
				Izmena_Cena();
			}
		});
		
	    cenovnik_teble.setVisible(true);
	}
	
	public void Izmena_Cena() {
		JFrame izmena_cena = new JFrame("Izmena cena");
		JPanel izmena_cena_panel = new JPanel();		
		
		izmena_cena.setSize(400, 500);
		
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (int) ((dimension.getWidth() - izmena_cena.getWidth()) / 2);
	    int y = (int) ((dimension.getHeight() - izmena_cena.getHeight()) / 2);
	    izmena_cena.setLocation(x, y);
		
	    izmena_cena.setTitle("Registracija radnika");
	    //CLOSING EVENT
	    izmena_cena.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
	    izmena_cena.addWindowListener(new WindowAdapter() {
	    	@Override
	    	public void windowClosing(WindowEvent e) {
	    		int opt = JOptionPane.showConfirmDialog(null, "Do you want to close this window?", "close", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
	    		if (opt == JOptionPane.YES_OPTION) {
	    			e.getWindow().dispose();
	    		}
	    	}
	    });
	    izmena_cena.add(izmena_cena_panel);
		
		MigLayout ml = new MigLayout("wrap 3", "[][][]", "[]10[]10[]10[]20[]");
		izmena_cena_panel.setLayout(ml);

		JLabel lbl_jednosobna = new JLabel("jednokrevetna");
		izmena_cena_panel.add(lbl_jednosobna);

		txt_jednosobna = new JTextField(20);
		izmena_cena_panel.add(txt_jednosobna, "span 2");

		JLabel lbl_dvokrevetna = new JLabel("dvokrevetna 1-1");
		izmena_cena_panel.add(lbl_dvokrevetna);

		txt_dvokrevetna = new JTextField(20);
		izmena_cena_panel.add(txt_dvokrevetna, "span 2");

		JLabel lbl_dvokrevetna2 = new JLabel("dvokrevetna 2");
		izmena_cena_panel.add(lbl_dvokrevetna2);

		txt_dvokrevetna2 = new JTextField(20);
		izmena_cena_panel.add(txt_dvokrevetna2, "span 2");

		JLabel lbl_trokrevetna = new JLabel("trokrevetna");
		izmena_cena_panel.add(lbl_trokrevetna);

		txt_trokrevetna = new JTextField(20);
		izmena_cena_panel.add(txt_trokrevetna, "span 2");

		JLabel lbl_cetvorokrevetna = new JLabel("četvorokrevetna");
		izmena_cena_panel.add(lbl_cetvorokrevetna);

		txt_cetvorokrevetna = new JTextField(20);
		izmena_cena_panel.add(txt_cetvorokrevetna, "span 2");
		
		JLabel lbl_dorucak = new JLabel("doručak");
		izmena_cena_panel.add(lbl_dorucak);

		txt_dorucak = new JTextField(20);
		izmena_cena_panel.add(txt_dorucak, "span 2");
		
		JLabel lbl_rucak = new JLabel("ručak");
		izmena_cena_panel.add(lbl_rucak);

		txt_rucak = new JTextField(20);
		izmena_cena_panel.add(txt_rucak, "span 2");
		
		JLabel lbl_vecera = new JLabel("večera");
		izmena_cena_panel.add(lbl_vecera);

		txt_vecera = new JTextField(20);
		izmena_cena_panel.add(txt_vecera, "span 2");
		
		JLabel lbl_pocetak = new JLabel("pocetak vazenja");
		izmena_cena_panel.add(lbl_pocetak);
		
		DateFormat df = new SimpleDateFormat("dd.MM.yyyy.");
		final JFormattedTextField txt_pocetak = new JFormattedTextField(df);
		txt_pocetak.setBounds(100, 20, 165, 25);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
		LocalDateTime now = LocalDateTime.now();
		txt_pocetak.setText(dtf.format(now));
		izmena_cena_panel.add(txt_pocetak, "span 2");

		JLabel lbl_kraj = new JLabel("kraj vazenja");
		izmena_cena_panel.add(lbl_kraj);

		final JFormattedTextField txt_kraj = new JFormattedTextField(df);
		txt_kraj.setBounds(100, 20, 165, 25);
		txt_kraj.setText(dtf.format(now));
		izmena_cena_panel.add(txt_kraj, "span 2");
		
		JButton btn_izmena = new JButton("Sačuvaj");
		izmena_cena_panel.add(btn_izmena);
		
		btn_izmena.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//DODA NOVI CENOVNIK ISPRED STAROG, RAZDVAJA IH JEDNA PRAZAN RED I TREBA DA DODA U DATUME IZMENE CENOVNIKA DATUM IZMENE/VAZENJA OBRATITI PAZNJU DA NE SME BITI PRAZNIH POLJA
				try {
					if (txt_jednosobna.getText().isEmpty() || txt_dvokrevetna.getText().isEmpty() || txt_dvokrevetna2.getText().isEmpty() || txt_trokrevetna.getText().isEmpty() || txt_cetvorokrevetna.getText().isEmpty() || txt_dorucak.getText().isEmpty() || txt_rucak.getText().isEmpty() || txt_vecera.getText().isEmpty()) {
						JOptionPane.showMessageDialog(null, "Wrong input!", "Input Error Message", JOptionPane.OK_OPTION);
					}
					else {
						List<String[]> arr = new ArrayList<String[]>();
						BufferedReader reader = new BufferedReader(new FileReader("src\\Cenovnik.csv"));
						String line = "";
						while ((line = reader.readLine()) != null) {
							String[] temp = line.split(",");
							arr.add(temp);
						}
						
						FileWriter writer;
						writer = new FileWriter("src\\Stare_Cene.csv");
						for (String[] s : arr) {
							String ispis = "";
							for (String str : s) {
								ispis += str + ",";
							}
							ispis = ispis.substring(0, ispis.length() - 1);
							writer.write(ispis + "\n");
						}
						writer.close();
						
						String[] jednosobna = txt_jednosobna.getText().split(";");
						String[] dvokrevetna = txt_dvokrevetna.getText().split(";");
						String[] dvokrevetna2 = txt_dvokrevetna2.getText().split(";");
						String[] troktevetna = txt_trokrevetna.getText().split(";");
						String[] cetvorokrevetna = txt_cetvorokrevetna.getText().split(";");
						String[] dorucak = txt_dorucak.getText().split(" ");
						String[] rucak = txt_rucak.getText().split(" ");
						String[] vecera = txt_vecera.getText().split(" ");
						
						LocalDate pocetak = LocalDate.parse(txt_pocetak.getText());
						LocalDate kraj = LocalDate.parse(txt_kraj.getText());
						
						String[] ime_entiteta = new String[arr.size()];
						int i = 0;
						for (String[] s : arr) {
							ime_entiteta[i] = s[0];
							i++;
						}					
						List<String[]> cene = new ArrayList<String[]>();
						cene.add(jednosobna);
						cene.add(dvokrevetna);
						cene.add(dvokrevetna2);
						cene.add(troktevetna);
						cene.add(cetvorokrevetna);
						cene.add(dorucak);
						cene.add(rucak);
						cene.add(vecera);
	
						writer = new FileWriter("src\\Cenovnik.csv");						
						writer.write("");
						writer.close();
						
						writer = new FileWriter("src\\Cenovnik.csv", true);
						arr.clear();
						for (i = 0; i < ime_entiteta.length; i++) {
							String[] temp = new String[2];
							temp[0] = ime_entiteta[i];
							String[] temp_cene = cene.get(i);
							if (temp_cene.length > 1) {
								String s = "";							
								for (String str : temp_cene) {
									s += str + ";";
								}
								s = s.substring(0, s.length() - 1);
								temp[1] = s;
							}
							else {
								temp[1] = temp_cene[0];
							}
							arr.add(temp);
						}
						
						for (String[] s : arr) {
							String ispis = "";
							for (String str : s) {
								ispis += str + ",";
							}
							ispis = ispis.substring(0, ispis.length() - 1);
							writer.write(ispis + "\n");
						}
						writer.close();
						
						writer = new FileWriter("src\\Cenovnik_Vazenje.csv");
						String ispis = pocetak.toString() + ";" +  kraj.toString();
						writer.write(ispis + "\n");
						writer.close();
					}
					
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		izmena_cena.add(izmena_cena_panel);
		izmena_cena.setVisible(true);
	}
	
	public void Prihod_Rashod() throws IOException {
		JFrame prihod_rashod = new JFrame();
		JPanel prihod_rashod_panel = new JPanel();
    	
		prihod_rashod.setSize(700, 300);
    	Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    final int x = (int) ((dimension.getWidth() - prihod_rashod.getWidth()) / 2);
	    final int y = (int) ((dimension.getHeight() - prihod_rashod.getHeight()) / 2);
		prihod_rashod.setLocation(x, y);
	    
		prihod_rashod.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		prihod_rashod.addWindowListener(new WindowAdapter() {
	    	@Override
	    	public void windowClosing(WindowEvent e) {
	    		int opt = JOptionPane.showConfirmDialog(null, "Do you want to close this window?", "close", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
	    		if (opt == JOptionPane.YES_OPTION) {
	    			e.getWindow().dispose();
	    		}
	    	}
	    });
		
		prihod_rashod.add(prihod_rashod_panel);
		
		BufferedReader reader = new BufferedReader(new FileReader("src\\Prihodi_Rashodi.csv"));
		List<String[]> arr = new ArrayList<String[]>();
		String line = "";
		int lines = 0;
		while((line = reader.readLine()) != null) {
			String[] red = line.split(",");						
			arr.add(red);
			lines++;
		}
		
		String[] collNames = {"Suma", "Datum transakcije", "Prihod/Rashod"};
		Object[][] data = new Object[lines][3];
		
		int i = 0;
		for (String[] s : arr) {
			data[i][0] = s[1];
			data[i][1] = s[0];
			data[i][2] = s[2];
			i++;
		}
		
		final JTable table = new JTable(data, collNames );
		table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getTableHeader().setReorderingAllowed(false);
		table.setPreferredScrollableViewportSize(new Dimension(500, 150));
		table.setFillsViewportHeight(true);
		
		tableSorter.setModel((AbstractTableModel) table.getModel());
		table.setRowSorter(tableSorter);
		
		JScrollPane scrollPane = new JScrollPane(table);
		prihod_rashod_panel.add(scrollPane);
		
		prihod_rashod.setVisible(true);
		
		//DODATI ONAJ KAO SEARCH BAR GDE MOZE OD date DO date DA PRIKAZE
	}
}
