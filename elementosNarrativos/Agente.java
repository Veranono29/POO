package elementosNarrativos;

import datos.Informacion;

public abstract class Agente extends TieneCreencia implements Accionable {
	//Tiempo de corte de volcado de creencias
	private int tiempoAnterior;
	//Lugar donde esta.
	private Lugar lugar;
	
	//Objeto en posesion.
	private Objeto objeto;
	
	//Peticion de un objeto de parte de una persona.
	private Informacion peticion;
	
	//Informacion sobre sus objetivos. No necesita getter ya que no lo va a necesitar otra clase diferente. Si necesita setter ya que la clase en si se instancia solo con el String nombre. El resto de informacion se le añade despues.
	//TODO si se puede añadir directamente mejor, quitamos muchos setters.
	//TODO si veis que un getter o setter no se va a usar, quitadlo, siempre que no sea uno que si podriamos usar pero que no lo usamos.
	private Informacion objetivo;
	
	//"Bitmap" de objetivos incumplidos. El primero sera la ubicacion y el segundo la posesion del objeto.
	private boolean[] yaObjetivos = {true,true};
	
	public Agente(String nombre) {
		super(nombre);
		
		//TODO Ver si mejor poner aca que yaObjetivos es true y true, o dejarlo como esta, ya que los array literales solo se puden usar en la declaracion.
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

	//TODO Elimine getObjeto. No se usa.
	//TODO No elimineis getObjeto. Si se usa.
	public Objeto getObjeto() {
		return objeto;
	}

	public void setObjeto(Objeto objeto) {
		this.objeto = objeto;
	}
	
	public Objeto dropObjeto() {
		Objeto objeto = this.objeto;
		//TODO Lo mismo que en dropPeticion, esto deberia ser setObjeto(null);?
		this.objeto = null;
		return objeto;
	}

	public Informacion getPeticion() {
		return peticion;
	}

	public void setPeticion(Informacion peticion) {
		this.peticion = peticion;
	}

	public void dropPeticion() {
		//TODO esto deberia ser this.peticion = this.setPeticion(null);?
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
	
	public void setObjetivo(Informacion objetivos) {
		this.objetivo = objetivos;
	}
}
