package jugadores;

public class NPC extends NNPC {

	public NPC(String nombre) {
		// TODO Auto-generated constructor stub
		super(nombre);
		}
	public int dameAccion(){
		
		//Como no hay motor de juegos, se devuelve un random de todas las acciones que hay.
		return (int)Math.random();
	}
	
	//este metodo elige una habitación a la que ir aleatoria de las contiguas
	@SuppressWarnings("unused")
	private int decidirLugar(int lugaresPosibles[]){
		return 1+1;
		//en esta línea hay que hacer referencia a la funcion del GameManager que controla los luagres adyacentes
		//return (int)Math.random(GameManager.lugaresAdyacentes(getNumSala()));
	}
}
