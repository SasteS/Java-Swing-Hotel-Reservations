package Users;

import java.time.LocalDate;

public class Korisnik {
	protected String ime;
	protected String prezime;
	protected String pol;
	protected LocalDate datumRodjenja;
	protected String telefon;
	protected String adresa;
	protected String username;
	protected String password;
	
	public Korisnik() {
		this.ime = "";
		this.prezime = "";
		this.pol = "";
		this.datumRodjenja = LocalDate.now();
		this.telefon = "123123123";
		this.adresa = "";
		this.username = "";
		this.password = "";
	}
	
	public Korisnik(String arg1, String arg2, String arg3, LocalDate arg4, String arg5, String arg6, String arg7, String arg8) {
		this.ime = arg1;
		this.prezime = arg2;
		this.pol = arg3;
		this.datumRodjenja = arg4;
		this.telefon = arg5;
		this.adresa = arg6;
		this.username = arg7;
		this.password = arg8;
	}
	
	public void set_ime(String arg1) {
		this.ime = arg1;
	}
	
	public String get_ime() {
		return this.ime;
	}
	
	public void set_prezime(String arg1) {
		this.prezime = arg1;
	}
	
	public String get_prezime() {
		return this.prezime;
	}
	
	public void set_pol(String arg1) {
		this.pol = arg1;
	}
	
	public String get_pol() {
		return this.pol;
	}
	
	public void set_tel(String arg1) {
		this.telefon = arg1;
	}
	
	public String get_tel() {
		return this.telefon;
	}
	
	public void set_adresa(String arg1) {
		this.adresa = arg1;
	}
	
	public String get_adresa() {
		return this.adresa;
	}

	public void set_user(String arg1) {
		this.username = arg1;
	}
	
	public String get_username() {
		return this.username;
	}
	
	public void set_pass(String arg1) {
		this.password = arg1;
	}
	
	public String get_pass() {
		return this.password;
	}
	
	public void set_date(LocalDate arg1) {
		this.datumRodjenja = arg1;
	}
	
	public LocalDate get_date() {
		return this.datumRodjenja;
	}
}
