package Hotel;

import java.time.LocalDate;

public class Rezervacije {
	protected int id;
	protected LocalDate pocetak;
	protected LocalDate kraj;
	protected Tip_Soba tip;
	protected String username;
	protected Stenje_Rezervacija stanje;
	protected int cena;	
	
	public Rezervacije() {
		this.id = (int)(Math.random() * 100);
		this.pocetak = LocalDate.now();
		this.kraj = LocalDate.now().plusDays(5);
		this.tip = Tip_Soba.ONE;
		this.username = "";
		this.stanje = Stenje_Rezervacija.NA_CEKANJU;
		this.cena = 2000;
	}
	
	public int get_id() {
		return this.id;
	}
	
	public LocalDate get_pocetak() {
		return this.pocetak;
	}
	
	public void set_pocetak(LocalDate arg1) {
		this.pocetak = arg1;
	}
	
	public LocalDate get_kraj() {
		return this.kraj;
	}
	
	public void set_kraj(LocalDate arg1) {
		this.kraj= arg1;
	}
	
	public Tip_Soba get_tip() {
		return this.tip;
	}
	
	public void set_tip(Tip_Soba arg1) {
		this.tip = arg1;
	}
	
	public String get_username() {
		return this.username;
	}
	
	public void set_username(String arg1) {
		this.username = arg1;
	}
	
	public Stenje_Rezervacija get_stanje() {
		return this.stanje;
	}
	
	public void set_stanje(Stenje_Rezervacija arg1) {
		this.stanje = arg1;
	}
	
	public int get_cena() {
		return this.cena;
	}

	public void set_cena(int arg1) {
		this.cena = arg1;
	}
}
