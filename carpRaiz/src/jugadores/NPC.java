package jugadores;

public class NPC extends NNPC {

	public NPC() {
		// TODO Auto-generated constructor stub
	}
	public int proximaAccion(){
		
		//Como no hay motor de juegos, se devuelve un random de todas las acciones que hay.
		return (int)Math.random();
	}
	
	//este es un metodo para elegir un lugar al que ir random de los posibles
	@SuppressWarnings("unused")
	private int decidirLugar(int lugaresPosibles[]){
		return 1+1;
		//en esta línea hay que hacer referencia a la funcion del GameManager que controla los luagres adyacentes
		//return (int)Math.random(GameManager.lugaresAdyacentes(getNumSala()));
	}
}
