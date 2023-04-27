package Etities;

public abstract class Employee {
	protected String name;
	protected String surname;
	protected String username;
	protected String password;
	protected String sex;
	protected String phone;
	protected String address;
	public enum Position { MANAGER, KOZMETIC, RECEPCIONIST, CLIENT };
	protected Position position;
	protected String sprema;
	protected int staz;
	protected int pay;
	
	public Employee() {
		name = "";
		surname = "";
		username = "";
		password = "";
		sex = "";
		phone = "";
		address = "";
		sprema = "";
		staz = 0;
		pay = 0;
	}
	
	public Employee(String name_, String surname_, String username_, String password_, String sex_, String phone_, String address_, Position position_, String sprema_, int staz_, int pay_) {
		name = name_;
		surname = surname_;
		username = username_;
		password = password_;
		sex = sex_;
		phone = phone_;
		address = address_;
		position = position_;
		sprema = sprema_;
		staz = staz_;
		pay = pay_;
	}
	
	public String GetName() {
		return name;
	}
	
	public void SetName(String arg) {
		name = arg;
	}
	
	public String GetSurame() {
		return surname;
	}
	
	public void SetSurame(String arg) {
		surname = arg;
	}
	
	public String GetUsername() {
		return username;
	}
	
	public void SetUsername(String arg) {
		username = arg;
	}
	
	public String GetPassword() {
		return password;
	}
	
	public void SetPassword(String arg) {
		password = arg;
	}
	
	public String GetSex() {
		return sex;
	}
	
	public void SetSex(String arg) {
		sex = arg;
	}
	
	public String GetPhone() {
		return phone;
	}
	
	public void SetPhone(String arg) {
		phone = arg;
	}
	
	public String GetAddress() {
		return address;
	}
	
	public void SetAddress(String arg) {
		address = arg;
	}
	
	public Position GetPosition() {
		return position;
	}
	
	public void SetPosition(Position position_) {
		position = position_;
	}
	
	public String GetSprema() {
		return sprema;
	}
	
	public void SetSprema(String arg) {
		sprema = arg;
	}
	
	public int GetStaz() {
		return staz;
	}
	
	public void SetStaz(int arg) {
		staz = arg;
	}
	
	public int GetPay() {
		return pay;
	}
	
	public void SetPay(int arg) {
		pay = arg;
	}
}
