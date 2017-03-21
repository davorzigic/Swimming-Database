package application;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Swimmer {
	

	private final SimpleIntegerProperty id;
	private final SimpleStringProperty firstName;
	private final SimpleStringProperty lastName;
	private final SimpleStringProperty DOB;
	private final SimpleStringProperty registrationNumber;
	private final SimpleStringProperty dateJoined;
	private final SimpleStringProperty parentName;
	private final SimpleStringProperty contactNumber;
	private final SimpleStringProperty coach;
	
	
	public Swimmer(int id,String fName, String lName, String DOB, String registrationNumber, String dateJoined, String parentName, String contactNumber, String coach) {
		this.id = new SimpleIntegerProperty(id);
		this.firstName = new SimpleStringProperty(fName);
		this.lastName = new SimpleStringProperty(lName);
		this.DOB = new SimpleStringProperty(DOB);
		this.registrationNumber = new SimpleStringProperty(registrationNumber);
		this.dateJoined = new SimpleStringProperty(dateJoined);
		this.parentName = new SimpleStringProperty(parentName);
		this.contactNumber = new SimpleStringProperty(contactNumber);
		this.coach = new SimpleStringProperty(coach);
	}



	public SimpleIntegerProperty getId() {
		return id;
	}

		
	public SimpleStringProperty getFirstName() {
		return firstName;
	}


	public SimpleStringProperty getLastName() {
		return lastName;
	}


	public SimpleStringProperty getDOB() {
		return DOB;
	}


	public SimpleStringProperty getRegistrationNumber() {
		return registrationNumber;
	}


	public SimpleStringProperty getDateJoined() {
		return dateJoined;
	}


	public SimpleStringProperty getParentName() {
		return parentName;
	}


	public SimpleStringProperty getContactNumber() {
		return contactNumber;
	}


	public SimpleStringProperty getCoach() {
		return coach;
	}
	
	public int getIdInteger() {
		return id.get();
	}
	
	public String getFirstNameString() { 
		return firstName.get(); 
	}
	
	public String getLastNameString() {
		return lastName.get();
	}
	
	public String getDOBSstring() {
		return DOB.get();
	}
	
	public String getRegistrationNumberString() {
		return registrationNumber.get();
	}
	
	public String getDOJString() {
		return dateJoined.get();
	}
	
	public String getParentNameString() {
		return parentName.get();
	}
	
	public String getContactNumberString() {
		return contactNumber.get();
	}
	
	public String getCoachString() {
		return coach.get();
	}
	

}
