package Services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import Etities.Cenovnik;

public class PriceService {
	public Cenovnik cenovnik;
	
	public PriceService() throws IOException {
		cenovnik = new Cenovnik(LoadPrices());
		System.out.println(cenovnik.toString());
	}
	
	public HashMap<String, Integer> LoadPrices() throws IOException {
		HashMap<String, Integer> loadedPrices = new HashMap<String, Integer>();
		
		BufferedReader reader = new BufferedReader(new FileReader(new File("src\\Data\\VrsteTretmana.csv")));
		String line;
		while ((line = reader.readLine()) != null) {
			String[] lineSplit = line.split(",");
			loadedPrices.put(lineSplit[0], Integer.parseInt(lineSplit[1]));
		}
		reader.close();
		
		return loadedPrices;
	}
}
