package Hotel;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Users.Soba;

public class Sobe {
	protected List<Soba> arr = new ArrayList<Soba>();
	protected HashMap<String, ArrayList<String[]>> dates = new HashMap<String, ArrayList<String[]>>();
	protected BufferedReader reader;
	
	public Sobe() throws IOException {
		UcitajDatume();
		UcitajSobe();	
	}

	public List<Soba> getSobe() {
		return arr;		
	}
	
	public void UcitajSobe() throws IOException {
		//TABELA
	    String line = "";
		
		List<String[]> data = new ArrayList<String[]>();
		
	    reader = new BufferedReader(new FileReader("src\\Sobe.csv"));
		
		while((line = reader.readLine()) != null) {
			String[] red = line.split(",");
			
			data.add(red);
		}
		
		//TEST DA LI RADI
		int index = 0;
		for (String[] item : data) {
			Soba soba = new Soba();
			soba.set_broj(Integer.parseInt(item[0]));
			soba.set_tip(item[1]);			
			soba.set_broj_ljudi(Integer.parseInt(item[2]));
			soba.set_Stanje(item[3]);

			//RADI		
			ArrayList<String[]> temp = dates.get(item[0]);

			for (String[] niz : temp) {				
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); 
				LocalDate date1 = LocalDate.parse(niz[0], formatter);
				LocalDate date2 = LocalDate.parse(niz[1], formatter);
				
				LocalDate[] temp_holder = {date1, date2};
				
				soba.add_date(temp_holder);
			}

			//TEST ZA OVO IZNAD
			System.out.println(soba.get_broj());
			for (LocalDate[] s : soba.get_dates()) {
				for (LocalDate date : s)
					System.out.println(date);
				System.out.println();
			}					
			
			arr.add(soba);
			index++;
		}	
		//CENA
		UcitajCenu(arr);
	}
	
	private void UcitajDatume() throws IOException {
		String line = "";
		
		try {
			reader = new BufferedReader(new FileReader("src\\Sobe_Datumi.csv"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		//MORA HASH MAPA - RADI
		while((line = reader.readLine()) != null) {
			
			String[] red = line.split(",");
			ArrayList<String[]> temp_list = new ArrayList<String[]>();
			for (int i = 1; i < red.length; i++) {
				String[] temp = red[i].split(";");
				temp_list.add(temp);				
			}
			dates.put(red[0], temp_list);
		}		
	}
	
	public void UcitajCenu(List<Soba> arr) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("src\\Cenovnik.csv"));
		String line = "";
		List<String[]> niz = new ArrayList<String[]>();
		while((line = reader.readLine()) != null) {
			String[] temp = line.split(",");
			niz.add(temp);
		}
		reader.close();
		
		reader = new BufferedReader(new FileReader("src\\Sezone.csv"));
		line = "";
		List<String[]> sezone = new ArrayList<String[]>();
		while ((line = reader.readLine()) != null) {
			sezone.add(line.split(","));
		}
		reader.close();
				
		LocalDate sad = LocalDate.now();
		String sezona1 = "";
		String m = sad.getMonth().toString();
		for (String[] sezona : sezone) {
			String[] temp = sezona[1].split(";");						
			for (String s : temp) {
				if (s.equals(m)) {					
					sezona1 = sezona[0];					
					break;
				}
			}			
		}
		
		for (Soba s : arr) {
			for (String[] item : niz) {
				String[] temp = item[1].split(";");
				if (item[0].equals(s.get_tip().toString())) {		
					if (sezona1.equals("high"))
						s.set_cena(Integer.parseInt(temp[1]));
					else if (sezona1.equals("mid"))
						s.set_cena(Integer.parseInt(item[2]));
					else if (sezona1.equals("low"))
						s.set_cena(Integer.parseInt(item[0]));
				}
			}
		}
	}
}
