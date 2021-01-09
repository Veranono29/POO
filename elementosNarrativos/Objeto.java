package elementosNarrativos;

public class Objeto implements Nombrable {
	//Nombre del Objeto. Sirve de ID.
	private String nombre;
	
	public Objeto(String nombre) {
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}
	
	@Override
	public String toString() {
		return nombre;
	}

	@Override
	public boolean siSoy(String nombre) {
		return (this.nombre == nombre);
	}
}
