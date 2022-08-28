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
import java.util.ArrayList;
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

public class Sobarica extends Zaposleni {
		protected TableRowSorter<AbstractTableModel> tableSorter = new TableRowSorter<AbstractTableModel>();		
		protected JFrame tabela_soba;
		protected JPanel tabela_soba_panel;
		
	public void Sobe_Za_Ciscenje(final String username) throws IOException {
		this.username = username;
		
		tabela_soba = new JFrame("pregled_soba");
		tabela_soba_panel = new JPanel();
		tabela_soba.setSize(650, 500);

		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		final int x = (int) ((dimension.getWidth() - tabela_soba.getWidth()) / 2);
		final int y = (int) ((dimension.getHeight() - tabela_soba.getHeight()) / 2);
		tabela_soba.setLocation(x, y);

		// CLOSING EVENT
		tabela_soba.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		tabela_soba.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				int opt = JOptionPane.showConfirmDialog(null, "Do you want to close the window?", "close",
						JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
				if (opt == JOptionPane.YES_OPTION) {
					e.getWindow().dispose();
				}
			}
		});

		tabela_soba.add(tabela_soba_panel);
		
		BufferedReader reader = new BufferedReader(new FileReader("src\\Sobarice_Sobe.csv"));
		String line = "";
		
		List<String> sobarica_sobe = new ArrayList<String>();
		
		while ((line = reader.readLine()) != null) {
			String[] temp = line.split(",");
			if (temp[0].equals(this.username)) {
				for (String s : temp) {
					sobarica_sobe.add(s);
				}
				break;
			}
		}			
		reader.close();
		
		reader = new BufferedReader(new FileReader("src\\Sobe.csv"));
		line = "";
		int lines = 0;
		List<String[]> arr = new ArrayList<String[]>();
		
		while ((line = reader.readLine()) != null) {
			String[] temp = line.split(",");
			for (String s : sobarica_sobe) {
				if (temp[0].equals(s)) {
					arr.add(temp);
					lines++;
				}
			}
		}			
		reader.close();		
		
		String[] collNames = { "Broj sobe", "Tip", "Stanje" };
		Object[][] data = new Object[lines][3];

		int i = 0;
		for (String[] s : arr) {
			if (s[3].equals("SPREMANJE")) {
			data[i][0] = s[0];
			data[i][1] = s[1];
			data[i][2] = s[3];
			i++;
			}
		}
		
		final JTable table = new JTable(data, collNames);
		table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getTableHeader().setReorderingAllowed(false);
		table.setPreferredScrollableViewportSize(new Dimension(500, 150));
		table.setFillsViewportHeight(true);

		tableSorter.setModel((AbstractTableModel) table.getModel());
		table.setRowSorter(tableSorter);

		JScrollPane scrollPane = new JScrollPane(table);
		tabela_soba_panel.add(scrollPane);
		
		JButton btn_oslobodi_sobu = new JButton("Oslobodi sobu");
		tabela_soba_panel.add(btn_oslobodi_sobu);
		btn_oslobodi_sobu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {            
            	int red = table.getSelectedRow();
				if(red == -1) {
					JOptionPane.showMessageDialog(null, "Morate odabrati red u tabeli.", "Greska", JOptionPane.WARNING_MESSAGE);
				}
				else {
					String broj_sobe = table.getValueAt(red, 0).toString();
					BufferedReader reader;
					try {
						reader = new BufferedReader(new FileReader("src\\Sobe.csv"));
						String line = "";
						int lines = 0;
						List<String[]> arr = new ArrayList<String[]>();
						
						while ((line = reader.readLine()) != null) {
							String[] temp = line.split(",");
							if (temp[0].equals(broj_sobe)) {
								temp[3] = "SLOBODNA";
							}
							arr.add(temp);
						}
						reader.close();
						
						FileWriter writer = new FileWriter("src\\Sobe.csv");						
						writer.write("");						
						writer.close();
						
						//PUNI NOVIM INFORMACIJAMA						
						writer = new FileWriter("src\\Sobe.csv", true);
						for (String[] temp : arr) {
							String ispis = "";
							for (String s : temp) {
								ispis += s + ",";
							}
							ispis = ispis.substring(0, ispis.length() - 1);
							System.out.println(ispis);
							writer.write(ispis + "\n");
						}
						writer.close();
						
						//MENJAM SOBARICA_SOBE
						reader = new BufferedReader(new FileReader("src\\Sobarice_Sobe.csv"));
						line = "";
						lines = 0;
						List<String> sobarica_sobe = new ArrayList<String>();
						
						while ((line = reader.readLine()) != null) {
							String[] temp = line.split(",");	
							for (String s : temp) {
								sobarica_sobe.add(s);
							}
							sobarica_sobe.add(" ");
						}
						reader.close();
						
						sobarica_sobe.remove(sobarica_sobe.indexOf(broj_sobe));
						
						writer = new FileWriter("src\\Sobarice_Sobe.csv");						
						writer.write("");						
						writer.close();
						
						//PUNI NOVIM INFORMACIJAMA						
						writer = new FileWriter("src\\Sobarice_Sobe.csv", true);
						String ispis = "";
						for (String temp : sobarica_sobe) {														
							System.out.println(temp);
							if (temp.equals(" ")) {
								ispis = ispis.substring(0, ispis.length() - 1);
								ispis += "\n";
							}
							else {
								ispis += temp + ",";	
							}
						}
						
						System.out.println(ispis);
						writer.write(ispis);
						writer.close();
						JOptionPane.showMessageDialog(null, "Morate odabrati red u tabeli.", "Info", JOptionPane.INFORMATION_MESSAGE);
						tabela_soba.dispose();
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		
		tabela_soba.setVisible(true);
	}
}
