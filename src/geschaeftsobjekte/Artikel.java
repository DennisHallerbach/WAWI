package geschaeftsobjekte;

import exceptions.OutOfStockException;

public class Artikel extends Produkt {
	private int   Lagerbestand;
	private String KBezeichnung;
	public Artikel(int Nummer ,String  Bezeichnung , double Preis){
		super(Nummer, Bezeichnung, Preis);
		Lagerbestand = 0;
		KBezeichnung = erzeugeKurzbezeichnung(Nummer, Bezeichnung);
	}
	
	public int getNummer() {
		return Nummer;
	}
	public String getBezeichnung() {
		return Bezeichnung;
	}
	public String getKurzbezeichnung() {
		return KBezeichnung;
	}
	public void setBezeichnung( String Bezeichnung) {
		this.Bezeichnung = Bezeichnung;
		KBezeichnung = erzeugeKurzbezeichnung(this.getNummer(), Bezeichnung);
	}
	public int getLagerbestand() {
		return Lagerbestand;
	}
	public void einlagern(int n) {
		Lagerbestand = Lagerbestand + n ;
	}
	public void auslagern(int n) throws OutOfStockException {
		if (Lagerbestand>=n) {
			Lagerbestand-= n;
		}else {
			throw new OutOfStockException("LAgerbestand zu klein", this);
	}}
	public String toString() {
		return Nummer + ", " + KBezeichnung +", " + Bezeichnung +", " + Lagerbestand + " auf Lager";
		
	}
	

	public static String erzeugeKurzbezeichnung(int nummer, String Bezeichnung ) {
		String Hilfe = Bezeichnung.replaceAll("a|e|i|o|u|A|E|I|O|U", "");
		String neuerB = "";
		int Pruefziffer = 0;
		for (int i = 0 ; i<Hilfe.length(); i++) {
			if((Hilfe.charAt(i)>=65 && Hilfe.charAt(i) <=90)) {
				neuerB = neuerB+Character.toUpperCase(Hilfe.charAt(i));
				
			}else if (Character.isDigit(Hilfe.charAt(i))) {
				neuerB= neuerB+ Hilfe.charAt(i);
			}else if ( (Hilfe.charAt(i) >=97 && Hilfe.charAt(i) <=122)) {
				neuerB = neuerB+Character.toUpperCase(Hilfe.charAt(i));
				
			}
		}
		
		
		String tmp2 = String.format("%8.8s", neuerB);
		neuerB = tmp2.replace(" ", "");
		
		String tmp =Integer.toString(nummer);
		if ( tmp.length()> 4) {
			tmp = tmp.substring(tmp.length() -4, tmp.length());
		}else if ( tmp.length() < 4) {
			while (tmp.length()<4) {
				tmp = "0" + tmp;
			}
		}
		
		neuerB = neuerB + tmp;
		
		for (int i = 0 ; i<neuerB.length(); i++) {
			if(Character.isDigit(neuerB.charAt(i))) {
				Pruefziffer = Pruefziffer +(neuerB.charAt(i) );
				
			}else {
					Pruefziffer = Pruefziffer + (neuerB.charAt(i)-'@' );
					
			}
			
		}
		
		Pruefziffer = Pruefziffer % 16;
		neuerB =neuerB + Integer.toHexString(Pruefziffer).toUpperCase();
		
		return neuerB;
	}
	
}
