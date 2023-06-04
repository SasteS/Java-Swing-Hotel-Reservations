package Etities;

import java.util.HashMap;

public class Cenovnik {
	public HashMap<String, Integer> treatmentNamePrice;
	
	public Cenovnik() {
		treatmentNamePrice = new HashMap<String, Integer>();
	}
	
	public Cenovnik(HashMap<String, Integer> treatmentNamePrice_) {
		treatmentNamePrice = treatmentNamePrice_;
	}
	
	@Override
	public String toString() {
		String returnString = "";
		for(String key : treatmentNamePrice.keySet()) {
			returnString += "treatment: " + key + "; price: " + treatmentNamePrice.get(key).toString() + "\n";
		}
		
		return returnString;
	}
}
