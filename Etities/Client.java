package Etities;

public class Client extends Employee {

	public Client() {
		super();
	}
	
	public Client(String name_, String surname_, String username_, String password_, String sex_, String phone_, String address_, int pay_) {
	    super(name_, surname_, username_, password_, sex_, phone_, address_, Position.CLIENT, "", 0, pay_);
	}
	
	@Override
	public String toString() {
		return "name: " + name + "; surname: " + surname + "; username: " + username + "; password: " + password + "; sex: " + sex + "; phone: " + phone + "; address: " + address + "; pay: " + ((Integer) pay).toString() +  "; role: client\n";
	}
	
}
