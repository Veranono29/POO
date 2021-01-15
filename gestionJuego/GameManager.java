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

public class GameManager extends ManejaDatos implements Acciones {
	
	private List<Erlacion> relaciones;

	private static final String nombreJugador = "PEPE";
	
	//Referencia al jugador jugable.
	private static Jugador pepe = null;
	
	//Interfaz grafica.
	private static PruebaDinamica interfaz;
	
	//Referencia a instancia de GameManager.
	private static Object yo;
	
	//Maximo de ajyacencias por lugar.
	private static final int maxAjyacencias = 3;
	
	//Cantidad de personas en el juego.
	private static int cantPersonas = 0;
	
	//Tiempo de "ejecucion" de la partida. Lleva la cuenta de los turnos y rondas.
	private static int tiempo = 0;
	
	private static boolean flagRonda = false;
	
	//Esta es la variable que almacena si alguien ha hecho algo en la ronda o si todos acabaron sus quehaceres. 
	//Esto es, guarda si el juego debe o no seguir.
	private static boolean bucleTurno = true;
	
	//Strings de lo que hay que detecctar en los anexos:
	private static final String[] textoTopes = {"<Localizaciones>","<Personajes>","<Objetos>"};
	private static final String[] textoTopesObjetivos = {"<Localizacion","<Posesion"};
	
	//Probabilidades:
	private static final double probabilidadOlvido = 0.00;
	private static final double probabilidadAceptar = 1.01;
	
	//Necesita accederse desde gestionJuego.
	protected static double getProbAceptar() {
		return probabilidadAceptar;
	}
	///////////////////////////////////////////////////////////////////protected, static
	public static Agente getJugador() {
		// le hacemos el casteo porque nunca sobra 
		return (Agente)pepe;
	}

	//Necesita accederse desde gestionJuego.
	protected static double getProbOlvido() {
		return probabilidadOlvido;
	}
	
	//Necesita accederse desde Agente.
	public static int getRonda() {
		return tiempo/cantPersonas;
	}
	
	protected static int getTurno() {
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
	
	protected static Object getGMins() {
		return yo;
	}
	
	//Se usa desde Agente.
	public static PruebaDinamica getInterfaz() {
		return interfaz;
	}
	
	private static String leerPalabra (Scanner lectura) throws FormatoIncorrecto{
		String[] resultado;
		
		resultado = lectura.nextLine().split("\\(");

		return resultado[0];
	}
	
	private static List<String> leerDatos(Scanner lectura) throws FormatoIncorrecto {
		List<String> dato = new ArrayList<String>();
		String[] resultado = lectura.nextLine().split("[(,)]");
		
		for(String string: resultado) {
			dato.add(string);
		}
		
		return dato;
	}
	
	private static boolean lineasIt(Scanner lectura, String ...tope) {
		for (String string : tope) {
			if(lectura.hasNext(string) || !lectura.hasNextLine()) {
				return false;
			}
		}
		return true;
	}
	
	private void rConfig() throws FileNotFoundException, FormatoIncorrecto {
		File anexoUno = new File("src/ANEXO_1");
		Scanner lectura;
		int leidoCompleto = 1;
		for(int cualAnexo = 0; cualAnexo < 2;cualAnexo++) {
			lectura = new Scanner(anexoUno);
			while(lectura.hasNextLine()){
				if(cualAnexo == 0)
					leidoCompleto *= rInstanciar(lectura);
				else 
					rDatos(lectura);
			}
			//30 es la multiplicaciÃ³n de 2, 3 y 5 (primos) para asegurarnos de que pasa por los cada uno 1 vez
			if (leidoCompleto != 30)
				throw new FormatoIncorrecto("Formato incorrecto en anexo 1: Faltan apartados/Apartados repetidos");
			lectura.close();
		}
		lectura = new Scanner(new File("src/ANEXO_2"));
		rObjetivos(lectura);
		lectura.close();
	}
	
	
	private int rInstanciar(Scanner lectura) throws FormatoIncorrecto{
		String titulo;
		int adyacenciasObtenidas = 0;	
		
		//Buscamos por "Etiqueta" los tres principales datos (con etiqueta me refiero a le nombre que aparece en <>)
		if (lectura.hasNext(textoTopes[0])){
			leerPalabra(lectura);
			while(lineasIt(lectura, textoTopes[1], textoTopes[2]) && adyacenciasObtenidas++ < maxAjyacencias+2) {
				titulo = leerPalabra(lectura);
				lugares.add( new Lugar(titulo));
			}
			return 2;
		}
		
		if (lectura.hasNext(textoTopes[1])) {
			leerPalabra(lectura);
			while(lineasIt(lectura, textoTopes[0], textoTopes[2])) {
				cantPersonas++;
				titulo = leerPalabra(lectura);
				if((pepe == null) && (titulo.equals(nombreJugador))) {
					pepe = new Jugador(titulo);
					agentes.add(pepe);
				}
				else {
					agentes.add( new NPC(titulo));
				}
				this.relaciones.add(new Erlacion(titulo));
				
			}
			return 3;
		}
		
		if (lectura.hasNext(textoTopes[2])){
			leerPalabra(lectura);
			while(lineasIt(lectura, textoTopes[1], textoTopes[0])) {
				titulo = leerPalabra(lectura);
				objetos.add( new Objeto(titulo));
			}
			return 5;
		}
		return 1;
	}
	
	private void rDatos(Scanner lectura) throws FormatoIncorrecto {
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
						if (datos.isEmpty())
							throw new FormatoIncorrecto("Formato incorrecto en anexo 1: Datos insuficientes en " + textoTopes[0] + ", " + lugar.getNombre());
						for(String nombre: datos) {
							for(Lugar adyacente: lugares) {
								//Llega 9 veces.
								if(adyacente.siSoy(nombre.trim())) {
									//Llega 3 veces.
									lugar.addLugar(adyacente);
									adyacenciasObtenidas++;
									break;
								}
							}							
							//Llega 3 veces (esta abajo y ++Antes para que se ejecute 1 vez menos).
							if(adyacenciasObtenidas >= maxAjyacencias) {
								break;	
							}
						}
						if(adyacenciasObtenidas==0)
							throw new FormatoIncorrecto("Formato incorrecto en anexo 1: Datos insuficientes en " + textoTopes[0] + ", " + lugar.getNombre());
						adyacenciasObtenidas = 0;
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
						
						if (datos.isEmpty())
							throw new FormatoIncorrecto("Formato incorrecto en anexo 1: Datos insuficientes en " + textoTopes[1] + ", " + persona.getNombre());

						for(Lugar lugar: lugares) {
							if(lugar.siSoy(datos.get(0))) {
								lugar.addAgente(persona);
								datos.remove(0);
								if(!datos.isEmpty())
									throw new FormatoIncorrecto("Formato incorrecto en anexo 1: Demasiados datos en " + persona.getNombre());
								break;
							}
						}
						if(persona.getLugar() == null)
							throw new FormatoIncorrecto("Formato incorrecto en anexo 1: Datos no declarados en " + textoTopes[1] + ", " + persona.getNombre());

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
						
						if (datos.isEmpty())
							throw new FormatoIncorrecto("Formato incorrecto en anexo 1: Datos insuficientes en " + textoTopes[2] + ", " + objeto.getNombre());
						
						try {
							if(datos.get(2) == null);
							throw new FormatoIncorrecto("Formato incorrecto en anexo 1: Demasiados datos en " + textoTopes[2] + objeto.getNombre());
						} catch (IndexOutOfBoundsException e) {
							;
						}
						
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
	private void rObjetivoSaltar(Scanner lectura) throws FormatoIncorrecto {
		if(lectura.hasNextLine())
			rObjetivos(lectura);
		else {
			for(Agente agente: agentes) {
				for(Erlacion erlacion: relaciones) {
					if(erlacion.getLugar() == null && erlacion.getObjeto() == null)
						throw new FormatoIncorrecto("Formato incorrecto en anexo 2: Agente " + erlacion.getNombre() + " sin proposito en la vida :(");
					if(erlacion.siSoy(agente)) {
						agente.setObjetivo((Objetivo) erlacion);
					}
				}
			}
		}
	}
	private void rObjetivos(Scanner lectura) throws FormatoIncorrecto {
		List<String> datos;
		
		if (lectura.hasNext(textoTopesObjetivos[0])){
			lectura.nextLine();
			while(lineasIt(lectura, textoTopesObjetivos[1])) {
				datos = leerDatos(lectura);
				for(Agente agente: agentes) {
					if(agente.siSoy(datos.get(0))) {
						datos.remove(0);
						for(Lugar lugar: lugares) {
							if(lugar.siSoy(datos.get(0))) {
								for(Erlacion erlacion: relaciones) {
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
			rObjetivoSaltar(lectura);
		}
		
		if (lectura.hasNext(textoTopesObjetivos[1])){
			lectura.nextLine();
			while(lineasIt(lectura, textoTopesObjetivos[0])) {
				datos = leerDatos(lectura);
				for(Agente agente: agentes) {
					if(agente.siSoy(datos.get(1))) {
						datos.remove(1);
						for(Objeto objeto: objetos) {
							if(objeto.siSoy(datos.get(0))) {
								for(Erlacion erlacion: relaciones) {
									if(erlacion.siSoy(agente)) {
										erlacion.setObjeto(objeto);
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
			rObjetivoSaltar(lectura);
		}
		relaciones = null;
	}
	
	/*** Acciones ***/
	
	//log de no hacer nada
	private static boolean log(Agente agente) {
		GameManager.sumaCreencia(agente.getLugar() , new Creencia(agente, null, null, tiempo));
		return false;
	}
	
	private static boolean log(Agente agente, Objeto objeto, Lugar lugar) {
		if(agente == null) {
			lugar.addCreencia(new Creencia(agente, objeto, lugar, tiempo));
		}
		else
			agente.getLugar().addCreencia(new Creencia(agente, objeto, lugar, tiempo));
		return true;
	}
	
	public static boolean pedirObjeto (Agente jugadorPeticionado, Peticion peticion){
		//Si no tiene el objeto, no le llega la peticion, simulando que la rechazo y el que hizo la peticion pierde el turno
		System.out.println("pato");
		if(jugadorPeticionado.getObjeto() != null)
			Acciones.pedirObjeto(jugadorPeticionado, peticion);
		return true;
	}
	
	//Dar objeto a una persona.
	public static boolean darObjeto (Agente jugadorPeticionado) {
		boolean logRet = log(jugadorPeticionado.getPeticion().getAgente(), jugadorPeticionado.getPeticion().getObjeto(),jugadorPeticionado.getLugar());
		Acciones.darObjeto(jugadorPeticionado);
		System.out.println("pato1");
		return logRet;
	}
	
	//Reocalizar actualiza la posicion del Agente
	public static boolean relocalizar (Agente jugadorTransladado, Lugar lugar) {
		Acciones.relocalizar(jugadorTransladado, lugar);
		System.out.println("Se mueve");
		return log(jugadorTransladado, null, lugar);
	}
	
	public static boolean cogerObjeto (Agente jugador, Objeto objeto) {
		Acciones.cogerObjeto(jugador, objeto);
		System.out.println("Coje");
		return log(jugador, objeto, jugador.getLugar());
	}
	
	public static boolean dejarObjeto (Agente jugador) {
		boolean logRet = log(null, jugador.getObjeto(), jugador.getLugar());
		Acciones.dejarObjeto(jugador);
		System.out.println("Deja");
		return logRet;
	}
	
	public static boolean pasarTurno (Agente jugador) {
		System.out.println("pato5");
		return GameManager.log(jugador);
	}
	
	private static void sumaCreencia(Lugar lugar, Informacion creencia) {
		Acciones.sumaCreencia(lugar, creencia);
	}
	
	protected static void volcadoCreencias(Agente persona) {
		Acciones.volcadoCreencias(persona);
	}
	
	//Se vuelcan las creencias y se modifica el tiempo anterior. Esto es, se gestiona el volcado. No confundir con volcadoCreencias, el cual produce el volcado.
	public static void conseguirCreencias(Agente persona) {
		GameManager.volcadoCreencias(persona);
		persona.setTiempoAnterior(GameManager.getTurno());
	}
	
	//Libera al Jugador de las creencias, pasandolas al set de la instacia de GameManager.
	private void liberarCreencias() {
		this.addVariasCreencias(pepe.getCreencias());
		pepe.limpiarCreencias();
	}
	
	/******/

	public GameManager() {
		super("Manejador");
		relaciones = new ArrayList<Erlacion>();
		yo = this;
		
	}
	
	private void rondaNPC() {
		while(bucleTurno) {
			bucleTurno = false;

			//Se itera por cada agente para hacer los turnos.
			for(Agente agente: agentes) {
				System.out.println(agente.getNombre());
				System.out.println(agente.getYaObjetivo(0));
				System.out.println(agente.getYaObjetivo(1));

				//Si todos hicieron lo suyo, ninguno cumplira la condicion, y por lo tanto ninguno cambiara bucleTurno, por lo que saldra del while.
				if(agente.compPersona()) {
					if(!bucleTurno && agente.dameAccion())
						bucleTurno = true;
				}
				tiempo++;
			}
			//A partir de aqui se ejecuta en la siguiente ronda, turno 0.
			//Se itera por cada lugar para volcarles las Informaciones a los agentes antes de borrar 
			for(Agente agente: agentes)
				conseguirCreencias(agente);
		}
		
	}

	public void ronda()  {
		//Para poder hacer jugar a los NPC:
		
		if(GameManager.flagRonda) {
			//flagRonda = false;
			for(Agente agente: agentes) {
				if(agente instanceof Jugador) {
				
					liberarCreencias();
					bucleTurno = interfaz.getBoleanDameAccion() || bucleTurno;
					//flagRonda = true;
					continue;
				}
				if(flagRonda) {
					bucleTurno = rondaAcciones(agente) || bucleTurno;
				}
				tiempo++;
			}
			for(Agente agente: agentes)
				 conseguirCreencias(agente);
		}
		else
			GameManager.flagRonda = true;
		
		if(!bucleTurno) {
			
			interfaz.dispose(); //elimina la interfaz al ganar la partida
			Main.datos();		//Weas
			return;
			
		}
		bucleTurno = false;
		
		for(Agente agente: agentes) {
			System.out.println("Bucle Rondas() " + agente.getNombre()); //Aqui llega
			bucleTurno = rondaAcciones(agente)||bucleTurno ;
			tiempo++;
			System.out.println("Tiempo en Ronda() = "+tiempo);
			if(agente instanceof Jugador) {
				System.out.println("ELSAPAOTGKFODAKSFKASDOFKAF");
				
				if(agente.getYaObjetivo(0) == false && agente.getYaObjetivo(1) == false){
					System.out.println("DANILAAAAAAAAAAAAA");
					rondaNPC();
					//ronda();
				}
				break;
			}
		}
	}
	
	private boolean rondaAcciones(Agente agente) {
		//Si todos hicieron lo suyo, ninguno cumplira la condicion, y por lo tanto ninguno cambiara bucleTurno, por lo que saldra del while.
		if(true) { //agente.compPersona()
			
			//dameAccion se encarga de indicar de forma indirecta que accion quiere realizar (y el log que debe hacer) y el resto de gestiones.
			return agente.dameAccion();
		}
		return false;
	}
	public void main() {
		try {
			rConfig();
		} catch (FileNotFoundException error) {
			
			System.out.println("No tienes los archivos de configuracion y objetivos");
			System.exit(0); 
			error.printStackTrace();		
		} catch (FormatoIncorrecto error) {
			
			System.out.println(error.getMensaje());
			System.exit(0); 
			error.printStackTrace();
		}
		if(pepe == null) {
			rondaNPC();
			return;
		}else{
			interfaz = new PruebaDinamica(pepe);
			interfaz.setVisible(true);
			//Primera llamada a ronda. No ejecutara la primera mitaz del "bucle truncado".
			ronda();
		}
    }
}
