package geschaeftsobjekte;

import java.util.ArrayList;
import java.util.List;

import exceptions.BookingException;
import exceptions.OutOfStockException;

public class Rechnung extends Geschaeftsobjekt {
private static  int nummer = 0;
private Kunde kunde;
private Rechnungsstatus zustand;
private List<Rechnungsposition> position = new ArrayList<Rechnungsposition>();
	public Rechnung() {
		super(nummer ++);
		zustand = Rechnungsstatus.IN_ERSTELLUNG;
		
	}
	public Rechnung (Kunde kunde){
		super(nummer++);
		this.kunde = kunde;
		zustand = Rechnungsstatus.IN_ERSTELLUNG;
	}
public int getNummer() {
		return nummer;
	}
	public Kunde getKunde() {
		return kunde;
	}
	public Rechnungsstatus getZustand() {
		return zustand;
	}
	public List<Rechnungsposition> getPosition() {
		return position;
	}
public Rechnungsstatus getzustand() {
	return zustand;
}
public Rechnungsposition addRechnungsposition(int anzahl, Produkt p) throws BookingException, OutOfStockException  {
	
	Rechnungsposition a = new Rechnungsposition(anzahl, p);
	if( zustand != Rechnungsstatus.IN_ERSTELLUNG) {
		throw new BookingException("Rechnung ist nicht in Erstellung");
		
	}
	for(Rechnungsposition b : position) {
		if ( b.getProdukt().equals(a.getProdukt()) && b.getProdukt() instanceof Artikel) {
			Artikel ar = (Artikel) a.getProdukt();
			
			if (ar.getLagerbestand() < (b.getAnzahl()+a.getAnzahl())) {
				throw new OutOfStockException("Lagerbestand zu klein", ar);
			}else {
			
				b.setAnzahl(b.getAnzahl() + a.getAnzahl());
				return b;
			}
		}
		if ( b.getProdukt().equals(a.getProdukt()) && b.getProdukt() instanceof Dienstleistung) {
			b.setAnzahl(b.getAnzahl() + a.getAnzahl());
			return b;
		}
	}
	if ( a.getProdukt() instanceof Artikel ) {
		Artikel br = (Artikel) a.getProdukt();
		if ( br.getLagerbestand() < a.getAnzahl()) {
			return null;
		}
	}
position.add(a);
return a;
}

public Rechnungsposition getRechnungsposition(Produkt p) {
	for( Rechnungsposition b : position) {
		if ( b.getProdukt().equals(p)) {
			return b;
		}
	}
	return null;
}
public int getAnzahlRechnungspositionen () {
	return position.size();
}
public List<Rechnungsposition> getRechnungspositionen(){
	return position;
}
public void buchen() throws OutOfStockException, BookingException {
	if ( zustand == Rechnungsstatus.IN_ERSTELLUNG) {
	for ( Rechnungsposition a : position) {
		if( a.getProdukt() instanceof Artikel) {
			Artikel b = (Artikel) a.getProdukt();
			b.auslagern(a.getAnzahl());
		}
	}
	}else {
		throw new BookingException("Rechnung nicht in Erstellung");
	}
	
	zustand = Rechnungsstatus.GEBUCHT;
	
}
public double getGesamtpreis() {
	double endpreis = 0;
	for ( Rechnungsposition p : position) {
		endpreis = endpreis + p.getPreis();
	}
	return endpreis;
}
@Override
public String toString() {
	StringBuilder sb = new StringBuilder();

	if(kunde != null ) {
	
	String b = "Rechnung: " + nummer;
	String c = "Kunde: " + kunde.getNummer();
	String d = kunde.getName();
	String e = kunde.getStrasse();
	String f = kunde.getOrt();
	String g1 = String.format( "%.2f", getGesamtpreis());
	String g = String.format("%34s", g1);
	sb.append(b + "\n");
	sb.append(c + "\n");
	sb.append(d + "\n");
	sb.append(e + "\n");
	sb.append(f + "\n" + "\n");
	for (Rechnungsposition p : this.getRechnungspositionen()) {
		sb.append( p.toString());
		sb.append("\n");
	}
	
	sb.append(  "---------------------------------------" + "\n");
	sb.append(g);
	sb.append(" EURO");
	}
	 else {
		String b = "Rechnung: " + nummer;
		String c = "Barverkauf";
		String g1 = String.format( "%.2f", getGesamtpreis());
		String g = String.format("%34s", g1);
		sb.append(b + "\n");
		sb.append(c + "\n" + "\n");
		
		
		for (Rechnungsposition p : position) {
			sb.append( p.toString() + "\n");
			
		}
		
		sb.append(  "---------------------------------------" + "\n");
		sb.append(g);
		sb.append(" EURO");
	}
	return sb.toString();
	
}
public Rechnungsstatus getRechnungsstatus() {
	// TODO Auto-generated method stub
	return zustand;
}
}
