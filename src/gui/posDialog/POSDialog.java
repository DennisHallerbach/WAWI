package gui.posDialog;

import java.util.ArrayList;

import java.util.Collections;
import java.util.List;

import geschaeftsobjekte.Artikel;
import geschaeftsobjekte.Kunde;
import geschaeftsobjekte.Produkt;
import geschaeftsobjekte.Rechnung;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;

import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Klasse POSDialog
 * Initialisiert und zeigt einen POS-Dialog zur Auswahl der Produkte (Dienstleistungen und 
 * Artikel) an. 
 */
public class POSDialog {
	private Stage stage;
	private Rechnung R;
	 TextArea txt;
	Button check;
	ChoiceBox<String> bitte;
	ArrayList<ProduktButton> button = new ArrayList<ProduktButton>();

	

	public Rechnung getR() {
		return R;
	}

	private POSDialogCloseHandler closeHandler;
	private POSDialogKundenauswahlHandler kundenauswahlHandler;
	private POSDialogCheckoutHandler checkoutHandler;
	private POSDialogProduktButtonHandler produktButtonHandler;
	
	private List<Kunde> kunden;
	private List<Produkt> produkte;
	
	public POSDialog(List<Produkt> produkte, List<Kunde> kunden) {
		this.kunden = kunden;
		this.produkte = produkte;

		initDialog();
		
		stage.setWidth(820.);
		stage.setHeight(700.);
		stage.initModality(Modality.APPLICATION_MODAL);
	}

	/**
	 * Initialisiert den Dialog, d.h.
	 * - erzeugt die ben�tigten Panes und UI Controls
	 * - k�mmert sich um das Layout, d.h. verbindet die Panes und Controls entsprechend
	 * - installiert die von aussen (der App-Klasse) gesetzen Methoden f�r die Ereignisbehandlung
	 */
	private void initDialog() {
		Collections.sort(produkte);

		ArrayList<String> test = new ArrayList<String>();	
		
		FlowPane right = new FlowPane();
		 txt = new TextArea();
		txt.setFont(new Font("Courier New", 14));
		txt.setPrefWidth(410);
		txt.setDisable(true);
		GridPane gp = new GridPane();
		for( Kunde k : kunden) {
			test.add(k.getName());
		}
		ObservableList<String> ku = FXCollections.observableList(test);
		
		bitte = new ChoiceBox<>(ku);
		bitte.getSelectionModel().select(0);
		bitte.setPrefWidth(410);
		
		 check = new Button("CHECKOUT");
		check.setPrefWidth(410);;
		check.setDisable(true);
		for ( Produkt p : produkte) {
			ProduktButton a = new ProduktButton(p);
			button.add(a);
			a.setDisable(true);
			
			a.setOnAction(event -> {
				produktButtonHandler.handleClick(a);
				//txt.setText(R.toString());
				
			});
			right.getChildren().add(a);
		}
		right.setPadding(new Insets(0,20,50,40));
		gp.add(bitte, 0, 0);
		gp.add(txt, 0, 1);
		gp.add(check, 0, 2);
		gp.add(right, 1, 1);
		bitte.setOnAction(event ->{
			String x = bitte.getSelectionModel().getSelectedItem();
			for ( Kunde k : kunden) {
				if (k.getName().equals(x)) {
					kundenauswahlHandler.kundeAusgewaehlt(k);
					
				}
			}
		});
		check.setOnAction(event -> {
			checkoutHandler.handleCheckout();
			
		});
		
		
		Scene scene = new Scene (gp);
		this.stage = new Stage();
		stage.setTitle("Kasse");
		stage.setScene(scene);
		stage.setOnCloseRequest(e -> {
					closeHandler.handleCloseEvent();
				});
		
		
		
	}
	public void Kundenauswahl() {
		check.setDisable(false);
		txt.setDisable(false);
		bitte.setDisable(true);
		txt.setText(R.toString());
		for(ProduktButton p : button) {
			if( p.getProdukt() instanceof Artikel) {
				Artikel AR = (Artikel) p.getProdukt();
				if ( AR.getLagerbestand()>0) {
					p.setDisable(false);
				}
			}else {
				p.setDisable(false);
			}
		}
	}
	public void SetzeAUswahl() {
		bitte.getSelectionModel().select(0);
		bitte.setDisable(false);
		check.setDisable(true);
		txt.setText("");
		txt.setDisable(true);
		for(ProduktButton p : button) {
			p.setDisable(true);
		}
		
	}
	public ArrayList<ProduktButton> getButton() {
		return button;
	}

	public void setButton(ArrayList<ProduktButton> button) {
		this.button = button;
	}
	public TextArea getTxt() {
		return txt;
	}

	public Button getCheck() {
		return check;
	}

	public ChoiceBox<String> getBitte() {
		return bitte;
	}

	public void setDisableTXT() {
		txt.setDisable(true);
	}public void setDisablefalseTXT() {
		txt.setDisable(false);
	}
	public void setDisableCheck() {
		check.setDisable(true);
	}public void setDisablefalseCheck() {
		check.setDisable(false);
	}
	public void setDisablebitte() {
		bitte.setDisable(true);
	}public void setDisablefalsebitte() {
		bitte.setDisable(false);
	}
	public void setTXTR() {
		txt.setText(R.toString());
	}
	public void setTXTLeet() {
		txt.setText("");
	}
	public void setR(Rechnung R) {
		this.R = R;
	}
	public void setCloseHandler(POSDialogCloseHandler closeHandler) {
		this.closeHandler = closeHandler;
	}

	public void setKundenauswahlHandler(POSDialogKundenauswahlHandler kundenauswahlHandler) {
		this.kundenauswahlHandler = kundenauswahlHandler;
	}

	public void setCheckoutHandler(POSDialogCheckoutHandler checkoutHandler) {
		this.checkoutHandler = checkoutHandler;
	}

	public void setProduktButtonHandler(POSDialogProduktButtonHandler produktButtonHandler) {
		this.produktButtonHandler = produktButtonHandler;
	}
	
	/**
	 * Zeigt den Dialog an
	 */
	public void show() {
		stage.show();
	}

	/**
	 * Verbirgt den Dialog
	 */
	public void hide() {
		stage.hide();
	}

	/**
	 * ab hier bitte Getter und Setter f�r alle Attribute anf�gen,
	 * so dass die Ereignisbehandlung die Dialog-Elemente von aussen 
	 * nutzen kann.
	 */
	public Stage getStage() {
		return stage;
	}
}
