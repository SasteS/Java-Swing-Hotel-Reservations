package Services;

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
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;

import org.joda.time.LocalDate;

import Etities.Employee;
import Etities.Kozmetic;
import Etities.Recepcionist;
import Etities.Tretman;

public class KozmeticService {
	private List<Kozmetic> allKozmetics;
	private String username;
	
	JFrame frame;
	JPanel panel;
	
   	protected JTable table;
	protected TableRowSorter<AbstractTableModel> tableSorter = new TableRowSorter<AbstractTableModel>();
	
	public KozmeticService(String username_) {
		username = username_;
		KozmeticsFromCSV();
	}
	
	public void KozmeticTreatments() throws IOException {		
		frame = new JFrame("pregled tretmana");
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
		
		List<String[]> treatmentsCSV = new ArrayList<String[]>();
		int lines = 0;		
		while ((line = reader.readLine()) != null) {
			String[] temp = line.split(",");
			if (temp[4].equals(username) && temp[5].equals("ZAKAZAN")) {
				treatmentsCSV.add(temp);
				lines++;
			}
		}
		reader.close();
		
		String[] collNames = { "Tip", "Klijent", "Vreme" };
		Object[][] data = new Object[lines][3];

		int i = 0;
		for (String[] treatmet : treatmentsCSV) {
			data[i][0] = treatmet[2];
			data[i][1] = treatmet[1];
			data[i][2] = treatmet[3];
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
		
		JButton btnIzvrsi = new JButton("Perform appointment");
		btnIzvrsi.setBounds(10, 80, 80, 25);
		btnIzvrsi.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int red = table.getSelectedRow();
				if(red == -1) {
					JOptionPane.showMessageDialog(null, "Morate odabrati red u tabeli.", "Greska", JOptionPane.WARNING_MESSAGE);
				}
				else {
					String treatmentId = table.getValueAt(red, 0).toString();
					
					Tretman tretman = null;
					int indexOfTreatment = 0;
					for (String[] treatment : treatmentsCSV) {
						if (treatmentId.equals(treatment[2])) { // u ovom slucaju id je tip
							try {
								tretman = new Tretman(treatment[2]);
								//clientUsername = treatment[1];
							} catch (IOException e1) {
								e1.printStackTrace();
							}
							indexOfTreatment = treatmentsCSV.indexOf(treatment);
							treatmentsCSV.set(indexOfTreatment, new String[] { treatment[0], treatment[1], treatment[2], treatment[3], treatment[4], "IZVRSEN", treatment[6] });
							break;
						}
					}
					try {
						List<String[]> allTreatmentsCSV = LoadAllTreatments();
						
						FileWriter writer;
						writer = new FileWriter("src\\Data\\KozmetickiTretmani.csv");					
						writer.write("");
						writer.close();
						
						writer = new FileWriter("src\\Data\\KozmetickiTretmani.csv", true);			
						int amount = 0;
						for (String[] treatment : allTreatmentsCSV) {
							if (treatment[0].equals(treatmentsCSV.get(indexOfTreatment)[0])) {
								writer.write(treatment[0] + "," + treatment[1] + "," + treatment[2] + "," + treatment[3] + "," + treatment[4] + ",IZVRSEN," + treatment[6] + "\n");
								amount = Integer.parseInt(treatment[6]);
							}
							else
								writer.write(treatment[0] + "," + treatment[1] + "," + treatment[2] + "," + treatment[3] + "," + treatment[4] + "," + treatment[5] + "," + treatment[6] + "\n");
						}
						writer.close();
						
						//WriteNewPayToCsv(tretman.GetCena(), true);
						
						AddBonusToKozmetic();
						UpdateKozmeticMonthlyRevenue(amount);
						
						JOptionPane.showMessageDialog(null, "Zakazani tretman izvršen!", "Information message", JOptionPane.INFORMATION_MESSAGE);
						frame.dispose();
					} catch (Exception e1) {
						System.out.println("error while writing.");
					}
				}
			}
		});
		panel.add(btnIzvrsi);
		
		frame.setVisible(true);	
	}
	
	public void UpdateKozmeticMonthlyRevenue(int amount) throws IOException, ParseException {
		HashMap<Date,List<String[]>> dateRevenues = new HashMap<Date,List<String[]>>();
	    List<Date> chronologicallyOrderedDates = new ArrayList<Date>();
	    
	    BufferedReader reader = new BufferedReader(new FileReader("src\\Data\\KozmeticJobRevenue.csv"));
	    String line = "";
		while((line = reader.readLine()) != null) {
			String[] lineSplit = line.split(",");
		
	        String pattern = "dd-MM-yyyy";  // Specify the pattern according to your date format
	        Date key;
	        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
			key = dateFormat.parse(lineSplit[0]);
			
			List<String[]> kozmeticsInfo = new ArrayList<String[]>();
			for (int i = 1; i < lineSplit.length; i++) {
				String[] kozmeticInfo = lineSplit[i].split(";");
				kozmeticsInfo.add(kozmeticInfo);
			}
			chronologicallyOrderedDates.add(key);
			dateRevenues.put(key, kozmeticsInfo);
		}
		reader.close();
		
		for (Date key : chronologicallyOrderedDates) {
			Calendar calendar = Calendar.getInstance();
			int currentMonth = calendar.get(Calendar.MONTH) + 1;
			calendar.setTime(key);
	        int keyMonth = calendar.get(Calendar.MONTH) + 1;
			if (keyMonth == currentMonth) {
				for (String[] kozmeticInfo : dateRevenues.get(key)) {
					if (kozmeticInfo[0].equals(username)) {
						for(Kozmetic kozmetic : allKozmetics) {
							if(kozmetic.GetUsername().equals(username)) {
								Integer newBonus = (Integer) kozmetic.GetBonus();
								kozmeticInfo[1] = (newBonus).toString();
								Double newAmountToAdd = Double.parseDouble(kozmeticInfo[2]) + amount;
								kozmeticInfo[2] = newAmountToAdd.toString();
								break;
							}
						}
					} 
				}
			}	
		}
		
		String ispis = "";
		for (Date key : chronologicallyOrderedDates) {
			String pattern = "dd-MM-yyyy";
	        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
	        String keyToString= dateFormat.format(key);
			ispis += keyToString;
			for (String[] kozmeticInfo : dateRevenues.get(key)) {
				for(Kozmetic kozmetic : allKozmetics) {
					if(kozmetic.GetUsername().equals(kozmeticInfo[0])) {
						ispis += "," + kozmetic.GetUsername() + ";" + kozmeticInfo[1] + ";" + kozmeticInfo[2];
						break;
					}
				}
			}
			ispis += "\n";
		}
		
		FileWriter writer;
		writer = new FileWriter("src\\Data\\KozmeticJobRevenue.csv");					
		writer.write("");
		writer.write(ispis);
		writer.close();
	}
	
	public void AddBonusToKozmetic() throws IOException, ParseException {		
		ManagerService managerService = new ManagerService();
		List<Object> allEntities = managerService.GetAllEntities();
		
		for (Object entity : allEntities) {
			if (((Employee)entity).GetUsername().equals(username)) {
				Kozmetic newKozmetic = (Kozmetic) entity;
				newKozmetic.SetPay(newKozmetic.GetPay() + 10);
				newKozmetic.SetBonus(newKozmetic.GetBonus() + 1);
				allEntities.set(allEntities.indexOf(entity), newKozmetic);
				break;
			}
		}
		
		for (Kozmetic kozmetic : allKozmetics) {
			if (kozmetic.GetUsername().equals(username))
				kozmetic.SetBonus(kozmetic.GetBonus() + 1);
		}
		
		managerService.SetAllEntities(allEntities);
		managerService.WriteEntitiesToCSV();
	}
	
	public void DisplaySchedule() throws IOException { //Mora da ima sortirano po vremenu
		frame = new JFrame("uvid u raspored");
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
		
		List<String[]> treatmentsCSV = new ArrayList<String[]>();
		int lines = 0;
		while ((line = reader.readLine()) != null) {
			String[] temp = line.split(",");
			if (temp[4].equals(username) && temp[5].equals("ZAKAZAN")) {
				treatmentsCSV.add(temp);
				lines++;
			}
		}
		reader.close();
		
		String[] collNames = { "Vreme", "Tip", "Klijent" };
		String[][] data = new String[lines][3];

		int i = 0;
		for (String[] treatmet : treatmentsCSV) {
			data[i][0] = treatmet[3];
			data[i][1] = treatmet[2];
			data[i][2] = treatmet[1];
			i++;
		}
		
		//String[] dates = new String[lines];
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"); 
		List<LocalDateTime> treatmentDates = new ArrayList<>();
		LocalDateTime date;
		for (int k = 0; k < lines; k++) {
			date = LocalDateTime.parse(data[k][0], formatter);
			treatmentDates.add(date);
			System.out.println(treatmentDates.get(k));
		}
		Collections.sort(treatmentDates);//, Comparator.naturalOrder());
		
		int comparisonResult;
		String[][] data2 = new String[lines][3];
		for(LocalDateTime currentDate : treatmentDates) {
			for(String[] dataLine : data) {
				LocalDateTime dateTime = LocalDateTime.parse(dataLine[0], formatter);
				comparisonResult = currentDate.compareTo(dateTime);
				if (comparisonResult == 0) {
					data2[treatmentDates.indexOf(currentDate)] = dataLine;
				}
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
		panel.add(scrollPane);
		
		frame.setVisible(true);	
	}
	
	public void KozmeticsFromCSV() {
		allKozmetics = new ArrayList<Kozmetic>();
		
		BufferedReader reader = null;
		String line = "";
		
		try {
			reader = new BufferedReader(new FileReader("src\\Data\\Users.csv"));
			while((line = reader.readLine()) != null) {
				String[] nextRow = line.split(",");
				
				if(nextRow[2].equals("k")) {
					String[] treatmentType = nextRow[8].split(";");
					Kozmetic kozmetic = new Kozmetic(nextRow[3], nextRow[4], nextRow[0], nextRow[1], nextRow[5], nextRow[6], nextRow[7], treatmentType, nextRow[9], Integer.parseInt(nextRow[10]), Double.parseDouble(nextRow[11]), Integer.parseInt(nextRow[12]));
					System.out.println(kozmetic.toString());
					allKozmetics.add(kozmetic);
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
	
	private List<String[]> LoadAllTreatments() throws IOException {
		List<String[]> treatments = new ArrayList<String[]>();
		BufferedReader reader = new BufferedReader(new FileReader("src\\Data\\KozmetickiTretmani.csv"));							//IZMENITI
		String line = "";
		
		//allTreatmentsCSV = new ArrayList<String[]>();
		while ((line = reader.readLine()) != null) {
			String[] treatmentsSplit = line.split(",");
			treatments.add(treatmentsSplit);
		}
		reader.close();
		
		return treatments;
	}
	
	public void PrintAllKozmetics() {
		for(Kozmetic entity : allKozmetics) {
			System.out.println(entity.toString());
		}
	}
}
