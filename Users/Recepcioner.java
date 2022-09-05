package Users;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JButton;
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
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
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

	JFrame pregled_soba;
	JPanel pregled_soba_panel;

	JFrame pregled2;
	JPanel pregled2_panel;

	LocalDate date_za_brisanje;

	JFrame dodela_sobarica;
	JPanel dodela_sobarica_panel;
	
	Integer cena_rez;
	JTextField tSearch;
	
	// metode
	public void RegisterGuest() { // e novog gosta - registracija
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

		passLabel.setText("Password(Broj pasosa)");
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

		dateLabel = new JLabel();

		dateLabel.setText("Datum Rodjenja(yyyy-MM-dd)");
		dateLabel.setBounds(10, 20, 80, 25);
		panel.add(dateLabel);

		dateText = new JTextField(20); // 20 je duzina textfields aka 20 karaktera
		dateText.setBounds(100, 20, 165, 25);
		panel.add(dateText, "wrap, left");

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
				user = userText.getText();
				char[] passCH = passText.getPassword();
				pass = new String(passCH);
				ime = imeText.getText();
				prezime = prezimeText.getText();
				pol = polText.getText();
				date = dateText.getText();
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
		        
		        //DATE VALIDATION
		        Pattern pattern_date = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$", Pattern.CASE_INSENSITIVE);
				Matcher matcher_date = pattern_date.matcher(date);
		        boolean found3 = matcher_date.find();
		        
				if (found == false || found2 == false || found3 == false) {
					JFrame optFrame = new JFrame();
					JOptionPane.showMessageDialog(optFrame, "Wrong input!", "Input Error Message",JOptionPane.OK_OPTION);
				}
				else if (found == true && found2 == true && found3 == true) {
					if (pass.equals("") || user.equals("")) {
						JFrame optFrame = new JFrame();
						JOptionPane.showMessageDialog(optFrame, "Wrong input!", "Input Error Message",
								JOptionPane.OK_OPTION);
					} else {
						check = false;
						Collection<String[]> coll = IscitajCSV();

						for (String[] item : coll) {
							if (user.equals(item[0]) && pass.equals(item[1])) {
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
								writer = new FileWriter("src\\Users.csv", true);
								String ispis = user + "," + pass + ",g," + ime + "," + prezime + "," + pol + "," + date
										+ "," + fon + "," + adresa + "\n";
								writer.write(ispis);
								writer.close();
								JFrame optFrame = new JFrame();
								JOptionPane.showMessageDialog(null, "Korisnik kreiran!", "Information message",
										JOptionPane.INFORMATION_MESSAGE);
								dialog.dispose();
							} catch (IOException e1) {
								e1.printStackTrace();
							}
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
			while ((line = reader.readLine()) != null) {
				String[] red = line.split(",");

				coll.add(red);
			}
			return coll;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
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

		// TABELA
		reader = new BufferedReader(new FileReader("src\\Rezervacije.csv"));
		String line = "";
		final List<String[]> rezervacije_csv = new ArrayList<String[]>();
		while ((line = reader.readLine()) != null) {
			String[] temp = line.split(",");
			if (temp[4].equals("ODOBRENA") && temp[8].equals("n")) {
				rezervacije_csv.add(temp);
			}
		}
		reader.close();

		String[] collNames = { "ID", "Pocetni datum", "Krajnji datum", "Tip sobe", "Gost", "Cena", "Stanje" };
		Object[][] data = new Object[rezervacije_csv.size()][7];

		int i = 0;
		for (String[] r : rezervacije_csv) {
			data[i][0] = Integer.parseInt(r[6]);
			data[i][1] = r[0];
			data[i][2] = r[1];
			data[i][3] = r[2];
			data[i][4] = r[3];
			data[i][5] = Integer.parseInt(r[5]);
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
		potvrdjene_rez_panel.add(scrollPane);

		potvrdjene_rez.add(potvrdjene_rez_panel);

		JPanel footer = new JPanel();
		JButton btn_check_in = new JButton("Check in");
		footer.add(btn_check_in);

		btn_check_in.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int red = table.getSelectedRow();
					if (red == -1) {
						JOptionPane.showMessageDialog(null, "Morate odabrati red u tabeli.", "Greska",
								JOptionPane.WARNING_MESSAGE);
					} else {
						String entitet_za_dodelu_sobe = table.getValueAt(red, 0).toString();
						cena_rez = Integer.parseInt(table.getValueAt(red, 5).toString());
						Sobe sobe = new Sobe();
						List<Soba> lista_soba = sobe.getSobe();

						String tip_potrebne_sobe = table.getValueAt(red, 3).toString();
						DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
						LocalDate pocetni = LocalDate.parse(table.getValueAt(red, 1).toString(), formatter);
						LocalDate krajnji = LocalDate.parse(table.getValueAt(red, 2).toString(), formatter);

						List<Soba> temp_sobe = new ArrayList<Soba>();
						for (Soba s : lista_soba) {
							List<LocalDate[]> dates_list = s.get_dates();
							LocalDate[] datumi_za_proveru = { pocetni, krajnji };

							if (s.get_Stanje().equals(Stanje_Sobe.SLOBODNA)) {
								boolean ne_sadrzi_date = true;
								if (dates_list.contains(datumi_za_proveru)) {
									ne_sadrzi_date = false;
								}
								if (ne_sadrzi_date == true) {
									if (Tip_Soba.valueOf(tip_potrebne_sobe).equals(Tip_Soba.ONE_ONE)) { // ||
																										// tip_potrebne_sobe.equals(Tip_Soba.TWO))
																										// {
										if (s.get_tip().equals(Tip_Soba.ONE_ONE) == false
												&& s.get_tip().equals(Tip_Soba.TWO) == false) {
											reader = new BufferedReader(new FileReader("src\\Sobe_Dodatni_Zahtevi.csv"));
											String line = "";
											List<String> dodatni = new ArrayList<String>();
											while ((line = reader.readLine()) != null) {
												String[] temp = line.split(",");
												if (s.get_broj() == Integer.parseInt(temp[0])) {
													String[] temp2 = temp[1].split(";");
													for (String str : temp2)
														dodatni.add(str);
												}
											}
											reader.close();

											reader = new BufferedReader(new FileReader("src\\Rezervacije.csv"));
											line = "";
											String[] stvari_dodatne = {};
											while ((line = reader.readLine()) != null) {
												String[] temp = line.split(",");
												if (temp[6].equals(entitet_za_dodelu_sobe)) {
													stvari_dodatne = temp[7].split(";");
												}
											}
											reader.close();

											boolean sve_ok = true;
											for (String item : stvari_dodatne) {
												if (dodatni.contains(item) == false) {
													sve_ok = false;
												}
											}
											if (sve_ok == true) {
												temp_sobe.add(s);
												continue;
											}
										}
									} else if (Tip_Soba.valueOf(tip_potrebne_sobe).equals(Tip_Soba.TWO)) { // ||
																											// tip_potrebne_sobe.equals(Tip_Soba.TWO))
																											// {
										if (s.get_tip().equals(Tip_Soba.ONE_ONE) == false
												&& s.get_tip().equals(Tip_Soba.TWO) == false) {
											reader = new BufferedReader(
													new FileReader("src\\Sobe_Dodatni_Zahtevi.csv"));
											String line = "";
											List<String> dodatni = new ArrayList<String>();
											while ((line = reader.readLine()) != null) {
												String[] temp = line.split(",");
												if (s.get_broj() == Integer.parseInt(temp[0])) {
													String[] temp2 = temp[1].split(";");
													for (String str : temp2)
														dodatni.add(str);
												}
											}
											reader.close();

											reader = new BufferedReader(new FileReader("src\\Rezervacije.csv"));
											line = "";
											String[] stvari_dodatne = {};
											while ((line = reader.readLine()) != null) {
												String[] temp = line.split(",");
												if (temp[6].equals(entitet_za_dodelu_sobe)) {
													stvari_dodatne = temp[7].split(";");
												}
											}
											reader.close();

											boolean sve_ok = true;
											for (String item : stvari_dodatne) {
												if (dodatni.contains(item) == false) {
													sve_ok = false;
												}
											}
											if (sve_ok == true) {
												temp_sobe.add(s);
												continue;
											}
										}
									} else if (Tip_Soba.valueOf(tip_potrebne_sobe).equals(Tip_Soba.ONE)) {
										if (s.get_tip().equals(Tip_Soba.ONE) == false) {
											reader = new BufferedReader(
													new FileReader("src\\Sobe_Dodatni_Zahtevi.csv"));
											String line = "";
											List<String> dodatni = new ArrayList<String>();
											while ((line = reader.readLine()) != null) {
												String[] temp = line.split(",");
												if (s.get_broj() == Integer.parseInt(temp[0])) {
													String[] temp2 = temp[1].split(";");
													for (String str : temp2)
														dodatni.add(str);
												}
											}
											reader.close();

											reader = new BufferedReader(new FileReader("src\\Rezervacije.csv"));
											line = "";
											String[] stvari_dodatne = {};
											while ((line = reader.readLine()) != null) {
												String[] temp = line.split(",");
												if (temp[6].equals(entitet_za_dodelu_sobe)) {
													stvari_dodatne = temp[7].split(";");
												}
											}
											reader.close();

											boolean sve_ok = true;
											for (String item : stvari_dodatne) {
												if (dodatni.contains(item) == false) {
													sve_ok = false;
												}
											}
											if (sve_ok == true) {
												temp_sobe.add(s);
												continue;
											}
										}
									} else if (Tip_Soba.valueOf(tip_potrebne_sobe).equals(Tip_Soba.TWO_ONE)) {
										if (s.get_tip().equals(Tip_Soba.TWO_ONE) == false) {
											reader = new BufferedReader(
													new FileReader("src\\Sobe_Dodatni_Zahtevi.csv"));
											String line = "";
											List<String> dodatni = new ArrayList<String>();
											while ((line = reader.readLine()) != null) {
												String[] temp = line.split(",");
												if (s.get_broj() == Integer.parseInt(temp[0])) {
													String[] temp2 = temp[1].split(";");
													for (String str : temp2)
														dodatni.add(str);
												}
											}
											reader.close();

											reader = new BufferedReader(new FileReader("src\\Rezervacije.csv"));
											line = "";
											String[] stvari_dodatne = {};
											while ((line = reader.readLine()) != null) {
												String[] temp = line.split(",");
												if (temp[6].equals(entitet_za_dodelu_sobe)) {
													stvari_dodatne = temp[7].split(";");
												}
											}
											reader.close();

											boolean sve_ok = true;
											for (String item : stvari_dodatne) {
												if (dodatni.contains(item) == false) {
													sve_ok = false;
												}
											}
											if (sve_ok == true) {
												temp_sobe.add(s);
												continue;
											}
										}
									} else if (Tip_Soba.valueOf(tip_potrebne_sobe).equals(Tip_Soba.TWO_TWO)) {
										if (s.get_tip().equals(Tip_Soba.TWO_TWO) == false) {
											reader = new BufferedReader(
													new FileReader("src\\Sobe_Dodatni_Zahtevi.csv"));
											String line = "";
											List<String> dodatni = new ArrayList<String>();
											while ((line = reader.readLine()) != null) {
												String[] temp = line.split(",");
												if (s.get_broj() == Integer.parseInt(temp[0])) {
													String[] temp2 = temp[1].split(";");
													for (String str : temp2)
														dodatni.add(str);
												}
											}
											reader.close();

											reader = new BufferedReader(new FileReader("src\\Rezervacije.csv"));
											line = "";
											String[] stvari_dodatne = {};
											while ((line = reader.readLine()) != null) {
												String[] temp = line.split(",");
												if (temp[6].equals(entitet_za_dodelu_sobe)) {
													stvari_dodatne = temp[7].split(";");
												}
											}
											reader.close();

											boolean sve_ok = true;
											for (String item : stvari_dodatne) {
												if (dodatni.contains(item) == false) {
													sve_ok = false;
												}
											}
											if (sve_ok == true) {
												temp_sobe.add(s);
												continue;
											}
										}
									}
								}
							} else {
								temp_sobe.add(s);
							}
						}

						for (Soba s : temp_sobe) {
							lista_soba.remove(s);
						}

						if (lista_soba.isEmpty()) {
							JOptionPane.showMessageDialog(null, "Ne postoje dostupne sobe", "Error message",
									JOptionPane.ERROR_MESSAGE);
						} else {
							potvrdjene_rez.dispose();
							LocalDate[] datumi_za_proveru = { pocetni, krajnji };
							Tabela_Potencijalnih_Soba(lista_soba, datumi_za_proveru, entitet_za_dodelu_sobe);
						}
					}

				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});

		potvrdjene_rez.add(footer, BorderLayout.SOUTH);

		potvrdjene_rez.setVisible(true);
	}

	public void Tabela_Potencijalnih_Soba(List<Soba> lista_soba, final LocalDate[] datumi_za_proveru,
			final String entitet_za_dodelu_sobe) {
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

		JButton dodeli_sobu = new JButton("Dodeli sobu");
		sobe_window_panel.add(dodeli_sobu);

		dodeli_sobu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int red = table.getSelectedRow();
				if (red == -1) {
					JOptionPane.showMessageDialog(null, "Morate odabrati red u tabeli.", "Greska",
							JOptionPane.WARNING_MESSAGE);
				} else {
					String broj_izabrane_sobe = table.getValueAt(red, 0).toString();
					try {
						reader = new BufferedReader(new FileReader("src\\Sobe_Datumi.csv"));
						String line = "";
						List<String[]> update_list = new ArrayList<String[]>();
						while ((line = reader.readLine()) != null) {
							String[] temp = line.split(",");
							update_list.add(temp);
						}

						List<String[]> update_list2 = new ArrayList<String[]>();
						for (String[] s : update_list) {
							if (s[0].equals(broj_izabrane_sobe)) {
								String[] temp;
								String tmp = "";
								for (String item : s) {
									tmp += item + ",";
								}
								tmp += datumi_za_proveru[0] + ";" + datumi_za_proveru[1];
								s = tmp.split(",");
							}
							update_list2.add(s);
						}

						FileWriter writer = new FileWriter("src\\Sobe_Datumi.csv");
						writer.write("");
						writer.close();

						// PUNI NOVIM INFORMACIJAMA
						writer = new FileWriter("src\\Sobe_Datumi.csv", true);
						for (String[] temp : update_list2) {
							String ispis = "";
							for (String s : temp) {
								ispis += s + ",";
							}
							ispis = ispis.substring(0, ispis.length() - 1);
							System.out.println(ispis);
							writer.write(ispis + "\n");
						}
						writer.close();

						// MENJA STATUS SOBE NA ZAUZETO
						reader = new BufferedReader(new FileReader("src\\Sobe.csv"));
						line = "";
						update_list.clear();
						while ((line = reader.readLine()) != null) {
							String[] temp = line.split(",");
							update_list.add(temp);
						}

						for (String[] item : update_list) {
							if (item[0].equals(broj_izabrane_sobe.toString())) {
								item[3] = "ZAUZETO";
							}
						}

						writer = new FileWriter("src\\Sobe.csv");
						writer.write("");
						writer.close();

						writer = new FileWriter("src\\Sobe.csv", true);
						for (String[] temp : update_list) {
							String ispis = "";
							for (String s : temp) {
								ispis += s + ",";
							}
							ispis = ispis.substring(0, ispis.length() - 1);
							System.out.println(ispis);
							writer.write(ispis + "\n");
						}
						writer.close();
						
						//SOBE_PRIHODI ISPIS
						reader = new BufferedReader(new FileReader("src\\Sobe_Prihodi.csv"));
						
						line = "";
						List<String[]> sobe_prihodi = new ArrayList<String[]>();
						while ((line = reader.readLine()) != null) {
							String[] temp = line.split(",");
							sobe_prihodi.add(temp);
						}
						reader.close();
						
						String prihod = cena_rez.toString();
						String danas = LocalDate.now().toString();
						
						String dodati_na_ispis = prihod + ";" + danas;
						int i = 0;
						List<String[]> sobe_prihodi_copy = new ArrayList<String[]>();
						for (String[] s : sobe_prihodi) {
							String[] temp = new String[s.length + 1];							
							if (broj_izabrane_sobe.toString().equals(s[0])) {
								String temp_ispis = "";
								int j = 0;
								for (String str : s) {
									temp[j] = str;
									j++;
								}
								temp[j] = dodati_na_ispis;
								sobe_prihodi_copy.add(temp);
							}
							else {
								sobe_prihodi_copy.add(s);
							}
							i++;
						}
						
						writer = new FileWriter("src\\Sobe_Prihodi.csv");
						writer.write("");
						writer.close();
						
						writer = new FileWriter("src\\Sobe_Prihodi.csv");
						for (String[] s : sobe_prihodi_copy) {
							String ispis = "";
							for (String str : s) {
								ispis += str + ",";
							}
							ispis = ispis.substring(0, ispis.length() - 1);
							writer.write(ispis + "\n");
						}
						writer.close();
						
						JOptionPane.showMessageDialog(null, "Uspesno dodeljena soba!", "Info",
								JOptionPane.INFORMATION_MESSAGE);
						sobe_window.dispose(); // MORAM JOS DA UKLONIM REZERVACIJU IZ CHECK_IN OPCIJA

						// TREBA DA OVERWRITE REZERVACIJE.CSV DA DODA NA REZERVACIJU KOJOJ JE DODAO SOBU
						// UMESTO,N ,D
						reader = new BufferedReader(new FileReader("src\\Rezervacije.csv"));

						line = "";
						final List<String[]> rezervacije_csv = new ArrayList<String[]>();
						while ((line = reader.readLine()) != null) {
							String[] temp = line.split(",");
							rezervacije_csv.add(temp);
						}
						reader.close();

						writer = new FileWriter("src\\Rezervacije.csv");
						writer.write("");
						writer.close();

						writer = new FileWriter("src\\Rezervacije.csv", true);
						for (String[] temp : rezervacije_csv) {
							String ispis = "";
							for (String s : temp) {
								ispis += s + ",";
							}
							ispis = ispis.substring(0, ispis.length() - 1);
							if (temp[6].equals(entitet_za_dodelu_sobe)) {
								if (ispis.substring(ispis.length() - 1).equals("n")) {
									ispis = ispis.substring(0, ispis.length() - 1);
									ispis += "d";
								}
							}
							System.out.println(ispis);
							writer.write(ispis + "\n");
						}
						writer.close();

					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});

		sobe_window.add(sobe_window_panel);
		sobe_window.setVisible(true);
	}

	public void RegulisiRezervaciju() throws IOException {// sredjivace rezervacije odobri/odbij, treba da se proveri
															// postoji li soba na tom datumu
		pregled2 = new JFrame("Pregled rezervacija");
		pregled2_panel = new JPanel();

		pregled2.setSize(670, 500);

		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		final int x = (int) ((dimension.getWidth() - pregled2.getWidth()) / 2);
		final int y = (int) ((dimension.getHeight() - pregled2.getHeight()) / 2);
		pregled2.setLocation(x, y);

		// CLOSING EVENT
		pregled2.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		pregled2.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				int opt = JOptionPane.showConfirmDialog(null, "Do you want to close the window?", "close",
						JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
				if (opt == JOptionPane.YES_OPTION) {
					e.getWindow().dispose();
				}
			}
		});

		// TABELA
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

		String[] collNames = { "ID", "Pocetni datum", "Krajnji datum", "Tip sobe", "Gost", "Cena", "Stanje", "Dodatne usluge" };
		Object[][] data = new Object[rezervacije_csv.size()][8];

		int i = 0;
		for (String[] r : rezervacije_csv) {
			data[i][0] = Integer.parseInt(r[6]);
			data[i][1] = r[0];
			data[i][2] = r[1];
			data[i][3] = r[2];
			data[i][4] = r[3];
			data[i][5] = Integer.parseInt(r[5]);
			data[i][6] = r[4];
			data[i][7] = r[7];
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
		table.setPreferredScrollableViewportSize(new Dimension(650, 150));
		table.setFillsViewportHeight(true);

		tableSorter.setModel((AbstractTableModel) table.getModel());
		table.setRowSorter(tableSorter);

		JScrollPane scrollPane = new JScrollPane(table);
		pregled2_panel.add(scrollPane);

		pregled2.add(pregled2_panel);

		//SEARCH BAR
		JPanel footer = new JPanel();
		JButton btn_odobri = new JButton("Odobri");
		footer.add(btn_odobri);
		JButton btn_odbij = new JButton("Odbij");
		footer.add(btn_odbij);
		
		tSearch = new JTextField(20);
		JPanel pSearch = new JPanel(new FlowLayout(FlowLayout.LEFT));		
		pSearch.setBackground(Color.LIGHT_GRAY);
		pSearch.add(new JLabel("Search:"));
		pSearch.add(tSearch);
		
		pregled2_panel.add(pSearch, BorderLayout.SOUTH);
		
		tSearch.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				changedUpdate(e);
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				changedUpdate(e);
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				//System.out.println("~ "+tfSearch.getText());
				if (tSearch.getText().trim().length() == 0) {
				     tableSorter.setRowFilter(null);
				  } else {
					  tableSorter.setRowFilter(RowFilter.regexFilter("(?i)" + tSearch.getText().trim()));
				  }
			}
		});

		//ODOBRI/ODBIJ
		rezervacije_csv.clear();

		reader = new BufferedReader(new FileReader("src\\Rezervacije.csv"));
		while ((line = reader.readLine()) != null) {
			String[] temp = line.split(",");
			rezervacije_csv.add(temp);
		}

		btn_odobri.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int red = table.getSelectedRow();
				if (red == -1) {
					JOptionPane.showMessageDialog(null, "Morate odabrati red u tabeli.", "Greska",
							JOptionPane.WARNING_MESSAGE);
				} else {
					String entitet_za_odobravanje = table.getValueAt(red, 0).toString();
					for (String[] r : rezervacije_csv) {
						if (r[6].equals(entitet_za_odobravanje)) {
							r[4] = "ODOBRENA";
							break;
						}
					}
					FileWriter writer;
					try {
						// PRAZNI DOSADASNJI FAJL
						writer = new FileWriter("src\\Rezervacije.csv");
						writer.write("");
						writer.close();

						// PUNI NOVIM INFORMACIJAMA
						writer = new FileWriter("src\\Rezervacije.csv", true);
						for (String[] temp : rezervacije_csv) {
							String ispis = temp[0] + "," + temp[1] + "," + temp[2] + "," + temp[3] + "," + temp[4] + ","
									+ temp[5] + "," + temp[6] + "," + temp[7] + "," + temp[8] + "\n";
							writer.write(ispis);
						}
						writer.close();

						//DODAJE U CSV SA DATUMIMA ZA KASNIJE PREGLED KOD ADMINA
						writer = new FileWriter("src\\Recepcioneri_Datumi_Odobravajna.csv",true);
						String ispis = LocalDate.now().toString() + "\n";
						writer.write(ispis);
						writer.close();
						
						JFrame optFrame = new JFrame();
						JOptionPane.showMessageDialog(null, "Rezervacija odobrena!", "Information message",
								JOptionPane.INFORMATION_MESSAGE);
						pregled2.dispose();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		});

		btn_odbij.addActionListener(new ActionListener() { // MORAM DODATI JOS I ZA NOVAC OD GOSTA/ADMINA-HOTELA
			public void actionPerformed(ActionEvent e) {
				int red = table.getSelectedRow();
				if (red == -1) {
					JOptionPane.showMessageDialog(null, "Morate odabrati red u tabeli.", "Greska",
							JOptionPane.WARNING_MESSAGE);
				} else {
					String ime_gosta = "";
					Integer pare_nazad = 0;
					String entitet_za_odbijanje = table.getValueAt(red, 0).toString();
					for (String[] r : rezervacije_csv) {
						if (r[6].equals(entitet_za_odbijanje)) {
							r[4] = "ODBIJENA";
							ime_gosta = r[3];
							pare_nazad = Integer.parseInt(r[5]);
							break;
						}
					}
					FileWriter writer;
					try {
						// PRAZNI DOSADASNJI FAJL
						writer = new FileWriter("src\\Rezervacije.csv");
						writer.write("");
						writer.close();

						// PUNI NOVIM INFORMACIJAMA
						writer = new FileWriter("src\\Rezervacije.csv", true);
						for (String[] temp : rezervacije_csv) {
							String ispis = temp[0] + "," + temp[1] + "," + temp[2] + "," + temp[3] + "," + temp[4] + ","
									+ temp[5] + "," + temp[6] + "," + temp[7] + "," + temp[8] + "\n";
							writer.write(ispis);
						}
						writer.close();

						//DODAJE U CSV SA DATUMIMA ZA KASNIJE PREGLED KOD ADMINA
						writer = new FileWriter("src\\Recepcioneri_Datumi_Odbijanja.csv",true);
						String ispis = LocalDate.now().toString() + "\n";
						writer.write(ispis);
						writer.close();
						
						JFrame optFrame = new JFrame();
						JOptionPane.showMessageDialog(null, "Rezervacija odbijena!", "Information message",
								JOptionPane.INFORMATION_MESSAGE);
						pregled2.dispose();
					} catch (IOException e1) {
						e1.printStackTrace();
					}

					// GOSTU CIJA JE BILA REZRVACIJA VRACA PARE NAZAD
					try {
						reader = new BufferedReader(new FileReader("src\\Users.csv"));

						String line = "";
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
							} else if (s[0].equals(ime_gosta)) {
								budzet_gosta = Integer.parseInt(s[9]);
								budzet_gosta += pare_nazad;
								s[9] = budzet_gosta.toString();
							}
						}

						adminov_budzet -= pare_nazad;

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
							String ispis = "";
							for (String str : s) {
								ispis += str + ",";
							}
							ispis = ispis.substring(0, ispis.length() - 1);
							writer.write(ispis + "\n");
						}
						writer.close();

						// UPISUJE U CSV PRIHOD RASHOD
						reader = new BufferedReader(new FileReader("src\\Prihodi_Rashodi.csv"));

						line = "";
						List<String[]> prihodi_rashodi = new ArrayList<String[]>();
						while ((line = reader.readLine()) != null) {
							String[] temp = line.split(",");
							prihodi_rashodi.add(temp);
						}
						reader.close();

						String danas = LocalDate.now().toString();
						String[] temp = { danas, pare_nazad.toString(), "rashod" };
						prihodi_rashodi.add(temp);

						writer = new FileWriter("src\\Prihodi_Rashodi.csv");
						writer.write("");
						writer.close();

						writer = new FileWriter("src\\Prihodi_Rashodi.csv");
						for (String[] s : prihodi_rashodi) {
							String ispis = "";
							for (String str : s) {
								ispis += str + ",";
							}
							ispis = ispis.substring(0, ispis.length() - 1);
							writer.write(ispis + "\n");
						}
						writer.close();

					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}
			}
		});

		pregled2.add(footer, BorderLayout.SOUTH);
		pregled2.setVisible(true);
	}

	public void Guest_List() throws IOException {
		guests_table = new JFrame("Check in");
		guests_table_panel = new JPanel();

		guests_table.setSize(650, 500);

		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		final int x = (int) ((dimension.getWidth() - guests_table.getWidth()) / 2);
		final int y = (int) ((dimension.getHeight() - guests_table.getHeight()) / 2);
		guests_table.setLocation(x, y);

		// CLOSING EVENT
		guests_table.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		guests_table.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				int opt = JOptionPane.showConfirmDialog(null, "Do you want to close the window?", "close",
						JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
				if (opt == JOptionPane.YES_OPTION) {
					e.getWindow().dispose();
				}
			}
		});

		guests_table.add(guests_table_panel);

		// TABELA
		String line = "";

		reader = new BufferedReader(new FileReader("src\\Users.csv"));

		int lines = 0;
		List<String[]> container = new ArrayList<String[]>();
		while ((line = reader.readLine()) != null) {
			String[] temp = line.split(",");
			if (temp[2].equals("g")) {
				container.add(temp);
				lines++;
			}
		}
		reader.close();

		String[] collNames = { "Korisnicko ime", "Ime", "Prezime" };
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

		// TABELA
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
			data[i][0] = Integer.parseInt(r[6]);
			data[i][1] = r[0];
			data[i][2] = r[1];
			data[i][3] = r[2];
			data[i][4] = r[3];
			data[i][5] = Integer.parseInt(r[5]);
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
		pregled_panel.add(scrollPane);

		pregled.add(pregled_panel);
		pregled.setVisible(true);
	}
	
	public void Pregled_Soba() throws IOException {
		pregled_soba = new JFrame("Rezervacija");
		pregled_soba_panel = new JPanel();

		pregled_soba.setSize(650, 500);

		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		final int x = (int) ((dimension.getWidth() - pregled_soba.getWidth()) / 2);
		final int y = (int) ((dimension.getHeight() - pregled_soba.getHeight()) / 2);
		pregled_soba.setLocation(x, y);

		// CLOSING EVENT
		pregled_soba.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		pregled_soba.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				int opt = JOptionPane.showConfirmDialog(null, "Do you want to close the window?", "close",
						JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
				if (opt == JOptionPane.YES_OPTION) {
					e.getWindow().dispose();
				}
			}
		});

		pregled_soba.add(pregled_soba_panel);

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

		int i = 0;
		int oduzmi_sobe_koje_ne_idu_u_tabelu = 0;
		for (Soba soba : sobe.getSobe()) {
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
		pregled_soba_panel.add(scrollPane);

		pregled_soba.setVisible(true);
	}

	public void Check_Out() throws IOException {
		pregled_soba = new JFrame("Rezervacija");
		pregled_soba_panel = new JPanel();

		pregled_soba.setSize(650, 500);

		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		final int x = (int) ((dimension.getWidth() - pregled_soba.getWidth()) / 2);
		final int y = (int) ((dimension.getHeight() - pregled_soba.getHeight()) / 2);
		pregled_soba.setLocation(x, y);

		// CLOSING EVENT
		pregled_soba.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		pregled_soba.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				int opt = JOptionPane.showConfirmDialog(null, "Do you want to close the window?", "close",
						JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
				if (opt == JOptionPane.YES_OPTION) {
					e.getWindow().dispose();
				}
			}
		});

		pregled_soba.add(pregled_soba_panel);

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

		int i = 0;
		int oduzmi_sobe_koje_ne_idu_u_tabelu = 0;
		for (Soba soba : sobe.getSobe()) {
			Stanje_Sobe temp_stanje = soba.get_Stanje();
			if (temp_stanje.equals(Stanje_Sobe.ZAUZETO)) {
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

				data[i][3] = "zauzeta";

				data[i][4] = soba.get_cena();
				i++;
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
		pregled_soba_panel.add(scrollPane);

		JLabel lbl = new JLabel("Unesite datum za uklanjanje.");
		pregled_soba_panel.add(lbl);

		DateFormat df = new SimpleDateFormat("dd.MM.yyyy.");
		final JFormattedTextField txtDate = new JFormattedTextField(df);
		txtDate.setBounds(100, 20, 165, 25);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
		LocalDateTime now = LocalDateTime.now();
		txtDate.setText(dtf.format(now));
		pregled_soba_panel.add(txtDate);

		JButton btn_check_out = new JButton("Check-out");
		pregled_soba_panel.add(btn_check_out);
		btn_check_out.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int red = table.getSelectedRow();
				if (red == -1) {
					JOptionPane.showMessageDialog(null, "Morate odabrati red u tabeli.", "Greska",
							JOptionPane.WARNING_MESSAGE);
				} else {
					String broj_sobe = table.getValueAt(red, 0).toString();
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
					date_za_brisanje = LocalDate.parse(txtDate.getText(), formatter);
					System.out.println(date_za_brisanje);
					// SETUJEM STATUS SOBE NA CISCENJE I DODELJUJEM SOBARICU

					Sobe sobe;
					try {
						sobe = new Sobe();

						for (Soba s : sobe.getSobe()) {
							if (s.get_broj() == Integer.parseInt(broj_sobe)) {
								s.set_Stanje("SPREMANJE");
							}
						}
						sobe.Rewrite_SobeCSV();

						// BRISEM DATUM REZERVACIJE
						reader = new BufferedReader(new FileReader("src\\Sobe_Datumi.csv"));
						String line = "";
						List<String[]> arr = new ArrayList<String[]>();
						while ((line = reader.readLine()) != null) {
							String[] temp = line.split(",");
							arr.add(temp);
						}
						
						boolean ne_postoji_date = false;
						FileWriter writer;
						try {
							writer = new FileWriter("src\\Sobe_Datumi.csv");
							writer.write("");
							writer.close();

							writer = new FileWriter("src\\Sobe_Datumi.csv", true);
							for (String[] s : arr) {
								String ispis = "";
								if (s[0].equals(broj_sobe)) {
									String[] spare_array = new String[s.length - 1];
									try {
										int cntr = 0;
										for (String str : s) {
											if (str.contains(date_za_brisanje.toString())) {
												continue;
											} else {
												spare_array[cntr] = str;
												cntr++;
											}
										}
									} catch (Exception e2) {
										JOptionPane.showMessageDialog(null, "Datum ne postoji", "Error",
												JOptionPane.ERROR_MESSAGE);
										
										ne_postoji_date = true;
									}

									for (String str : spare_array) {
										ispis += str + ",";
									}
								} else {
									for (String str : s) {
										ispis += str + ",";
									}
								}
								ispis = ispis.substring(0, ispis.length() - 1);
								writer.write(ispis + "\n");
							}
							writer.close();
						} catch (IOException e1) {
							e1.printStackTrace();
						}

						// DODELJIVANJE SOBE SOBARICI
						if (ne_postoji_date == false) {
							Dodeli_Sobaricu(broj_sobe);

							pregled_soba.dispose();
						}

					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		});

		pregled_soba.setVisible(true);
	}

	public void Dodeli_Sobaricu(final String broj_sobe) throws IOException {
		reader = new BufferedReader(new FileReader("src\\Sobarice_Sobe.csv"));
		String line = "";
		int lines = 0;
		List<String[]> arr = new ArrayList<String[]>();

		while ((line = reader.readLine()) != null) {
			String[] temp = line.split(",");
			arr.add(temp);
			lines++;
		}

		String ime_sobarice_sa_min_posla = "";
		int broj_soba_za_ciscenje = 0;
		int min = 20;
		List<String> arr2 = new ArrayList<String>();
		for (String[] s : arr) {
			broj_soba_za_ciscenje = s.length - 1;
			if (broj_soba_za_ciscenje < min) {
				min = broj_soba_za_ciscenje;
				ime_sobarice_sa_min_posla = s[0];
			}
		}

		for (String[] s : arr) {
			if (s[0] == ime_sobarice_sa_min_posla) {
				for (String str : s) {
					arr2.add(str);
				}
				arr2.add(broj_sobe);
			}
		}

		FileWriter writer = new FileWriter("src\\Sobarice_Sobe.csv");
		writer.write("");
		writer.close();

		// PUNI NOVIM INFORMACIJAMA
		writer = new FileWriter("src\\Sobarice_Sobe.csv", true);
		for (String[] temp : arr) {
			String ispis = "";
			if (temp[0] == ime_sobarice_sa_min_posla) {
				for (String str : arr2) {
					ispis += str + ",";
				}
			} else {
				for (String str : temp) {
					ispis += str + ",";
				}
			}
			ispis = ispis.substring(0, ispis.length() - 1);
			writer.write(ispis + "\n");
		}
		writer.close();
		
		
		//OPTERECENJA
		reader = new BufferedReader(new FileReader("src\\Sobarice_Opterecenje.csv"));
		line = "";
		lines = 0;
		arr.clear();

		while ((line = reader.readLine()) != null) {
			String[] temp = line.split(",");
			arr.add(temp);
			lines++;
		}
		
		Integer broj_opterecenja = 0;
		for (String[] s : arr) {
			if (s[0].equals(ime_sobarice_sa_min_posla)) {
				broj_opterecenja = Integer.parseInt(s[1]);
				broj_opterecenja++;
				s[1] = broj_opterecenja.toString();
			}
		}
		
		writer = new FileWriter("src\\Sobarice_Opterecenje.csv");
		writer.write("");
		writer.close();

		// PUNI NOVIM INFORMACIJAMA
		writer = new FileWriter("src\\Sobarice_Opterecenje.csv", true);
		for (String[] temp : arr) {
			String ispis = "";
			if (temp[0] == ime_sobarice_sa_min_posla) {
				for (String str : arr2) {
					ispis += str + ",";
				}
			} else {
				for (String str : temp) {
					ispis += str + ",";
				}
			}
			ispis = ispis.substring(0, ispis.length() - 1);
			writer.write(ispis + "\n");
		}
		writer.close();
	}
}
