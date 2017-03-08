package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ViewCoachScene extends Application {

	BorderPane viewCoachesBorderPane;
	Scene viewCoachScene = new Scene(viewCoachesBorderPane, 225, 300, Color.rgb(0, 0, 0, 0));
	Connection conn;
	PreparedStatement pst = null;
	ResultSet rs = null;
	TableView<Coach> caoachesTable;
	
	

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		
		viewCoachScene = new Scene(viewCoachesBorderPane, 225, 300, Color.rgb(0, 0, 0, 0));
		
		// Table for viewing data inside database
		caoachesTable = new TableView<>();
		final ObservableList<Coach> data = FXCollections.observableArrayList();

		// Getting the ID from the database and inserting it into the first
		// column
		TableColumn<Coach, String> column1 = new TableColumn<Coach, String>("ID");
		column1.setMinWidth(10);
		column1.setCellValueFactory(new PropertyValueFactory<Coach, String>("idSwimmer"));
		column1.setCellValueFactory(new Callback<CellDataFeatures<Coach, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<Coach, String> u) {
				// u.getValue() returns the Person instance for a particular
				// TableView row

				return u.getValue().getId();
			}
		});

		// Getting the first name from the database and inserting it into
		// the second column
		TableColumn<Coach, String> column2 = new TableColumn<Coach, String>("First name");
		column2.setMinWidth(100);
		column2.setCellValueFactory(new PropertyValueFactory<Coach, String>("firstName"));
		column2.setCellValueFactory(new Callback<CellDataFeatures<Coach, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<Coach, String> u) {
				// u.getValue() returns the Person instance for a particular
				// TableView row

				return u.getValue().getFirstName();
			}
		});

		// Getting the last name from the database and inserting it into the
		// third column
		TableColumn<Coach, String> column3 = new TableColumn<Coach, String>("Last Name");
		column3.setMinWidth(100);
		column3.setCellValueFactory(new PropertyValueFactory<Coach, String>("lastName"));
		column3.setCellValueFactory(new Callback<CellDataFeatures<Coach, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<Coach, String> u) {
				// u.getValue() returns the Person instance for a particular
				// TableView row

				return u.getValue().getLastName();
			}
		});

		// Getting the DOB from the database and inserting it into the
		// fourth column
		TableColumn<Coach, String> column4 = new TableColumn<Coach, String>("DOB");
		column4.setMinWidth(100);
		column4.setCellValueFactory(new PropertyValueFactory<Coach, String>("DOB"));
		column4.setCellValueFactory(new Callback<CellDataFeatures<Coach, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<Coach, String> u) {
				// u.getValue() returns the User instance for a particular
				// TableView row

				return u.getValue().getEmail();
			}
		});

		// Getting the DOB from the database and inserting it into the

		TableColumn<Coach, String> column5 = new TableColumn<Coach, String>("Registration number");
		column5.setMinWidth(100);
		column5.setCellValueFactory(new PropertyValueFactory<Coach, String>("registrationNumber"));
		column5.setCellValueFactory(new Callback<CellDataFeatures<Coach, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<Coach, String> u) {
				// u.getValue() returns the User instance for a particular
				// TableView row

				return u.getValue().getPhone();
			}
		});

		// Getting the DOB from the database and inserting it into the

		TableColumn<Coach, String> column6 = new TableColumn<Coach, String>("DOJ");
		column6.setMinWidth(100);
		column6.setCellValueFactory(new PropertyValueFactory<Coach, String>("DOJ"));
		column6.setCellValueFactory(new Callback<CellDataFeatures<Coach, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<Coach, String> u) {
				// u.getValue() returns the User instance for a particular
				// TableView row

				return u.getValue().getUsername();
			}
		});

		// Getting the DOB from the database and inserting it into the

		TableColumn<Coach, String> column7 = new TableColumn<Coach, String>("Parent Name");
		column7.setMinWidth(100);
		column7.setCellValueFactory(new PropertyValueFactory<Coach, String>("parentName"));
		column7.setCellValueFactory(new Callback<CellDataFeatures<Coach, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<Coach, String> u) {
				// u.getValue() returns the User instance for a particular
				// TableView row

				return u.getValue().getPassword();
			}
		});

		// Getting the DOB from the database and inserting it into the

		TableColumn<Coach, String> column8 = new TableColumn<Coach, String>("Contact Number");
		column8.setMinWidth(100);
		column8.setCellValueFactory(new PropertyValueFactory<Coach, String>("contactNumber"));
		column8.setCellValueFactory(new Callback<CellDataFeatures<Coach, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<Coach, String> u) {
				// u.getValue() returns the User instance for a particular
				// TableView row

				return u.getValue().getLincenceNumber();
			}
		});

		

		// Filling the table with the data from the database
		try {
			data.clear(); // clears the table
			String query = "select * from swimmers";

			pst = conn.prepareStatement(query);
			rs = pst.executeQuery();

			while (rs.next()) {
				data.add(new Coach(rs.getString("idCoach"), rs.getString("firstName"), rs.getString("lastName"),
						rs.getString("email"), rs.getString("phone"), rs.getString("username"),
						rs.getString("password"), rs.getString("licenceNumber")));
				caoachesTable.setItems(data);
			}
			pst.close();
			rs.close();
		} catch (Exception e2) {
			System.err.println(e2);

		}

		// Here was unchecked warning
		caoachesTable.getColumns().addAll(column1, column2, column3, column4, column5, column6, column7, column8);
		caoachesTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		// Sorting button in the table
		caoachesTable.setTableMenuButtonVisible(true);
		
		VBox vBoxForTable = new VBox();
		vBoxForTable.getChildren().add(caoachesTable);
		Group viewCoachesGroup = new Group();
		viewCoachesGroup.getChildren().addAll(vBoxForTable);
		
		viewCoachesBorderPane = new BorderPane();
		viewCoachesBorderPane.setCenter(vBoxForTable);
		BorderPane.setMargin(viewCoachesGroup, new Insets(20, 20, 20, 20));
	}

}
