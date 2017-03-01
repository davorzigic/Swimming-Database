package application;

import javafx.beans.property.SimpleStringProperty;

public class Coach {
	
	private final SimpleStringProperty id;
	private final SimpleStringProperty firstName;
	private final SimpleStringProperty lastName;
	private final SimpleStringProperty email;
	private final SimpleStringProperty phone;
	private final SimpleStringProperty username;
	private final SimpleStringProperty password;
	private final SimpleStringProperty licenceNumber;
	
	
	public Coach(String id,String fName, String lName, String email, String phone, String username, String password, String licenceNumber) {
		this.id = new SimpleStringProperty(id);
		this.firstName = new SimpleStringProperty(fName);
		this.lastName = new SimpleStringProperty(lName);
		this.email = new SimpleStringProperty(email);
		this.phone = new SimpleStringProperty(phone);
		this.username = new SimpleStringProperty(username);
		this.password = new SimpleStringProperty(password);
		this.licenceNumber = new SimpleStringProperty(licenceNumber);
	}


	public SimpleStringProperty getUsername() {
		return username;
	}

	public SimpleStringProperty getId() {
		return id;
	}

	public SimpleStringProperty getPassword() {
		return password;
	}

	public SimpleStringProperty getEmail() {
		return email;
	}


	public SimpleStringProperty getFirstName() {
		return firstName;
	}


	public SimpleStringProperty getLastName() {
		return lastName;
	}


	public SimpleStringProperty getLincenceNumber() {
		return licenceNumber;
	}


	public SimpleStringProperty getPhone() {
		return phone;
	}


}
