package gestionJuego;


import elementosNarrativos.NPC;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import java.util.ArrayList;
import java.util.List;

import datos.Creencia;
import datos.Erlacion;
import datos.Informacion;
import datos.Objetivo;
import datos.Peticion;
import elementosNarrativos.Agente;
import elementosNarrativos.Jugador;
//TODO si quitar sumaCreencia. quita el import de abajo, el de Acciones.
import elementosNarrativos.Lugar;
import elementosNarrativos.ManejaDatos;
import elementosNarrativos.Objeto;

//TODO deberia ser abstracta. Arreglar error ese.
/* Si no se puede como esta:
 * Cambiar todo this.cosa a GameManager.
 * Cambiar todos los metodos a static.
 * Cambiar manejador.main() a GameManager.main() en gestionJuego.Main.java.
 */
public class GameManager extends ManejaDatos implements Acciones {
	//TODO hacedor();
	
	private static List<Erlacion> erlaciones;
	//private static List<String> nombres;
	
	//TODO los finales los metemos en una interfaz?
	//TODO mejor dejarlo protected o como aparece en default, ya que no necesitamos que las subclases la vean (no va a tener).
	//Nombre del jugador jugable tal y como aparece en ANEXO_1 y ANEXO_2.
	private static final String nombreJugador = "PEPE";
	
	//Referencia al jugador jugable.
	private static Jugador pepe = null;
	
	//Maximo de ajyacencias por lugar.
	private static final int maxAjyacencias = 3;
	
	//Cantidad de personas en el juego.
	private static int cantPersonas = 0;
	
	//Tiempo de "ejecucion" de la partida. Lleva la cuenta de los turnos y rondas.
	private static int tiempo = 0;
	
	//Strings de lo que hay que detecctar en los anexos:
	private static final String[] textoTopes = {"<Localizaciones>","<Personajes>","<Objetos>"};
	private static final String[] textoTopesObjetivos = {"<Localización Personajes>","<Posesión Objetos>"};
	
	//Probabilidades:
	//TODO esto asi, o lo ponemos para cambiar en la interfaz?
	private static final double probabilidadOlvido = -0.01;
	private static final double probabilidadAceptar = 1.01;
	
	
	public GameManager() {
		super("Manejador");
	}
	
	
	//Necesita accederse desde gestionJuego.
	protected static double getProbAceptar() {
		return probabilidadAceptar;
	}

	//Necesita accederse desde gestionJuego.
	protected static double getProbOlvido() {
		return probabilidadOlvido;
	}
	//Necesita accederse desde gestionJuego (interfaz grafica, por ejemplo).
	protected static int getRonda() {
		return tiempo/cantPersonas;
	}
	
	//Necesita accederse desde Agente.
	public static int getTurno() {
		//En el caso de haber terminado los turnos y estar en el volcado, esta en el turno 0.
		return tiempo%cantPersonas;
	}
	
	//Necesita accederse desde Creencia.
	public static int getRonda(int tiempoT) {
		return tiempoT/cantPersonas;
	}
	
	//Necesita accederse desde Creencia..
	public static int getTurno(int tiempoT) {
		//En el caso de haber terminado los turnos y estar en el volcado en el cual
		return tiempoT%cantPersonas;
	}
		
	protected static Agente getJugador(){
		return pepe;
	}
	
	private static String leerPalabra(int desde, String linea) {
		int posicion;
		char letra;
		String resultado;
		
		resultado = "";
		
		for(posicion = desde; posicion < linea.length() && Character.isLetter(letra = linea.charAt(posicion)); posicion++) {
			resultado += letra;
		}
		return resultado;
	}
	
	private static String leerPalabra(String linea) {
		return leerPalabra(0, linea);
	}
	
	private static List<String> leerDatos(Scanner lectura) {
		int posicion;
		String temp;
		String resultado;
		List<String> datos = new ArrayList<String>();
		
		temp = lectura.nextLine();
		resultado = "";
		//TODO si no funciona, aca el +1 quitalo o no se.
		for(posicion = 0; posicion < temp.length(); posicion+=resultado.length()+1) {
			resultado = leerPalabra(posicion, temp);
			datos.add(resultado);
		}
		return datos;
	}
	
	//TODO si no funciona el String ...tope, usamos este y otro con solo 1 String.
	/*private static boolean lineasIt(Scanner lectura, String tope1, String tope2) {
		return (!(lectura.hasNext(tope1) || lectura.hasNext(tope2)));
	}*/
	private static boolean lineasIt(Scanner lectura, String ...tope) {
		for (String string : tope) {
			if(lectura.hasNext(string)) {
				return false;
			}
		}
		return true;
	}
	
	private static String  rNombre() {
		String nombre;
		Scanner nom = new Scanner(System.in);
		System.out.println("Cual es tu nombre: ");
		nombre = nom.next();
		nom.close();
		return nombre;
	}
	
	//TODO aca tenemos que recojer el FileNotFoundException no?
	private void rConfig() throws FileNotFoundException {
		File[] anexos = {new File("ANEXO_1"),new File("ANEXO_2")};
		Scanner lectura = null;
		
		//Para poder poner los datos en cualquier orden.
		for(int cualAnexo = 0; cualAnexo < anexos.length;cualAnexo++) {
			lectura = new Scanner(anexos[cualAnexo]);
			while(lectura.hasNextLine()){
				if(cualAnexo == 0)
					rInstanciar(lectura);
				else
					rDatos(lectura);
			}
		}
		rObjetivos(lectura);
	}
	
	
	public void rInstanciar(Scanner lectura){
		String titulo;
		int adyacenciasObtenidas = 0;
		//Buscamos por "Etiqueta" los tres principales datos (con etiqueta me refiero a le nombre que aparece en <>)
		if (lectura.hasNext(textoTopes[0])){
			while(lineasIt(lectura, textoTopes[1], textoTopes[2]) && adyacenciasObtenidas++ < 4) {
				titulo = leerPalabra(lectura.nextLine());
				lugares.add( new Lugar(titulo));
			}
		}
		
		if (lectura.hasNext(textoTopes[1])) {
			while(lineasIt(lectura, textoTopes[0], textoTopes[2])) {
				cantPersonas++;
				titulo = leerPalabra(lectura.nextLine());
				if((pepe == null) && (titulo == nombreJugador)) {
					pepe = new Jugador(rNombre());
					agentes.add(pepe);
				}
				else {
					agentes.add( new NPC(titulo));
				}
				erlaciones.add( new Erlacion(titulo));
			}
		}
		
		if (lectura.hasNext(textoTopes[2])){
			while(lineasIt(lectura, textoTopes[1], textoTopes[0])) {
				titulo = leerPalabra(lectura.nextLine());
				objetos.add( new Objeto(titulo));
			}
		}
	}
	
	public void rDatos(Scanner lectura) {
		List<String> datos;
		int adyacenciasObtenidas = 0;
		
		if (lectura.hasNext(textoTopes[0])){
			while(lineasIt(lectura, textoTopes[1], textoTopes[2])) {
				//Llega n veces, n siendo num de lugares.
				datos = leerDatos(lectura);
				
				for(Lugar lugar: lugares) {
					//Llega n veces, n siendo num de lugares.
					if(lugar.siSoy(datos.get(0))) {
						//Llega 1 vez.
						datos.remove(0);
						
						for(String nombre: datos) {
							for(Lugar adyacente: lugares) {
								//Llega 9 veces.
								if(adyacente.siSoy(nombre)) {
									//Llega 3 veces.
									lugar.addLugar(adyacente);
								}
							
							}
							
							//Llega 3 veces (esta abajo y ++Antes para que se ejecute 1 vez menos).
							if(++adyacenciasObtenidas < maxAjyacencias) {
								break;
							}
						}
						break;
					}
				}
			}
		}
		
		if (lectura.hasNext(textoTopes[1])) {
			while(lineasIt(lectura, textoTopes[0], textoTopes[2])) {
				datos = leerDatos(lectura);
				
				for(Agente persona: agentes) {
					if(persona.siSoy(datos.get(0))) {
						datos.remove(0);
						
						for(Lugar lugar: lugares) {
							if(lugar.siSoy(datos.get(0))) {
								lugar.addAgente(persona);
								break;
							}
						}
						break;
					}
				}
			}
		}
		
		if (lectura.hasNext(textoTopes[2])){
			while(lineasIt(lectura, textoTopes[1], textoTopes[0])) {
				datos = leerDatos(lectura);
				
				for(Objeto objeto: objetos) {
					if(objeto.siSoy(datos.get(0))) {
						datos.remove(0);
						
						for(Lugar lugar: lugares) {
							if(lugar.siSoy(datos.get(0))) {
								lugar.addObjeto(objeto);
								datos.remove(0);
								break;
							}
						}
						if(!datos.isEmpty()) {
							for(Agente agente: agentes) {
								if(agente.siSoy(datos.get(0))) {
									agente.addObjeto(objeto);
									break;
								}
							}
						}
						break;
					}
				}
			}
		}
	}
	
	private void rObjetivos(Scanner lectura) {
		//TODO meter en erlaciones los datos de lugar y objeto.
		List<String> datos;
		
		if (lectura.hasNext(textoTopesObjetivos[0])){
			while(lineasIt(lectura, textoTopes[1], textoTopes[0])) {
				datos = leerDatos(lectura);
				
				for(Agente agente: agentes) {
					if(agente.siSoy(datos.get(0))) {
						datos.remove(0);
						
						for(Lugar lugar: lugares) {
							if(lugar.siSoy(datos.get(0))) {
								for(Erlacion erlacion: erlaciones) {
									if(erlacion.siSoy(agente)) {
										erlacion.setLugar(lugar);
										break;
									}
								}
								break;
							}
						}
						break;
					}
				}
			}
		}
		
		if (lectura.hasNext(textoTopesObjetivos[1])){
			while(lineasIt(lectura, textoTopes[1], textoTopes[0])) {
				datos = leerDatos(lectura);
				
				for(Agente agente: agentes) {
					if(agente.siSoy(datos.get(1))) {
						datos.remove(1);
						
						for(Objeto objeto: objetos) {
							if(objeto.siSoy(datos.get(0))) {
								for(Erlacion erlacion: erlaciones) {
									if(erlacion.siSoy(agente)) {
										erlacion.setObjeto(objeto);
										agente.setObjetivo((Objetivo) erlacion);
										break;
									}
								}
								break;
							}
						}
						break;
					}
				}
			}
		}
		erlaciones = null;
	}
	
	/*** Acciones ***/
	
	public static boolean log(Agente agente) {
		GameManager.sumaCreencia(agente.getLugar() , new Creencia(agente, null, null, tiempo));
		
		//agente.getLugar().addCreencia(new Informacion(agente, null, null));
		return false;
	}
	
	public static boolean log(Agente agente, Objeto objeto, Lugar lugar) {
		agente.getLugar().addCreencia(new Creencia(agente, objeto, lugar, tiempo));
		return true;
	}
	
	public static boolean pedirObjeto (Agente jugadorPeticionado, Peticion peticion){
		Acciones.pedirObjeto(jugadorPeticionado, peticion);
		return true;
	}
	
	//Dar objeto a una persona.
	public static boolean darObjeto (Agente jugadorPeticionado) {
		boolean logRet = log(jugadorPeticionado.getPeticion().getAgente(), jugadorPeticionado.getPeticion().getObjeto(),jugadorPeticionado.getLugar());
		Acciones.darObjeto(jugadorPeticionado);
		return logRet;
	}
	
	public static boolean relocalizar (Agente jugadorTransladado, Lugar lugar) {
		Acciones.relocalizar(jugadorTransladado, lugar);
		return log(jugadorTransladado, null, lugar);
	}
	
	public static boolean cogerObjeto (Agente jugador, Objeto objeto) {
		Acciones.cogerObjeto(jugador, objeto);
		return log(jugador, objeto, jugador.getLugar());
	}
	
	public static boolean dejarObjeto (Agente jugador) {
		boolean logRet = log(null, jugador.getObjeto(), jugador.getLugar());
		Acciones.dejarObjeto(jugador);
		return logRet;
	}
	
	/* No hace falta
	public static boolean verLugar(Agente persona) {
		Acciones.verLugar(persona);
	}
	*/
	
	//TODO entoces esto que.
	private static void sumaCreencia(Lugar lugar, Informacion creencia) {
		Acciones.sumaCreencia(lugar, creencia);
	}
	
	//TODO el volcado de creencias debera ir dentro de dameAccion (justo antes de decidir nada, para tener los nuevos datos). Se declara aqui.
	protected static void volcadoCreencias(Agente persona) {
		Acciones.volcadoCreencias(persona);
	}
	
	public static void conseguirCreencias(Agente persona) {
		Acciones.conseguirCreencias(persona);
	}
	
	//Libera al Jugador de las creencias, pasandolas al set de la instacia de GameManager.
	private void liberarCreencias() {
		this.addVariasCreencias(pepe.getCreencias());
		pepe.limpiarCreencias();
	}
	
	/******/

	/*public GameManager(String nombre) {
		super(nombre);
		// TODO Borrad esto al final. Si no se pone java se enfada y no te dice nada mas
	}*/

	public void main() {
		
		//TODO Gerardo, por aca lo que tengas que poner de instanciar la interfaz o lo de iniciarla o asi.
		//TODO Gerardo, recuerda que el punto donde le pides que te pasemos la ronda es en el dameAccion de elementosNarrativos.Agente.
		
		//Esta es la variable que almacena si alguien ha hecho algo en esta ronda o si todos acabaron sus quehaceres. 
		//Esto es, guarda si el juego debe o no seguir.
		boolean bucleTurno = true;
		
		//Este otro sirve de apoyo al bucleTurno.
		boolean seMovieron;
		
		try {
			this.rConfig();
		} catch (FileNotFoundException error) {
			//Si, no?
			System.out.println("No tienes los archivos de configuracion y objetivos");
			System.exit(0); 
			error.printStackTrace();
		}
		
		while(bucleTurno) {
			bucleTurno = false;
			
			//Se itera por cada agente para hacer los turnos.
			for(Agente agente: agentes) {
				
				//Si todos hicieron lo suyo, ninguno cumplira la condicion, y por lo tanto ninguno cambiara bucleTurno, por lo que saldra del while.
				if(agente.compPersona()) {
					
					//dameAccion se encarga de indicar que accion quiere realizar (y el log que debe hacer) y el resto de gestiones.
					seMovieron = agente.dameAccion();
					
					//Si el agente hizo alguna accion y si todavia nadie la hizo, marcar que al menos alguien hizo algo en esta ronda para poder tener una siguiente.
					if(agente instanceof Jugador && !bucleTurno/* && TODO Gerardo, como era lo del getBooleanDameAccion?*/) {
						bucleTurno = true;
					}
					else
						if(!bucleTurno && seMovieron)
							bucleTurno = true;
				}
				//Aumento el tiempo del turno.
				tiempo++;
			}
			//A partir de aqui se ejecuta en la siguiente ronda, turno 0.
			//Se borra la referencia a las creencias guardadas en el jugador jugable, y se agrega en el set de creencias del GameManager, quien almacenara las creencias obtenidas por el jugador durante la partida.
			liberarCreencias();
			
			//Se itera por cada lugar para volcarles las Informaciones a los agentes antes de borrar 
			for(Lugar lugar: lugares)
				for(Agente agente: agentes)
					if(agente.getLugar() == lugar)
						agente.addVariasCreencias(lugar.getCreencias());
		}
	}
}
