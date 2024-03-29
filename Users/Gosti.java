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
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import Hotel.Rezervacije;
import Hotel.Sobe;
import Hotel.Stanje_Sobe;
import Hotel.Stenje_Rezervacija;
import Hotel.Tip_Soba;
import net.miginfocom.swing.MigLayout;

public class Gosti extends Korisnik {
	JFrame reservate;
	JPanel reservate_panel;

	BufferedReader reader;
	JFrame reservate_table;
	JPanel reservate_table_panel;
	protected JTable table;
	protected TableRowSorter<AbstractTableModel> tableSorter = new TableRowSorter<AbstractTableModel>();
	
	List<Rezervacije> rezervacije = new ArrayList<Rezervacije>();
	String ispis_rezervacija;

	JFrame pregled;
	JPanel pregled_panel;
	
	// metode
	public void ZahtevRezervacija(final String username) throws IOException { // pravi zahtev za rezervaciju koji ce kasnije recepcioner da
															// odobri/odbije
		reservate = new JFrame("Check in");
		reservate_panel = new JPanel(new MigLayout("wrap, insets 20, fill", "[center]20[center]",
				"[center]20[center]20[center]20[center]20[center]")); // arg 2 broj col, arg 3 br redova);

		reservate.setSize(650, 500);

		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		final int x = (int) ((dimension.getWidth() - reservate.getWidth()) / 2);
		final int y = (int) ((dimension.getHeight() - reservate.getHeight()) / 2);
		reservate.setLocation(x, y);

		// CLOSING EVENT
		reservate.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		reservate.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				int opt = JOptionPane.showConfirmDialog(null, "Do you want to close the window?", "close",
						JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
				if (opt == JOptionPane.YES_OPTION) {
					e.getWindow().dispose();
				}
			}
		});

		try {
			// UPIT - datum dolaska, broj nocenja, broj ljudi, dodatne usluge
			JLabel lbl_dolazak_date = new JLabel("Datum dolaska");
			lbl_dolazak_date.setBounds(10, 20, 80, 25);
			reservate_panel.add(lbl_dolazak_date);
	
			DateFormat df = new SimpleDateFormat("dd.MM.yyyy.");
			final JFormattedTextField txtDate = new JFormattedTextField(df);
			txtDate.setBounds(100, 20, 165, 25);
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
			LocalDateTime now = LocalDateTime.now();
			txtDate.setText(dtf.format(now));
			reservate_panel.add(txtDate, "wrap, left");
	
			JLabel lbl_nocenja = new JLabel("Broj noćenja");
			lbl_nocenja.setBounds(10, 20, 80, 25);
			reservate_panel.add(lbl_nocenja);
	
			final JTextField txt_nocenja = new JTextField(20);
			txt_nocenja.setBounds(100, 20, 165, 25);
			reservate_panel.add(txt_nocenja, "wrap, left");
	
			JLabel lbl_br_ljudi = new JLabel("Broj ljudi");
			lbl_br_ljudi.setBounds(10, 20, 80, 25);
			reservate_panel.add(lbl_br_ljudi);
	
			final JTextField txt_br_ljudi = new JTextField(20);
			txt_br_ljudi.setBounds(100, 20, 165, 25);
			reservate_panel.add(txt_br_ljudi, "wrap, left");
	
			JLabel lbl_dodatne_opc = new JLabel("Dodatne opcije:");
			lbl_dodatne_opc.setBounds(10, 20, 80, 25);
			reservate_panel.add(lbl_dodatne_opc);
	
			//DODATNE OPCIJE
			List<String> opcije = new ArrayList<String>();
			reader = new BufferedReader(new FileReader("src\\Dodatni_Kriterijumi.csv"));
			String line = "";
			while ((line = reader.readLine()) != null) {
				opcije.add(line);
			}
			reader.close();
			
			String[] opcije_niz = new String[opcije.size()];
			int index = 0;
			for (String opcija : opcije) {
				opcije_niz[index] = opcija;
				index++;
			}
			
			final JList list_dodatne_opc = new JList(opcije_niz);
			list_dodatne_opc.setVisibleRowCount(opcije_niz.length);
			JScrollPane sp = new JScrollPane(list_dodatne_opc);
			reservate_panel.add(sp, "wrap, left");
	
			list_dodatne_opc.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	
			JButton dodaj_opciju = new JButton("Dodaj opciju");
			reservate_panel.add(dodaj_opciju);
	
			final JLabel lbl_opcije = new JLabel("Opcije: ");
			reservate_panel.add(lbl_opcije, "left");
	
			dodaj_opciju.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String temp = "";
					List<String> selected_options = list_dodatne_opc.getSelectedValuesList();
					ispis_rezervacija = "";
					for (String item : selected_options) {
						temp += item + ", ";
						ispis_rezervacija += item + ";";
					}
					temp = temp.substring(0, temp.length() - 2);
					ispis_rezervacija = ispis_rezervacija.substring(0, ispis_rezervacija.length() - 1);
					lbl_opcije.setText("Opcije: " + temp);
				}
			});
		
		
			JButton btn_reservate = new JButton("reservate");
			reservate_panel.add(btn_reservate);
			btn_reservate.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
						LocalDate formatterdtext_converted_to_date_start = LocalDate.parse(txtDate.getText(), formatter);
						if (formatterdtext_converted_to_date_start.isAfter(LocalDate.now()) || formatterdtext_converted_to_date_start == LocalDate.now()) {
							reservate.dispose();
		
							reader = new BufferedReader(new FileReader("src\\Rezervacije.csv"));
							String line = "";
							List<String> container = new ArrayList<String>();
							while ((line = reader.readLine()) != null) {
								container.add(line);
							}
							reader.close();
		
							FileWriter writer = new FileWriter("src\\Rezervacije.csv", true);
		
							formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
							formatterdtext_converted_to_date_start = LocalDate.parse(txtDate.getText(), formatter);
							LocalDate formatterdtext_converted_to_date_end = formatterdtext_converted_to_date_start.plusDays(Integer.parseInt(txt_nocenja.getText()));															
							
							//DODATI I U ENTITET REZERVACIJE NOVU REZERVACIJU - TREBA IZ CSVA DA PRAVI TABELU?
							Rezervacije rezervacija = new Rezervacije();				
							rezervacija.set_pocetak(formatterdtext_converted_to_date_start);
							rezervacija.set_kraj(formatterdtext_converted_to_date_end);
							rezervacija.set_username(username);
		//////////////////////////////				
							reader = new BufferedReader(new FileReader("src\\Sezone.csv"));
							line = "";
							List<String[]> sezone = new ArrayList<String[]>();
							while ((line = reader.readLine()) != null) {
								sezone.add(line.split(","));
							}
							reader.close();								
							
							String sezona1 = "";
							String sezona2 = "";
							for (String[] sezona : sezone) {
								String[] temp = sezona[1].split(";");						
								for (String s : temp) {
									if (s.equals(formatterdtext_converted_to_date_start.getMonth().toString())) {					
										sezona1 = sezona[0];					
										break;
									}
								}
								for (String s : temp) {
									if (s.equals(formatterdtext_converted_to_date_end.getMonth().toString())) {
										sezona2 = sezona[0];					
										break;
									}
								}
							}
							
							System.out.println("sezona1: " + sezona1);
							System.out.println("sezona1: " + sezona2);
							
							if (txt_br_ljudi.getText().equals("1")) {
								rezervacija.set_tip(Tip_Soba.ONE);
							}
							else if (txt_br_ljudi.getText().equals("2")) {
								Tip_Soba[] values = { Tip_Soba.ONE_ONE, Tip_Soba.TWO };
					            int length = values.length;
					            int randIndex = new Random().nextInt(length);
					            rezervacija.set_tip(values[randIndex]);
							}
							else if (txt_br_ljudi.getText().equals("3")) {
								rezervacija.set_tip(Tip_Soba.TWO_ONE);
								
							}
							else if (txt_br_ljudi.getText().equals("4")) {
								rezervacija.set_tip(Tip_Soba.TWO_TWO);
							}
							else {
								JOptionPane.showMessageDialog(null, "Maksimalan broj ljudi po rezervaciji je 4!\nKreirajte novu rezervaciju za ostatak ljudi.", "InfoBox", JOptionPane.INFORMATION_MESSAGE);
							}
							
							int cena = 0;
							if (sezona1.equals(sezona2) == false) {
								LocalDate ld2 = LocalDate.parse("01." + formatterdtext_converted_to_date_end.getMonthValue() + "." +  formatterdtext_converted_to_date_end.getYear() + ".", formatter);
								long daysBetween = ChronoUnit.DAYS.between(formatterdtext_converted_to_date_start, ld2);
								System.out.println(daysBetween);
								long daysBetween2 = ChronoUnit.DAYS.between(formatterdtext_converted_to_date_start, formatterdtext_converted_to_date_end);
								long daysAfter = daysBetween2-daysBetween;
								System.out.println(daysAfter);
								
								reader = new BufferedReader(new FileReader("src\\Cenovnik.csv"));
								line = "";
								List<String[]> cenovnik = new ArrayList<String[]>();
								while ((line = reader.readLine()) != null) {
									cenovnik.add(line.split(","));
								}
								reader.close();											
								
								for (String[] s : cenovnik) {
									String[] temp = s[1].split(";");
									if (s[0].equals(rezervacija.get_tip().toString())) {					
										if (sezona1.equals("low")) {
											cena += Integer.parseInt(temp[0]) * daysBetween;
										}
										else if (sezona1.equals("mid")) {
											cena += Integer.parseInt(temp[2]) * daysBetween;
										}
										else if (sezona1.equals("high")) {
											cena += Integer.parseInt(temp[1]) * daysBetween;
										}
										
										if (sezona2.equals("low")) {
											cena += Integer.parseInt(temp[0]) * daysAfter;
										}
										else if (sezona2.equals("mid")) {
											cena += Integer.parseInt(temp[2]) * daysAfter;
										}
										else if (sezona2.equals("high")) {
											cena += Integer.parseInt(temp[1]) * daysAfter;
										}
									}
								}
							}
							else {
								reader = new BufferedReader(new FileReader("src\\Cenovnik.csv"));
								line = "";
								List<String[]> cenovnik = new ArrayList<String[]>();
								while ((line = reader.readLine()) != null) {
									cenovnik.add(line.split(","));
								}
								reader.close();
								
								long daysBetween2 = ChronoUnit.DAYS.between(formatterdtext_converted_to_date_start, formatterdtext_converted_to_date_end);
								
								for (String[] s : cenovnik) {
									String[] temp = s[1].split(";");							
									if (s[0].equals(rezervacija.get_tip().toString())) {
										if (sezona1.equals("low")) {									
											cena += Integer.parseInt(temp[0]) * daysBetween2;
										}
										else if (sezona1.equals("mid")) {
											cena += Integer.parseInt(temp[2]) * daysBetween2;
										}
										else if (sezona1.equals("high")) {
											cena += Integer.parseInt(temp[1]) * daysBetween2;
										}
									}				
								}
							}
							rezervacija.set_cena(cena);
							System.out.println(cena);
		////////////////////////////////
							//DODATNE USLUGE CENA - RADI
							long daysBetween2 = ChronoUnit.DAYS.between(formatterdtext_converted_to_date_start, formatterdtext_converted_to_date_end);
							String[] cena_usluge = ispis_rezervacija.split(";");
							int total_cena = rezervacija.get_cena();
							for (String item : cena_usluge) {
								System.out.println(item);
								if (item.equals("dorucak")) {
									total_cena += 300 * daysBetween2;
								}
								else if (item.equals("rucak")) {
									total_cena += 500 * daysBetween2;
								}
								else if (item.equals("vecera")) {
									total_cena += 400 * daysBetween2;
								}
								else if (item.equals("klima uređaj")) {
									total_cena += 200 * daysBetween2;
								}
								else if (item.equals("balkon")) {
									total_cena += 500 * daysBetween2;
								}
								else if (item.equals("tv")) {
									total_cena += 100 * daysBetween2;
								}
								else if (item.equals("pušačka soba")) {
									total_cena += 100 * daysBetween2;
								}
								else if (item.equals("nepušačka soba")) {
									total_cena += 100 * daysBetween2;
								}
							}
							rezervacija.set_cena(total_cena);
							
							rezervacije.add(rezervacija);
							
							String ispis = rezervacija.get_pocetak() + "," + rezervacija.get_kraj() + "," + rezervacija.get_tip() + "," + rezervacija.get_username() + "," + Stenje_Rezervacija.NA_CEKANJU + "," + rezervacija.get_cena() + "," + rezervacija.get_id() + "," + ispis_rezervacija + ",n";
							writer.write(ispis + "\n");
							writer.close();								
							
							//ODUZIMANJE NOVCA OD KORISNIKA I DODAVANJE ADMINU/HOTELU
							reader = new BufferedReader(new FileReader("src\\Users.csv"));
							line = "";
							List<String[]> gosti = new ArrayList<String[]>();
							while ((line = reader.readLine()) != null) {
								String[] temp = line.split(",");
								gosti.add(temp);
							}
							reader.close();
							
							Integer budzet_gosta = 0;
							Integer adminov_budzet = 0;
							for (String[] s : gosti) {
								if (s[2].equals("a")) {
									adminov_budzet = Integer.parseInt(s[9]);							
								}
								else if (s[0].equals(username)) {
									budzet_gosta = Integer.parseInt(s[9]);
									budzet_gosta -= total_cena;
									s[9] = budzet_gosta.toString();
								}
							}
							
							adminov_budzet += total_cena;
							
							for (String[] s : gosti) {
								if (s[2].equals("a")) {
									s[9] = adminov_budzet.toString();
									break;
								}
							}
							
							writer = new FileWriter("src\\Users.csv");
							writer.write("");
							writer.close();
							
							writer = new FileWriter("src\\Users.csv");
							for (String[] s : gosti) {
								ispis = "";
								for (String str : s) {
									ispis += str + ",";
								}
								ispis = ispis.substring(0, ispis.length() - 1);
								writer.write(ispis + "\n");
							}
							writer.close();		
							
							//UPISUJE U CSV PRIHOD RASHOD
							reader = new BufferedReader(new FileReader("src\\Prihodi_Rashodi.csv"));
							
							line = "";
							List<String[]> prihodi_rashodi = new ArrayList<String[]>();
							while ((line = reader.readLine()) != null) {
								String[] temp = line.split(",");
								prihodi_rashodi.add(temp);
							}
							reader.close();
							
							Integer total_cena2 = total_cena;
							String danas = LocalDate.now().toString();
							String[] temp = { danas, total_cena2.toString(), "prihod" };
							prihodi_rashodi.add(temp);
							
							writer = new FileWriter("src\\Prihodi_Rashodi.csv");
							writer.write("");
							writer.close();
							
							writer = new FileWriter("src\\Prihodi_Rashodi.csv");
							for (String[] s : prihodi_rashodi) {
								ispis = "";
								for (String str : s) {
									ispis += str + ",";
								}
								ispis = ispis.substring(0, ispis.length() - 1);
								writer.write(ispis + "\n");
							}
							writer.close();											
							
							JOptionPane.showMessageDialog(null, "Uspesno kreirana rezervacija!", "InfoBox: " + "Reservation creation", JOptionPane.INFORMATION_MESSAGE);
							
							// nece trebati ovde ali za sad nemam gde da ga premestim
							//reservate_tabela(txtDate, txt_nocenja);
						}
						else {
							JOptionPane.showMessageDialog(null, "Datum ne sme biti u proslosti!", "InfoBox: " + "greska", JOptionPane.INFORMATION_MESSAGE);
						}
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			});
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Wrong input!", "Information message", JOptionPane.INFORMATION_MESSAGE);
		}
		
		reservate.add(reservate_panel);
		reservate.setVisible(true);
	}
	
	public void PregledRezervacija(final String username) throws IOException {
		pregled = new JFrame("Pregled rezervacija");
		pregled_panel = new JPanel();

		pregled.setSize(650, 270);

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
			if (temp[3].equals(username)) {
				rezervacije_csv.add(temp);
			}
		}
		reader.close();
				
		int odobrene = 0;
		for (String[] r : rezervacije_csv) {
			if (r[4].equals("ODOBRENA"))
				odobrene++;
		}
		
		String[] collNames = { "ID", "Pocetni datum", "Krajnji datum", "Tip sobe", "Gost", "Cena", "Stanje" };
		Object[][] data = new Object[odobrene][7];		
		
		int i = 0;		
		for (String[] r : rezervacije_csv) {
			if (r[4].equals("ODOBRENA")) {
				data[i][0] = Integer.parseInt(r[6]);
				data[i][1] = r[0];
				data[i][2] = r[1];
				data[i][3] = r[2];
				data[i][4] = r[3];
				data[i][5] = Integer.parseInt(r[5]);
				data[i][6] = r[4];
				i++;
			}			
		}
		
		DefaultTableModel model = new DefaultTableModel(data, collNames) {
		    private static final long serialVersionUID = 1L;

		    @Override
		    public Class getColumnClass(int col) {
		        if (col == 5 || col == 0) {
		            return Integer.class;
		        }
		        return String.class;
		    }

		};
		final JTable table = new JTable(model);
		table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getTableHeader().setReorderingAllowed(false);
		table.setPreferredScrollableViewportSize(new Dimension(500, 150));
		table.setFillsViewportHeight(true);

		tableSorter.setModel((AbstractTableModel) table.getModel());
		table.setRowSorter(tableSorter);

		JScrollPane scrollPane = new JScrollPane(table);
		pregled_panel.add(scrollPane);

		JButton btn_otkazi = new JButton("Otkaži rezervaciju");
		pregled_panel.add(btn_otkazi, BorderLayout.SOUTH);
		
		btn_otkazi.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {					
				try {
					int red = table.getSelectedRow();
					if(red == -1) {
						JOptionPane.showMessageDialog(null, "Morate odabrati red u tabeli.", "Greska", JOptionPane.WARNING_MESSAGE);
					}
					else {	
						Otkazi(red, table, username);
						pregled.dispose();
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		pregled.add(pregled_panel);
		pregled.setVisible(true);
	}
	
	public void Otkazi(int red, JTable table, String username) throws IOException {		
		if(red == -1) {
			JOptionPane.showMessageDialog(null, "Morate odabrati red u tabeli.", "Greska", JOptionPane.WARNING_MESSAGE);
		}
		else {
			String id_rez = table.getValueAt(red, 0).toString();			
			
			reader = new BufferedReader(new FileReader("src\\Rezervacije.csv"));
			String line = "";
			List<String[]> rezervacije_csv = new ArrayList<String[]>();
			while ((line = reader.readLine()) != null) {
				String[] temp = line.split(",");
				rezervacije_csv.add(temp);
			}
			reader.close();
			
			System.out.println(id_rez + " " + username);
			for (String[] s : rezervacije_csv) {
				System.out.println(s[6] + " " + s[3]);
				if (s[6].equals(id_rez) && s[3].equals(username)) {
					s[4] = "OTKAZANA";
				}
			}
			
			FileWriter writer;
			writer = new FileWriter("src\\Rezervacije.csv");
			writer.write("");
			writer.close();
			
			writer = new FileWriter("src\\Rezervacije.csv");
			for (String[] s : rezervacije_csv) {
				String ispis = "";
				for (String str : s) {
					ispis += str + ",";
				}
				ispis = ispis.substring(0, ispis.length() - 1);
				writer.write(ispis + "\n");
			}
			writer.close();
			
			//DODAJE U CSV SA DATUMIMA ZA KASNIJE PREGLED KOD ADMINA
			writer = new FileWriter("src\\Gosti_Datumi_Otkazivanja.csv",true);
			String ispis = LocalDate.now().toString() + "\n";
			writer.write(ispis);
			writer.close();
			
			JOptionPane.showMessageDialog(null, "Uspešno otkazana rezervacija!", "InfoBox", JOptionPane.INFORMATION_MESSAGE);			
		}
	}
	
	public void Pregled_Svih_Rezervacija(String username) throws IOException {		
		JFrame pregled_svih = new JFrame("Pregled rezervacija");
		JPanel pregled_svih_panel = new JPanel();

		pregled_svih.setSize(650, 270);

		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		final int x = (int) ((dimension.getWidth() - pregled_svih.getWidth()) / 2);
		final int y = (int) ((dimension.getHeight() - pregled_svih.getHeight()) / 2);
		pregled_svih.setLocation(x, y);

		// CLOSING EVENT
		pregled_svih.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		pregled_svih.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				int opt = JOptionPane.showConfirmDialog(null, "Do you want to close the window?", "close",
						JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
				if (opt == JOptionPane.YES_OPTION) {
					e.getWindow().dispose();
				}
			}
		});
		
		reader = new BufferedReader(new FileReader("src\\Rezervacije.csv"));
		String line = "";
		List<String[]> rezervacije_csv = new ArrayList<String[]>();
		while ((line = reader.readLine()) != null) {
			String[] temp = line.split(",");
			if (temp[3].equals(username)) {
				rezervacije_csv.add(temp);
			}
		}
		reader.close();
		
		String[] collNames = { "ID", "Pocetni datum", "Krajnji datum", "Tip sobe", "Gost", "Cena", "Stanje" };
		Object[][] data = new Object[rezervacije_csv.size()][7];		
		
		Integer ukupan_trosak = 0;
		int i = 0;		
		for (String[] r : rezervacije_csv) {
			data[i][0] = Integer.parseInt(r[6]);
			data[i][1] = r[0];
			data[i][2] = r[1];
			data[i][3] = r[2];
			data[i][4] = r[3];
			if (r[4].equals("ODBIJENA")) {
				data[i][5] = "0";			
			}
			else {
				data[i][5] = r[5];
				ukupan_trosak += Integer.parseInt(r[5]);
			}
			data[i][6] = r[4];			
			i++;
		}
		
		DefaultTableModel model = new DefaultTableModel(data, collNames) {
		    private static final long serialVersionUID = 1L;

		    @Override
		    public Class getColumnClass(int col) {
		        if (col == 5 || col == 0) {
		            return Integer.class;
		        }
		        return String.class;
		    }

		};
		final JTable table = new JTable(model);
		table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getTableHeader().setReorderingAllowed(false);
		table.setPreferredScrollableViewportSize(new Dimension(500, 150));
		table.setFillsViewportHeight(true);

		tableSorter.setModel((AbstractTableModel) table.getModel());
		table.setRowSorter(tableSorter);

		JScrollPane scrollPane = new JScrollPane(table);
		pregled_svih_panel.add(scrollPane);
		
		JLabel lbl_ukupan_trosak = new JLabel("Ukupan trošak: " + ukupan_trosak.toString());
		pregled_svih_panel.add(lbl_ukupan_trosak);
		
		pregled_svih.add(pregled_svih_panel);
		pregled_svih.setVisible(true);
	}
}