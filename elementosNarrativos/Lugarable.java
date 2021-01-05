package elementosNarrativos;

import java.util.Iterator;

public interface Lugarable {
	public Object getLugares();
	public Object dropLugar(Lugar lugar);
	public void addLugar(Lugar lugar);
	public Iterator<Lugar> lugarIt();
}