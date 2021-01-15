package elementosNarrativos;

import java.util.ArrayList;

import datos.Informacion;
import gestionJuego.GameManager;

public abstract class Agente extends TieneCreencia implements Accionable {
	//Tiempo de corte de volcado de creencias
	private int tiempoAnterior;
	//Lugar donde esta.
	private Lugar lugar;
	
	//Objeto en posesion.
	private Objeto objeto;
	
	//Peticion de un objeto de parte de una persona.
	private Informacion peticion;
	
	private Informacion objetivo;
	
	//"Bitmap" de objetivos incumplidos. El primero sera la ubicacion y el segundo la posesion del objeto.
	private boolean[] yaObjetivos = {true, true};
	
	public Agente(String nombre) {
		super(nombre);
		creencias = new ArrayList<Informacion>();
		objetivo = null;
	}
	
	@Override
	public boolean dameAccion() {
		GameManager.conseguirCreencias(this);
		System.out.println(this.getNombre()+"Ha llegado al if() maldito");
		
		if(GameManager.getJugador().getNombre().equals(this.nombre)) { 
			System.out.println("El juagdor llego que "+this.getNombre());
			GameManager.getInterfaz().setRonda();
	
		}
	
		return false;
	}
	
	@Override
	public boolean siSoy(String nombre) {
		return (this.nombre.equals(nombre));
	}
	
	public int getTiempoAnterior() {
		return tiempoAnterior;
	}

	public void setTiempoAnterior(int tiempoAnterior) {
		this.tiempoAnterior = tiempoAnterior;
	}

	public Lugar getLugar() {
		return lugar;
	}

	public void setLugar(Lugar lugar) {
		this.lugar = lugar;
	}

	public Objeto getObjeto() {
		return objeto;
	}

	@Override
	public void addObjeto(Objeto objeto) {
		this.objeto = objeto;
	}
	
	public Objeto dropObjeto() {
		Objeto objeto = this.objeto;
		this.objeto = null;
		return objeto;
	}

	public Objeto dropObjeto(Objeto objeto) {
		if(this.objeto == objeto) {
			this.objeto = null;
			return objeto;
		}
		return null;
	}
	
	public Informacion getPeticion() {
		return peticion;
	}

	public void setPeticion(Informacion peticion) {
		this.peticion = peticion;
	}

	public void dropPeticion() {
		this.peticion = null;
	}
	
	public boolean getYaObjetivo(int index) {
		return yaObjetivos[index];
	}
	
	public void setYaObjetivo(int index,boolean ya) {
		this.yaObjetivos[index] = ya;
	}

	public Informacion getObjetivo() {
		return objetivo;
	}
	
	public void setObjetivo(Informacion objetivo) {
		if(this.objetivo == null)
			this.objetivo = objetivo;
	}
	
	//Comprobar persona comprueba si el agente ya ha cumplido sus objetivos en la partida.
	//True significa que esta incumplido, y false que esta cumplido, como persona.yaObjetivos. 
	//Aplicable tambien a compLugar y compObjeto.
	/*public boolean compPersona() {
		if( (yaObjetivos[0] && compLugar()) || (yaObjetivos[1] && compObjeto()) ) {
			return true;
		}
		return false;
	}*/
	public boolean compPersona() {
		if(compLugar() || compObjeto()) {
			return true;
		}
		return false;
	}
	private boolean compLugar() {
		if(lugar == objetivo.getLugar() || objetivo.getLugar() == null) {
			yaObjetivos[0] = false;
		}
		else
			yaObjetivos[0] = true;
		return yaObjetivos[0];
	}
	private boolean compObjeto() {
		if(objeto == objetivo.getObjeto() || objetivo.getObjeto() == null) {
			yaObjetivos[1] = false;
		}
		else
			yaObjetivos[1] = true;
		return yaObjetivos[1];
	}
}
