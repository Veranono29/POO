package gameManager;

public class GameManager {
	//bucle es la variable con la que sabemos si han hecho algo en esta ronda
	boolean bucle=true;
	
	public GameManager() {
		// TODO Auto-generated constructor stub
	}
	//a esta funcion se le pasa un entero con el numero de sala y te dice las salas adyacentes
	//se presupone que el programador hará un while(pepe[i]!=null)
	private int[] lugaresAdyacentes(int lugOrigen){
		int[] pepe = new int[10];
		switch (lugOrigen){
			case 0: 
				pepe[0]=1;
				return pepe;
			
			case 1: 
				pepe[0]=0;
				pepe[1]=2;
				return pepe;
			
			case 2: 
				pepe[0]=1;
				pepe[1]=3;
				pepe[2]=4;
				return pepe;
			
			case 3: 
				pepe[0]=2;
				return pepe;
			
			case 4: 
				pepe[0]=2;
				return pepe;
			
		}
		
		return pepe;
	}
	/*
	//COsas que hay qeu traducir
while(bucle){
	bucle = false;
	//if(hacenCosas()&& !bucle){
		//bucle =true;
	//}
}
*/
}
