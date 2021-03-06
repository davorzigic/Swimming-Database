package application;

import java.awt.Checkbox;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.Optional;

import javax.swing.GroupLayout.Alignment;

import org.omg.CORBA.PUBLIC_MEMBER;

import com.sun.prism.shader.DrawCircle_LinearGradient_REFLECT_AlphaTest_Loader;

import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListView.EditEvent;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Main extends Application {

	Connection conn;
	PreparedStatement pst = null;
	ResultSet rs = null;
	Stage thestage;
	Button newCoach, newSwimmer, viewCoaches, viewSwimmer, backBtn, closeBtn, deleteBtn, refreshBtn, editBtn;
	BorderPane firstBorderPane, secondBorderPane, thirdBorderPane, fourthBorderPane;
	Scene mainScene, viewSwimmerScene, addSwimmerScene;
	TextField idSwimmer, firstName, lastName, registrationId, parentName, contactNumber;
	ComboBox<String> coach;
	DatePicker DOB, DOJ;
	ImageView logoPlace;
	ViewSwimmersScene viewSwimmersScene;
//	final ObservableList<Swimmer> data = FXCollections.observableArrayList();
	Stage primaryStage;
	
	TableView<Swimmer> swimmersTable;

	
	@SuppressWarnings("unchecked")
	@Override
	public void start(Stage primaryStage) {
		
		
		try {
			this.primaryStage = primaryStage;
			thestage = this.primaryStage;
			
			

			// Check the connection with the database
			CheckConnection();

			primaryStage.setTitle("Swimming");
			primaryStage.getIcons().add(new Image("file:Shark.jpg"));

			firstBorderPane = new BorderPane();
			mainScene = new Scene(firstBorderPane, 225, 300, Color.rgb(0, 0, 0, 0));
			
			
			
			thirdBorderPane = new BorderPane();
			addSwimmerScene = new Scene(thirdBorderPane, 500, 500);
			
			

			Group root = new Group();
			

			// Disables only maximize button
			primaryStage.setResizable(false);

			// Disables minimaze and maximaze buttons
			// primaryStage.initStyle(StageStyle.UTILITY);

//			viewSwimmerScene = new Scene(secondBorderPane, 1200, 600);
			

			VBox vBoxForButtons = new VBox(5);
			vBoxForButtons.setPadding(new Insets(10, 0, 0, 10));

			VBox vBoxForTable = new VBox(5);
			vBoxForTable.setPadding(new Insets(10, 10, 10, 10));

			VBox vBoxTextFields = new VBox(5);
			vBoxTextFields.setPadding(new Insets(10, 10, 10, 10));

			// Defining TextFields

//			idSwimmer = new TextField();
//			idSwimmer.setPrefSize(200, 20);
//			idSwimmer.setFont(Font.font("SanSerif", 15));
//			idSwimmer.setPromptText("ID");
//
//			firstName = new TextField();
//			firstName.setPrefSize(200, 20);
//			firstName.setFont(Font.font("SanSerif", 15));
//			firstName.setPromptText("First Name");
//
//			lastName = new TextField();
//			lastName.setPrefSize(200, 20);
//			lastName.setFont(Font.font("SanSerif", 15));
//			lastName.setPromptText("Last Name");
//
//			registrationId = new TextField();
//			registrationId.setPrefSize(200, 20);
//			registrationId.setFont(Font.font("SanSerif", 15));
//			registrationId.setPromptText("Registration ID");
//
//			parentName = new TextField();
//			parentName.setPrefSize(200, 20);
//			parentName.setFont(Font.font("SanSerif", 15));
//			parentName.setPromptText("Parent Name");
//
//			contactNumber = new TextField();
//			contactNumber.setPrefSize(200, 20);
//			contactNumber.setFont(Font.font("SanSerif", 15));
//			contactNumber.setPromptText("Contact Number");
//
//			ObservableList<String> coaches = FXCollections.observableArrayList("Davor", "Boba", "Aleksandar","Maja","Tijana","Branny");
//			coach = new ComboBox<>(coaches);
//			coach.setPrefSize(490, 20);
//			coach.setPromptText("Coach");
//
//			DOB = new DatePicker();
//			DOB.setPromptText("Date of birth");
//			DOB.setPrefSize(490, 20);
//			DOB.setStyle("-fx-font-size:15");
//
//			DOJ = new DatePicker(LocalDate.now());
//			DOJ.setPromptText("Date of joining");
//			DOJ.setPrefSize(490, 20);
//			DOJ.setStyle("-fx-font-size:15");
//
//			// Save button
//			Button saveButton = new Button("Save");
//			saveButton.setFont(Font.font("SanSerif", 15));
//			saveButton.setOnAction(e -> {
//				try {
//					
//					if (idSwimmer.getText().isEmpty()) {
//						System.out.println("You dont have ID");
//						Alert alert = new Alert(AlertType.WARNING);
//						alert.setTitle("Information dialog");
//						alert.setHeaderText(null);
//						alert.setContentText("Your ID field is empty.");
//						alert.showAndWait();
//					} else if(firstName.getText().isEmpty()) {
//						Alert alert = new Alert(AlertType.WARNING);
//						alert.setTitle("Information dialog");
//						alert.setHeaderText(null);
//						alert.setContentText("Your First Name field is empty.");
//						alert.showAndWait();
//					} else if(lastName.getText().isEmpty()) {
//						Alert alert = new Alert(AlertType.WARNING);
//						alert.setTitle("Information dialog");
//						alert.setHeaderText(null);
//						alert.setContentText("Your Last Name field is empty.");
//						alert.showAndWait();
//					} else if(parentName.getText().isEmpty()) {
//						Alert alert = new Alert(AlertType.WARNING);
//						alert.setTitle("Information dialog");
//						alert.setHeaderText(null);
//						alert.setContentText("Your Parent Name field is empty.");
//						alert.showAndWait();
//					} else if(coach.getSelectionModel().isEmpty()) {
//						Alert alert = new Alert(AlertType.WARNING);
//						alert.setTitle("Information dialog");
//						alert.setHeaderText(null);
//						alert.setContentText("You have to pick coach.");
//						alert.showAndWait();
//					} else {
//						Alert saveConfirmation = new Alert(AlertType.CONFIRMATION);
//						saveConfirmation.setTitle("Confirmation Dialog");
//						// alert.setHeaderText("Look, a Confirmation Dialog");
//						saveConfirmation.setContentText("Save swimmer?");
//		
//						Optional<ButtonType> result = saveConfirmation.showAndWait();
//						if (result.get() == ButtonType.OK) {
//						// ... user chose OK
//							String query = "INSERT INTO swimmers (idSwimmer, firstName, lastName, DOB, registrationId, dateJoined, parentName, contactNumber, coach) VALUES(?,?,?,?,?,?,?,?,?)";
//							pst = conn.prepareStatement(query);
//							Integer value1A = Integer.parseInt(idSwimmer.getText());
//							pst.setInt(1, value1A);
//							// pst.setString(1, idSwimmer.getText());
//							pst.setString(2, (firstName.getText().substring(0,1).toUpperCase() + firstName.getText().substring(1)));
//							pst.setString(3, (lastName.getText().substring(0,1).toUpperCase() + lastName.getText().substring(1)));
//							pst.setString(4, ((TextField) DOB.getEditor()).getText());
//							pst.setString(5, registrationId.getText());
//							pst.setString(6, ((TextField) DOJ.getEditor()).getText());
//							pst.setString(7, (parentName.getText().substring(0,1).toUpperCase() + parentName.getText().substring(1)));
//							pst.setString(8, contactNumber.getText());
//							pst.setString(9, coach.getValue());
//							pst.execute();
//							pst.close();
//	
//							clearFields();
//	
//							Alert alert = new Alert(AlertType.INFORMATION);
//							alert.setTitle("Information dialog");
//							alert.setHeaderText(null);
//							alert.setContentText("User has been created!");
//							alert.showAndWait();
//						} else {
//							saveConfirmation.close();
//						}
//						
//					}
//
//				} catch (Exception e1) {
//					System.err.println(e1);
//					e1.printStackTrace();
//				}
//			});
//
//			vBoxTextFields.getChildren().addAll(idSwimmer, firstName, lastName, DOB, registrationId, DOJ, parentName,
//					contactNumber, coach, saveButton);
//
//			addSwimmerGroup.getChildren().addAll(vBoxTextFields);
//
//			Button backFromNewSwimmer = new Button("Back");
//			backFromNewSwimmer.setOnAction(e -> {
//				thestage.setScene(mainScene);
//				thestage.show();
//			});
//			thirdBorderPane.setTop(backFromNewSwimmer);
//			BorderPane.setMargin(backFromNewSwimmer, new Insets(10, 0, 0, 10));
//
//			thirdBorderPane.setCenter(vBoxTextFields);
//			BorderPane.setMargin(addSwimmerGroup, new Insets(20, 20, 20, 20));

			Label label = new Label();
			label.setTextFill(Color.RED);
			if (conn == null) {
				label.setText("Connection Not Successfull");
			} else {
				label.setText("Connection Successfull");
			}
			label.setFont(new Font("SanSerif", 15));
			
			TextField username = new TextField();
			username.setFont(Font.font("SanSerif", 20));
			username.setPromptText("Username");

			PasswordField password = new PasswordField();
			password.setFont(Font.font("SanSerif", 20));
			password.setPromptText("Password");
			
			// Defining buttons
			newCoach = new Button("Add new coach");
			newCoach.setPrefSize(200, 20);
			newCoach.setFont(Font.font("SanSerif", 15));
			newCoach.setOnAction(e -> {	
				fourthBorderPane = new BorderPane();
				AddNewCoach viewCoach = new AddNewCoach(fourthBorderPane,600,400);						
				thestage.setScene(viewCoach);
				thestage.show();
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
				secondBorderPane = new BorderPane();
				viewSwimmersScene = new ViewSwimmersScene(secondBorderPane, 1200, 600);				
				thestage.setScene(viewSwimmersScene);
				thestage.show();
//				try {
//					viewSwimmersScene.getData().clear(); // clears the table
//					String query = "select * from swimmers";
//
//					viewSwimmersScene.setPst() pst = viewSwimmersScene.getConn(); conn.prepareStatement(query);
//					rs = pst.executeQuery();
//
//					while (rs.next()) {
//						viewSwimmersScene.getData().add(new Swimmer(rs.getInt("idSwimmer"), rs.getString("firstName"), rs.getString("lastName"),
//								rs.getString("DOB"), rs.getString("registrationId"), rs.getString("dateJoined"),
//								rs.getString("parentName"), rs.getString("contactNumber"), rs.getString("coach")));
//						swimmersTable.setItems(viewSwimmersScene.getData());
//					}
//					pst.close();
//					rs.close();
//					viewSwimmersScene.fillingTable();
//				} catch (Exception e2) {
//					System.err.println(e2);
//
//				}
//				thestage.setScene(viewSwimmerScene);
//				thestage.show();
			});
			
			
			// Close the application button
			closeBtn = new Button("Exit");
			closeBtn.setPrefSize(200, 20);
			closeBtn.setFont(Font.font("SanSerif", 15));
			closeBtn.setOnAction(e -> {
				thestage.close();
			});
			
			// Inserting logo into the imageView
			Image logo = new Image("file:logoViktorija.png");
			ImageView logoPlace = new ImageView();
			logoPlace.setImage(logo);
			logoPlace.setFitWidth(200);
			logoPlace.setPreserveRatio(true);
			logoPlace.setSmooth(true);
			logoPlace.setCache(true);

			vBoxForButtons.getChildren().addAll(logoPlace, label, newCoach, newSwimmer, viewCoaches, viewSwimmer,
					closeBtn);
			root.getChildren().addAll(vBoxForButtons);

			firstBorderPane.setCenter(vBoxForButtons);
			BorderPane.setMargin(root, new Insets(20, 20, 20, 20));



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
	
	public void checkEmpty(TextField name) {
		if(name.getText().isEmpty()) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Information dialog");
			alert.setHeaderText(null);
			alert.setContentText("First name, last name, parent name and coach are mandatory fields.");
			alert.showAndWait();
		}
	}
	

	public static void main(String[] args) {
		launch(args);
	}

	
}
