package Etities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Tretman {
	String tipTretmana;
	boolean isZakazan;
	Stanje stanje;
	Double cena;
	
	public Tretman() {
		tipTretmana = "";
		isZakazan = true;
		stanje = Stanje.ZAKAZAN;
		cena = 0.0;
	}
	
	public Tretman(String tip) throws NumberFormatException, IOException {
		tipTretmana = tip;
		isZakazan = true;
		stanje = Stanje.ZAKAZAN;
		cena = LoadCena();
	}
	
	public Double LoadCena() throws NumberFormatException, IOException {
		Double cena = 0.0;
		
		BufferedReader reader = new BufferedReader(new FileReader(new File("src\\Data\\VrsteTretmana.csv")));
		String line;
		while ((line = reader.readLine()) != null) {
			String[] lineSplit = line.split(",");
			if (tipTretmana.equals(lineSplit[0]))
				cena = Double.parseDouble(lineSplit[1]);
		}
		return cena;
	}
	
	public String GetTip() {
		return tipTretmana;
	}
	
	public Double GetCena() {
		return cena;
	}
}
