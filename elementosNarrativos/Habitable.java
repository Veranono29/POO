package elementosNarrativos;

import java.util.Iterator;

public interface Habitable {
	public Object getAgentes();
	public Object dropAgente(Agente persona);
	public void addAgente(Agente persona);
	public Iterator<Agente> agenteIt();
}
