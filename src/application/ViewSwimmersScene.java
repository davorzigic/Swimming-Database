package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

public class ViewSwimmersScene extends Scene {
	
	final ObservableList<Swimmer> data;
	TableView<Swimmer> swimmersTable;
	
	Connection conn;
	PreparedStatement pst;
	ResultSet rs = null;
	
	Button backBtn, editBtn, refreshBtn, deleteBtn;
	VBox vBoxForTable;
	Group viewSwimmersGroup;
	
	public ViewSwimmersScene(BorderPane root, double width, double height) {
		super(root, width, height);
		// TODO Auto-generated constructor stub
		swimmersTable = new TableView<>();
		data = FXCollections.observableArrayList();
		vBoxForTable = new VBox(5);
		viewSwimmersGroup = new Group();
		vBoxForTable.getChildren().add(swimmersTable);
		viewSwimmersGroup.getChildren().addAll(vBoxForTable);

		root.setCenter(vBoxForTable);
		BorderPane.setMargin(viewSwimmersGroup, new Insets(20, 20, 20, 20));
		
		CheckConnection();
		settingTable();
		
		
		
	}

	
	
	
	@SuppressWarnings("unchecked")
	private void settingTable() {
		// Getting the ID from the database and inserting it into the first
		// column
		TableColumn<Swimmer, Number> column1 = new TableColumn<Swimmer, Number>("ID");
		column1.setMinWidth(10);
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
		
		fillingTable();
		
	}
	
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
	
	
	public ObservableList<Swimmer> getData() {
		return data;
	}
	
	public PreparedStatement getPst() {
		return pst;
	}

	public void setPst(PreparedStatement pst) {
		this.pst = pst;
	}

	public Connection getConn() {
		return conn;
	}


	public void setConn(Connection conn) {
		this.conn = conn;
	}
	
	public ResultSet getRs() {
		return rs;
	}


	public void setRs(ResultSet rs) {
		this.rs = rs;
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
}
