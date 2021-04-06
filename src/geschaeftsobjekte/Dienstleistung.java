package geschaeftsobjekte;

public class Dienstleistung extends Produkt {
	private String Einheit;

	public Dienstleistung(int Nummer, String Bezeichnung, double Preis, String Einheit) {
		super(Nummer, Bezeichnung, Preis);
		this.Einheit = Einheit;
		
	}
@Override
	public String getEinheit() {
		return Einheit;
	}

	public void setEinheit(String Einheit) {
		this.Einheit = Einheit;
	}

}
