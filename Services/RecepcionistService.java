package Services;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import Etities.Employee;
import Etities.Recepcionist;
import net.miginfocom.swing.MigLayout;

public class RecepcionistService {
	private List<Recepcionist> allRecepcionists;
	
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
	
	public RecepcionistService() {
		RecepcionistsFromCSV();
	}
	
	public void RegisterClient() {
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

		passLabel.setText("Password");
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
				try {
					user = userText.getText();
					char[] passCH = passText.getPassword();
					pass = new String(passCH);
					ime = imeText.getText();
					prezime = prezimeText.getText();
					pol = polText.getText();
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
			        
					if (found == false || found2 == false) {
						JFrame optFrame = new JFrame();
						JOptionPane.showMessageDialog(optFrame, "Wrong input!", "Input Error Message",JOptionPane.OK_OPTION);
					}
					else if (found == true && found2 == true) {
						if (pass.equals("") || user.equals("")) {
							JFrame optFrame = new JFrame();
							JOptionPane.showMessageDialog(optFrame, "Wrong input!", "Input Error Message",
									JOptionPane.OK_OPTION);
						} else {
							check = false;
							ManagerService managerService = new ManagerService();
							List<Object> allEntities = managerService.GetAllEntities();
	
							for (Object entity : allEntities) {
								if (user.equals(((Employee) entity).GetUsername()) && pass.equals(((Employee) entity).GetPassword())) {
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
									writer = new FileWriter("src\\Data\\Users.csv", true);
									String ispis = user + "," + pass + ",c," + ime + "," + prezime + "," + pol + "," + fon + "," + adresa + ",100000\n";
									writer.write(ispis);
									writer.close();
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
				catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "Popuniti sva polja!", "Information message", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		panel.add(btn);

		dialog.setVisible(true);
	}
	
	public void RegulateReservation() throws IOException {
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
			treatmentsCSV.add(temp);
			lines++;
		}
		reader.close();
		
		String[] collNames = { "Vreme", "Tip", "Klijent", "Kozmeticar" };
		String[][] data = new String[lines][4];

		int i = 0;
		for (String[] treatmet : treatmentsCSV) {
			data[i][0] = treatmet[3];
			data[i][1] = treatmet[2];
			data[i][2] = treatmet[1];
			data[i][3] = treatmet[4];
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
	
	public void RecepcionistsFromCSV(){
		allRecepcionists = new ArrayList<Recepcionist>();
		
		BufferedReader reader = null;
		String line = "";
		
		try {
			reader = new BufferedReader(new FileReader("src\\Data\\Users.csv"));
			while((line = reader.readLine()) != null) {
				String[] nextRow = line.split(",");
				
				if(nextRow[2].equals("r")) {
					Recepcionist recepcionist = new Recepcionist(nextRow[3], nextRow[4], nextRow[0], nextRow[1], nextRow[5], nextRow[6], nextRow[7], nextRow[8], Integer.parseInt(nextRow[9]), Integer.parseInt(nextRow[10]));
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
	
	public void PrintAllKozmetics() {
		for(Recepcionist entity : allRecepcionists) {
			System.out.println(entity.toString());
		}
	}
}
