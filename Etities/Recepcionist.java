package Etities;

public class Recepcionist extends Employee {
	
	public Recepcionist() {
		super();
	}
	
	public Recepcionist(String name_, String surname_, String username_, String password_, String sex_, String phone_, String address_, String sprema_, int staz_, int pay_) {
	    super(name_, surname_, username_, password_, sex_, phone_, address_, Position.RECEPCIONIST, sprema_, staz_, pay_);
	  }
	
	@Override
	public String toString() {
		return "name: " + name + "; surname: " + surname + "; username: " + username + "; password: " + password + "; sex: " + sex  + "; phone: " + phone + "; address: " + address + "; sprema: " + sprema + "; staz: " + ((Integer) staz).toString() + "; pay: " + ((Integer) pay).toString() +  "; role: recepcionist\n";
	}
}
