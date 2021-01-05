package elementosNarrativos;

//Extiende la clase abstracta intermedia TieneCreencia en vez de haber tenido otra de Lugarable, Objetable o Habitable respectivamente  ya que consideramos importante que extienda de TieneCreencia
public class Lugar extends ManejaDatos {
	
	public Lugar(String nombre) {
		super(nombre);
	}
}
