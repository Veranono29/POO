package gestionJuego;

public class Main {
	
	public static void main() {
		GameManager manejador = new GameManager();
		manejador.main();
		DataManager.main(manejador);
	}
}
