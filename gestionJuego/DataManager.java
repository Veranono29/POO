package gestionJuego;

import java.util.ArrayList;
import java.util.HashSet;
//TODO em... creo que todas las veces le puse de util.List, no? esque tambien esta el de awt y ese no queremos.
import java.util.List;
import java.util.Set;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import datos.Creencia;
import datos.Informacion;
import elementosNarrativos.Agente;
import elementosNarrativos.Jugador;
import elementosNarrativos.Objeto;


//TODO ta bien que no herede?
//No hereda de ManejaDatos, porque aunque maneje datos, no ManejaDatos. Hace cosas un poco (bastante) diferences.
//Tampoco hereda de TieneCreencia, porque aunque tenga creencias, no TieneCreencias. Hace cosas un poco (bastante) diferences.
public abstract class DataManager {
	
	//TODO aiuda, como esto?
	/*private static List<Set<Informacion>> infoObjetos;
	private static List<Set<Informacion>> infoAgentes;
	private static List<Creencia> creencias;*/
	
	public static void main(GameManager manejador) throws IOException {
		
		int contador = 0;
		
		List<Set<Informacion>> infoObjetos = new ArrayList<Set<Informacion>>();
		List<Set<Informacion>> infoAgentes = new ArrayList<Set<Informacion>>();
		List<Informacion> creencias = new ArrayList<Informacion>();
		Creencia creen = null;
		try{
			File datos = new File("movAgentes.txt");
			if (!datos.createNewFile()){
				datos.delete();
				datos.createNewFile();
		      } 
		}
		catch(IOException error) {
			System.out.println("Error creando el fichero de datos movPersonas");
		}
		try{
			File datos = new File("movObjetos.txt");
			if (!datos.createNewFile()){
				datos.delete();
				datos.createNewFile();
		      } 
		}
		catch(IOException error) {
			System.out.println("Error creando el fichero de datos movObjetos");
		}
		FileWriter writerAgentes = new FileWriter("movAgentes.txt");
		FileWriter writerObjetos = new FileWriter("movObjetos.txt");
		//List<Objeto> obj = new ArrayList<Objeto>();
		//List<Agente> agen = new ArrayList<Agente>();
		
		//Agrego las creencias (las de los NPC + el de manejador (que son las del Jugador). No guarda repetidos, asique no habra problema con ello.
		for(Agente agente: manejador.getAgentes()) {
			if(!(agente instanceof Jugador)) {
				creencias.addAll(manejador.getCreencias());
			}
			else {
				creencias.addAll(agente.getCreencias());
			}
		}
		
		//TODO Esto pa la memoria.
		//Iteramos sobre todos las creencias generadas en el juego.
		//Solo nos interesa saber sobre los agentes y objetos.
		//Los tipos de creencia son:
		/* Agente Objeto Lugar -> Agente logro Objeto en Lugar. En modo no importa.
		 * null   Objeto Lugar -> Alguien (no se necesita saber) dejo Objeto en Lugar.
		 * Agente null   Lugar -> Agente se movio a Lugar.
		 */
		//Hay una posibilidad de que se olviden creencias. Contra mas gente este presente, menos posibilidades de que se olvide ya que todos los que "lo vierton" tienen una referencia a la creencia.
		//Si si olvida, Tambien se olvida para los registros y ficheros finales, ya que es tal y como se veria en el juego.
		//Se puede cambiar la probabilidad, aunque recomendamos el 0%.
		for(Informacion creencia: creencias) {
			
			//Iteramos sobre los objetos del juego. Si encontramos creencias donde se le nombre, lo añadimos a un set.
			for(Objeto objeto: manejador.getObjeto()) {
				infoObjetos.add(new HashSet<Informacion>());
				if(creencia.getObjeto() == objeto)
					infoObjetos.get(contador).add(creencia);
				contador++;
			}
			contador = 0;
			//Iteramos sobre los agentes del juego. Si encontramos creencias donde se le nombre, lo añadimos a un set.
			for(Agente agente: manejador.getAgentes()) {
				infoAgentes.add(new HashSet<Informacion>());
				if(creencia.getAgente() == agente)
					infoAgentes.get(contador).add(creencia);
				contador++;
			}
		}
		
		contador = 0;
		
		/*Ahora se itera sobre los set de objetos y el de agentes. No hace falta filtrado previo ya que cada set "pertenece" al agente/objeto en si.*/
		
		for(Objeto objeto: manejador.getObjeto()) {

			//Por cada set de creencias sobre un objeto, se crea un fichero con la info del recorrido de este.
			writerObjetos.write("<" + objeto.getNombre() + ">\nLUGAR AGENTE RONDA TURNO");
			//TODO le puedo meter el ++ en el get(contador)
			for(Informacion creencia: infoObjetos.get(contador)){
				creen = ((Creencia) creencia);
				writerObjetos.write(creen.info(objeto));
			}
			if(creen.getAgente().getYaObjetivo(1)) {
				writerObjetos.write("LLEGA A SU DESTINO");
			}
			writerObjetos.close();
			contador++;
		}
		contador = 0;
		for(Agente agente: manejador.getAgentes()) {

			//Por cada set de creencias sobre un agente, se crea un fichero con la info del recorrido de este.
			writerAgentes.write("<" + agente.getNombre() + ">\nLUGAR AGENTE RONDA TURNO");
			//TODO le puedo meter el ++ en el get(contador)?
			for(Informacion creencia: infoAgentes.get(contador)) {
				creen = ((Creencia) creencia);
				writerAgentes.write(creen.info(agente));
			}
			if(!creen.getAgente().compPersona()) {
				writerAgentes.write("LLEGO A SU DESTINO");
			}
			writerAgentes.close();
			contador++;
		}
		
		
		
		//TODO aca se debe de guardar en listas de Informaciones:
		/* Una por cada Objeto, con donde estuvo y quien lo tuvo.
		 * Una por cada Agente, donde estuvo y donde estuvo.
		 */
		/*Como?:
		 * 0) un enum con los objetos que existen y agentes que existen (GameManager.agnetes/objetos).
		 * 1) todas las creencias (las de los NPC + el de manejador (que son las del Jugador) se vuelcan en un set. No guarda repetidos, asique no habra problema con ello.
		 * 2) un foreahc de las creencias.
		 * 3) varios if (entre los if del listado no son else.
			*if creencia.objeto == tal {guardar en el set de ese objeto (es la InfoObjetos[eseObjetoIndexadoEnElEnum]} else if objeto == cual {} else if...
			*if creencia.lugar ...
		 * 4) un foreach por cada InfoCosa (las dos que tenemos)
		 * 5) en cada foreach un foreach de cada objeto (vamo, un foreach anidado)
		 * 6) ahi dentro, guardamos la info de Informacion en un fichero tal que o mas o menos:
<OBJETO_NOMBRE>
LUGAR PERTENENCIA RONDA TURNO
Cafeteria Nadie 1 1
Cafeteria Nadie 1 2
Cafeteria Paco 1 3
Libreria Nadie 3 3
Libreria Alvaro 4 7
LLEGO A SU DESTINO

o para Agente:
<PERSONA_NOMBRE>
LUGAR OBJETO RONDA TURNO
...
...
...
		 *Si llego a su dueño (si la persona que lo tiene necesita ese objeto), se pone al final lo de LLEGO A SU DESTINO
		 */

		      
	}
}
