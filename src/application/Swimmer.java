package application;

import javafx.beans.property.SimpleStringProperty;

public class Swimmer {
	
	private final SimpleStringProperty id;
	private final SimpleStringProperty firstName;
	private final SimpleStringProperty lastName;
	private final SimpleStringProperty DOB;
	private final SimpleStringProperty registrationNumber;
	private final SimpleStringProperty dateJoined;
	private final SimpleStringProperty parentName;
	private final SimpleStringProperty contactNumber;
	private final SimpleStringProperty coach;
	
	
	public Swimmer(String id,String fName, String lName, String DOB, String registrationNumber, String dateJoined, String parentName, String contactNumber, String coach) {
		this.id = new SimpleStringProperty(id);
		this.firstName = new SimpleStringProperty(fName);
		this.lastName = new SimpleStringProperty(lName);
		this.DOB = new SimpleStringProperty(DOB);
		this.registrationNumber = new SimpleStringProperty(registrationNumber);
		this.dateJoined = new SimpleStringProperty(dateJoined);
		this.parentName = new SimpleStringProperty(parentName);
		this.contactNumber = new SimpleStringProperty(contactNumber);
		this.coach = new SimpleStringProperty(coach);
	}


	public SimpleStringProperty getId() {
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



}
