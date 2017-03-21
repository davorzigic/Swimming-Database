package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.TreeSet;
import java.util.function.Predicate;

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.util.Callback;

public class ViewSwimmersScene extends Scene implements methodsTest {
	
	final ObservableList<Swimmer> data;
	TableView<Swimmer> swimmersTable;
	
	Connection conn;
	PreparedStatement pst;
	ResultSet rs = null;
	
	Button backBtn, editBtn, refreshBtn, deleteBtn;
	VBox vBoxForTable, vBoxForBackButton;
	Group viewSwimmersGroup, backButtonGroup;
	
	BorderPane root;
	
	TextField idSwimmer, firstName, lastName, registrationId, parentName, contactNumber, searchField;
	ComboBox<String> coach;
	DatePicker DOB, DOJ;
	Label labelTxt;
	
	
	public ViewSwimmersScene(BorderPane root, double width, double height) {
		super(root, width, height);
		// TODO Auto-generated constructor stub
		this.root = root;
		swimmersTable = new TableView<>();
		data = FXCollections.observableArrayList();
		
		CheckConnection();
		settingScene();

		
		
	}
	
	/***
	 * Method that sets the scene
	 */
	private void settingScene() {
		settingTable();
		settingBackButton();
		settingDeleteButton();
		settingRefreshButton();
		settingButtonsRightSide();
		settingForm();
		setFilter();
		
		
	}
	
	/***
	 * Method that sets the button that takes us back to the main screen
	 */
	private void settingBackButton() {
		
//		backBtn = new Button("Back to Menu");
//		backBtn.setPrefSize(100, 20);
//		backBtn.setOnAction(e -> {
//			
//		});
//		backBtn.setPadding(new Insets(5, 5, 5, 5));
		backButton backBtn = new backButton();
		
		
		labelTxt = new Label();
		labelTxt.setText("Plivački Klub Viktorija 1906");
		
		HBox upperOne = new HBox();
		upperOne.getChildren().addAll(labelTxt, backBtn);
		upperOne.setAlignment(Pos.BASELINE_CENTER);
		
		

		root.setTop(upperOne);
		BorderPane.setAlignment(backBtn, Pos.TOP_RIGHT);
		BorderPane.setMargin(backBtn, new Insets(10, 5, 10, 10));
	}
	
	
	@SuppressWarnings("unchecked")
	/***
	 * This method sets the table and puts it into Scene
	 */
	private void settingTable() {
		// Getting the ID from the database and inserting it into the first
		// column
		TableColumn<Swimmer, Number> column1 = new TableColumn<Swimmer, Number>("ID");
		column1.setMinWidth(20);
		column1.setCellValueFactory(new PropertyValueFactory<Swimmer, Number>("idSwimmer"));
		column1.setCellValueFactory(new Callback<CellDataFeatures<Swimmer, Number>, ObservableValue<Number>>() {
			public ObservableValue<Number> call(CellDataFeatures<Swimmer, Number> u) {
				// u.getValue() returns the Person instance for a particular
				// TableView row

				return u.getValue().getId();
			}
		});

		// Getting the first name from the database and inserting it into
		// the second column
		TableColumn<Swimmer, String> column2 = new TableColumn<Swimmer, String>("First name");
		column2.setMinWidth(100);
		column2.setCellValueFactory(new PropertyValueFactory<Swimmer, String>("firstName"));
		column2.setCellValueFactory(new Callback<CellDataFeatures<Swimmer, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<Swimmer, String> u) {
				// u.getValue() returns the Person instance for a particular
				// TableView row

				return u.getValue().getFirstName();
			}
		});

		// Getting the last name from the database and inserting it into the
		// third column
		TableColumn<Swimmer, String> column3 = new TableColumn<Swimmer, String>("Last Name");
		column3.setMinWidth(100);
		column3.setCellValueFactory(new PropertyValueFactory<Swimmer, String>("lastName"));
		column3.setCellValueFactory(new Callback<CellDataFeatures<Swimmer, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<Swimmer, String> u) {
				// u.getValue() returns the Person instance for a particular
				// TableView row

				return u.getValue().getLastName();
			}
		});

		// Getting the DOB from the database and inserting it into the
		// fourth column
		TableColumn<Swimmer, String> column4 = new TableColumn<Swimmer, String>("DOB");
		column4.setMinWidth(100);
		column4.setCellValueFactory(new PropertyValueFactory<Swimmer, String>("DOB"));
		column4.setCellValueFactory(new Callback<CellDataFeatures<Swimmer, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<Swimmer, String> u) {
				// u.getValue() returns the User instance for a particular
				// TableView row

				return u.getValue().getDOB();
			}
		});

		// Getting the DOB from the database and inserting it into the

		TableColumn<Swimmer, String> column5 = new TableColumn<Swimmer, String>("Registration number");
		column5.setMinWidth(100);
		column5.setCellValueFactory(new PropertyValueFactory<Swimmer, String>("registrationNumber"));
		column5.setCellValueFactory(new Callback<CellDataFeatures<Swimmer, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<Swimmer, String> u) {
				// u.getValue() returns the User instance for a particular
				// TableView row

				return u.getValue().getRegistrationNumber();
			}
		});

		// Getting the DOB from the database and inserting it into the

		TableColumn<Swimmer, String> column6 = new TableColumn<Swimmer, String>("DOJ");
		column6.setMinWidth(100);
		column6.setCellValueFactory(new PropertyValueFactory<Swimmer, String>("DOJ"));
		column6.setCellValueFactory(new Callback<CellDataFeatures<Swimmer, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<Swimmer, String> u) {
				// u.getValue() returns the User instance for a particular
				// TableView row

				return u.getValue().getDateJoined();
			}
		});

		// Getting the DOB from the database and inserting it into the

		TableColumn<Swimmer, String> column7 = new TableColumn<Swimmer, String>("Parent Name");
		column7.setMinWidth(100);
		column7.setCellValueFactory(new PropertyValueFactory<Swimmer, String>("parentName"));
		column7.setCellValueFactory(new Callback<CellDataFeatures<Swimmer, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<Swimmer, String> u) {
				// u.getValue() returns the User instance for a particular
				// TableView row

				return u.getValue().getParentName();
			}
		});

		// Getting the DOB from the database and inserting it into the

		TableColumn<Swimmer, String> column8 = new TableColumn<Swimmer, String>("Contact Number");
		column8.setMinWidth(100);
		column8.setCellValueFactory(new PropertyValueFactory<Swimmer, String>("contactNumber"));
		column8.setCellValueFactory(new Callback<CellDataFeatures<Swimmer, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<Swimmer, String> u) {
				// u.getValue() returns the User instance for a particular
				// TableView row

				return u.getValue().getContactNumber();
			}
		});

		TableColumn<Swimmer, String> column9 = new TableColumn<Swimmer, String>("Coach");
		column9.setMinWidth(100);
		column9.setCellValueFactory(new PropertyValueFactory<Swimmer, String>("coach"));
		column9.setCellValueFactory(new Callback<CellDataFeatures<Swimmer, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<Swimmer, String> u) {
				// u.getValue() returns the User instance for a particular
				// TableView row

				return u.getValue().getCoach();
			}
		});
		
		// Here was unchecked warning
		swimmersTable.getColumns().addAll(column1, column2, column3, column4, column5, column6, column7, column8,
				column9);
		swimmersTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		// Sorting button in the table
		swimmersTable.setTableMenuButtonVisible(true);
		
		vBoxForTable = new VBox(5);
		viewSwimmersGroup = new Group();
		vBoxForTable.getChildren().add(swimmersTable);
		vBoxForTable.setPadding(new Insets(5,0,0,0));
		viewSwimmersGroup.getChildren().addAll(vBoxForTable);
		BorderPane.setMargin(viewSwimmersGroup, new Insets(0, 0, 0, 0));
		
		root.setCenter(vBoxForTable);
		
		fillingTable();
		
	}
	
	/***
	 * This method fills the table with the data from database 
	 */
	private void fillingTable() {
		try {
			data.clear(); // clears the table
			String query = "select * from swimmers";

			pst = conn.prepareStatement(query);
			rs = pst.executeQuery();

			while (rs.next()) {
				data.add(new Swimmer(rs.getInt("idSwimmer"), rs.getString("firstName"),
						rs.getString("lastName"), rs.getString("DOB"), rs.getString("registrationId"),
						rs.getString("dateJoined"), rs.getString("parentName"), rs.getString("contactNumber"),
						rs.getString("coach")));
				swimmersTable.setItems(data);
			}
			pst.close();
			rs.close();
		} catch (Exception e2) {
			System.err.println(e2);
			e2.printStackTrace();

		}
		
		
	}
	
	
	/***
	 * Method that sets the button for deleting the data from the table
	 */
	private void settingDeleteButton() {
		// DELETE SELECTED BUTTON
		deleteBtn = new Button("Delete");
		deleteBtn.setPadding(new Insets(5, 5, 5, 5));
		deleteBtn.setPrefSize(100, 20);

		deleteBtn.setOnAction(e -> {
			Swimmer selectedItem = swimmersTable.getSelectionModel().getSelectedItem();
			
			if (selectedItem == null) {
					Alert noSelectionWarning = new Alert(AlertType.WARNING);
					noSelectionWarning.setTitle("No Selection");
					noSelectionWarning.setHeaderText("No Person Selected");
					noSelectionWarning.setContentText("Please select a person in the table.");

					noSelectionWarning.showAndWait();
			} else {
			
				Alert deleteConfirmation = new Alert(AlertType.CONFIRMATION);
				deleteConfirmation.setTitle("Confirmation Dialog");
				// alert.setHeaderText("Look, a Confirmation Dialog");
				deleteConfirmation.setContentText("Do you really want to delete selected swimmer?");

				Optional<ButtonType> result = deleteConfirmation.showAndWait();
				if (result.get() == ButtonType.OK) {
					// ... user chose OK

					try {
						String query = "DELETE FROM swimmers WHERE idSwimmer = ?";
						pst = conn.prepareStatement(query);
						pst.setInt(1, selectedItem.getIdInteger());
						pst.execute();
						pst.close();
					} catch (Exception e3) {
						// TODO: handle exception
						e3.printStackTrace();
					}
					swimmersTable.getItems().remove(selectedItem);
				} else {
					// ... user chose CANCEL or closed the dialog
					deleteConfirmation.close();
				}
			
			// Don't select anything after deleting
			swimmersTable.getSelectionModel().clearSelection();
			System.out.println();
			}
		});
		


	}
	
	
	/***
	 * This methods sets the Refresh button
	 */
	public void settingRefreshButton() {
		// REFRESH BUTTON
		refreshBtn = new Button("Refresh");
		refreshBtn.setPadding(new Insets(5, 5, 5, 5));
		refreshBtn.setPrefSize(100, 20);
		refreshBtn.setOnAction(e -> {
			try {
				data.clear(); // clears the table
				String query = "select * from swimmers";

				pst = conn.prepareStatement(query);
				rs = pst.executeQuery();

				while (rs.next()) {
					data.add(new Swimmer(rs.getInt("idSwimmer"), rs.getString("firstName"),
							rs.getString("lastName"), rs.getString("DOB"), rs.getString("registrationId"),
							rs.getString("dateJoined"), rs.getString("parentName"), rs.getString("contactNumber"),
							rs.getString("coach")));
					swimmersTable.setItems(data);
				}
				pst.close();
				rs.close();
			} catch (Exception e2) {
				System.err.println(e2);

			}
		});
		
		
	}
	
	/***
	 * This method sets the buttons right of the table
	 */
	public void settingButtonsRightSide() {
		
		VBox forButtons = new VBox(5);
		forButtons.getChildren().addAll(deleteBtn,refreshBtn);
		forButtons.setPadding(new Insets(5,0,0,0));
		root.setRight(forButtons);
		BorderPane.setMargin(forButtons, new Insets(25,5,5,5));
		
	}
	
	public void settingLeftPane() {
		
		
	}
	
	
	/***
	 * Method that sets inserting new data form
	 */
	public void settingForm() {
		VBox vBoxTextFields = new VBox(5);
		vBoxTextFields.setPadding(new Insets(10, 10, 10, 10));

		// Defining TextFields

		idSwimmer = new TextField();
		idSwimmer.setPrefSize(150, 20);
		idSwimmer.setFont(Font.font("SanSerif", 15));
		idSwimmer.setPromptText("ID");

		firstName = new TextField();
		firstName.setPrefSize(150, 20);
		firstName.setFont(Font.font("SanSerif", 15));
		firstName.setPromptText("First Name");

		lastName = new TextField();
		lastName.setPrefSize(150, 20);
		lastName.setFont(Font.font("SanSerif", 15));
		lastName.setPromptText("Last Name");

		registrationId = new TextField();
		registrationId.setPrefSize(150,20);
		registrationId.setFont(Font.font("SanSerif", 15));
		registrationId.setPromptText("Registration ID");

		parentName = new TextField();
		parentName.setPrefSize(150, 20);
		parentName.setFont(Font.font("SanSerif", 15));
		parentName.setPromptText("Parent Name");

		contactNumber = new TextField();
		contactNumber.setPrefSize(150, 20);
		contactNumber.setFont(Font.font("SanSerif", 15));
		contactNumber.setPromptText("Contact Number");

		ObservableList<String> coaches = FXCollections.observableArrayList("Davor", "Boba", "Aleksandar","Maja","Tijana","Branny");
		coach = new ComboBox<>(coaches);
		coach.setPrefSize(150, 20);
		coach.setPromptText("Coach");

		DOB = new DatePicker();
		DOB.setPromptText("Date of birth");
		DOB.setPrefSize(150, 20);
		DOB.setStyle("-fx-font-size:15");

		DOJ = new DatePicker(LocalDate.now());
		DOJ.setPromptText("Date of joining");
		DOJ.setPrefSize(150, 20);
		DOJ.setStyle("-fx-font-size:15");
		
		
		

		// SAVE BUTTON
		Button saveButton = new Button("Save");
		saveButton.setPrefSize(150, 15);
		saveButton.setFont(Font.font("SanSerif", 15));
		
		saveButton.setOnAction(e -> {
			try {
				
				if (idSwimmer.getText().isEmpty()) {
					System.out.println("You dont have ID");
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("Information dialog");
					alert.setHeaderText(null);
					alert.setContentText("Your ID field is empty.");
					alert.showAndWait();
				} else if(firstName.getText().isEmpty()) {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("Information dialog");
					alert.setHeaderText(null);
					alert.setContentText("Your First Name field is empty.");
					alert.showAndWait();
				} else if(lastName.getText().isEmpty()) {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("Information dialog");
					alert.setHeaderText(null);
					alert.setContentText("Your Last Name field is empty.");
					alert.showAndWait();
				} else if(parentName.getText().isEmpty()) {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("Information dialog");
					alert.setHeaderText(null);
					alert.setContentText("Your Parent Name field is empty.");
					alert.showAndWait();
				} else if(coach.getSelectionModel().isEmpty()) {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("Information dialog");
					alert.setHeaderText(null);
					alert.setContentText("You have to pick coach.");
					alert.showAndWait();
				} else {
					Alert saveConfirmation = new Alert(AlertType.CONFIRMATION);
					saveConfirmation.setTitle("Confirmation Dialog");
					// alert.setHeaderText("Look, a Confirmation Dialog");
					saveConfirmation.setContentText("Save swimmer?");
	
					Optional<ButtonType> result = saveConfirmation.showAndWait();
					if (result.get() == ButtonType.OK) {
					// ... user chose OK
						String query = "INSERT INTO swimmers (idSwimmer, firstName, lastName, DOB, registrationId, dateJoined, parentName, contactNumber, coach) VALUES(?,?,?,?,?,?,?,?,?)";
						pst = conn.prepareStatement(query);
						Integer value1A = Integer.parseInt(idSwimmer.getText());
						pst.setInt(1, value1A);
						// pst.setString(1, idSwimmer.getText());
						pst.setString(2, (firstName.getText().substring(0,1).toUpperCase() + firstName.getText().substring(1)));
						pst.setString(3, (lastName.getText().substring(0,1).toUpperCase() + lastName.getText().substring(1)));
						pst.setString(4, ((TextField) DOB.getEditor()).getText());
						pst.setString(5, registrationId.getText());
						pst.setString(6, ((TextField) DOJ.getEditor()).getText());
						pst.setString(7, (parentName.getText().substring(0,1).toUpperCase() + parentName.getText().substring(1)));
						pst.setString(8, contactNumber.getText());
						pst.setString(9, coach.getValue());
						pst.execute();
						pst.close();

						clearFields();

						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Information dialog");
						alert.setHeaderText(null);
						alert.setContentText("User has been created!");
						alert.showAndWait();
						
						try {
							data.clear(); // clears the table
							String queryRefresh = "select * from swimmers";

							pst = conn.prepareStatement(queryRefresh);
							rs = pst.executeQuery();

							while (rs.next()) {
								data.add(new Swimmer(rs.getInt("idSwimmer"), rs.getString("firstName"),
										rs.getString("lastName"), rs.getString("DOB"), rs.getString("registrationId"),
										rs.getString("dateJoined"), rs.getString("parentName"), rs.getString("contactNumber"),
										rs.getString("coach")));
								swimmersTable.setItems(data);
							}
							pst.close();
							rs.close();
						} catch (Exception e2) {
							System.err.println(e2);

						}
					} else {
						saveConfirmation.close();
					}
					
				}

			} catch (Exception e1) {
				System.err.println(e1);
				e1.printStackTrace();
			}
		});
		
		VBox firstRow = new VBox(5);
		firstRow.getChildren().addAll(idSwimmer,firstName,lastName, registrationId, DOB,DOJ, parentName, contactNumber, coach, saveButton);
		firstRow.setPadding(new Insets(5,10,10,10));
		
		VBox secondRow = new VBox(5);
		secondRow.getChildren().add(saveButton);
		secondRow.setPadding(new Insets(0,10,10,10));
		
		VBox.setMargin(saveButton, new Insets(370,0,0,0));
		
		Group group = new Group();
		group.getChildren().addAll(secondRow,firstRow);
		
		root.setLeft(group);
		
	}
	
		
	
	/***
	 * Method that sets filter text field
	 */
	public void setFilter() {
		
		
		searchField = new TextField();
		searchField.setFont(Font.font("San Serif", 15));
		searchField.setPromptText("Filter data");
		searchField.setMaxWidth(920);
		
		FilteredList<Swimmer> filteredData = new FilteredList<>(data, e-> true);
		searchField.setOnKeyReleased(e -> {
			searchField.textProperty().addListener((observableValue, oldValue, newValue) -> {
				filteredData.setPredicate((Predicate<? super Swimmer>) swimmer -> {
					if(newValue == null || newValue.isEmpty()) {
						return true;
					}
					String lowerCasеFilter = newValue.toLowerCase();
					if(swimmer.getFirstNameString().toLowerCase().contains(lowerCasеFilter)) {
						return true;
					}else if(swimmer.getLastNameString().toLowerCase().contains(lowerCasеFilter)) {
						return true;
					}else if(swimmer.getParentNameString().toLowerCase().contains(lowerCasеFilter)) {
						return true;
					}else if(swimmer.getContactNumberString().toLowerCase().contains(lowerCasеFilter)) {
						return true;
					}else if(swimmer.getRegistrationNumberString().toLowerCase().contains(lowerCasеFilter)) {
						return true;
					}else if(swimmer.getCoachString().toLowerCase().contains(lowerCasеFilter)) {
						return true;
					}
					
					return false;
				});
			});
			SortedList<Swimmer> sortedData = new SortedList<>(filteredData);
			sortedData.comparatorProperty().bind(swimmersTable.comparatorProperty());
			swimmersTable.setItems(sortedData);
		});
			
		
		root.setBottom(searchField);
		BorderPane.setAlignment(searchField, Pos.BASELINE_CENTER);
		BorderPane.setMargin(searchField, new Insets(0,0,0,55));


	}
	
	/***
	 * This method clears the fields after pressing the Save button
	 */
	public void clearFields() {
		idSwimmer.clear();
		firstName.clear();
		lastName.clear();
		DOB.setValue(null);
		registrationId.clear();
		DOJ.setValue(null);
		parentName.clear();
		contactNumber.clear();
		coach.setValue(null);
	}
	
	
	
	/***
	 * This method checks do we have the connection with the database
	 */
	public void CheckConnection() {
		conn = SQLConnection.DbConnector();
		if (conn == null) {
			System.out.println("Connection Not Successful");
			System.exit(1);
		} else {
//			System.out.println("Connection Successful");
		}
	}
}
