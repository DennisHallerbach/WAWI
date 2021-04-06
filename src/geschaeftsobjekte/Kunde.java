package geschaeftsobjekte;

public class Kunde extends Geschaeftsobjekt {
	protected String Name;
	protected String Straße , Ort;

	public Kunde(int Nummer , String Name, String Straße , String Ort) {
		super(Nummer);
		this.Name = Name;
		this.Straße = Straße;
		this.Ort = Ort;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getStrasse() {
		return Straße;
	}

	public void setStrasse(String straße) {
		Straße = straße;
	}

	public String getOrt() {
		return Ort;
	}

	public void setOrt(String ort) {
		Ort = ort;
	}

}
