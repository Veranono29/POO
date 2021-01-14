package elementosNarrativos;

import java.util.List;

import datos.Informacion;

public interface Creenciable {
	public List<Informacion> getCreencias();
	public Informacion dropCreencia(Informacion info);
	public void addCreencia(Informacion info);
	public void addVariasCreencias(List<Informacion> list);
}
