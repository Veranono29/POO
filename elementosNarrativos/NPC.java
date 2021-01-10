package elementosNarrativos;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import datos.Informacion;
import datos.Peticion;


public class NPC extends Agente {	
	public NPC(String nombre) {
		super(nombre);
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean dameAccion() {
		if(this.getPeticion().getObjeto() == this.getObjeto()) {		//En primer lugar comparamos si tenemos alguna petición que contenga el objeto que llevamos
			Acciones.darObjeto(this);									//Si la tenemos, damos el objeto							
			return gestionJuego.GameManager.log(this.getPeticion().getAgente(), this.getPeticion().getObjeto(), this.getPeticion().getLugar());		
		}
		
		Informacion ultimaInfo = null;																//Usaremos una variable para seleccionar información relevante
		List<Lugar> opciones = new ArrayList<Lugar>();												//Y un arraylist de opciones a las que nos podemos mover
		
		if(getYaObjetivo(0)) {																	//En el caso en el que ya estamos en posesión del objeto
			
			for(int index = this.getCreencias().size()-1 ; index >=0 ; index--){					//Iteramos sobre las creencias del personaje
				if (this.getCreencias().get(index).getLugar() == this.getObjetivo().getLugar()){	//Y buscamos en ellas nuestro lugar objetivo(Para saber si hemos estado)
					for(Lugar siguienteLugar: (Iterable<Lugar>) () -> this.getLugar().lugarIt()){	//Iteramos sobre las adyacencias de nuestra habitación
						for(int index2 = this.getCreencias().size()-1 ; index2 >=0 ; index2--){		//Iteramos de nuevo...
							if(this.getObjetivo().getLugar() == siguienteLugar){					//Si estamos adyacentes a nuestra habitación objetivo, nos movemos a ella
								Acciones.relocalizar(this, siguienteLugar);
								return gestionJuego.GameManager.log(this, null, this.getLugar());	
							}																			
							else if(this.getCreencias().get(index2).getLugar() == siguienteLugar)	//Si no estamos adyacentes a nuestro objetivo, buscamos si hemos estado previamente en alguna
								opciones.add(siguienteLugar);										//para aumentar las posibilidades de volver sobre nuestros pasos y encontrarnosla
						}
					}
					return relocalizador(opciones);													//Relocalizador explicado abajo
				}
			}	
			for(Lugar siguienteLugar: (Iterable<Lugar>) () -> this.getLugar().lugarIt()){			//Si no hemos estado en el lugar objetivo anteriormente
				opciones.add(siguienteLugar);														//Hacemos lo contrario y priorizamos las habitaciones no visitadas
				for(int index = this.getCreencias().size()-1 ; index >=0 ; index--){
					if(this.getCreencias().get(index).getLugar() == siguienteLugar)
							opciones.remove(siguienteLugar);
				}				
			}
			return relocalizador(opciones);
		}
		else {
			if(((HashSet<Objeto>)(this.getLugar().getObjeto())).contains(this.getObjetivo().getObjeto())){	//En el caso en el que no tenemos el objetyo
				Acciones.cogerObjeto(this, this.getObjetivo().getObjeto());										//Primero miramos si esta en la habitación en la que nos encontramos
				return gestionJuego.GameManager.log(this, this.getObjeto(), this.getLugar());					//Si esta, lo cogemos
			}
			else {
				for(int index = this.getCreencias().size()-1 ; index >=0 ; index--) {
					if ((ultimaInfo = this.getCreencias().get(index)).getObjeto() == this.getObjetivo().getObjeto() && ultimaInfo.getAgente() != null){	//Buscamos en nuestras creencias si
						for(Agente npc: (Iterable<Agente>) () -> this.getLugar().agenteIt()){  					//hemos visto nuestro objeto en posesión de algun otro jugador
							if (ultimaInfo.getAgente() == npc){													//y comparamos los agentes de la habitación con los de las creencias
								 Acciones.pedirObjeto(npc, new Peticion(this, this.getObjetivo().getObjeto()));	//Si hay una coincidencia, le mandamos una petición
								 return gestionJuego.GameManager.log(this.getPeticion().getObjeto(), this.getPeticion().getLugar()); 							
							}
						}
					}
				}
				for(Lugar siguienteLugar: (Iterable<Lugar>) () -> this.getLugar().lugarIt()){					//Si no, priorizamos de nuevo las habitaciones no visitadas
					opciones.add(siguienteLugar);
					for(int index = this.getCreencias().size()-1 ; index >=0 ; index--){						//Para tener más posibilidades de encontrar el objeto
						if(this.getCreencias().get(index).getLugar() == siguienteLugar)
								opciones.remove(siguienteLugar);
					}
				}
				return relocalizador(opciones);
			}
		}
	}
	
	
	
	
	
	public boolean relocalizador(List<Lugar> opciones) {									//La función relocalizador escoje una localización aleatoria de entre las opciones
		if (opciones.size() == 0) {															//Si no hay opciones, las rellena de todas las habitaciones adyacentes y elije aleatoriamente
			if( Math.random() <= 0.20 )														//Añadimos un factor aleatorio para que pueda no hacer nada si no tiene opciones buenas
				return gestionJuego.GameManager.log(this);
			else {
				for(Lugar siguienteLugar: (Iterable<Lugar>) () -> this.getLugar().lugarIt()){
					opciones.add(siguienteLugar);
				}
			}
			Acciones.relocalizar(this, opciones.get((int) Math.floor(Math.random() * (opciones.size()-1))));
			return gestionJuego.GameManager.log(this, null, this.getLugar());
		}
		else {
			Acciones.relocalizar(this, opciones.get((int)Math.floor(Math.random() * (opciones.size()-1))));
			return gestionJuego.GameManager.log(this, null, this.getLugar());
		}
	}
}