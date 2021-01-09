package elementosNarrativos;

public class Jugador extends Agente {
	//TODO El jugardor tambien tiene nombre. El game manager debera pedirlo de antes de ponerse a crear los Agentes, asique lo pedira antes de iniciar partida en pantalla emergente.
	public Jugador(String nombre) {
		super(nombre);
	}
	
	@Override
	public boolean dameAccion() {
		// TODO dameAccion de jugador jugable
		return false;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
