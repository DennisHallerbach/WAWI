package exceptions;

import geschaeftsobjekte.Produkt;

public class OutOfStockException extends Exception {
	private Produkt p;
	public OutOfStockException ( String txt , Produkt p) {
		super(txt);
		this.p = p;
	}
public Produkt getP() {
	return p;
}
}
