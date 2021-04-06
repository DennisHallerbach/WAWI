package geschaeftsobjekte;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public abstract class Geschaeftsobjekt implements Serializable {
protected int Nummer;
public Geschaeftsobjekt(int Nummer){
	this.Nummer = Nummer;
}
public int getNummer() {
	return Nummer;
}

}
