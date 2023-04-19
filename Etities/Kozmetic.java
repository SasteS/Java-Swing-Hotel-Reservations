package Etities;

public class Kozmetic extends Employee {
	private String[] treatmentType;
	
	public Kozmetic() {
		super();
		treatmentType = new String[] {};
	}
	
	public Kozmetic(String name_, String surname_, String username_, String password_, String sex_, String phone_, String address_, String[] treatmentType_) {
	    super(name_, surname_, username_, password_, sex_, phone_, address_, Position.KOZMETIC);
	    treatmentType = treatmentType_;
	  }
	
	@Override
	public String toString() {
		String typesString = "";
		for(String type : treatmentType)
			typesString += type + ", ";
		return "name: " + name + "; surname: " + surname + "; username: " + username + "; password: " + password + "; sex: " + sex  + "; phone: " + phone + "; address: " + address + "; treatmentType: { " + typesString + "}; role: kozmetic\n";
	}
}
