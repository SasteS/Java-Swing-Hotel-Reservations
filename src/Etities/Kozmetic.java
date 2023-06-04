package Etities;

public class Kozmetic extends Employee {
	private String[] treatmentType;
	private int bonus;
	
	public Kozmetic() {
		super();
		treatmentType = new String[] {};
	}
	
	public Kozmetic(String name_, String surname_, String username_, String password_, String sex_, String phone_, String address_, String[] treatmentType_, String sprema_, int staz_, Double pay_, int bonus_) {
	    super(name_, surname_, username_, password_, sex_, phone_, address_, Position.KOZMETIC, sprema_, staz_, pay_);
	    treatmentType = treatmentType_;
	    bonus = bonus_;
	  }

	public int GetBonus() {
		return bonus;
	}
	
	public void SetBonus(int bonus_) {
		bonus = bonus_;
	}
	
	public String GettreatmentTypeInStringFormat() {
		String typesString = "";
		for(String type : treatmentType)
			typesString += type + ";";
		typesString = typesString.substring(0, typesString.length()-1);
		return typesString;
	}
	
	public void SetTreatmentType(String treatmentType_) {
		treatmentType = treatmentType_.split(";");
	}
	
	@Override
	public String toString() {
		String typesString = "";
		for(String type : treatmentType)
			typesString += type + ", ";
		return "name: " + name + "; surname: " + surname + "; username: " + username + "; password: " + password + "; sex: " + sex  + "; phone: " + phone + "; address: " + address + "; treatmentType: { " + typesString + "}; sprema: " + sprema + "; staz: " + ((Integer) staz).toString() + "; pay: " + ((Double) pay).toString() + "; role: kozmetic\n";
	}
}
