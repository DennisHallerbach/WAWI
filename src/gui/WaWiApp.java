package gui;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import exceptions.BookingException;
import exceptions.OutOfStockException;
import geschaeftsobjekte.Artikel;
import geschaeftsobjekte.Dienstleistung;
import geschaeftsobjekte.Kunde;
import geschaeftsobjekte.Produkt;
import geschaeftsobjekte.Rechnung;

import gui.LagerbestaendeSIchtenDialog.LagerbestaendeSichtenDialog;
import gui.LagerbestaendeSIchtenDialog.LagerbestaendeSichtenDialogCloseHandler;

import gui.LoginDialog.LoginDialog;
import gui.LoginDialog.LoginHandler;
import gui.posDialog.POSDialog;
import gui.posDialog.POSDialogCheckoutHandler;
import gui.posDialog.POSDialogCloseHandler;
import gui.posDialog.POSDialogKundenauswahlHandler;
import gui.posDialog.POSDialogProduktButtonHandler;
import gui.posDialog.ProduktButton;
import javafx.application.Application;

import javafx.stage.Stage;

/**
 * Startet die Dialoge der Anwendung und definiert f�r jeden Dialog
 * die Handler-Methoden (=Lambdas) f�r die Behandlung der Ereignisse des 
 * Dialogs. 
 * Tritt ein Ereignis auf, ruft der Dialog den hier definierten 
 * Handler auf. Dadurch entsteht eine Trennung (Modularisierung) zwischen
 * den Klassen der Benutzeroberfl�che und der Ereignisbehandlung (diese Datei).
 * Vorteile sind die getrennte Testbarkeit der Ereignisbehandlung sowie 
 * der minimierte Aufwand beim Umstieg auf eine andere UI-Technologie. Letzteres
 * ergibt sich daraus, dass die gesch�ftslogik-Klassen keine Abh�ngigkeiten 
 * zum UI-Framework (hier: JavaFX) besitzen (siehe Imports in dieser Datei). 
 * 
 */
public class WaWiApp extends Application {
	/**
	 * Ben�tigte Daten: Produkte und Kunden
	 */
	private List<Produkt> produkte;
	private List<Kunde> kunden;
	
	/**
	 * Dialoge der App
	 */
	private POSDialog posDialog;
	private LoginDialog lg;
	private LagerbestaendeSichtenDialog lad;
	
	
	static public void main(String[] args) {
		launch(); // Anwendung starten
	}

	@Override
	public void start(Stage stage) throws Exception {
		produkte = initialisiereProdukte();
		kunden = initialisiereKunden();
		showLoginDialog();
		
		
		
	}

	/**
	 * Erzeugt eine Instanz von POSDialog, setzt f�r diese die ben�tigten 
	 * Ereignis-Handler und zeigt den Dialog an.
	 * Aufgabenteilung:
	 * - POSDialog zeigt den Dialog an und erlaubt Eingaben
	 * - die Reaktion auf Eingaben wird *hier* vorgenommen, 
	 *   indem entsprechende Lambda-Ausdr�cke definiert werden, 
	 *   die zu den geforderten Interfaces passen:
	 * 		- Kundenauswahl (Interface POSDialogKundenauswahlHandler)
	 * 		- Checkoutbutton geklickt (Interface POSDialogCheckoutHandler)
	 * 		- "Dialog schliessen"-Button geklickt (Interface POSDialogCloseHandler)
	 * 		- Produktbutton geklickt (Interface POSDialogProduktButtonHandler)
	 *   Die Lambdas werden hier definiert und dem POSDialog bekannt gemacht (per Setter-Aufruf)
	 */
	private void showPOSDialog() {
		this.posDialog = new POSDialog(produkte,kunden);
		POSDialogKundenauswahlHandler kundenauswahlHandler = (Kunde k) ->{
			if(!(k.getName().equals("<bitte auswaehlen>"))) {
				if(k.getName().equals("Barverkauf")) {
					posDialog.setR(new Rechnung());
				}else 
					posDialog.setR(new Rechnung(k));
			}
			/*posDialog.setDisablefalseCheck();
			posDialog.setDisablefalseTXT();
			posDialog.setDisablebitte();
			
			posDialog.setTXTR();
			
			
			for(ProduktButton p : posDialog.getButton()) {
				if( p.getProdukt() instanceof Artikel) {
					Artikel AR = (Artikel) p.getProdukt();
					if ( AR.getLagerbestand()>0) {
						p.setDisable(false);
					}
				}else {
					p.setDisable(false);
				}
			}*/
			posDialog.Kundenauswahl();
			
			};
			posDialog.setKundenauswahlHandler(kundenauswahlHandler);
		
		POSDialogProduktButtonHandler produkthandler = (ProduktButton p) ->{
			try {
				if(p.getProdukt() instanceof Dienstleistung) {
				posDialog.getR().addRechnungsposition(1, p.getProdukt());
				}else if ( posDialog.getR().getRechnungsposition(p.getProdukt()) != null) {
					int n = posDialog.getR().getRechnungsposition(p.getProdukt()).getAnzahl();
				if(p.getProdukt() instanceof Artikel) {
					
					Artikel ar = (Artikel) p.getProdukt();
					if ((ar.getLagerbestand()- n) >1 )  {
						posDialog.getR().addRechnungsposition(1, p.getProdukt());
					}
					
					else {
						posDialog.getR().addRechnungsposition(1, p.getProdukt());
						p.setDisable(true);
					}
				}}else {
					if(p.getProdukt() instanceof Artikel) {
						
						Artikel ar = (Artikel) p.getProdukt();
						if (ar.getLagerbestand()>1)  {
							posDialog.getR().addRechnungsposition(1, p.getProdukt());
						}
						
						else {
							posDialog.getR().addRechnungsposition(1, p.getProdukt());
							p.setDisable(true);
						}
					}
					
				}
				
			} catch (BookingException e) {
				e.printStackTrace();

				    
			} catch (OutOfStockException e) {
				e.printStackTrace();
				/*Button x = new Button("Ok.");
				 Stage dialogStage = new Stage();
				 posDialog.getStage().hide();
				    dialogStage.initModality(Modality.WINDOW_MODAL);

				    VBox vbox = new VBox(new Text(e.toString()), x);
				    vbox.setAlignment(Pos.CENTER);
				    vbox.setPadding(new Insets(15));
				    x.setOnAction(event-> {
				    	dialogStage.close();*/
				    	p.setDisable(true);
				  /* 	posDialog.getStage().show();
				    });
				    

				    dialogStage.setScene(new Scene(vbox));
				    dialogStage.show();*/
			}
			posDialog.setTXTR();
			
		};
		posDialog.setProduktButtonHandler(produkthandler);
		POSDialogCheckoutHandler checkouthandler = () ->{
			
			try {
				
				posDialog.getR().buchen();
				
				
			} catch (OutOfStockException e) {
				e.printStackTrace();
				
			} catch (BookingException e) {
				e.printStackTrace();
			}
			posDialog.SetzeAUswahl();
		};
		posDialog.setCheckoutHandler(checkouthandler);
	
	POSDialogCloseHandler closehandler = () ->{
		posDialog.hide();
		showLoginDialog();
	};
	posDialog.setCloseHandler(closehandler);
	posDialog.show();
	}
	public void  showLAD() {
		this.lad = new LagerbestaendeSichtenDialog(produkte);
		
		
		
		LagerbestaendeSichtenDialogCloseHandler close1 = () ->{
			showLoginDialog();
			lad.hide();
			
		};
		lad.setCloseHandler(close1);;
		lad.show();
	}
	
	public void showLoginDialog() {
		this.lg = new LoginDialog();
		LoginHandler loginhandler = (String BN, String PW) ->{
			
		if (BN.equals("Hugo")) {
			if(PW.equals("123")) {
		
			this.lg.hide();
			showPOSDialog();
			}
		}else if (BN.equals("admin") && PW.equals("123")){
			lg.Leeren();
			lg.hide();
			showLAD();
		}else {
		
			
			if(BN.isEmpty()|| !(BN.matches(".*\\w.*"))) {
		
			lg.BNisEmpty();
		}else if( PW.isEmpty()|| !(PW.matches(".*\\w.*"))) {
			lg.PWisEmpty();
		}else {
			lg.falscheKOmbi();
		}
		}
			
		};
		lg.setLfhandler(loginhandler);
		lg.show();
	}
	/**
	 * Erzeugt und initialisiert eine Kundenliste und gibt diese zur�ck
	 * @return Kundenliste
	 */
	private List<Kunde> initialisiereKunden() {
		List<Kunde> kunden = new ArrayList<>();
		
		kunden.add(new Kunde(-1, "<bitte auswaehlen>", "", ""));
		kunden.add(new Kunde(0, "Barverkauf", "", ""));
		kunden.add(new Kunde(1, "Madonna", "Sunset Boulevard 1", "Hollywood"));
		kunden.add(new Kunde(2, "Heidi Klum", "Modelwalk 13", "L.A."));				

		return kunden;
	}
	
	/**
	 * Erzeugt und initialisiert eine Produktliste und gibt diese zur�ck
	 * @return Produktliste
	 */
	private List<Produkt> initialisiereProdukte() {
		List<Produkt> produkte = new LinkedList<>();
		
		Artikel p1 = new Artikel(12345, "Arbeitsplatte A1", 89.90);
		p1.einlagern(1);
		Dienstleistung p2 = new Dienstleistung(100, "K�chenmontage", 75., "h");
		Artikel p3 = new Artikel(98989876, "Akku-Handsauger", 129.90);
		p3.einlagern(3);
		Artikel p4 = new Artikel(5261, "Spax 6x100", 3.99);
		p4.einlagern(4);
		Artikel p5 = new Artikel(4593, "Coca Cola 12x1l", 12.69);
		p5.einlagern(5);
		Artikel p6 = new Artikel(4594, "Capri-Sonne", 8.99);
		p6.einlagern(6);
		Artikel p7 = new Artikel(4595, "Jever Partyfass 5l", 8.99);
		p7.einlagern(7);
		Artikel p8 = new Artikel(12346, "Arbeitsplatte A2", 99.90);
		p8.einlagern(1);
		Artikel p9 = new Artikel(526, "Laminatbodenpack", 13.99);
		p9.einlagern(5);
		Dienstleistung p10 = new Dienstleistung(123, "Parkettmontage", 75.00, "qm");
		Dienstleistung p11 = new Dienstleistung(128, "Montage Sockelleisten", 5.59, "lfdm");

		produkte.add(p1);
		produkte.add(p2);
		produkte.add(p3);
		produkte.add(p4);
		produkte.add(p5);
		produkte.add(p6);
		produkte.add(p7);
		produkte.add(p8);
		produkte.add(p9);
		produkte.add(p10);
		produkte.add(p11);
		
		return produkte;
	}

}
