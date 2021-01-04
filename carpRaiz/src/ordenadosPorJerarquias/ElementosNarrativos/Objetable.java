package ElementosNarrativos;

public interface Objetable {	
	//TODO a ver, en estas interfaces tengo el problema de que se "repetiria" el codigo en Agente, pero realemente no se repite. Pero un poco si. Ponemos lo de Agente a Set de cosas y que implemente Lugarable y Objetable?
	public Object getObjetos();
	public Objeto dropObjeto(Objeto objeto);
	public void addObjeto(Objeto objeto);
}
