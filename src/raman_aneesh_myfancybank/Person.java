package raman_aneesh_myfancybank;

public class Person {
	private String name;

	public Person(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	} 
	
	public String toString() {
		return name.toString();
	}
}
