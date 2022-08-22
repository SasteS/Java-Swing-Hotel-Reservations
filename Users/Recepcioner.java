package Users;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;
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
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;

import Hotel.Sobe;
import Hotel.Stanje_Sobe;
import Hotel.Tip_Soba;
import net.miginfocom.swing.MigLayout;

public class Recepcioner extends Zaposleni {
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
    JTextField dateText;
    JLabel fonLabel;
    JTextField fonText;
    JLabel adresaLabel;
    JTextField adresaText;
    
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
	
    JFrame guests_table;
    JPanel guests_table_panel;
    
    BufferedReader reader;
	protected TableRowSorter<AbstractTableModel> tableSorter = new TableRowSorter<AbstractTableModel>(); 

	JFrame pregled;
	JPanel pregled_panel;
	
	JFrame potvrdjene_rez;
	JPanel potvrdjene_rez_panel;
	
	JFrame sobe_window;
	JPanel sobe_window_panel;
	
	//metode
	public void RegisterGuest() { //e novog gosta - registracija 
		dialog = new JDialog();
		panel = new JPanel();
		
		dialog.setSize(400, 1000);
		
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (int) ((dimension.getWidth() - dialog.getWidth()) / 2);
	    int y = (int) ((dimension.getHeight() - dialog.getHeight()) / 2);
	    dialog.setLocation(x, y);
		
	    dialog.setTitle("Registracija gostiju");
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
		
		userLabel.setText("Username(email)");
		userLabel.setBounds(10, 20, 80, 25);
		panel.add(userLabel);
		
		userText = new JTextField(20); //20 je duzina textfields aka 20 karaktera
		userText.setBounds(100, 20, 165, 25);
		panel.add(userText, "wrap, left");
		
		
		passLabel = new JLabel();

		passLabel.setText("Password(Broj pasosa)");
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
		
		dateText = new JTextField(20); //20 je duzina textfields aka 20 karaktera
		dateText.setBounds(100, 20, 165, 25);
		panel.add(dateText, "wrap, left");
		
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
		
		//WRITE
		btn = new JButton("Register guest");
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
				date = dateText.getText();
				fon = fonText.getText();
				adresa = adresaText.getText();
				
				if (pass.equals("") || user.equals("")) {
					JFrame optFrame = new JFrame();
					JOptionPane.showMessageDialog(optFrame, "Wrong input!", "Input Error Message", JOptionPane.OK_OPTION);
				}
				else {
					check = false;
					Collection<String[]> coll = IscitajCSV();
					
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
							String ispis = user + "," + pass + ",g," + ime + "," + prezime + "," + pol + "," + date + "," + fon + "," + adresa + "\n";
							writer.write(ispis);
							writer.close();
							JFrame optFrame = new JFrame();
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
				e.printStackTrace();
			}
		}
		return null;	
	}
	
	public void Check_In() throws IOException {
		potvrdjene_rez = new JFrame("Pregled rezervacija");
		potvrdjene_rez_panel = new JPanel();

		potvrdjene_rez.setSize(650, 500);

		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		final int x = (int) ((dimension.getWidth() - potvrdjene_rez.getWidth()) / 2);
		final int y = (int) ((dimension.getHeight() - potvrdjene_rez.getHeight()) / 2);
		potvrdjene_rez.setLocation(x, y);

		// CLOSING EVENT
		potvrdjene_rez.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		potvrdjene_rez.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				int opt = JOptionPane.showConfirmDialog(null, "Do you want to close the window?", "close",
						JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
				if (opt == JOptionPane.YES_OPTION) {
					e.getWindow().dispose();
				}
			}
		});
		
		//TABELA
		reader = new BufferedReader(new FileReader("src\\Rezervacije.csv"));
		String line = "";
		final List<String[]> rezervacije_csv = new ArrayList<String[]>();
		while ((line = reader.readLine()) != null) {
			String[] temp = line.split(",");				
			if (temp[4].equals("ODOBRENA")) {
				rezervacije_csv.add(temp);	
			}
		}
		reader.close();
		
		String[] collNames = { "ID", "Pocetni datum", "Krajnji datum", "Tip sobe", "Gost", "Cena", "Stanje" };
		Object[][] data = new Object[rezervacije_csv.size()][7];
		
		int i = 0;
		for (String[] r : rezervacije_csv) {
			data[i][0] = r[6];
			data[i][1] = r[0];
			data[i][2] = r[1];
			data[i][3] = r[2];
			data[i][4] = r[3];
			data[i][5] = r[5];
			data[i][6] = r[4];
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
		potvrdjene_rez_panel.add(scrollPane);						

		potvrdjene_rez.add(potvrdjene_rez_panel);
		
		JPanel footer = new JPanel();		
		JButton btn_check_in = new JButton("Check in");
		footer.add(btn_check_in);
		
		btn_check_in.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) { 
				  try {
					  int red = table.getSelectedRow();
						if(red == -1) {
							JOptionPane.showMessageDialog(null, "Morate odabrati red u tabeli.", "Greska", JOptionPane.WARNING_MESSAGE);
						}
						else {
							String entitet_za_dodelu_sobe = table.getValueAt(red, 0).toString();
							Sobe sobe = new Sobe();
							List<Soba> lista_soba = sobe.getSobe();
							
							String tip_potrebne_sobe = table.getValueAt(red, 3).toString();
							for (String[] s : rezervacije_csv) {
								if (s[6].equals(entitet_za_dodelu_sobe)) { //AKO SE POKLAPA ID
									
								}
							}
							
							List<Soba> temp = new ArrayList<Soba>();
							for (Soba s : lista_soba) {
								if (!s.get_tip().equals(Tip_Soba.valueOf(tip_potrebne_sobe))) {
									temp.add(s);
								}
								if (true) { //DATUM ISTO MORA DA SE PROVERI A I STANJE
									
								}
								if(true) {
									
								}
							}
							
							for(Soba s : temp) {
								lista_soba.remove(s);
							}
							
							Tabela_Potencijalnih_Soba(lista_soba);
						}
					
				  } catch (IOException e1) {
					  e1.printStackTrace();
				  }
			  } 
		} );
		
		potvrdjene_rez.add(footer, BorderLayout.SOUTH);
		
		potvrdjene_rez.setVisible(true);
	}
	
	public void Tabela_Potencijalnih_Soba(List<Soba> lista_soba) {
		sobe_window = new JFrame("Pregled rezervacija");
		sobe_window_panel = new JPanel();

		sobe_window.setSize(650, 500);

		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		final int x = (int) ((dimension.getWidth() - sobe_window.getWidth()) / 2);
		final int y = (int) ((dimension.getHeight() - sobe_window.getHeight()) / 2);
		sobe_window.setLocation(x, y);

		// CLOSING EVENT
		sobe_window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		sobe_window.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				int opt = JOptionPane.showConfirmDialog(null, "Do you want to close the window?", "close",
						JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
				if (opt == JOptionPane.YES_OPTION) {
					e.getWindow().dispose();
				}
			}
		});
		
		String[] collNames = { "Broj", "Tip", "Broj ljudi" };
		Object[][] data = new Object[lista_soba.size()][3];
		
		int i = 0;
		for (Soba s : lista_soba) {
			data[i][0] = s.get_broj();
			data[i][1] = s.get_tip();
			data[i][2] = s.get_broj_ljudi();
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
		sobe_window_panel.add(scrollPane);						

		sobe_window.add(sobe_window_panel);
		sobe_window.setVisible(true);
	}
	
	public void RegulisiRezervaciju() throws IOException {//sredjivace rezervacije odobri/odbij, treba da se proveri postoji li soba na tom datumu
		pregled = new JFrame("Pregled rezervacija");
		pregled_panel = new JPanel();

		pregled.setSize(650, 500);

		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		final int x = (int) ((dimension.getWidth() - pregled.getWidth()) / 2);
		final int y = (int) ((dimension.getHeight() - pregled.getHeight()) / 2);
		pregled.setLocation(x, y);

		// CLOSING EVENT
		pregled.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		pregled.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				int opt = JOptionPane.showConfirmDialog(null, "Do you want to close the window?", "close",
						JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
				if (opt == JOptionPane.YES_OPTION) {
					e.getWindow().dispose();
				}
			}
		});
		
		//TABELA
		reader = new BufferedReader(new FileReader("src\\Rezervacije.csv"));
		String line = "";
		final List<String[]> rezervacije_csv = new ArrayList<String[]>();
		while ((line = reader.readLine()) != null) {
			String[] temp = line.split(",");				
			if (temp[4].equals("NA_CEKANJU")) {
				rezervacije_csv.add(temp);	
			}
		}
		reader.close();
		
		String[] collNames = { "ID", "Pocetni datum", "Krajnji datum", "Tip sobe", "Gost", "Cena", "Stanje" };
		Object[][] data = new Object[rezervacije_csv.size()][7];
		
		int i = 0;
		for (String[] r : rezervacije_csv) {
			data[i][0] = r[6];
			data[i][1] = r[0];
			data[i][2] = r[1];
			data[i][3] = r[2];
			data[i][4] = r[3];
			data[i][5] = r[5];
			data[i][6] = r[4];
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
		pregled_panel.add(scrollPane);						

		pregled.add(pregled_panel);
		
		JPanel footer = new JPanel();		
		JButton btn_odobri = new JButton("Odobri");
		footer.add(btn_odobri);		
		JButton btn_odbij = new JButton("Odbij");
		footer.add(btn_odbij);
		
		rezervacije_csv.clear();
		
		reader = new BufferedReader(new FileReader("src\\Rezervacije.csv"));
		while ((line = reader.readLine()) != null) {
			String[] temp = line.split(",");				
			rezervacije_csv.add(temp);
		}
		
		btn_odobri.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	int red = table.getSelectedRow();
				if(red == -1) {
					JOptionPane.showMessageDialog(null, "Morate odabrati red u tabeli.", "Greska", JOptionPane.WARNING_MESSAGE);
				}
				else {
					String entitet_za_odobravanje = table.getValueAt(red, 0).toString();					
					for(String[] r : rezervacije_csv) {
						if (r[6].equals(entitet_za_odobravanje)) {
							r[4] = "ODOBRENA";
							break;
						}
					}
					FileWriter writer;
					try {
						//PRAZNI DOSADASNJI FAJL
						writer = new FileWriter("src\\Rezervacije.csv");						
						writer.write("");						
						writer.close();
						
						//PUNI NOVIM INFORMACIJAMA
						writer = new FileWriter("src\\Rezervacije.csv", true);
						for (String[] temp : rezervacije_csv) {
							String ispis = temp[0] + "," + temp[1] + "," + temp[2] + "," + temp[3] + "," + temp[4] + "," + temp[5] + "," + temp[6] + "," + temp[7] + "\n";							
							writer.write(ispis);
						}
						writer.close();
						
						JFrame optFrame = new JFrame();
						JOptionPane.showMessageDialog(null, "Rezervacija odobrena!", "Information message", JOptionPane.INFORMATION_MESSAGE);
						pregled.dispose();						
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
            }
        });
		
		btn_odbij.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	int red = table.getSelectedRow();
				if(red == -1) {
					JOptionPane.showMessageDialog(null, "Morate odabrati red u tabeli.", "Greska", JOptionPane.WARNING_MESSAGE);
				}
				else {
					String entitet_za_odbijanje = table.getValueAt(red, 0).toString();
					for(String[] r : rezervacije_csv) {
						if (r[6].equals(entitet_za_odbijanje)) {
							r[4] = "ODBIJENA";
							break;
						}
					}
					FileWriter writer;
					try {
						//PRAZNI DOSADASNJI FAJL
						writer = new FileWriter("src\\Rezervacije.csv");						
						writer.write("");						
						writer.close();
						
						//PUNI NOVIM INFORMACIJAMA
						writer = new FileWriter("src\\Rezervacije.csv", true);
						for (String[] temp : rezervacije_csv) {
							String ispis = temp[0] + "," + temp[1] + "," + temp[2] + "," + temp[3] + "," + temp[4] + "," + temp[5] + "," + temp[6] + "," + temp[7] + "\n";
							writer.write(ispis);
						}
						writer.close();
						
						JFrame optFrame = new JFrame();
						JOptionPane.showMessageDialog(null, "Rezervacija odbijena!", "Information message", JOptionPane.INFORMATION_MESSAGE);
						pregled.dispose();						
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
            }
        });
		
		pregled.add(footer, BorderLayout.SOUTH);
		pregled.setVisible(true);
	}
	
	public void Guest_List() throws IOException {
		guests_table = new JFrame("Check in");
		guests_table_panel = new JPanel();
		
		guests_table.setSize(650, 500);
		
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    final int x = (int) ((dimension.getWidth() - guests_table.getWidth()) / 2);
	    final int y = (int) ((dimension.getHeight() - guests_table.getHeight()) / 2);
	    guests_table.setLocation(x, y);
		
	    //CLOSING EVENT
	    guests_table.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	    guests_table.addWindowListener(new WindowAdapter() {
	    	@Override
	    	public void windowClosing(WindowEvent e) {
	    		int opt = JOptionPane.showConfirmDialog(null, "Do you want to close the window?", "close", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
	    		if (opt == JOptionPane.YES_OPTION) {
	    			e.getWindow().dispose();		    			
	    		}
	    	}
	    });	    
	    
	    guests_table.add(guests_table_panel);
	    
		//TABELA
	    String line = "";
		
	    reader = new BufferedReader(new FileReader("src\\Users.csv"));
		
		int lines = 0;
		List<String[]> container = new ArrayList<String[]>();
		while((line = reader.readLine()) != null) {
			String[] temp = line.split(",");
			if (temp[2].equals("g")) {
				container.add(temp);
				lines++;	
			}				
		}
		reader.close();
	    
		String[] collNames = {"Korisnicko ime", "Ime", "Prezime"};
		Object[][] data = new Object[lines][3];
		
		for (int i = 0; i < container.size(); i++) {
			String[] temp = container.get(i);
			data[i][0] = temp[0];
			data[i][1] = temp[3];
			data[i][2] = temp[4];
		}
		
		final JTable table = new JTable(data, collNames);
		table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getTableHeader().setReorderingAllowed(false);
		table.setPreferredScrollableViewportSize(new Dimension(500, 150));
		table.setFillsViewportHeight(true);
			
		tableSorter.setModel((AbstractTableModel) table.getModel());
		table.setRowSorter(tableSorter);
		
		JScrollPane scrollPane = new JScrollPane(table);
		guests_table_panel.add(scrollPane);
		
		guests_table.setVisible(true);

	}
	
	public void Pregled_Rezervacija() throws IOException {
		pregled = new JFrame("Pregled rezervacija");
		pregled_panel = new JPanel();

		pregled.setSize(650, 500);

		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		final int x = (int) ((dimension.getWidth() - pregled.getWidth()) / 2);
		final int y = (int) ((dimension.getHeight() - pregled.getHeight()) / 2);
		pregled.setLocation(x, y);

		// CLOSING EVENT
		pregled.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		pregled.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				int opt = JOptionPane.showConfirmDialog(null, "Do you want to close the window?", "close",
						JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
				if (opt == JOptionPane.YES_OPTION) {
					e.getWindow().dispose();
				}
			}
		});
		
		//TABELA
		reader = new BufferedReader(new FileReader("src\\Rezervacije.csv"));
		String line = "";
		List<String[]> rezervacije_csv = new ArrayList<String[]>();
		while ((line = reader.readLine()) != null) {
			String[] temp = line.split(",");				
			rezervacije_csv.add(temp);			
		}
		reader.close();
		
		String[] collNames = { "ID", "Pocetni datum", "Krajnji datum", "Tip sobe", "Gost", "Cena", "Stanje" };
		Object[][] data = new Object[rezervacije_csv.size()][7];
		
		int i = 0;
		for (String[] r : rezervacije_csv) {
			data[i][0] = r[6];
			data[i][1] = r[0];
			data[i][2] = r[1];
			data[i][3] = r[2];
			data[i][4] = r[3];
			data[i][5] = r[5];
			data[i][6] = r[4];
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
		pregled_panel.add(scrollPane);

		pregled.add(pregled_panel);
		pregled.setVisible(true);
	}
}
