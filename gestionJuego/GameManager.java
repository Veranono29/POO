package gestionJuego;

import elementosNarrativos.ManejaDatos;
import elementosNarrativos.NPC;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import datos.Erlacion;
import datos.Informacion;
import datos.Objetivo;
import elementosNarrativos.Agente;
import elementosNarrativos.Jugador;
//TODO si quitar sumaCreencia. quita el import de abajo, el de Acciones.
import elementosNarrativos.Acciones;
import elementosNarrativos.Lugar;
import elementosNarrativos.Objeto;

//TODO deberiamos importarlo y usar los privates o extenderlo y usar protected.

public abstract class GameManager extends DataManager {
	//TODO hacedor();
	
	private static List<Erlacion> erlaciones;
	private static List<String> nombres;
	
	//TODO mejor dejarlo protected o como aparece en default, ya que no necesitamos que las subclases la vean (no va a tener).
	private static final String nombreJugador = "PEPE";
	private static Agente pepe = null;
	private static final int maxAjyacencias = 3;
	private static final int maxPersonas = 10;
	private static int tiempo = 0;
	private static final String[] textoTopes = {"<Localizaciones>","<Personajes>","<Objetos>"};
	private static final String[] textoTopesObjetivos = {"<Localización Personajes>","<Posesión Objetos>"};
	
	protected static int getRonda() {
		return tiempo/maxPersonas;
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
	
	private static void rConfig() throws FileNotFoundException {
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
	
	
	public static void rInstanciar(Scanner lectura){
		String titulo;
		int adyacenciasObtenidas;
		//Buscamos por "Etiqueta" los tres principales datos (con etiqueta me refiero a le nombre que aparece en <>)
		if (lectura.hasNext(textoTopes[0])){
			while(lineasIt(lectura, textoTopes[1], textoTopes[2])) {
				titulo = leerPalabra(lectura.nextLine());
				lugares.add( new Lugar(titulo));
			}
		}
		
		if (lectura.hasNext(textoTopes[1])) {
			while(lineasIt(lectura, textoTopes[0], textoTopes[2])) {
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
	
	public static void rDatos(Scanner lectura) {
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
									lugar.addAgente(adyacente);
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
	
	private static void rObjetivos(Scanner lectura) {
		//TODO meter en erlaciones los datos de lugar y objeto.
		List<String> datos;
		Scanner lectura = new Scanner(ANEXO_1);
		
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
	
	public static boolean log(Agente agente) {
		Acciones.sumaCreencia(agente.getLugar() , new Informacion(agente, null, null));
		
		//agente.getLugar().addCreencia(new Informacion(agente, null, null));
		return false;
	}
	
	public static boolean log(Agente agente, Objeto objeto, Lugar lugar) {
		agente.getLugar().addCreencia(new Informacion(agente, objeto, lugar));
		return true;
	}

	public GameManager(String nombre) {
		super(nombre);
		// TODO Borrad esto al final. Si no se pone java se enfada y no te dice nada mas
	}

	public static void main(String[] args) {	
		boolean bucleTurno = true;
		String nombreJugador;
		
		//rNombre();
		
		while(bucleTurno) {
			bucleTurno = false;
			
			//for( Agente paco : agentes)
		}
	}
}
