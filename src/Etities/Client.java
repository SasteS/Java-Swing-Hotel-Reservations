package Etities;

public class Client extends Employee {
	String card;
	
	public Client() {
		super();
		card = "false";
	}
	
	public Client(String name_, String surname_, String username_, String password_, String sex_, String phone_, String address_, Double pay_, String card_) {
	    super(name_, surname_, username_, password_, sex_, phone_, address_, Position.CLIENT, "", 0, pay_);
	    card = card_;
	}
	
	public String GetCard() {
		return card;
	}
	
	public void SetCard(String card_) {
		card = card_;
	}
	
	@Override
	public String toString() {
		return "name: " + name + "; surname: " + surname + "; username: " + username + "; password: " + password + "; sex: " + sex + "; phone: " + phone + "; address: " + address + "; pay: " + ((Double) pay).toString() +  "; role: client\n";
	}
	
}
