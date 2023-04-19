//package Testovi;
//
//import static org.junit.Assert.*;
//
//import java.io.FileWriter;
//import java.io.IOException;
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.junit.AfterClass;
//import org.junit.BeforeClass;
//import org.junit.Test;
//
//import Hotel.Rezervacije;
//import Hotel.Stenje_Rezervacija;
//import Hotel.Tip_Soba;
//
//public class RezervacijeTest {
//	public static List<Rezervacije> rezervacije = new ArrayList<Rezervacije>();
//	
//	@BeforeClass
//	public static void setUpBeforeClass() throws IOException {
//		System.out.println("Starting test...");
//		
//		Rezervacije rez1 = new Rezervacije();
//		rez1.set_id(1);
//		rez1.set_pocetak(LocalDate.parse("2022-02-12", DateTimeFormatter.ofPattern("yyyy-MM-dd")));
//		rez1.set_kraj(LocalDate.parse("2022-02-17", DateTimeFormatter.ofPattern("yyyy-MM-dd")));
//		rez1.set_username("asdawd@awdwd.com");
//		String dodatne1 = "dorucak;rucak";
//		rez1.set_cena(5000);
//		rezervacije.add(rez1);
//		
//		Rezervacije rez2 = new Rezervacije();
//		rez2.set_id(2);
//		rez2.set_pocetak(LocalDate.parse("2022-03-02", DateTimeFormatter.ofPattern("yyyy-MM-dd")));
//		rez2.set_kraj(LocalDate.parse("2022-04-17", DateTimeFormatter.ofPattern("yyyy-MM-dd")));
//		rez2.set_username("asdawd@adddsd.com");
//		String dodatne2 = "dorucak";
//		rez2.set_cena(3220);
//		rezervacije.add(rez2);
//		
//		Rezervacije rez3 = new Rezervacije();
//		rez3.set_id(3);
//		rez3.set_pocetak(LocalDate.parse("2022-10-10", DateTimeFormatter.ofPattern("yyyy-MM-dd")));
//		rez3.set_kraj(LocalDate.parse("2022-10-11", DateTimeFormatter.ofPattern("yyyy-MM-dd")));
//		rez3.set_username("asdawd@awdwd.com");
//		String dodatne3 = "rucak;vecera";
//		rez3.set_cena(5120);
//		rezervacije.add(rez3);
//		
//		FileWriter writer = new FileWriter("src//Rezervacije_Test.csv", true);
//		
//		int i = 0;
//		for (Rezervacije r : rezervacije) {
//			if (i == 0)
//				writer.write(r.get_pocetak().toString() + "," + r.get_kraj().toString() + "," + r.get_tip().toString() + "," + r.get_username() + "," + r.get_stanje().toString() + "," + r.get_cena() + "," + r.get_id()+ "," + dodatne1 + "n\n");
//			else if (i == 1)
//				writer.write(r.get_pocetak().toString() + "," + r.get_kraj().toString() + "," + r.get_tip().toString() + "," + r.get_username() + "," + r.get_stanje().toString() + "," + r.get_cena() + "," + r.get_id()+ "," + dodatne2 + "n\n");
//			else
//				writer.write(r.get_pocetak().toString() + "," + r.get_kraj().toString() + "," + r.get_tip().toString() + "," + r.get_username() + "," + r.get_stanje().toString() + "," + r.get_cena() + "," + r.get_id()+ "," + dodatne3 + "n\n");
//			i++;
//		}
//	}
//	
//	@AfterClass
//	public static void setUpAfterClass() {
//		System.out.println("Test ended!");
//	}
//	
//	@Test
//	public void test() {
//		Rezervacije rez1 = new Rezervacije();
//		rez1.set_id(1);
//		rez1.set_pocetak(LocalDate.parse("2022-02-12", DateTimeFormatter.ofPattern("yyyy-MM-dd")));
//		rez1.set_kraj(LocalDate.parse("2022-02-17", DateTimeFormatter.ofPattern("yyyy-MM-dd")));
//		rez1.set_username("asdawd@awdwd.com");
//		String dodatne1 = "dorucak;rucak";
//		rez1.set_cena(5000);
//		
//		Rezervacije rez2 = new Rezervacije();
//		rez2.set_id(2);
//		rez2.set_pocetak(LocalDate.parse("2022-03-02", DateTimeFormatter.ofPattern("yyyy-MM-dd")));
//		rez2.set_kraj(LocalDate.parse("2022-04-17", DateTimeFormatter.ofPattern("yyyy-MM-dd")));
//		rez2.set_username("asdawd@adddsd.com");
//		String dodatne2 = "dorucak";
//		rez2.set_cena(3220);
//		
//		Rezervacije rez3 = new Rezervacije();
//		rez3.set_id(3);
//		rez3.set_pocetak(LocalDate.parse("2022-10-10", DateTimeFormatter.ofPattern("yyyy-MM-dd")));
//		rez3.set_kraj(LocalDate.parse("2022-10-11", DateTimeFormatter.ofPattern("yyyy-MM-dd")));
//		rez3.set_username("asdawd@awdwd.com");
//		String dodatne3 = "rucak;vecera";
//		rez3.set_cena(5120);
//		
//		boolean all_ok = true;
//		
//		for (Rezervacije r : rezervacije) {			
//			System.out.println(rezervacije.indexOf(r));
//			System.out.println(r.get_id());
//			System.out.println(r.get_cena());
//			System.out.println(r.get_username());
//			System.out.println(r.get_kraj());
//			System.out.println(r.get_pocetak());
//			System.out.println(r.get_stanje());
//			System.out.println(r.get_tip());
//			System.out.println();
//		}
//		System.out.println();
//		
//		System.out.println(rez1.get_id());
//		System.out.println(rez1.get_cena());
//		System.out.println(rez1.get_username());
//		System.out.println(rez1.get_kraj());
//		System.out.println(rez1.get_pocetak());
//		System.out.println(rez1.get_stanje());
//		System.out.println(rez1.get_tip());
//		System.out.println();
//		
//		if (rez1.equals(rezervacije.get(0)) && rez2.equals(rezervacije.get(1)) && rez3.equals(rezervacije.get(2)))
//			all_ok = true;
//		else 
//			all_ok = false;
//		
//		System.out.println(all_ok);
//		assertTrue(all_ok);
//	}
//
//}
