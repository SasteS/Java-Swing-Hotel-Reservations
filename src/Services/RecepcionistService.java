package Services;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.DefaultComboBoxModel;
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
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import Etities.Client;
import Etities.Employee;
import Etities.Kozmetic;
import Etities.Manager;
import Etities.Recepcionist;
import Etities.Tretman;
import Etities.Employee.Position;
import net.miginfocom.swing.MigLayout;

public class RecepcionistService {
	private List<Recepcionist> allRecepcionists;
	List<String[]> treatmentsCSV;
	String recepcionistUsername;
	
	JFrame frame;
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
	String fon;
	String adresa;
	
	protected JTable table;
	protected TableRowSorter<AbstractTableModel> tableSorter = new TableRowSorter<AbstractTableModel>();
	
//	JLabel tipTretmanaLabel;
//	JTextField tipTretmanaText;
//	JLabel kozmeticarLabel;
//	JTextField kozmeticarText;
	JLabel datumVremeLabel;
	JTextField datumVremeText;	
	JLabel clientLabel;
	JTextField clientText;
	
	String tipTretmana;
	String kozmeticar;
	String datumVreme;
	String clientUsername;
	
	JButton btnChange;
	
	JLabel idLabel;
	JTextField idText;
	JLabel kozmeticarLabel;
	JTextField kozmeticarText;
	JLabel klijentLabel;
	JTextField klijentText;
	JLabel tipTretmanaLabel;
	JTextField tipTretmanaText;
	
	String[] optionsToChooseKozmetic;
	JComboBox<String> jComboBoxKozmetic;
	
	public RecepcionistService(String username) {
		RecepcionistsFromCSV();
		recepcionistUsername = username;
	}
	
	public void MakeReservation() throws IOException {
		dialog = new JDialog();
		panel = new JPanel();

		dialog.setSize(400, 500);

		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - dialog.getWidth()) / 2);
		int y = (int) ((dimension.getHeight() - dialog.getHeight()) / 2);
		dialog.setLocation(x, y);

		dialog.setTitle("Rezervacija tretmana");
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
		
		//BITNE INFORMACIJE		
		List<String> optionsToChooseTretman = LoadTreatments();
		String[] tretmanList = new String[optionsToChooseTretman.size()];
		for (String option : optionsToChooseTretman)
			tretmanList[optionsToChooseTretman.indexOf(option)] = option;
		final JComboBox<String> jComboBoxTretman = new JComboBox<>(tretmanList);
		jComboBoxTretman.setBounds(80, 50, 140, 20);
        panel.add(jComboBoxTretman, "wrap, left");
        
        optionsToChooseKozmetic = new String[] {};
        jComboBoxTretman.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int kozmeticCount = 0;
		        tipTretmana = jComboBoxTretman.getItemAt(jComboBoxTretman.getSelectedIndex());
		        try {
					BufferedReader reader = new BufferedReader(new FileReader("src\\Data\\Users.csv"));
					String line = "";
					while((line = reader.readLine()) != null) {
						String[] nextRow = line.split(",");
						
						String[] treatments = nextRow[8].split(";");
		 				
						if(nextRow[2].equals("k")) {
							for (String option : treatments) {
								if (option.equals(tipTretmana))
									kozmeticCount++;
							}
						}
					}
				} catch(Exception e1) {
					System.out.println("error reading the files.");
				}
		        
		        optionsToChooseKozmetic = new String[kozmeticCount + 1];
		        System.out.println("duzina niza = " + ((Integer)kozmeticCount).toString());
		        kozmeticCount = 0;
		        try {
					BufferedReader reader = new BufferedReader(new FileReader("src\\Data\\Users.csv"));
					String line = "";
					while((line = reader.readLine()) != null) {
						String[] nextRow = line.split(",");
						
						String[] treatments = nextRow[8].split(";");
						
						if(nextRow[2].equals("k")) {
							for (String option : treatments) {
								if (option.equals(tipTretmana)) {
									optionsToChooseKozmetic[kozmeticCount] = nextRow[0];
									kozmeticCount++;
								}
							}
						}
					}
				} catch(Exception e1) {
					System.out.println("error reading the files.");
				}
		        optionsToChooseKozmetic[kozmeticCount] = "auto";
		        jComboBoxKozmetic.setModel(new DefaultComboBoxModel<>(optionsToChooseKozmetic));
			}
        });
        		
		jComboBoxKozmetic = new JComboBox<>(optionsToChooseKozmetic);
		jComboBoxKozmetic.setBounds(80, 50, 140, 20);
        panel.add(jComboBoxKozmetic, "wrap, left");
		
		datumVremeLabel = new JLabel();

		datumVremeLabel.setText("Datum vreme(dd-MM-yyyy HH:mm)");
		datumVremeLabel.setBounds(10, 20, 80, 25);
		panel.add(datumVremeLabel);

		datumVremeText = new JTextField(20); // 20 je duzina textfields aka 20 karaktera
		datumVremeText.setBounds(100, 20, 165, 25);
		panel.add(datumVremeText, "wrap, left");

		clientLabel = new JLabel();

		clientLabel.setText("Client Username");
		clientLabel.setBounds(10, 20, 80, 25);
		panel.add(clientLabel);

		clientText = new JTextField(20); // 20 je duzina textfields aka 20 karaktera
		clientText.setBounds(100, 20, 165, 25);
		panel.add(clientText, "wrap, left");
		
		btn = new JButton("Make appointment");
		btn.setBounds(10, 80, 80, 25);
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					tipTretmana = jComboBoxTretman.getItemAt(jComboBoxTretman.getSelectedIndex());
					kozmeticar = jComboBoxKozmetic.getItemAt(jComboBoxKozmetic.getSelectedIndex());
					datumVreme = datumVremeText.getText();
					clientUsername = clientText.getText();
					
					System.out.println(tipTretmana);
					System.out.println(kozmeticar);
					System.out.println(datumVreme);
					System.out.println(clientUsername);
					
					//DODAJ U CSV
					FileWriter writer;
					writer = new FileWriter("src\\Data\\KozmetickiTretmani.csv", true);
					Random rand = new Random();
			        Integer id = rand.nextInt(100) + 1;
			        
			        int randomIndex = rand.nextInt(optionsToChooseKozmetic.length);
			        String randomString = optionsToChooseKozmetic[randomIndex];
			        if (kozmeticar.equals("auto"))
			        	kozmeticar = randomString;
					
			        Tretman treatment = new Tretman(tipTretmana);
					String ispis = id.toString() + "," + clientUsername + "," + treatment.GetTip() + ","+ datumVreme + "," + kozmeticar + ",ZAKAZAN," + treatment.GetCena().toString() + "\n";
					
					writer.write(ispis);
					writer.close();
					
					WriteNewPayToCsv(treatment.GetCena(), false);
					UpdateMoneySpent(treatment.GetCena());
					
					AddBonusToRecepcionist();
					
					JOptionPane.showMessageDialog(null, "Uspesno", "Information message", JOptionPane.INFORMATION_MESSAGE);
					dialog.dispose();
				}
				catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "Popuniti sva polja!", "Information message", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		panel.add(btn);

		dialog.setVisible(true);
	}
	
	public List<String> LoadTreatments() throws IOException {
		List<String> treatments = new ArrayList<String>();
		BufferedReader reader = new BufferedReader(new FileReader("src\\Data\\VrsteTretmana.csv"));							//IZMENITI
		String line = "";
		
		treatmentsCSV = new ArrayList<String[]>();
		while ((line = reader.readLine()) != null) {
			String[] treatmentsSplit = line.split(",");
			treatments.add(treatmentsSplit[0]);
		}
		reader.close();
		
		return treatments;
	}

	public void ReservationView() throws IOException {
		frame = new JFrame("uvid u rezervacije");
		panel = new JPanel();
		frame.setSize(650, 270);

		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		final int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
		final int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
		frame.setLocation(x, y);

		// CLOSING EVENT
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				int opt = JOptionPane.showConfirmDialog(null, "Do you want to close the window?", "close",
						JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
				if (opt == JOptionPane.YES_OPTION) {
					e.getWindow().dispose();
				}
			}
		});
		
		frame.add(panel);
		
		BufferedReader reader = new BufferedReader(new FileReader("src\\Data\\KozmetickiTretmani.csv"));							//IZMENITI
		String line = "";
		
		treatmentsCSV = new ArrayList<String[]>();
		int lines = 0;
		while ((line = reader.readLine()) != null) {
			String[] temp = line.split(",");
			treatmentsCSV.add(temp);
			lines++;
		}
		reader.close();
		
		String[] collNames = { "Id","Vreme", "Tip", "Klijent", "Kozmeticar", "Status", "Cena" };
		String[][] data = new String[lines][7];

		int i = 0;
		for (String[] treatmet : treatmentsCSV) {
			data[i][0] = treatmet[0];
			data[i][1] = treatmet[3];
			data[i][2] = treatmet[2];
			data[i][3] = treatmet[1];
			data[i][4] = treatmet[4];
			data[i][5] = treatmet[5];
			data[i][6] = treatmet[6];
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
		panel.add(scrollPane);
		
		btnChange = new JButton("Change appointment");
		btnChange.setBounds(10, 80, 80, 25);
		btnChange.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int red = table.getSelectedRow();
				if(red == -1) {
					JOptionPane.showMessageDialog(null, "Morate odabrati red u tabeli.", "Greska", JOptionPane.WARNING_MESSAGE);
				}
				else {
					String treatmentId = table.getValueAt(red, 0).toString();
					
					for (String[] treatment : treatmentsCSV) {							
						if (treatmentId.equals(treatment[0])) {
							Detaljan_pregled(treatment[0], treatment[3], treatment[2], treatment[1], treatment[4], treatment[5]);
						}
					}
				}
			}
		});
		panel.add(btnChange);
		
		JButton btnOtkazi = new JButton("Cancel appointment");
		btnOtkazi.setBounds(10, 80, 80, 25);
		btnOtkazi.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int red = table.getSelectedRow();
				if(red == -1) {
					JOptionPane.showMessageDialog(null, "Morate odabrati red u tabeli.", "Greska", JOptionPane.WARNING_MESSAGE);
				}
				else {
					String treatmentId = table.getValueAt(red, 0).toString();
					
					Tretman tretman = null;
					
					for (String[] treatment : treatmentsCSV) {							
						if (treatmentId.equals(treatment[0])) {
							try {
								tretman = new Tretman(treatment[2]);
								clientUsername = treatment[1];
							} catch (IOException e1) {
								e1.printStackTrace();
							}
							treatmentsCSV.set(treatmentsCSV.indexOf(treatment), new String[] { treatment[0], treatment[1], treatment[2], treatment[3], treatment[4], "OTKAZAO_SALON", treatment[6] });
							break;
						}
					}
					try {
						FileWriter writer;
						writer = new FileWriter("src\\Data\\KozmetickiTretmani.csv");					
						writer.write("");
						writer.close();
						
						writer = new FileWriter("src\\Data\\KozmetickiTretmani.csv", true);					
						for (String[] treatment : treatmentsCSV) {
							writer.write(treatment[0] + "," + treatment[1] + "," + treatment[2] + "," + treatment[3] + "," + treatment[4] + "," + treatment[5] + "," + treatment[6] + "\n");
						}
						writer.close();
						
						WriteNewPayToCsv(tretman.GetCena(), true);
						
						JOptionPane.showMessageDialog(null, "Zakazani tretman otkazan!", "Information message", JOptionPane.INFORMATION_MESSAGE);
					} catch (Exception e1) {
						System.out.println("error while writing.");
					}
				}
			}
		});
		panel.add(btnOtkazi);
		
		frame.setVisible(true);
	}
	
	public void Detaljan_pregled(String id_, String datumVreme_, String tip_, String klijent_, String kozmeticar_, String status_) {
		JFrame detaljan = new JFrame("Detaljan pregled");
		JPanel detaljan_panel = new JPanel();
		MigLayout ml = new MigLayout("wrap 3", "[][][]", "[]10[]10[]10[]20[]");
		detaljan_panel.setLayout(ml);
		
		detaljan.setSize(500, 500);
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (int) ((dimension.getWidth() - detaljan.getWidth()) / 2);
	    int y = (int) ((dimension.getHeight() - detaljan.getHeight()) / 2);
	    detaljan.setLocation(x, y);
		
	    detaljan.setTitle("Izmena rezervacije");
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

		datumVremeLabel = new JLabel();
		
		datumVremeLabel.setText("Datum i vreme");
		datumVremeLabel.setBounds(10, 20, 80, 25);
		detaljan_panel.add(datumVremeLabel);
		
		datumVremeText = new JTextField(20); //20 je duzina textfields aka 20 karaktera
		datumVremeText.setBounds(100, 20, 165, 25);
		datumVremeText.setText(datumVreme_);
		detaljan_panel.add(datumVremeText, "wrap, left");

		tipTretmanaLabel = new JLabel();
		
		tipTretmanaLabel.setText("tip tretmana");
		tipTretmanaLabel.setBounds(10, 20, 80, 25);
		detaljan_panel.add(tipTretmanaLabel);
		
		tipTretmanaText = new JTextField(20); //20 je duzina textfields aka 20 karaktera
		tipTretmanaText.setBounds(100, 20, 165, 25);
		tipTretmanaText.setText(tip_);
		detaljan_panel.add(tipTretmanaText, "wrap, left");
		
		klijentLabel = new JLabel();
		
		klijentLabel.setText("klijent");
		klijentLabel.setBounds(10, 20, 80, 25);
		detaljan_panel.add(klijentLabel);
		
		klijentText = new JTextField(20); //20 je duzina textfields aka 20 karaktera
		klijentText.setBounds(100, 20, 165, 25);
		klijentText.setText(klijent_);
		detaljan_panel.add(klijentText, "wrap, left");
		
		kozmeticarLabel = new JLabel();
		
		kozmeticarLabel.setText("kozmeticar");
		kozmeticarLabel.setBounds(10, 20, 80, 25);
		detaljan_panel.add(kozmeticarLabel);
		
		kozmeticarText = new JTextField(20); //20 je duzina textfields aka 20 karaktera
		kozmeticarText.setBounds(100, 20, 165, 25);
		kozmeticarText.setText(kozmeticar_);
		detaljan_panel.add(kozmeticarText, "wrap, left");
		
		btn = new JButton("Save update");
		btn.setBounds(10, 80, 80, 25);
		btn.addActionListener(new ActionListener() { 
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String datumVreme = datumVremeText.getText();
					String tip = tipTretmanaText.getText();
					String klijent = klijentText.getText();
					String kozmeticar = kozmeticarText.getText();
					
					for	(String[] treatment : treatmentsCSV) {
						if (treatment[0].equals(id_)) {
							treatmentsCSV.set(treatmentsCSV.indexOf(treatment), new String[] { id_, klijent, tip, datumVreme, kozmeticar, status_ });
							break;
						}
					}
					
					FileWriter writer;
					writer = new FileWriter("src\\Data\\KozmetickiTretmani.csv");					
					writer.write("");
					writer.close();
					
					writer = new FileWriter("src\\Data\\KozmetickiTretmani.csv", true);					
					for (String[] treatment : treatmentsCSV) {
						writer.write(treatment[0] + "," + treatment[1] + "," + treatment[2] + "," + treatment[3] + "," + treatment[4] + "," + treatment[5] + "," + treatment[6] +"\n");
					}
					writer.close();
					
					JOptionPane.showMessageDialog(null, "Zakazani tretman promenjen!", "Information message", JOptionPane.INFORMATION_MESSAGE);
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
	
	public void RecepcionistsFromCSV(){
		allRecepcionists = new ArrayList<Recepcionist>();
		
		BufferedReader reader = null;
		String line = "";
		
		try {
			reader = new BufferedReader(new FileReader("src\\Data\\Users.csv"));
			while((line = reader.readLine()) != null) {
				String[] nextRow = line.split(",");
				
				if(nextRow[2].equals("r")) {
					Recepcionist recepcionist = new Recepcionist(nextRow[3], nextRow[4], nextRow[0], nextRow[1], nextRow[5], nextRow[6], nextRow[7], nextRow[8], Integer.parseInt(nextRow[9]), Double.parseDouble(nextRow[10]), Integer.parseInt(nextRow[11]));
					System.out.println(recepcionist.toString());
					allRecepcionists.add(recepcionist);
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
	
	public void UpdateMoneySpent(Double cena) throws IOException, ParseException {
		ManagerService managerService = new ManagerService();
		List<Object> allEntities = managerService.GetAllEntities();
		HashMap<String, Double> clientMoneySpent = new HashMap<String, Double>();
		
		BufferedReader reader = new BufferedReader(new FileReader("src\\Data\\MoneySpent.csv"));
		String line = "";
		while((line = reader.readLine()) != null) {
			String[] lineSplit = line.split(",");
			clientMoneySpent.put(lineSplit[0], Double.parseDouble(lineSplit[1]));
		}
		reader.close();
		
		String ispis = "";
		for (Object entity : allEntities) {
			if (((Employee)entity).GetPosition().equals(Position.CLIENT)) {
				if (((Employee)entity).GetUsername().equals(clientUsername))
					clientMoneySpent.put(((Client) entity).GetUsername(), clientMoneySpent.get(((Client) entity).GetUsername()) + cena);
				ispis += ((Client) entity).GetUsername() + "," + clientMoneySpent.get(((Client) entity).GetUsername()).toString() + "\n";
			}
		}
		
		FileWriter writer;
		writer = new FileWriter("src\\Data\\MoneySpent.csv");
		writer.write("");
		writer.write(ispis);
		writer.close();
	}
	
	public void WriteNewPayToCsv(Double cena, boolean add) throws IOException, ParseException {
		ManagerService managerService = new ManagerService();
		List<Object> allEntities = managerService.GetAllEntities();
		
		for (Object entity : allEntities) {
			if (((Employee)entity).GetUsername().equals(clientUsername)) {
				Client newClient = (Client) entity;
				if (add)
					newClient.SetPay(newClient.GetPay() + cena);
				else {
					if (newClient.GetCard().equals("false"))
						newClient.SetPay(newClient.GetPay() - cena);
					else
						newClient.SetPay(newClient.GetPay() - 0.9*cena);
					
				}
				allEntities.set(allEntities.indexOf(entity), newClient);
				break;
			}
		}
		
		managerService.SetAllEntities(allEntities);
		managerService.WriteEntitiesToCSV();
	}
	
	public void AddBonusToRecepcionist() throws IOException, ParseException {		
		ManagerService managerService = new ManagerService();
		List<Object> allEntities = managerService.GetAllEntities();
		
		for (Object entity : allEntities) {
			if (((Employee)entity).GetUsername().equals(recepcionistUsername)) {
				Recepcionist newRecepcionist = (Recepcionist) entity;
				newRecepcionist.SetPay(newRecepcionist.GetPay() + 10);
				newRecepcionist.SetBonus(newRecepcionist.GetBonus() + 1);
				allEntities.set(allEntities.indexOf(entity), newRecepcionist);
				break;
			}
		}
		
		managerService.SetAllEntities(allEntities);
		managerService.WriteEntitiesToCSV();
	}
	
	public void PrintAllRecepcionists() {
		for(Recepcionist entity : allRecepcionists) {
			System.out.println(entity.toString());
		}
	}
}