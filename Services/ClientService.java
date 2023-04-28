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
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
import net.miginfocom.swing.MigLayout;

public class ClientService {
	private List<Client> allClients;
	List<String[]> treatmentsCSV;
	
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
	
	public ClientService() {
		ClientsFromCSV();
	}
	
	public void MakeReservation(String username_) {
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
		String[] optionsToChooseTretman = { "tip1", "tip2", "tip3" };
		final JComboBox<String> jComboBoxTretman = new JComboBox<>(optionsToChooseTretman);
		jComboBoxTretman.setBounds(80, 50, 140, 20);
        panel.add(jComboBoxTretman, "wrap, left");
		
        
        int kozmeticCount = 0;
        try {
			BufferedReader reader = new BufferedReader(new FileReader("src\\Data\\Users.csv"));
			String line = "";
			while((line = reader.readLine()) != null) {
				String[] nextRow = line.split(",");
				
				if(nextRow[2].equals("k")) {
					kozmeticCount++;
				}
			}
		} catch(Exception e) {
			System.out.println("error reading the files.");
		}
        
        String[] optionsToChooseKozmetic = new String[kozmeticCount + 1];
        kozmeticCount = 0;
        try {
			BufferedReader reader = new BufferedReader(new FileReader("src\\Data\\Users.csv"));
			String line = "";
			while((line = reader.readLine()) != null) {
				String[] nextRow = line.split(",");
				
				if(nextRow[2].equals("k")) {
					optionsToChooseKozmetic[kozmeticCount] = nextRow[0];
					kozmeticCount++;
				}
			}
		} catch(Exception e) {
			System.out.println("error reading the files.");
		}
        optionsToChooseKozmetic[kozmeticCount] = "auto";
        		
		final JComboBox<String> jComboBoxKozmetic = new JComboBox<>(optionsToChooseKozmetic);
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
					String ispis = id.toString() + "," + clientUsername + "," + tipTretmana + ","+ datumVreme + "," + kozmeticar + ",zakazan\n";
					
					writer.write(ispis);
					writer.close();
					
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
		
		String[] collNames = { "Vreme", "Tip", "Kozmeticar", "Status" };
		String[][] data = new String[lines][4];

		int i = 0;
		for (String[] treatmet : treatmentsCSV) {
			data[i][0] = treatmet[3];
			data[i][1] = treatmet[2];
			data[i][2] = treatmet[4];
			data[i][3] = treatmet[5];
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
		
		frame.setVisible(true);
	}
	
	public void ClientsFromCSV(){
		allClients = new ArrayList<Client>();
		
		BufferedReader reader = null;
		String line = "";
		
		try {
			reader = new BufferedReader(new FileReader("src\\Data\\Users.csv"));
			while((line = reader.readLine()) != null) {
				String[] nextRow = line.split(",");
				
				if(nextRow[2].equals("c")) {
					Client client = new Client(nextRow[3], nextRow[4], nextRow[0], nextRow[1], nextRow[5], nextRow[6], nextRow[7], 100000);
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
	
	public void PrintAllRecepcionists() {
		for(Client entity : allClients) {
			System.out.println(entity.toString());
		}
	}
}
