package Users;

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
	public void ZahtevRezervacija(final String username) { // pravi zahtev za rezervaciju koji ce kasnije recepcioner da
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

		String[] opcije = { "dorucak", "rucak", "vecera" };
		final JList list_dodatne_opc = new JList(opcije);
		list_dodatne_opc.setVisibleRowCount(3);
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
					reservate.dispose();
					JOptionPane.showMessageDialog(null, "Uspesno kreirana rezervacija!",
							"InfoBox: " + "Reservation creation", JOptionPane.INFORMATION_MESSAGE);

					reader = new BufferedReader(new FileReader("src\\Rezervacije.csv"));
					String line = "";
					List<String> container = new ArrayList<String>();
					while ((line = reader.readLine()) != null) {
						container.add(line);
					}
					reader.close();

					FileWriter writer = new FileWriter("src\\Rezervacije.csv", true);

					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
					LocalDate formatterdtext_converted_to_date_start = LocalDate.parse(txtDate.getText(), formatter);
					LocalDate formatterdtext_converted_to_date_end = formatterdtext_converted_to_date_start.plusDays(Integer.parseInt(txt_nocenja.getText()));

					//DODATI I U ENTITET REZERVACIJE NOVU REZERVACIJU - TREBA IZ CSVA DA PRAVI TABELU?
					Rezervacije rezervacija = new Rezervacije();				
					rezervacija.set_pocetak(formatterdtext_converted_to_date_start);
					rezervacija.set_kraj(formatterdtext_converted_to_date_end);
					rezervacija.set_username(username);
				
					reader = new BufferedReader(new FileReader("src\\Cenovnik.csv"));
					line = "";
					List<String[]> cenovnik = new ArrayList<String[]>();
					while ((line = reader.readLine()) != null) {
						cenovnik.add(line.split(","));
					}
					reader.close();
					
					//CENA TIPA SOBE
					if (txt_br_ljudi.getText().equals("1")) {
						rezervacija.set_tip(Tip_Soba.ONE);
						int brojac = 0;
						for (String[] s : cenovnik) {
							if (brojac < 5) {
								if (rezervacija.get_tip().equals(Tip_Soba.valueOf(s[0])))
									rezervacija.set_cena(Integer.parseInt(s[1]));
							}
							else break;
							brojac++;
						}
					}
					else if (txt_br_ljudi.getText().equals("2")) {
						Tip_Soba[] values = { Tip_Soba.ONE_ONE, Tip_Soba.TWO };
			            int length = values.length;
			            int randIndex = new Random().nextInt(length);		
			            
						rezervacija.set_tip(values[randIndex]);
						int brojac = 0;
						for (String[] s : cenovnik) {
							if (brojac < 5) {
								if (rezervacija.get_tip().equals(Tip_Soba.valueOf(s[0])))
									rezervacija.set_cena(Integer.parseInt(s[1]));
							}
							else break;
							brojac++;
						}
					}					
					else if (txt_br_ljudi.getText().equals("3")) {
						rezervacija.set_tip(Tip_Soba.TWO_ONE);
						int brojac = 0;
						for (String[] s : cenovnik) {
							if (brojac < 5) {
								if (rezervacija.get_tip().equals(Tip_Soba.valueOf(s[0])))
									rezervacija.set_cena(Integer.parseInt(s[1]));
							}
							else break;
							brojac++;
						}
					}
					else if (txt_br_ljudi.getText().equals("4")) {
						rezervacija.set_tip(Tip_Soba.TWO_TWO);
						int brojac = 0;
						for (String[] s : cenovnik) {
							if (brojac < 5) {
								if (rezervacija.get_tip().equals(Tip_Soba.valueOf(s[0])))
									rezervacija.set_cena(Integer.parseInt(s[1]));
							}
							else break;
							brojac++;
						}
					}
					
					//DODATNE USLUGE CENA - RADI
					String[] cena_usluge = ispis_rezervacija.split(";");
					int total_cena = 0;
					for (String item : cena_usluge) {
						System.out.println(item);
						if (item.equals("dorucak")) {
							total_cena += 300;
						}
						else if (item.equals("rucak")) {
							total_cena += 500;
						}
						else if (item.equals("vecera")) {
							total_cena += 400;
						}	
					}
					int nova_cena = rezervacija.get_cena() + total_cena;
					rezervacija.set_cena(nova_cena);
					
					rezervacije.add(rezervacija);
					
					String ispis = rezervacija.get_pocetak() + "," + rezervacija.get_kraj() + "," + rezervacija.get_tip() + "," + rezervacija.get_username() + "," + Stenje_Rezervacija.NA_CEKANJU + "," + rezervacija.get_cena() + "," + rezervacija.get_id() + "," + ispis_rezervacija;
					writer.write(ispis + "\n");
					writer.close();								
					
					// nece trebati ovde ali za sad nemam gde da ga premestim
					reservate_tabela(txtDate, txt_nocenja);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});

		reservate.add(reservate_panel);
		reservate.setVisible(true);
	}

	public void reservate_tabela(JFormattedTextField txtDate, JTextField txt_nocenja) throws IOException {
		reservate_table = new JFrame("Rezervacija");
		reservate_table_panel = new JPanel();

		reservate_table.setSize(650, 500);

		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		final int x = (int) ((dimension.getWidth() - reservate_table.getWidth()) / 2);
		final int y = (int) ((dimension.getHeight() - reservate_table.getHeight()) / 2);
		reservate_table.setLocation(x, y);

		// CLOSING EVENT
		reservate_table.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		reservate_table.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				int opt = JOptionPane.showConfirmDialog(null, "Do you want to close the window?", "close",
						JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
				if (opt == JOptionPane.YES_OPTION) {
					e.getWindow().dispose();
				}
			}
		});

		reservate_table.add(reservate_table_panel);

		// TABELA
		String line = "";

		reader = new BufferedReader(new FileReader("src\\Sobe.csv"));

		int lines = 0;
		while ((line = reader.readLine()) != null) {
			lines++;
		}
		reader.close();

		String[] collNames = { "Broj sobe", "Tip sobe", "Broj ljudi", "Stanje", "Cena" };
		Object[][] data = new Object[lines][5];

		Sobe sobe = new Sobe();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
		LocalDate formatterdtext_converted_to_date_start = LocalDate.parse(txtDate.getText(), formatter);
		LocalDate formatterdtext_converted_to_date_end = formatterdtext_converted_to_date_start.plusDays(Integer.parseInt(txt_nocenja.getText()));
		LocalDate[] bothDates = { formatterdtext_converted_to_date_start, formatterdtext_converted_to_date_end };

		int i = 0;
		int oduzmi_sobe_koje_ne_idu_u_tabelu = 0;
		for (Soba soba : sobe.getSobe()) {
			if (soba.get_dates().isEmpty() || soba.get_dates().contains(bothDates) == false) {// da moze bilo koji date/ da li postoji bas taj/ da li se preklapaju samo odredjeni dani
				boolean ne_moze = false;
				for (LocalDate[] ld : soba.get_dates()) {
					if (ld[0].equals(formatterdtext_converted_to_date_start)
							|| ld[1].equals(formatterdtext_converted_to_date_end)) {
						ne_moze = true;
						oduzmi_sobe_koje_ne_idu_u_tabelu++;
						break;
					}
				}
				if (!ne_moze) {
					data[i][0] = soba.get_broj();

					Tip_Soba temp_tip = soba.get_tip();
					if (temp_tip.equals(Tip_Soba.ONE)) {
						data[i][1] = "jednokrevetna";
					} else if (temp_tip.equals(Tip_Soba.ONE_ONE) || temp_tip.equals(Tip_Soba.TWO)) {
						data[i][1] = "dvokrevetna";
					} else if (temp_tip.equals(Tip_Soba.TWO_ONE)) {
						data[i][1] = "trokrevetna";
					} else if (temp_tip.equals(Tip_Soba.TWO_TWO)) {
						data[i][1] = "četvorokrevetna";
					}

					data[i][2] = soba.get_broj_ljudi();

					Stanje_Sobe temp_stanje = soba.get_Stanje();
					if (temp_stanje.equals(Stanje_Sobe.ZAUZETO)) {
						data[i][3] = "zauzeta";
					} else if (temp_stanje.equals(Stanje_Sobe.SPREMANJE)) {
						data[i][3] = "spremanje";
					} else if (temp_stanje.equals(Stanje_Sobe.SLOBODNA)) {
						data[i][3] = "slobodna";
					}
					
					data[i][4] = soba.get_cena();
					i++;
				}
			}
		}
		Object[][] data2 = new Object[i][5]; // IZBACUJE VISAK PRAZNIH REDOVA U TABELI(ONI U KOJIMA BI BILI IZBACEBE
												// SOBE)
		for (int j = 0; j < i; j++) {
			for (int k = 0; k < 5; k++) {
				data2[j][k] = data[j][k];
			}
		}

		final JTable table = new JTable(data2, collNames);
		table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getTableHeader().setReorderingAllowed(false);
		table.setPreferredScrollableViewportSize(new Dimension(500, 150));
		table.setFillsViewportHeight(true);

		tableSorter.setModel((AbstractTableModel) table.getModel());
		table.setRowSorter(tableSorter);

		JScrollPane scrollPane = new JScrollPane(table);
		reservate_table_panel.add(scrollPane);

		reservate_table.setVisible(true);
	}
	
	public void PregledRezervacija(final String username) throws IOException {
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
			if (temp[3].equals(username)) {
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
		pregled.setVisible(true);
	}
}