//TODO borrar :))))))))))))))))
package ElementosNarrativos;

import java.util.HashSet;
import java.util.Set;

public abstract class HasObjetos implements Objetable {
	private Set<Objeto> objetos = new HashSet<Objeto>();
	
	@Override
	public Objeto dropObjeto(Objeto objeto) {
			//TODO si al final las precondiciones van en Acciones, esto devuelve booleano de si lo elimino o no.
			objetos.remove(objeto);
			return objeto;
	}
}
