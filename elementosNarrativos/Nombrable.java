package elementosNarrativos;

public interface Nombrable {
	//getNombre devuelve lo mismo que toString, pero se implementan ambos para mayor comodidad al usarlo.
	//TODO como hago un default si necesito 
	public String getNombre();
	
	//TODO este no funciona.
	@Override
	public String toString();
	
	public boolean siSoy(String nombre);
}
