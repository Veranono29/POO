package ElementosNarrativos;

import java.util.List;

import Datos.Informacion;

public interface Creenciable {
	public List<Informacion> getCreencias();
	public Informacion dropCreencia(Informacion info);
	public void addCreencia(Informacion info);
}
