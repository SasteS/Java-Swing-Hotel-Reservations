package Etities;

public class Manager extends Employee {
	
	public Manager() {
		super();
	}
	
	public Manager(String name_, String surname_, String username_, String password_, String sex_, String phone_, String address_) {
	    super(name_, surname_, username_, password_, sex_, phone_, address_, Position.MANAGER);
	}
	
	@Override
	public String toString() {
		return "name: " + name + "; surname: " + surname + "; username: " + username + "; password: " + password + "; sex: " + sex + "; phone: " + phone + "; address: " + address + "; role: manager\n";
	}
}
