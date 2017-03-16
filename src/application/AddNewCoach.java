package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class AddNewCoach extends Scene {

	
	Main test = new Main();
	VBox vBoxForFields;
	TextField coachName;
	Button back;

	

	
	public AddNewCoach(BorderPane root, double width, double height) {
		super(root, width, height);
		// TODO Auto-generated constructor stub
		vBoxForFields = new VBox(5);
		coachName = new TextField();
		back = new Button("Back");
		root.setCenter(vBoxForFields);
		root.setTop(back);
		BorderPane.setAlignment(back, Pos.TOP_RIGHT);
		BorderPane.setMargin(back, new Insets(10, 10, 10, 10));
		setFields();
		setBackButton();
	
	}
	
	private void setFields() {
		
		coachName.setPrefSize(200, 20);
		coachName.setFont(Font.font("SanSerif", 15));
		coachName.setPromptText("Testing");
		vBoxForFields.getChildren().add(coachName);
		
	
	}
	
	private void setBackButton() {
		
		
		back.setFont(Font.font("SanSerif", 15));
		back.setOnAction(e -> {
//			theStage.close();
		});
		
		
	}
	
	public String getCoachName() {
		return coachName.getText().toString();
	}
	
	
}
