package jugadores;

//Clase padre de ambos jugadores
public abstract class NNPC {
	//el objeto que tiene cada persona en posesión
	//Cosa objeto = null; 
	
	//boolean de saber si han hecho algo
	//boolean accion = false;
	
	//int con el numero de la sala
	private int numSala;
	
	public NNPC() {
		// TODO Auto-generated constructor stub
	}
	
	public void setNumSala(int numSala) {
		this.numSala = numSala;
	}
	public int getNumSala() {
		return numSala;
	}
	public abstract int proximaAccion();
	
}
