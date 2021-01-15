package gestionJuego;

public class Main {
	
	static GameManager manejador;
	
	public static void main(String[] args) {
		manejador = new GameManager();
		manejador.main();
		
	}
	
	protected static void datos(){
		try {
			DataManager.main(manejador);
		} catch (Exception e) {
			System.out.println("No se ha podido acceder a DataManager");
		}
	}
}
