package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

public class Main extends Application {

	Connection conn;
	PreparedStatement pst = null;
	ResultSet rs = null;
	// Button newCoach, newSwimmer, viewCoaches, viewSwimmers;

	@SuppressWarnings("unchecked")
	@Override
	public void start(Stage primaryStage) {
		try {
			// Check the connection with the database
			CheckConnection();

			primaryStage.setTitle("Swimming");
			BorderPane layout = new BorderPane();

			Scene scene = new Scene(layout, 300, 400, Color.rgb(0, 0, 0, 0));

			Group root = new Group();
			Scene newSwimmerScene = new Scene(root, 1200, 600, Color.rgb(0, 0, 0, 0));
			// Scene scene = new Scene(root, 320, 250, Color.rgb(0, 0, 0, 0));

			Color foreground = Color.rgb(255, 255, 255, 0.9);
			Rectangle background = new Rectangle(320, 250);
			background.setX(0);
			background.setY(0);
			background.setArcHeight(15);
			background.setArcWidth(15);
			background.setFill(Color.rgb(0, 0, 0, 0.55));
			background.setStroke(foreground);
			background.setStrokeWidth(1.5);

			VBox vbox = new VBox(5);
			vbox.setPadding(new Insets(10, 0, 0, 10));

			Label label = new Label();
			label.setTextFill(Color.RED);
			if (conn == null) {
				label.setText("Connection Not Successfull");
			} else {
				label.setText("Connection Successfull");
			}
			label.setFont(new Font("SanSerif", 20));

			VBox buttons = new VBox(5);

			Button newCoach = new Button("Add new coach");
			newCoach.setPrefSize(200, 20);
			newCoach.setFont(Font.font("SanSerif", 15));
			newCoach.setOnAction(e -> {
				primaryStage.setScene(scene);
				primaryStage.show();
			});

			Button newSwimmer = new Button("Add new swimmer");
			newSwimmer.setPrefSize(200, 20);
			newSwimmer.setFont(Font.font("SanSerif", 15));
			newSwimmer.setOnAction(e -> {
				primaryStage.setScene(scene);
				primaryStage.show();
			});

			Button viewCoaches = new Button("View All Coaches");
			viewCoaches.setPrefSize(200, 20);
			viewCoaches.setFont(Font.font("SanSerif", 15));
			viewCoaches.setOnAction(e -> {
				primaryStage.setScene(scene);
				primaryStage.show();
			});

			Button viewSwimmer = new Button("View All Swimmers");
			viewSwimmer.setPrefSize(200, 20);
			viewSwimmer.setFont(Font.font("SanSerif", 15));
			viewSwimmer.setOnAction(e -> {
				primaryStage.setScene(newSwimmerScene);
				primaryStage.show();
			});

			buttons.getChildren().addAll(label, newCoach, newSwimmer, viewCoaches, viewSwimmer);

			layout.setCenter(buttons);
			BorderPane.setMargin(root, new Insets(20, 20, 20, 20));

			// Table for viewing data inside database
			TableView<Swimmer> table = new TableView<>();
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

			TableColumn<Swimmer, String> column6 = new TableColumn<Swimmer, String>("DOB");
			column6.setMinWidth(100);
			column6.setCellValueFactory(new PropertyValueFactory<Swimmer, String>("DOB"));
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
			
			try {
				data.clear();
				String query = "select * from swimmers";

				pst = conn.prepareStatement(query);
				rs = pst.executeQuery();

				while (rs.next()) {
					data.add(new Swimmer(rs.getString("idSwimmer"), rs.getString("firstName"), rs.getString("lastName"),
							rs.getString("DOB"), rs.getString("registrationId"), rs.getString("dateJoined"),
							rs.getString("parentName"), rs.getString("contactNumber"), rs.getString("coach")));
					table.setItems(data);
				}
				pst.close();
				rs.close();
			} catch (Exception e2) {
				System.err.println(e2);

			}
			

			// Here was unchecked warning
			table.getColumns().addAll(column1, column2, column3, column4, column5, column6, column7, column8, column9);
			table.setTableMenuButtonVisible(true);

			VBox rightSide = new VBox(5);
			rightSide.getChildren().addAll(table);
			layout.setRight(rightSide);
			BorderPane.setMargin(table, new Insets(0, 20, 10, 0));

			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
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

	public static void main(String[] args) {
		launch(args);
	}
}
