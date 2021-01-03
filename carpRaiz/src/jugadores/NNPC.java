package jugadores;

import interfaces.Accionable;

//Clase padre de ambos jugadores
public abstract class NNPC implements Accionable {
	//el objeto que tiene cada persona en posesión
	//Objeto objeto = null; 
	
	//boolean de saber si han hecho algo
	//boolean accion = false;
	
	//int con el numero de la sala
	private int numSala;
	
	//nombre del personaje
	public String nombre;
	
	public NNPC(String nombre) {
		// TODO Auto-generated constructor stub
		this.nombre=nombre;
	}
	
	public void setNumSala(int numSala) {
		this.numSala = numSala;
	}
	public int getNumSala() {
		return numSala;
	}
	
	
}
