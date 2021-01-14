package gestionJuego;

public class FormatoIncorrecto extends Exception{
	private String errorMensaje;
	public FormatoIncorrecto(String error){
		errorMensaje = error;
	}
	protected String getMensaje() {
		return errorMensaje;
	}
}
