package gui.LoginDialog;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class LoginDialog {
	private Stage stage;
	private  String BN = "Paule";
	private int PW = 1234;
	private LoginHandler lfhandler;
	private Label Fehler;
	private TextField TBN;
	private PasswordField TPW;
	public void setLfhandler(LoginHandler lfhandler) {
		this.lfhandler = lfhandler;
	}

	private LoginHandler LH;
	
	public LoginDialog() {
		
		initDialog();
		
		stage.initModality(Modality.APPLICATION_MODAL);
        stage.setWidth(400.);
	}
	
	
	
	
	
	private void initDialog() {
		BorderPane tst = new BorderPane();
		Label BN = new Label ( "Benutzername: ");
		Label PW = new Label ( "Passwort:");
		 TBN = new TextField();
		 TPW = new PasswordField();
		 Fehler = new Label();
		TBN.setOnKeyReleased(event->{
			Fehler.setText("");
		});
		TPW.setOnKeyReleased(event->{
			Fehler.setText("");
		});
		GridPane gp1 = new GridPane();
		
		Button bt = new Button("Login");
		
		bt.setPrefWidth(200);
		
		bt.setOnMouseClicked(event -> {
			lfhandler.checkLogin( TBN.getText(),  TPW.getText());
			
			
		});
		
		
		gp1.add(BN, 0, 0);
		GridPane.setMargin(BN, new Insets(0,0,20,0));
		gp1.add(TBN, 1, 0);
		GridPane.setMargin(TBN, new Insets(0,0,20,10));
		gp1.add(PW, 0, 1);
		
		gp1.add(TPW, 1, 1);
		GridPane.setMargin(TPW, new Insets(0,0,0,10));
		
		BorderPane.setMargin(gp1, new Insets(20,50,20,50));
		tst.setTop(gp1);
		BorderPane.setMargin(Fehler, new Insets(0,40,40,30));
		tst.setCenter(Fehler);
		
		tst.setBottom(bt);
		BorderPane.setAlignment(bt, Pos.BASELINE_CENTER);
		
		
		Scene scene = new Scene (tst);
		this.stage = new Stage();
		stage.setTitle("Login");
		stage.setScene(scene);
	}


public void BNisEmpty() {
	this.Fehler.setText("Bitte geben Sie einen Benutzernamen ein!");
	this.Fehler.setTextFill(Color.web("red"));
	TBN.setText("");
	//TPW.setText("");
	
}
public void PWisEmpty() {
	this.Fehler.setText("Bitte geben Sie ein Passwort ein!");
	this.Fehler.setTextFill(Color.web("red"));
	//TBN.setText("");
	TPW.setText("");
}
public void falscheKOmbi() {
	this.Fehler.setText("Bitte geben Sie eine gültige Benutzer/Passwortkombi ein!");
	this.Fehler.setTextFill(Color.web("red"));
	TBN.setText("");
	TPW.setText("");
}
public void Leeren() {
	TBN.setText("");
	TPW.setText("");
}


	public Stage getStage() {
		return stage;
	}





	public void setStage(Stage stage) {
		this.stage = stage;
	}





	public LoginHandler getLH() {
		return LH;
	}





	public void setLH(LoginHandler lH) {
		LH = lH;
	}





	public String getBN() {
		return BN;
	}





	public int getPW() {
		return PW;
	}





	public void show() {
		stage.show();
	}

	/**
	 * Verbirgt den Dialog
	 */
	public void hide() {
		stage.hide();
	}
}
