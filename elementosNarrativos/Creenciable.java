package elementosNarrativos;

import java.util.Iterator;
import java.util.List;

import datos.Informacion;

public interface Creenciable {
	public List<Informacion> getCreencias();
	public Informacion dropCreencia(Informacion info);
	public void addCreencia(Informacion info);
	public Iterator<Informacion> creenciaIt();
}
