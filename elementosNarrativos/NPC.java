package elementosNarrativos;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import datos.Informacion;
import datos.Peticion;
import gestionJuego.GameManager;


public class NPC extends Agente {	
	public NPC(String nombre) {
		super(nombre);
	}
	
	//Implementa una peque�a "IA" para los NPC.
	//No pueden dejar objetos, puesto que solo pueden cojer el objeto que les interesa.
	@Override
	public boolean dameAccion() {
		System.out.println(this.nombre+ " est� en NPC-DameAccion()");
		super.dameAccion();
		
		if(this.getPeticion() != null  && this.getPeticion().getObjeto() != this.getObjetivo().getObjeto())		//En primer lugar comparamos si tenemos alguna petición que contenga el objeto que llevamos
			return GameManager.darObjeto(this);										//Si la tenemos, damos el objeto							
		else 
			this.dropPeticion();
		if (this.getObjeto() != this.getObjetivo().getObjeto() && this.getObjeto() != null) {
			return GameManager.dejarObjeto(this);
		}
		Informacion ultimaInfo = null;																//Usaremos una variable para seleccionar información relevante
		List<Lugar> opciones = new ArrayList<Lugar>();												//Y un arraylist de opciones a las que nos podemos mover
		
		if(!getYaObjetivo(1)){																	//En el caso en el que ya estamos en posesión del objeto
			System.out.println("Hola" );
			for(int index = this.getCreencias().size()-1 ; index >=0 ; index--){					//Iteramos sobre las creencias del personaje
				if (this.getCreencias().get(index).getLugar() == this.getObjetivo().getLugar()){	//Y buscamos en ellas nuestro lugar objetivo(Para saber si hemos estado)
					for(Lugar siguienteLugar: this.getLugar().getLugares()){					//Iteramos sobre las adyacencias de nuestra habitación
						for(int index2 = this.getCreencias().size()-1 ; index2 >=0 ; index2--){		//Iteramos de nuevo...
							if(this.getObjetivo().getLugar() == siguienteLugar){					//Si estamos adyacentes a nuestra habitación objetivo, nos movemos a ella
								return GameManager.relocalizar(this, siguienteLugar);
							}																			
							else if(this.getCreencias().get(index2).getLugar() == siguienteLugar)	//Si no estamos adyacentes a nuestro objetivo, buscamos si hemos estado previamente en alguna
								opciones.add(siguienteLugar);										//para aumentar las posibilidades de volver sobre nuestros pasos y encontrarnosla
						}
					}
					return relocalizador(opciones);													//Relocalizador explicado abajo
				}
			}																	//Si no hemos estado en el lugar objetivo anteriormente	
			for(Lugar siguienteLugar: this.getLugar().getLugares()) {
				opciones.add(siguienteLugar);								//Hacemos lo contrario y priorizamos las habitaciones no visitadas
				for(int index = this.getCreencias().size()-1 ; index >=0 ; index--){
					if(this.getCreencias().get(index).getLugar() == siguienteLugar)
							opciones.remove(siguienteLugar);
				}				
			}
			return relocalizador(opciones);
		}
		else {
			if(((HashSet<Objeto>)(this.getLugar().getObjeto())).contains(this.getObjetivo().getObjeto())){	//En el caso en el que no tenemos el objetyo
				return GameManager.cogerObjeto(this, this.getObjetivo().getObjeto());										//Primero miramos si esta en la habitación en la que nos encontramos
			}
			else {
				for(int index = this.getCreencias().size()-1 ; index >=0 ; index--) {
					ultimaInfo = this.getCreencias().get(index);
					if (ultimaInfo.getObjeto() == this.getObjetivo().getObjeto())
						break;
				}
				if (ultimaInfo != null && ultimaInfo.getObjeto() == this.getObjetivo().getObjeto() && ultimaInfo.getAgente() != null && ultimaInfo.getAgente() instanceof Jugador){	//Buscamos en nuestras creencias si
					for(Agente npc: this.getLugar().getAgentes()){  					//hemos visto nuestro objeto en posesión de algun otro jugador
						if (ultimaInfo.getAgente() == npc){	
																								//y comparamos los agentes de la habitación con los de las creencias
							return GameManager.pedirObjeto(npc, new Peticion((Agente)this));	//Si hay una coincidencia, le mandamos una petición
						}
					}
				}	
				for(Lugar siguienteLugar: this.getLugar().getLugares()){
					opciones.add(siguienteLugar);											//Priorizamos de nuevo las habitaciones no visitadas
					for(int index = this.getCreencias().size()-1 ; index >=0 ; index--){	//Para tener más posibilidades de encontrar el objeto
						if(this.getCreencias().get(index).getLugar() == siguienteLugar)
								opciones.remove(siguienteLugar);
					}
				}
				return relocalizador(opciones);
			}
		}
	}
	
	
	
	
	
	public boolean relocalizador(List<Lugar> opciones) {										//La función relocalizador escoje una localización aleatoria de entre las opciones
		if (opciones.size() == 0) {															//Si no hay opciones, las rellena de todas las habitaciones adyacentes y elije aleatoriamente
			if( Math.random() <= 0.20 )														//Añadimos un factor aleatorio para que pueda no hacer nada si no tiene opciones buenas
				return GameManager.pasarTurno(this);
			else {
				for(Lugar siguienteLugar: this.getLugar().getLugares()){
					opciones.add(siguienteLugar);
				}
			}
			return GameManager.relocalizar(this, opciones.get(new Random().nextInt(opciones.size())));
		}
		else {
			return GameManager.relocalizar(this, opciones.get(new Random().nextInt(opciones.size())));
		}
	}
}
