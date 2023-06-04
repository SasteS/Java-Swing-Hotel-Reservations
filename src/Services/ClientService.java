package Services;

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
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

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
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;

import Etities.Client;
import Etities.Employee;
import Etities.Employee.Position;
import Etities.Tretman;
import net.miginfocom.swing.MigLayout;

public class ClientService {
	private List<Client> allClients;
	List<String[]> treatmentsCSV;
	List<String[]> allTreatmentsCSV;
	
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
	
	JLabel datumVremeLabel;
	JTextField datumVremeText;	
	
	String tipTretmana;
	String kozmeticar;
	String datumVreme;
	String clientUsername;
	
	String[] optionsToChooseKozmetic;
	JComboBox<String> jComboBoxKozmetic;
	
	public ClientService() {
		ClientsFromCSV();
	}
	
	public void MakeReservation(String username_) throws IOException {
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
		
		btn = new JButton("Make appointment");
		btn.setBounds(10, 80, 80, 25);
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					tipTretmana = jComboBoxTretman.getItemAt(jComboBoxTretman.getSelectedIndex());
					kozmeticar = jComboBoxKozmetic.getItemAt(jComboBoxKozmetic.getSelectedIndex());
					datumVreme = datumVremeText.getText();
					clientUsername = username_;
					
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
					
					Client oldClient = null;
					Client newClient = null;
					for (Client client : allClients) {
						if (client.GetUsername().equals(clientUsername)) {
							oldClient = client;
							if (client.GetCard().equals("false"))
								client.SetPay(client.GetPay() - treatment.GetCena());
							else
								client.SetPay(client.GetPay() - 0.9*treatment.GetCena());
							UpdateMoneySpent(treatment.GetCena());
							newClient = client;
							break;
						}
					}
					
					WriteNewPayToCsv(oldClient, newClient);
					
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
	
	private List<String> LoadTreatments() throws IOException {
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

	public void ViewReservations(String username_) throws IOException {
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
			if (temp[1].equals(username_)) {
				treatmentsCSV.add(temp);
				lines++;
			}
		}
		reader.close();
		
		String[] collNames = { "Vreme", "Tip", "Kozmeticar", "Status", "Cena" };
		String[][] data = new String[lines][5];

		int i = 0;
		for (String[] treatmet : treatmentsCSV) {
			data[i][0] = treatmet[3];
			data[i][1] = treatmet[2];
			data[i][2] = treatmet[4];
			data[i][3] = treatmet[5];
			data[i][4] = treatmet[6];
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
					try {						
						allTreatmentsCSV = LoadAllTreatments();
						
						FileWriter writer;
						writer = new FileWriter("src\\Data\\KozmetickiTretmani.csv");					
						writer.write("");
						writer.close();
						
						writer = new FileWriter("src\\Data\\KozmetickiTretmani.csv", true);
						for (String[] treatment : allTreatmentsCSV) {
							if (treatment[3].equals(treatmentId)) {
								writer.write(treatment[0] + "," + treatment[1] + "," + treatment[2] + "," + treatment[3] + "," + treatment[4] + ",OTKAZAO_KLIJENT," + treatment[6] + "\n");
								tretman = new Tretman(treatment[2]);
								clientUsername = treatment[1];
							}
							else
								writer.write(treatment[0] + "," + treatment[1] + "," + treatment[2] + "," + treatment[3] + "," + treatment[4] + "," + treatment[5] + "," + treatment[6] + "\n");
						}
						writer.close();
						
						allClients.clear();
						ClientsFromCSV();
						Client oldClient = null;
						Client newClient = null;
						for (Client client : allClients) {
							if (client.GetUsername().equals(clientUsername)) {
								oldClient = client;
								client.SetPay(client.GetPay() + 0.9 * tretman.GetCena());
								newClient = client;
								break;
							}
						}
						
						WriteNewPayToCsv(oldClient, newClient);
						
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
	
	public void ClientsFromCSV() {
		allClients = new ArrayList<Client>();
		
		BufferedReader reader = null;
		String line = "";
		
		try {
			reader = new BufferedReader(new FileReader("src\\Data\\Users.csv"));
			while((line = reader.readLine()) != null) {
				String[] nextRow = line.split(",");
				
				if(nextRow[2].equals("c")) {
					Client client = new Client(nextRow[3], nextRow[4], nextRow[0], nextRow[1], nextRow[5], nextRow[6], nextRow[7], Double.parseDouble(nextRow[8]), nextRow[9]);
					System.out.println(client.toString());
					allClients.add(client);
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
	
	public void WriteNewPayToCsv(Client oldClient, Client newClient) throws IOException, ParseException {
		ManagerService managerService = new ManagerService();
		List<Object> allEntities = managerService.GetAllEntities();
		
		for (Object entity : allEntities) {
			if (((Employee)entity).GetUsername().equals(oldClient.GetUsername())) {
				allEntities.set(allEntities.indexOf(entity), newClient);
				break;
			}
		}
		
		managerService.SetAllEntities(allEntities);
		managerService.WriteEntitiesToCSV();
	}
	
	public void PrintAllClients() {
		for(Client entity : allClients) {
			System.out.println(entity.toString());
		}
	}
	
	private List<String[]> LoadAllTreatments() throws IOException {
		List<String[]> treatments = new ArrayList<String[]>();
		BufferedReader reader = new BufferedReader(new FileReader("src\\Data\\KozmetickiTretmani.csv"));							//IZMENITI
		String line = "";
		
		treatmentsCSV = new ArrayList<String[]>();
		while ((line = reader.readLine()) != null) {
			String[] treatmentsSplit = line.split(",");
			treatments.add(treatmentsSplit);
		}
		reader.close();
		
		return treatments;
	}
}
