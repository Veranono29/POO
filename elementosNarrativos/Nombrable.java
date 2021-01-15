package elementosNarrativos;

public interface Nombrable {
	//getNombre devuelve lo mismo que toString, pero se implementan ambos para mayor comodidad al usarlo.

	public String getNombre();
	

	@Override
	public String toString();
	
	public boolean siSoy(String nombre);
}
