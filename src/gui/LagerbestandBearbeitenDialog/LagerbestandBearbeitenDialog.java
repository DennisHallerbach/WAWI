package gui.LagerbestandBearbeitenDialog;

import geschaeftsobjekte.Artikel;
import gui.LagerbestaendeSIchtenDialog.LagerbestaendeSichtenDialog;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import javafx.stage.Stage;

public class LagerbestandBearbeitenDialog {
	private Stage stage;
	private Artikel AR;
private LagerbestandBearbeitenDialogCloseHandler close;
private LagerbestaendeSichtenDialog log;

public LagerbestaendeSichtenDialog getLog() {
	return log;
}
public void setLog(LagerbestaendeSichtenDialog log) {
	this.log = log;
}
public LagerbestandBearbeitenDialog(Artikel selektierterArtikel){
	initDialog(selektierterArtikel);
	//stage.initModality(Modality.APPLICATION_MODAL);
    stage.setWidth(400.);
}
private void initDialog(Artikel selektierterArtikel) {
	 AR = selektierterArtikel;
		GridPane GP = new GridPane();
		GP.setPadding(new Insets ( 20,40,30,20));
		Label Nummer = new Label("Nummer");
		Label BZG = new Label("Bezeichnung");
		Label KBZG = new Label("Kurzbezeichnung");
		Label AL = new Label("Alter Lagerbestand  ");
		Label NL = new Label("Neuer Lagerbestand  ");
		Label Nummer1 = new Label("" + AR.getNummer());
		Label BZG1 = new Label (AR.getBezeichnung());
		Label KBZG1 = new Label(AR.getKurzbezeichnung());
		Label AL1 = new Label("" + AR.getLagerbestand());
		TextField NL1 = new TextField();
		GP.add(Nummer, 0, 0);
		GridPane.setMargin(Nummer, new Insets(0,0,20,0));
		GP.add(Nummer1, 1, 0);
		GridPane.setMargin(Nummer1, new Insets(0,0,20,5));
		GP.add(BZG, 0, 1);
		GridPane.setMargin(BZG, new Insets(0,0,20,0));
		GP.add(BZG1, 1, 1);
		GridPane.setMargin(BZG1, new Insets(0,0,20,5));
		GP.add(KBZG, 0, 2);
		GridPane.setMargin(KBZG, new Insets(0,0,20,0));
		GP.add(KBZG1, 1, 2);
		GridPane.setMargin(KBZG1, new Insets(0,0,20,5));
		GP.add(AL, 0, 3);
		GridPane.setMargin(AL, new Insets(0,0,20,0));
		GP.add(AL1, 1, 3);
		GridPane.setMargin(AL1, new Insets(0,0,20,5));
		GP.add(NL, 0, 4);
		GridPane.setMargin(NL, new Insets(0,0,20,0));
		GP.add(NL1, 1, 4);
		GridPane.setMargin(NL1, new Insets(0,0,20,5));
		Scene scene = new Scene (GP);
		this.stage = new Stage();
		stage.setOnCloseRequest(e -> {
			int neuerLagerbst = selektierterArtikel.getLagerbestand();
			if(isNumber(NL1.getText())) {
				Double a =  Double.parseDouble(NL1.getText());
				int b =  a.intValue();
				if(a>=0) {
					neuerLagerbst = b;
				}
			}
			close.handleCloseEvent(neuerLagerbst);
		});
		stage.setTitle("Lagerbestände bearbeiten");
		stage.setScene(scene);
		//stage.show();
		

		
}
public void show() {
	stage.showAndWait();
}

public void hide() {
	stage.hide();
}
public static boolean isNumber(String str) {
	/*for (char c : str.toCharArray()) {
		if (!Character.isDigit(c)) {
			return false;
		}
	}
	return true;*/
	try {
		Double.parseDouble(str);
	}
	catch(NumberFormatException e) {
		return false;
	}
	return true;
}
public void setClose(LagerbestandBearbeitenDialogCloseHandler close) {
	this.close = close;
}
}
