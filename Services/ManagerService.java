package Services;

import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import Etities.Manager;
import Etities.Kozmetic;
import Etities.Recepcionist;

public class ManagerService {
	private List<Object> allEmployees;
	
	public ManagerService() {
		EmployeesFromCSV();
	}

	private void EmployeesFromCSV() {
		allEmployees = new ArrayList<Object>();
		
		BufferedReader reader = null;
		String line = "";
		
		try {
			reader = new BufferedReader(new FileReader("src\\Data\\Users.csv"));
			while((line = reader.readLine()) != null) {
				String[] nextRow = line.split(",");
				switch(nextRow[2]) {	//m - manager, k - kozmetic, r - receprionist, c - client
				case "m":
					Manager manager = new Manager(nextRow[3], nextRow[4], nextRow[0], nextRow[1], nextRow[5], nextRow[6], nextRow[7]);
					System.out.println(manager.toString());
					allEmployees.add(manager);
					break;
				case "k":
					String[] treatmentType = nextRow[8].split(";");
					Kozmetic kozmetic = new Kozmetic(nextRow[3], nextRow[4], nextRow[0], nextRow[1], nextRow[5], nextRow[6], nextRow[7], treatmentType);
					System.out.println(kozmetic.toString());
					allEmployees.add(kozmetic);
					break;
				case "r":
					Recepcionist recepcionist = new Recepcionist(nextRow[3], nextRow[4], nextRow[0], nextRow[1], nextRow[5], nextRow[6], nextRow[7]);
					System.out.println(recepcionist.toString());
					allEmployees.add(recepcionist);
					break;
				case "c":
					//Client client = new Client(nextRow[0], nextRow[1], nextRow[3], nextRow[4], nextRow[5], nextRow[6], nextRow[7]);
					//System.out.println(manager.toString());
					//allEmployees.add(manager);
					break;
				default:
					break;
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
}
