package Services;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;

import Etities.Kozmetic;

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
			if (temp[4].equals(username)) {
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
		
//		JButton btn_oslobodi_sobu = new JButton("Oslobodi sobu");
//		panel.add(btn_oslobodi_sobu);
//		//user = this.username;
//		btn_oslobodi_sobu.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//            	int red = table.getSelectedRow();
//				if(red == -1) {
//					JOptionPane.showMessageDialog(null, "Morate odabrati red u tabeli.", "Greska", JOptionPane.WARNING_MESSAGE);
//				}
//				else {
//					String broj_sobe = table.getValueAt(red, 0).toString();
//					BufferedReader reader;
//					try {
//						reader = new BufferedReader(new FileReader("src\\Sobe.csv"));
//						String line = "";
//						int lines = 0;
//						List<String[]> arr = new ArrayList<String[]>();
//						
//						while ((line = reader.readLine()) != null) {
//							String[] temp = line.split(",");
//							if (temp[0].equals(broj_sobe)) {
//								temp[3] = "SLOBODNA";
//							}
//							arr.add(temp);
//						}
//						reader.close();
//						
//						FileWriter writer = new FileWriter("src\\Sobe.csv");						
//						writer.write("");						
//						writer.close();
//						
//						//PUNI NOVIM INFORMACIJAMA						
//						writer = new FileWriter("src\\Sobe.csv", true);
//						for (String[] temp : arr) {
//							String ispis = "";
//							for (String s : temp) {
//								ispis += s + ",";
//							}
//							ispis = ispis.substring(0, ispis.length() - 1);
//							System.out.println(ispis);
//							writer.write(ispis + "\n");
//						}
//						writer.close();
//						
//						//MENJAM SOBARICA_SOBE
//						reader = new BufferedReader(new FileReader("src\\Sobarice_Sobe.csv"));
//						line = "";
//						lines = 0;
//						List<String> sobarica_sobe = new ArrayList<String>();
//						
//						while ((line = reader.readLine()) != null) {
//							String[] temp = line.split(",");	
//							for (String s : temp) {
//								sobarica_sobe.add(s);
//							}
//							sobarica_sobe.add(" ");
//						}
//						reader.close();
//						
//						sobarica_sobe.remove(sobarica_sobe.indexOf(broj_sobe));
//						
//						writer = new FileWriter("src\\Sobarice_Sobe.csv");						
//						writer.write("");						
//						writer.close();
//						
//						//PUNI NOVIM INFORMACIJAMA						
//						writer = new FileWriter("src\\Sobarice_Sobe.csv", true);
//						String ispis = "";
//						for (String temp : sobarica_sobe) {														
//							System.out.println(temp);
//							if (temp.equals(" ")) {
//								ispis = ispis.substring(0, ispis.length() - 1);
//								ispis += "\n";
//							}
//							else {
//								ispis += temp + ",";
//							}
//						}
//						
//						//PUNI CVS SA DATUMOM KAD JE SOBA OCISCENA I IME SOBARICE						
//						writer = new FileWriter("src\\Sobarice_Datumi_Ciscenja.csv", true);						
//						//ispis = user + "," + LocalDate.now().toString() + "\n";
//						
//						System.out.println(ispis);
//						writer.write(ispis);
//						writer.close();
//						JOptionPane.showMessageDialog(null, "Soba očišćena.", "Info", JOptionPane.INFORMATION_MESSAGE);
//						frame.dispose();
//					} catch (FileNotFoundException e1) {
//						e1.printStackTrace();
//					} catch (IOException e1) {
//						e1.printStackTrace();
//					}
//				}
//			}
//		});
		
		frame.setVisible(true);	
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
			if (temp[4].equals(username)) {
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
					Kozmetic kozmetic = new Kozmetic(nextRow[3], nextRow[4], nextRow[0], nextRow[1], nextRow[5], nextRow[6], nextRow[7], treatmentType, nextRow[9], Integer.parseInt(nextRow[10]), Integer.parseInt(nextRow[11]));
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
	
	public void PrintAllKozmetics() {
		for(Kozmetic entity : allKozmetics) {
			System.out.println(entity.toString());
		}
	}
}
