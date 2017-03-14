package application;

import java.awt.Checkbox;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.Optional;

import javax.swing.GroupLayout.Alignment;

import com.sun.prism.shader.DrawCircle_LinearGradient_REFLECT_AlphaTest_Loader;

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.util.StringConverter;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

public class Main extends Application {

	Connection conn;
	PreparedStatement pst = null;
	ResultSet rs = null;
	Stage thestage;
	Button newCoach, newSwimmer, viewCoaches, viewSwimmer, back, close, delete, refresh;
	BorderPane firstBorderPane, secondBorderPane, thirdBorderPane, fourthBorderPane;
	Scene mainScene, viewSwimmerScene, addSwimmerScene;
	TextField idSwimmer, firstName, lastName, registrationId, parentName, contactNumber;
	ComboBox<String> coach;
	DatePicker DOB, DOJ;
	ImageView logoPlace;
	final ObservableList<Swimmer> data = FXCollections.observableArrayList();

	TableView<Swimmer> swimmersTable;

	@SuppressWarnings("unchecked")
	@Override
	public void start(Stage primaryStage) {
		try {

			thestage = primaryStage;

			// Check the connection with the database
			CheckConnection();

			primaryStage.setTitle("Swimming");
			primaryStage.getIcons().add(new Image("file:Shark.jpg"));

			firstBorderPane = new BorderPane();
			secondBorderPane = new BorderPane();
			thirdBorderPane = new BorderPane();
			fourthBorderPane = new BorderPane();

			Group root = new Group();
			Group viewSwimmersGroup = new Group();
			Group addSwimmerGroup = new Group();

			mainScene = new Scene(firstBorderPane, 225, 300, Color.rgb(0, 0, 0, 0));

			// Disables only maximize button
			primaryStage.setResizable(false);

			// Disables minimaze and maximaze buttons
			// primaryStage.initStyle(StageStyle.UTILITY);

			viewSwimmerScene = new Scene(secondBorderPane, 1200, 600);
			addSwimmerScene = new Scene(thirdBorderPane, 500, 500);

			VBox vBoxForButtons = new VBox(5);
			vBoxForButtons.setPadding(new Insets(10, 0, 0, 10));

			VBox vBoxForTable = new VBox(5);
			vBoxForTable.setPadding(new Insets(10, 10, 10, 10));

			VBox vBoxTextFields = new VBox(5);
			vBoxTextFields.setPadding(new Insets(10, 10, 10, 10));

			// Defining TextFields

			idSwimmer = new TextField();
			idSwimmer.setPrefSize(200, 20);
			idSwimmer.setFont(Font.font("SanSerif", 15));
			idSwimmer.setPromptText("ID");

			firstName = new TextField();
			firstName.setPrefSize(200, 20);
			firstName.setFont(Font.font("SanSerif", 15));
			firstName.setPromptText("First Name");

			lastName = new TextField();
			lastName.setPrefSize(200, 20);
			lastName.setFont(Font.font("SanSerif", 15));
			lastName.setPromptText("Last Name");

			registrationId = new TextField();
			registrationId.setPrefSize(200, 20);
			registrationId.setFont(Font.font("SanSerif", 15));
			registrationId.setPromptText("Registration ID");

			parentName = new TextField();
			parentName.setPrefSize(200, 20);
			parentName.setFont(Font.font("SanSerif", 15));
			parentName.setPromptText("Parent Name");

			contactNumber = new TextField();
			contactNumber.setPrefSize(200, 20);
			contactNumber.setFont(Font.font("SanSerif", 15));
			contactNumber.setPromptText("Contact Number");

			ObservableList<String> coaches = FXCollections.observableArrayList("Davor", "Boba", "Aleksandar","Maja","Tijana","Branny");
			coach = new ComboBox<>(coaches);
			coach.setPrefSize(490, 20);
			coach.setPromptText("Coach");

			DOB = new DatePicker();
			DOB.setPromptText("Date of birth");
			DOB.setPrefSize(490, 20);
			DOB.setStyle("-fx-font-size:15");

			// DOB.setConverter(new StringConverter<LocalDate>()
			// {
			// private DateTimeFormatter dateTimeFormatter =
			// DateTimeFormatter.ofPattern("dd/MM/yyyy");
			//
			// @Override
			// public String toString(LocalDate localDate)
			// {
			// if(localDate==null)
			// return "";
			// return dateTimeFormatter.format(localDate);
			// }
			//
			// @Override
			// public LocalDate fromString(String dateString)
			// {
			// if(dateString==null || dateString.trim().isEmpty())
			// {
			// return null;
			// }
			// return LocalDate.parse(dateString,dateTimeFormatter);
			// }
			// });

			DOJ = new DatePicker(LocalDate.now());
			DOJ.setPromptText("Date of joining");
			DOJ.setPrefSize(490, 20);
			DOJ.setStyle("-fx-font-size:15");

			// Save button
			Button saveButton = new Button("Save");
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

					} else {
						String query = "INSERT INTO swimmers (idSwimmer, firstName, lastName, DOB, registrationId, dateJoined, parentName, contactNumber, coach) VALUES(?,?,?,?,?,?,?,?,?)";
						pst = conn.prepareStatement(query);
						Integer value1A = Integer.parseInt(idSwimmer.getText());
						pst.setInt(1, value1A);
						// pst.setString(1, idSwimmer.getText());
						pst.setString(2, firstName.getText());
						pst.setString(3, lastName.getText());
						pst.setString(4, ((TextField) DOB.getEditor()).getText());
						pst.setString(5, registrationId.getText());
						pst.setString(6, ((TextField) DOJ.getEditor()).getText());
						pst.setString(7, parentName.getText());
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
						
						System.out.println(coach.getSelectionModel().toString());
					}

				} catch (Exception e1) {
					System.err.println(e1);
				}
			});

			vBoxTextFields.getChildren().addAll(idSwimmer, firstName, lastName, DOB, registrationId, DOJ, parentName,
					contactNumber, coach, saveButton);

			addSwimmerGroup.getChildren().addAll(vBoxTextFields);

			Button backFromNewSwimmer = new Button("Back");
			backFromNewSwimmer.setOnAction(e -> {
				thestage.setScene(mainScene);
				thestage.show();
			});
			thirdBorderPane.setTop(backFromNewSwimmer);
			BorderPane.setMargin(backFromNewSwimmer, new Insets(10, 0, 0, 10));

			thirdBorderPane.setCenter(vBoxTextFields);
			BorderPane.setMargin(addSwimmerGroup, new Insets(20, 20, 20, 20));

			Label label = new Label();
			label.setTextFill(Color.RED);
			if (conn == null) {
				label.setText("Connection Not Successfull");
			} else {
				label.setText("Connection Successfull");
			}
			label.setFont(new Font("SanSerif", 15));

			// Defining buttons

			newCoach = new Button("Add new coach");
			newCoach.setPrefSize(200, 20);
			newCoach.setFont(Font.font("SanSerif", 15));
			newCoach.setOnAction(e -> {
				primaryStage.setScene(mainScene);
				primaryStage.show();
			});

			newSwimmer = new Button("Add new swimmer");
			newSwimmer.setPrefSize(200, 20);
			newSwimmer.setFont(Font.font("SanSerif", 15));
			newSwimmer.setOnAction(e -> {
				thestage.setScene(addSwimmerScene);
				thestage.show();
			});

			viewCoaches = new Button("View All Coaches");
			viewCoaches.setPrefSize(200, 20);
			viewCoaches.setFont(Font.font("SanSerif", 15));
			viewCoaches.setOnAction(EventHandler -> {
				// ViewCoach coachScene = new ViewCoach(fourthBorderPane);
				// thestage.setScene(coachScene);
				// thestage.show();
			});

			viewSwimmer = new Button("View All Swimmers");
			viewSwimmer.setPrefSize(200, 20);
			viewSwimmer.setFont(Font.font("SanSerif", 15));
			viewSwimmer.setOnAction(e -> {
				thestage.setScene(viewSwimmerScene);
				thestage.show();
				try {
					data.clear(); // clears the table
					String query = "select * from swimmers";

					pst = conn.prepareStatement(query);
					rs = pst.executeQuery();

					while (rs.next()) {
						data.add(new Swimmer(rs.getString("idSwimmer"), rs.getString("firstName"), rs.getString("lastName"),
								rs.getString("DOB"), rs.getString("registrationId"), rs.getString("dateJoined"),
								rs.getString("parentName"), rs.getString("contactNumber"), rs.getString("coach")));
						swimmersTable.setItems(data);
					}
					pst.close();
					rs.close();
				} catch (Exception e2) {
					System.err.println(e2);

				}
				thestage.setScene(viewSwimmerScene);
//				String query = "select * from swimmers";
//
//				pst = conn.prepareStatement(query);
//				rs = pst.executeQuery();
//
//				while (rs.next()) {
//					data.add(new Swimmer(rs.getString("idSwimmer"), rs.getString("firstName"),
//							rs.getString("lastName"), rs.getString("DOB"), rs.getString("registrationId"),
//							rs.getString("dateJoined"), rs.getString("parentName"), rs.getString("contactNumber"),
//							rs.getString("coach")));
//					swimmersTable.setItems(data);
//				}
//				pst.close();
//				rs.close();
				thestage.show();
			});

			close = new Button("Exit");
			close.setPrefSize(200, 20);
			close.setFont(Font.font("SanSerif", 15));
			close.setOnAction(e -> {
				thestage.close();
			});

			Image logo = new Image("file:logoViktorija.png");
			ImageView logoPlace = new ImageView();
			logoPlace.setImage(logo);
			logoPlace.setFitWidth(200);
			logoPlace.setPreserveRatio(true);
			logoPlace.setSmooth(true);
			logoPlace.setCache(true);

			vBoxForButtons.getChildren().addAll(logoPlace, label, newCoach, newSwimmer, viewCoaches, viewSwimmer,
					close);
			root.getChildren().addAll(vBoxForButtons);

			firstBorderPane.setCenter(vBoxForButtons);
			BorderPane.setMargin(root, new Insets(20, 20, 20, 20));

			// Table for viewing data inside database
			swimmersTable = new TableView<>();
			final ObservableList<Swimmer> data = FXCollections.observableArrayList();

			// Getting the ID from the database and inserting it into the first
			// column
			TableColumn<Swimmer, String> column1 = new TableColumn<Swimmer, String>("ID");
			column1.setMinWidth(10);
			column1.setCellValueFactory(new PropertyValueFactory<Swimmer, String>("idSwimmer"));
			column1.setCellValueFactory(new Callback<CellDataFeatures<Swimmer, String>, ObservableValue<String>>() {
				public ObservableValue<String> call(CellDataFeatures<Swimmer, String> u) {
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

			// Filling the table with the data from the database
			try {
				data.clear(); // clears the table
				String query = "select * from swimmers";

				pst = conn.prepareStatement(query);
				rs = pst.executeQuery();

				while (rs.next()) {
					data.add(new Swimmer(rs.getString("idSwimmer"), rs.getString("firstName"), rs.getString("lastName"),
							rs.getString("DOB"), rs.getString("registrationId"), rs.getString("dateJoined"),
							rs.getString("parentName"), rs.getString("contactNumber"), rs.getString("coach")));
					swimmersTable.setItems(data);
				}
				pst.close();
				rs.close();
			} catch (Exception e2) {
				System.err.println(e2);

			}

			// Here was unchecked warning
			swimmersTable.getColumns().addAll(column1, column2, column3, column4, column5, column6, column7, column8,
					column9);
			swimmersTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

			// Sorting button in the table
			swimmersTable.setTableMenuButtonVisible(true);

			// BACK BUTTON
			back = new Button("Back to Menu");
			back.setOnAction(e -> {
				thestage.setScene(mainScene);
				thestage.show();
			});
			back.setPadding(new Insets(5, 5, 5, 5));
			secondBorderPane.setTop(back);
			BorderPane.setMargin(back, new Insets(10, 0, 0, 10));

			// DELETE SELECTED BUTTON
			delete = new Button("Delete");
			delete.setPadding(new Insets(5, 5, 5, 5));
			delete.setPrefSize(100, 20);

			delete.setOnAction(e -> {
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
							pst.setInt(1, Integer.parseInt(selectedItem.getIdString()));
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

				
				System.out.println();
				}
			});
			

			// REFRESH BUTTON
			refresh = new Button("Refresh");
			refresh.setPadding(new Insets(5, 5, 5, 5));
			refresh.setPrefSize(100, 20);
			refresh.setOnAction(e -> {
				try {
					data.clear(); // clears the table
					String query = "select * from swimmers";

					pst = conn.prepareStatement(query);
					rs = pst.executeQuery();

					while (rs.next()) {
						data.add(new Swimmer(rs.getString("idSwimmer"), rs.getString("firstName"),
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

			VBox tableButtons = new VBox(5);
			tableButtons.getChildren().addAll(delete, refresh);
			BorderPane.setMargin(tableButtons, new Insets(35, 5, 0, 0));
			secondBorderPane.setRight(tableButtons);

			vBoxForTable.getChildren().add(swimmersTable);
			viewSwimmersGroup.getChildren().addAll(vBoxForTable);

			secondBorderPane.setCenter(vBoxForTable);
			BorderPane.setMargin(viewSwimmersGroup, new Insets(20, 20, 20, 20));

			mainScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(mainScene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
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
			System.out.println("Connection Successful");
		}
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

	public static void main(String[] args) {
		launch(args);
	}
}
