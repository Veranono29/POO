package ElementosNarrativos;

import Datos.Peticion;

public abstract class Agente extends TieneCreencia implements Accionable {
	//Nombre del Agente. Servira como ID.
	private String nombre;
	
	//Lugar donde esta.
	private Lugar lugar;
	
	//Objeto en posesion.
	private Objeto objeto;
	
	//Peticion de un objeto de parte de una persona.
	private Peticion peticion;
	
	//"Bitmap" de objetivos incumplidos. El primero sera la posesion del objeto y el segundo la ubicacion.
	private boolean[] yaObjetivos = {true,true};
	
	public Agente(String nombre) {
		this.nombre = nombre;
		
		//TODO Ver si mejor poner aca que yaObjetivos es true y true, o dejarlo como esta, ya que los array literales solo se puden usar en la declaracion.
	}
}
