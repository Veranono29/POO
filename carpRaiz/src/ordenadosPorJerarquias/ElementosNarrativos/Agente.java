package ElementosNarrativos;

import Datos.Informacion;
import Datos.Peticion;

public abstract class Agente extends TieneCreencia implements Accionable {
	//Nombre del Agente. Servira como ID.
	private String nombre;
	
	//Tiempo de corte de volcado de creencias
	private int tiempoAnterior;
	//Lugar donde esta.
	private Lugar lugar;
	
	//Objeto en posesion.
	private Objeto objeto;
	
	//Peticion de un objeto de parte de una persona.
	private Peticion peticion;
	
	//Informacion sobre sus objetivos. No necesita getter ya que no lo va a necesitar otra clase diferente. Si necesita setter ya que la clase en si se instancia solo con el String nombre. El resto de informacion se le añade despues.
	//TODO si se puede añadir directamente mejor, quitamos muchos setters.
	//TODO si veis que un getter o setter no se va a usar, quitadlo, siempre que no sea uno que si podriamos usar pero que no lo usamos.
	private Informacion objetivo;
	
	//"Bitmap" de objetivos incumplidos. El primero sera la ubicacion y el segundo la posesion del objeto.
	private boolean[] yaObjetivos = {true,true};
	
	public Agente(String nombre) {
		this.nombre = nombre;
		
		//TODO Ver si mejor poner aca que yaObjetivos es true y true, o dejarlo como esta, ya que los array literales solo se puden usar en la declaracion.
	}
	
	public String getNombre() {
		return nombre;
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

	public Peticion getPeticion() {
		return peticion;
	}

	public void setPeticion(Peticion peticion) {
		this.peticion = peticion;
	}

	public void dropPeticion() {
		//TODO esto deberia ser this.peticion = this.setPeticion(null);?
		this.peticion = null;
	}
	
	public boolean getYaObjetivos(int index) {
		return yaObjetivos[index];
	}
	
	//TODO sospecho que no lo vamos a usar. Esta pa simplificarnos la vida pero si no se usa borrarlo. No se porque lo digo en plural... Raúl, si no lo usas para el dameAccion, borralo. Tambien cambiale el nombre al metodo quitandole la s del final
	public boolean[] getYaObjetivos() {
		return yaObjetivos;
	}
	
	public void setYaObjetivos(int index,boolean ya) {
		this.yaObjetivos[index] = ya;
	}
	
	//TODO sospecho que no lo vamos a usar. Esta pa simplificarnos la vida pero si no se usa borrarlo. No se porque lo digo en plural... Raúl, si no lo usas para el dameAccion, borralo. Tambien cambiale el nombre al metodo quitandole la s del final
	public void setYaObjetivos(boolean[] yaObjetivos) {
		this.yaObjetivos = yaObjetivos;
	}

	public Informacion getObjetivo() {
		return objetivo;
	}
	
	public void setObjetivo(Informacion objetivos) {
		this.objetivo = objetivos;
	}
}
