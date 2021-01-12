package datos;

import elementosNarrativos.Lugar;
import elementosNarrativos.Objeto;

public class Objetivo extends Informacion {
	public Objetivo(Objeto objeto, Lugar lugar) {
		super(null, objeto, lugar);
	}
	
	@Override
	public String toString() {
		String texto = "";
		if(objeto != null && lugar != null)
			texto = "Tener " + objeto.getNombre() + " y estar en " + lugar.getNombre();
		else if(objeto == null)
			texto = "Estar en " + lugar.getNombre();
		//Realmente nunca va a llegar aca sin tener lugar == null, pero mira, es un if, no me voy a morir por ponerlo no vaya a ser.
		else if(lugar == null)
			texto = "Tener " + objeto.getNombre();
			
		return "Objetivo: " + texto;
	}
}
