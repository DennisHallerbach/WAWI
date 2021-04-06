package gui.LagerbestaendeSIchtenDialog;



import java.util.ArrayList;

import java.util.List;


import exceptions.OutOfStockException;
import geschaeftsobjekte.Artikel;
import geschaeftsobjekte.Produkt;

import gui.LagerbestandBearbeitenDialog.LagerbestandBearbeitenDialog;
import gui.LagerbestandBearbeitenDialog.LagerbestandBearbeitenDialogCloseHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.scene.Node;
import javafx.scene.Scene;

import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

import javafx.stage.Stage;

public class LagerbestaendeSichtenDialog {
	private Stage stage;
	private TableView<Artikel> tableView;
	private LagerbestandBearbeitenDialog dialog;
	private LagerbestaendeSichtenDialogCloseHandler closeHandler;

	Artikel AR ;

	public LagerbestaendeSichtenDialog(List<Produkt> produkte) {
		iniDialog(produkte);
        stage.setWidth(600.);
        
		
	}
	public void iniDialog(List<Produkt> produkte) {
		List<Artikel> tmp = new ArrayList <Artikel>();
		for(Produkt p : produkte) {
			if ( p instanceof Artikel) {
				Artikel a = (Artikel) p;
				tmp.add(a);
			}
		}
		
		List<Artikel> artikel = tmp; // bitte anpassen!
		Node tabelle = initTabelle(artikel);
		BorderPane f = new BorderPane();
		f.setTop(tabelle);
		Scene scene = new Scene (f);
		this.stage = new Stage();
		stage.setTitle("Lagerbestände verwalten");
		stage.setScene(scene);
		
		//stage.initModality(Modality.APPLICATION_MODAL);
        stage.setWidth(600.);
        stage.setOnCloseRequest(e -> {
			
			closeHandler.handleCloseEvent();
		});
	}
	
	public void setCloseHandler(LagerbestaendeSichtenDialogCloseHandler handler) {
		this.closeHandler = handler;
	}
	
	public void show() {
		stage.showAndWait();
	}
	
	public void hide() {
		stage.hide();
	}
	
	@SuppressWarnings("unchecked")
	/**
	 * Erzeugt eine TableView und f�llt sie mit Daten aus der �bergebenen Artikel-Liste.
	 * Setzt einen Handler f�r das Doppelklick-Event auf einer Zeile
	 * 
	 * @param artikel Anzuzeigende Artikel-Liste
	 * @return Erzeugte und bef�llte TableView
	 */
	private Node initTabelle(List<Artikel> artikel) {
		/*
		 * Artikel-Daten in observable array list setzen
		 */
		ObservableList<Artikel> liste = FXCollections.observableArrayList(artikel);
		
		/*
		 * TableView erzeugen und Daten setzen
		 */
		tableView = new TableView<>(liste);

		/*
		 * Spalten definieren und der tableView bekannt machen
		 */
        TableColumn<Artikel, Integer> nummerColumn = new TableColumn<>("Nummer");
        nummerColumn.setCellValueFactory(new PropertyValueFactory<Artikel, Integer>("nummer"));
        TableColumn<Artikel, String> bezeichnungColumn = new TableColumn<>("Bezeichnung");
		bezeichnungColumn.setCellValueFactory(new PropertyValueFactory<Artikel, String>("bezeichnung"));
		TableColumn<Artikel, String> kurzbezeichnungColumn = new TableColumn<>("Kurzbezeichnung");
		kurzbezeichnungColumn.setCellValueFactory(new PropertyValueFactory<Artikel, String>("kurzbezeichnung"));
		TableColumn<Artikel, Integer> lagerbestandColumn = new TableColumn<>("Lagerbestand");
		lagerbestandColumn.setCellValueFactory(new PropertyValueFactory<Artikel, Integer>("lagerbestand"));
		tableView.getColumns().addAll(nummerColumn, bezeichnungColumn, kurzbezeichnungColumn, lagerbestandColumn);
        tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        /**
         * Handler f�r Doppelklick-Event auf Tabellenzeile
         * Hier sollte showLagerbestandBearbeitenDialog() aufgerufen werden
         */
        tableView.setRowFactory( tv -> {
            TableRow<Artikel> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2) {
                 
                	showLagerbestandBearbeitenDialog(tableView.getSelectionModel().getSelectedItems().get(0));
                	
                	// hier bitte eigenen Code einf�gen
            		
                }
            });
            return row ;
        });
		return tableView;
	}

	/**
	 * Ruft den Lagerstand-Bearbeiten-Dialog auf.
	 * Zuvor wird ein Close-Handler definiert, der den neuen Lagerstand im Artikel-Array setzt
	 * und die Tabelle aktualisiert (hierf�r tableView.refresh() aufrufen)
	 *
	 * @param selektierterArtikel Artikel, dessen Lagerbestand aktualisiert werden soll
	 */
	private void showLagerbestandBearbeitenDialog(Artikel selektierterArtikel) {
		this.dialog = new LagerbestandBearbeitenDialog(selektierterArtikel);
		LagerbestandBearbeitenDialogCloseHandler close = (int Lagerbestand) ->{
			AR = selektierterArtikel;
			Laberbestandändern(Lagerbestand);
			
			tableView.refresh();
			
			
			
			
		};
		
		dialog.setClose(close);
		dialog.show();
	}
	
	public void Laberbestandändern ( int lg) {
		if ( AR.getLagerbestand() <= lg) {
			AR.einlagern(lg-this.AR.getLagerbestand());
		}else {
			try {
				AR.auslagern(this.AR.getLagerbestand()-lg);
			} catch (OutOfStockException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	

	public TableView<Artikel> getTableView() {
		return tableView;
	}

	public Stage getStage() {
		return stage;
	}
}
