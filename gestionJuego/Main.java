package gestionJuego;

import java.io.IOException;

public class Main {
	
	public static void main(String[] args) {
		GameManager manejador = new GameManager();
		manejador.main();
		/*try {
			DataManager.main(manejador);
		} catch (IOException e) {
			System.out.println("No se ha podido acceder a DataManager");
		}*/
	}
}
