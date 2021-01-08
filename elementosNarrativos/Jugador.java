package elementosNarrativos;

public class Jugador extends Agente {
	//TODO El jugardor tambien tiene nombre. El game manager debera pedirlo de antes de ponerse a crear los Agentes, asique lo pedira antes de iniciar partida en pantalla emergente.
	public Jugador(String nombre) {
		super(nombre);
	}
	
	@Override
	public boolean dameAccion() {
		// TODO dameAccion de jugador jugable
		//if relocalizar
			return gestionJuego.GameManager.log(this, null, this.getLugar());
			Acciones.relocalizar(this, /*LO QUE DIGA EL BOTON*/);
		//if cojemos objeto
			Acciones.cogerObjeto(this, this.getLugar().getObjeto());
			return gestionJuego.GameManager.log(this, this.getObjeto(), this.getLugar());
		//if pedimos objeto
			Acciones.pedirObjeto(/*LO QUE DIGA EL BOTON*/, new Peticion(this, this.getObjetivo().getObjeto()))
			return gestionJuego.GameManager.log(this.getPeticion().getAgente(), this.getPeticion().getObjeto(), this.getPeticion().getLugar()); 
		// if tenemos peticion
			Acciones.darObjeto(this);
			return gestionJuego.GameManager.log(this.getPeticion().getAgente(), this.getPeticion().getObjeto(), this.getPeticion().getLugar());
		//if nada
			return gestionJuego.GameManager.log(this);
	}
}