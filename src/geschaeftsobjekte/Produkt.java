package geschaeftsobjekte;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public abstract class Produkt extends Geschaeftsobjekt implements Comparable<Produkt> {
protected String Bezeichnung ;
protected  double Preis;
	 public Produkt(int Nummer, String Bezeichnung , double Preis) {
		super(Nummer);
		this.Bezeichnung = Bezeichnung;
		this.Preis = Preis;
		
	}
	 
	 
	
	public int getNummer() {
		return Nummer;
	}
@Override
public boolean equals (Object ojc) {
	if ( ojc instanceof Artikel && this instanceof Artikel) {
		Artikel a = (Artikel) ojc;
		if ( a.getNummer() == this.getNummer()) {
			return true;
		}}
		if ( ojc instanceof Dienstleistung && this instanceof Dienstleistung) {
			Dienstleistung d = (Dienstleistung) ojc;
			if ( d.getNummer() == this.getNummer()) {
				return true;
			}}
		return false;
	}
	

public String getBezeichnung() {
	return Bezeichnung;
}


public void setBezeichnung(String bezeichnung) {
	Bezeichnung = bezeichnung;
}


public double getPreis() {
	return Preis;
}


public void setPreis(double preis) {
	Preis = preis;
}
public String getEinheit() {
	return "  ";
}


@Override
/*public int compareTo(Produkt produkt) {
	if ( this instanceof Dienstleistung && produkt instanceof Artikel )  {
	return -1;
	}
	if ( this instanceof Artikel && produkt instanceof Dienstleistung )  {
		return 1;
		}
	if ((this instanceof Artikel && produkt instanceof Artikel) || ( this instanceof Dienstleistung && produkt instanceof Dienstleistung) ) {
		if( this.getBezeichnung() == produkt.getBezeichnung()) {
			return (int) (this.getPreis() - produkt.getPreis());
		}else if (this.getBezeichnung() == null && produkt.getBezeichnung() != null) {
			return -1;
		} else if (this.getBezeichnung() != null && produkt.getBezeichnung() == null) {
			return 1;
		}else if (this.getBezeichnung() == null && produkt.getBezeichnung() == null) {
			return (int) (this.getPreis() - produkt.getPreis());
		}
	}

return 0;
}*/
public int compareTo(Produkt produkt) {
	if ( this instanceof Dienstleistung && produkt instanceof Artikel )  {
	return -1;
	}
	if ( this instanceof Artikel && produkt instanceof Dienstleistung )  {
		return 1;
		}
	if ((this instanceof Artikel && produkt instanceof Artikel) || ( this instanceof Dienstleistung && produkt instanceof Dienstleistung) ) {
		if( this.getBezeichnung() == produkt.getBezeichnung()) {
			return (int) (this.getPreis() - produkt.getPreis());
		}else if (this.getBezeichnung() == null && produkt.getBezeichnung() != null) {
			return -1;
		} else if (this.getBezeichnung() != null && produkt.getBezeichnung() == null) {
			return 1;
		}else if (this.getBezeichnung() == null && produkt.getBezeichnung() == null) {
			return (int) (this.getPreis() - produkt.getPreis());
		}else if (this.getBezeichnung() != produkt.getBezeichnung()) {
			return this.getBezeichnung().compareTo(produkt.getBezeichnung());
		}
	}

return 0;
}
public static List<Produkt> loadProducts(String SERIALIZATION_PATH){ List<Produkt> produkte = new LinkedList<>();
try {
                     // oeffnet die Datei "data/Produkt.ser" zum lesen
InputStream ins = new FileInputStream(SERIALIZATION_PATH); ObjectInputStream objin = new ObjectInputStream(ins);
// als erstes Objekt wird ein Integer-Objekt gelesen,
// das angibt, wie viele Produkt-Objekte kommen werden
int i=objin.readInt(); 
int z=0;
while(z++ < i){

// lese das nächste Produkt-Objekt aus der Datei
Produkt p = (Produkt) objin.readObject();
produkte.add(p);  // und füge es der Liste hinzu
}                     // die Datei muss wieder geschlossen/freigegeben werden
objin.close();
ins.close();
} catch (Exception e) {
e.printStackTrace(); // im Fehlerfall Fehlermeldung ausgeben return produkte; // Liste der Produkte zurueckgeben
}
return produkte;
}
}

