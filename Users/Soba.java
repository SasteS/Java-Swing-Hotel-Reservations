package Users;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Hotel.Stanje_Sobe;
import Hotel.Tip_Soba;

public class Soba {
	protected int broj;
	protected Tip_Soba tip;
	protected Stanje_Sobe stanje;
	protected int broj_ljudi;
	protected List<LocalDate[]> datumi;
	protected int cena;

	public Soba() throws IOException {
		broj = 0;
		tip = Tip_Soba.ONE;
		stanje = Stanje_Sobe.SPREMANJE;
		broj_ljudi = 0;
		datumi = new ArrayList<LocalDate[]>();
		cena = 0;
	}
	
	public Soba(int arg1, Tip_Soba arg2, Stanje_Sobe arg3, int arg4, ArrayList<LocalDate[]> arg5, int arg6) throws IOException {
		broj = arg1;
		tip = arg2;
		stanje = arg3;
		broj_ljudi = arg4;
		datumi = new ArrayList<LocalDate[]>();
		for (LocalDate[] datum : arg5) {
			datumi.add(datum);
		}
		cena = arg6;
	}
	
	public int get_broj() {
		return this.broj;
	}
	
	public void set_broj(int arg1) {
		this.broj = arg1;
	}
	
	public Tip_Soba get_tip() {
		return this.tip;
	}
	
	public void set_tip(String item) {
		this.tip = Tip_Soba.valueOf(item);
	}
	
	public Stanje_Sobe get_Stanje() {
		return this.stanje;
	}
	
	public void set_Stanje(String arg1) {
		this.stanje = Stanje_Sobe.valueOf(arg1);
	}
	
	public int get_broj_ljudi() {
		return this.broj_ljudi;
	}
	
	public void set_broj_ljudi(int arg1) {
		this.broj_ljudi = arg1;
	}
	
	public List<LocalDate[]> get_dates() {
		return this.datumi;
	}
	
	public void add_date(LocalDate[] arg1) {
		this.datumi.add(arg1);
	}
	
	public int get_cena() {
		return this.cena;
	}
	
	public void set_cena(int arg1) {
		this.cena = arg1;
	}	
}
