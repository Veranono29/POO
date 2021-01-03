package jugadores;

public class FabricaPersonajes {

	public FabricaPersonajes() {
				
	}
	
	public NNPC getPersonaje(String tipo,String nombre){
		NNPC tempo = null;
		switch (tipo){
			case "jugador":
				 tempo = getJugador(nombre);
				 break;
			case "npc":
				tempo = getNPC(nombre);
				break;
			default:
				break;
		}
		return tempo;
		
		/*
		switch (tipo){
			case "jugador":
				// tempo = getJugador(nombre);
				 return (NNPC)getJugador(nombre);
			case "npc":
				//tempo = getNPC(nombre);
				return (NNPC)getNPC(nombre);
			default:
				break;
		}*/
		
		
	}
	private Jugador getJugador(String nombre){
	Jugador ju = new Jugador(nombre);
		return ju;
	}
	
	private NPC getNPC(String nombre){
		NPC npc = new NPC(nombre);
		return npc;
	}
	
}
