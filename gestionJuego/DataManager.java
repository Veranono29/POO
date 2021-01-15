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

public abstract class DataManager {
	
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
		for(Agente agente: manejador.getAgentes()) {
			if(!(agente instanceof Jugador)) {
				creencias.addAll(manejador.getCreencias());
			}
			else {
				creencias.addAll(agente.getCreencias());
			}
		}

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
			if(infoObjetos.get(contador)!=null){
				for(Informacion creencia: infoObjetos.get(contador)){
					creen = ((Creencia) creencia);
					writerObjetos.write(creen.info(objeto));
				}
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
	}
}
