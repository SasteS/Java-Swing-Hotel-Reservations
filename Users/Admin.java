package Users;

import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class Admin extends Korisnik {

	
	//metode
	public void DodajZaposlenog() {//registruje novog zaposlenog
		
	}
	
	public void PrikazSvihEndtiteta(JPanel panel) throws IOException {
		BufferedReader reader = null;
		String line = "";
		
		try {
			reader = new BufferedReader(new FileReader("src\\Users.csv"));
			
			int lines = 0;
			while((line = reader.readLine()) != null) {
				lines++;
			}
			reader.close();
			//reader.mark(0);
			//reader.reset();
			reader = new BufferedReader(new FileReader("src\\Users.csv"));
			
			Object[][] data = new Object[lines][2];
			//Collection<String[]> coll = new ArrayList<String[]>();
			int i = 0;
			while((line = reader.readLine()) != null) {
				String[] red = line.split(",");
				
				data[i][0] = red[0];
				if (red[2].equals("a")) {
					data[i][1] = "admin";	
				}
				else if (red[2].equals("r")) {
					data[i][1] = "recepcioner";
				}
				else if (red[2].equals("s")) {
					data[i][1] = "sobarica";
				}
				else if (red[2].equals("g")) {
					data[i][1] = "gost";
				}				
				i++;
				//coll.add(red);
			}
			reader.close();
			System.out.println(i);
			
			//TEST U KONZOLI
			//for(int index = 0; index < lines; index++) {
			//	for(int jodex = 0; jodex < 2; jodex++) {
			//		System.out.println(data[index][jodex]);
			//	}
			//}
			
			String[] collNames = {"Korisničko ime", "Pozicija"};
			
			JTable table = new JTable(data, collNames);
			table.setPreferredScrollableViewportSize(new Dimension(500, 50));
			table.setFillsViewportHeight(true);
			
			JScrollPane scrollPane = new JScrollPane(table);
			panel.add(scrollPane);
			
		}
		finally {
			try {
				reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
