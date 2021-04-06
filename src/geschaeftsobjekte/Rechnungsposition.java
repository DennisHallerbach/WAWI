package geschaeftsobjekte;

public class Rechnungsposition {
private int Anzahl;
private Produkt produkt;
public Rechnungsposition ( int Anzahl, Produkt produkt){
	this.Anzahl = Anzahl;
	this.produkt = produkt;
}
public void setAnzahl( int Anzahl) {
	this.Anzahl = Anzahl;
}
public int getAnzahl() {
	return Anzahl;
}
public double getPreis() {
	return produkt.getPreis() * Anzahl;
}
public Produkt getProdukt() {
	return produkt;
}
@Override
public String toString() {
	StringBuilder sb = new StringBuilder();
	//if(produkt.getBezeichnung().length()>34) {
	String a = String.format("%1.34s", produkt.getBezeichnung());
	sb.append(a);
	//}
	//else {
	//	sb.append(produkt.getBezeichnung());
	//}
	sb.append("\n"+ " ");
	sb.append(String.format("%3d", this.Anzahl) + " ");
	if( produkt.getEinheit() != null) {
	String b = String.format("%-3.3s", produkt.getEinheit()) ;
	sb.append(b + " ");
	
	}else {
		sb.append(String.format("%-3.3s", "") + " ");
		
	}
	sb.append( "x ");
	String d = String.format( "%8.2f", produkt.getPreis());
	sb.append(d + " = ");
	String e = String.format("%12.2f", ( produkt.getPreis() * this.Anzahl));
	sb.append(e);
	sb.append( " EURO");
	return sb.toString();
}
}
