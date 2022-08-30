package Users;

public class Zaposleni extends Korisnik {
	protected String sprema;
	protected int staz;
	protected int plata;
	
	public String get_sprema() {
		return this.sprema;
	}
	
	public void set_sprema(String arg1) {
		this.sprema = arg1;
	}

	public int get_staz() {
		return this.staz;
	}
	
	public void set_staz(int arg1) {
		this.staz = arg1;
	}

	public int get_plata() {
		return this.plata;
	}
	
	public void set_plata(int arg1) {
		this.plata = arg1;
	}
}