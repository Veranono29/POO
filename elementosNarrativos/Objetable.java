package elementosNarrativos;

public interface Objetable {	
	//TODO hace falta meterle abstracto, o si no se pone default se da por hecho que es abstracto.
	public Object getObjeto();
	
	//TODO como hago para que me deje cambiar los parametros en las distintas implementaciones?
	public Objeto dropObjeto(Objeto objeto);
	public void addObjeto(Objeto objeto);
}
