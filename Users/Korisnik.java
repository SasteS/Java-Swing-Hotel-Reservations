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
}
