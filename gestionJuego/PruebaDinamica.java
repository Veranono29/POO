//:D

package gestionJuego;

import elementosNarrativos.Agente;
import elementosNarrativos.Jugador;
import elementosNarrativos.Lugar;
import elementosNarrativos.Objeto;

import datos.Objetivo;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JTextPane;

public class PruebaDinamica extends JFrame {
	//private boolean boleanoDelVotonDeDar=false;
	private boolean boleanDameAccion;
	//TODO Aqui deberia ir todo al construcotr en vez de definirse?
	private final int MARCO_X=100,MARCO_Y=100,ANCHO_MARCO=1400,ALTO_MARCO=550;	//EL MARCO
	
	private final int anchoBoton=97,altoBoton=40;
		//Como los botones de accion van a ser sustituidos por las personas al pulsar el boton DAR
	private final int SUBZONA_TOP_IZ_X = 307, SUBZONA_TOP_IZ_Y = 173;	//307,123
	private final int SUBZONA_BOT_DER_X = 525+anchoBoton, SUBZONA_BOT_DER_Y = 226+altoBoton; //525,326
	
	private JPanel contentPane;
		//BOTONES ACCIONES
	private JButton botonRecoger = new JButton("Recoger");	//Hay que implementarlo
	private JButton botonPasarTurno = new JButton("Pasar");	//dameAccion(false)
	private JButton botonDar = new JButton("Dar");
	private JButton botonPedir = new JButton("Pedir");
	private BotonDeCoger botonDejar = new BotonDeCoger();
	private JButton botonVolver;
	private JButton botonLugar = new JButton("Moverse");	//Será como pedir pero para lugares
		//Accesos a info
	private Jugador rudolf; 
	private Lugar ru ;
	
		//botones para elegir persona a la que pedir
	private BotonDePersona[] botonPer;
	private int sizePer;
		//Objetos suelo
	private BotonDeCoger[] botonSuelo;
	private int sizeObj;
		//lugares
	 private BotonDeMoverse[] botonesLugares;
	 private int sizeLugar;
	 	//Numero de ronda
	 JTextPane txtpnTurnoJorge;
	 private int ronda=0;
	 	//Objetivos
	 JTextField txtLiObjetivo;
	 
	public PruebaDinamica(Agente pepe) {
		
		rudolf= (Jugador) pepe;	//debo tratarlo como jugador porque si no no puedo invocar los metodos propios del jugador
		ru =rudolf.getLugar(); 
			//Prueba
		System.out.println(rudolf.getObjetivo());
		
		//Frame
		setTitle("Ekaitz, Raul, Gerardo, Daniel");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(MARCO_X, MARCO_Y, ANCHO_MARCO,ALTO_MARCO);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
/////////////////////////////////////////////////////////////////////////////
			//ACIONES DE LOS BOTONES
		//BOTON RECOGER	SALA ->1
		botonRecoger.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				irASeleccionObjeto();	//crea e imprime la lista de objetos disponibles en la sala
				
			}
		});
		botonRecoger.setBounds(307, 123, 97, 40);
		contentPane.add(botonRecoger);
		//Si ya tiene un objeto asociado, no puede pedir objeto ni recogerlo del suelo
		if(rudolf.getObjeto()!=null)botonRecoger.setVisible(false);	//Si tiene objeto no puede pedir
		
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
		//BOTON DEJAR EN LA SALA ->2	//Terminado
		
		botonDejar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
					boleanDameAccion= rudolf.dameAccion(botonDejar);	// ->dameAccion(null) es 
					ocultarTodo();//AL haber pusado un botÃ¯Â¿Â½n se ocultan los demÃ¯Â¿Â½s y se acaba la ronda
						
			}
		});
		botonDejar.setBounds(425, 226, 97, 40);
		contentPane.add(botonDejar);
		if(rudolf.getObjeto()==null)botonDejar.setVisible(false);	//si no tiene objeto no puede dejar nada
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
		//boton ACCEPTAR PETION ->3
		botonDar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Dar");
				boleanDameAccion = rudolf.dameAccion(true); //-> esto da el objeto a quien se lo estÃ¯Â¿Â½ pidiendo
				botonDar.setVisible(false);	//AL pulsarlo significa que le estÃ¯Â¿Â½s dando el objeto a alguien
				ocultarTodo();//AL haber pusado un botÃ¯Â¿Â½n se ocultan los demÃ¯Â¿Â½s y se acaba la ronda
				
			}
		});
		botonDar.setBounds(525, 326, 97, 40);
		contentPane.add(botonDar);
		if(rudolf.getPeticion()==null)
			botonDar.setVisible(false);

		//BOTON PEDIR OBJETO	->4
		botonPedir.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				System.out.println("Adios");
				irASeleccionJugador();		//Se imprimen todos los posibles y luego seleccionas
			}
		});
		botonPedir.setBounds(1000, 123, 97, 40);
		contentPane.add(botonPedir);		//El boton add ya lo hace visible al crearlo
		if(ru.getObjeto()==null){
			botonPedir.setVisible(false);
		}

		//BOTON PASAR TURNO
			botonPasarTurno.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					boleanDameAccion=rudolf.dameAccion(false);	//	
					ocultarTodo();
					
				}
			});	
			botonPasarTurno.setBounds(307, 326, 97, 40);	
			contentPane.add(botonPasarTurno);					
			
			//Turnos	
		//texto del turno
		txtpnTurnoJorge = new JTextPane();
		txtpnTurnoJorge.setText("Turno "+ronda);
		txtpnTurnoJorge.setBounds(700, 13, 97, 22);
		contentPane.add(txtpnTurnoJorge);
	
		
	
		//BOTON VOLVER		
		botonVolver = new JButton("Volver");
		botonVolver.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				volverASeleccionAccion();
				}
			});
		botonVolver.setBounds(700, 451, 74, 25);
		contentPane.add(botonVolver);

		
		//HABITACIONES
			//MOVERSE A LUGARES
		botonLugar.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				
				System.out.println("Lugares");
					irASeleccionLugar();//AL haber pusado un boton se ocultan los demas y se acaba la ronda
				
				}
			});
		
		botonLugar.setBounds(12, 34, 97, 40);
		contentPane.add(botonLugar);
		botonLugar.setVisible(true);
		
			//LISTA DE Objetivos
		txtLiObjetivo = new JTextField( ("Tus objetivos:\n"+(Objetivo)rudolf.getObjetivo()).toString() );	
		txtLiObjetivo.setBounds(12, 123, 235, 165);
		contentPane.add(txtLiObjetivo);
		informarEstado();
			//Asegurarse de que todas las cosas se ven com0 deben
		ocultarBotonesAccion();
		mostrarBotonesAccion();
		System.out.println("RUDOLF TIENE UN OBJETO?"+(rudolf.getObjeto()!=null?rudolf.getObjeto().getNombre():"No tiene objeto"));
	}
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private void irASeleccionJugador(){	//oculta bootnes y muestra bootnes para seleccoinar a que persona le pides el objeto 
		setPersonasAdyacentes();	//inicializa los botones de persona y carga el tamanio de persona
		System.out.println("Size per: "+ sizePer);
		ocultarBotonesAccion(); //se ocultan los botones de las acciones para mostrar estos en su lugar 
		
		
		int maxPorFila= 3, numFilas=(sizePer/maxPorFila)+1;
		int posActualX=SUBZONA_TOP_IZ_X;	//variables temporales para almecenar las X/Y de cada boton
		int posActualY=SUBZONA_TOP_IZ_Y;
		int IncX,IncY;
		IncY=(SUBZONA_BOT_DER_Y-SUBZONA_TOP_IZ_Y)/(numFilas); //Calculamos el NumDeFilas y lo usamo como denominador para dividir la altura maxima
		IncX = (SUBZONA_BOT_DER_X-SUBZONA_TOP_IZ_X)/maxPorFila;
		//preparacion
		//Boton volver para poder volver a la seleccion de accion
		botonVolver.setVisible(true);
		
		/*
		 * Para poder impirmir los botones y que se ajusten a un tama�o predeterminado en el ejeX e Y debemos hacer todo esto
		 * 
		 * 
		 * */
		int i=0; 		
		for(i=0;i<((sizePer/maxPorFila)*maxPorFila);++i){	//est va hasta el maximo multiplo de 3 menor que el maximo de botones 
			System.out.println(i);
			
			botonPer[i].setBounds(posActualX,posActualY,IncX,IncY);
			contentPane.add(botonPer[i]);
			posActualX+=IncX;
			
			
				if(((i+1)%maxPorFila==0)&&(i!=0)){			//si el boton es multiplo de 3 y por ende es nueva fila, se incrementa la Y
					posActualX =  SUBZONA_TOP_IZ_X;			//la X inicial se resetea
					posActualY += IncY;						//se incrementa la Y(baja una fila)
				}
			
		}
		//Se pueden juntar ambos bucles con un if, pero como el momento en el que
		//se cumple ese if es constante pues es mejor separarlos en 2 continuos ynos ahorramos el if
		try {
			IncX=(SUBZONA_BOT_DER_X-SUBZONA_TOP_IZ_X)/(sizePer-i);		
		} catch (Exception e) {
			
		}
		//La segunda parte del bucle
		for(;i<sizePer;++i){
			//System.out.println(i);
			botonPer[i].setBounds(posActualX,posActualY,IncX,IncY);
			contentPane.add(botonPer[i]);
			posActualX+=IncX;
			}	
		//Le aniado las acciones a realizar y los muestro (yo lo habria metido en los otros bucles pero no le gustaba a java)
		if(sizePer!=0){
			for(BotonDePersona a : botonPer){
				a.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						boleanDameAccion = rudolf.dameAccion(a); 
						ocultarTodo();
					}
				});
				a.setVisible(true);
			}	
		}
	}
	
	private void irASeleccionObjeto(){	//oculta bootnes y muestra botones para seleccoinar a que objeto recoger del suelo
			//Data Para que funcione
		setObjetosSuelo();		//setea los nuevos valores del metodo 
		ocultarBotonesAccion(); //se ocultan los botones de las acciones para mostrar estos en su lugar
		
		int maxPorFila= 3, numFilas=(sizeObj/maxPorFila)+1;
		int posActualX=SUBZONA_TOP_IZ_X;	//variables temporales para almecenar las X/Y de cada boton
		int posActualY=SUBZONA_TOP_IZ_Y;
		int IncX,IncY;
		IncY=(SUBZONA_BOT_DER_Y-SUBZONA_TOP_IZ_Y)/(numFilas); //Calculamos el NumDeFilas y lo usamo como denominador para dividir la altura maxima
		IncX = (SUBZONA_BOT_DER_X-SUBZONA_TOP_IZ_X)/maxPorFila;
		
		//PAra poder vovler
		botonVolver.setVisible(true);
		
		
		int i=0; 		
		for(i=0;i<((sizeObj/maxPorFila)*maxPorFila);++i){	//desde 0 hasta la ultima columna fila todos son iguales
			
			botonSuelo[i].setBounds(posActualX,posActualY,IncX,IncY);
			botonSuelo[i].setVisible(true);
			contentPane.add(botonSuelo[i]);
			posActualX+=IncX;
			
				if(((i+1)%maxPorFila==0)&&(i!=0)){							
					posActualX =  SUBZONA_TOP_IZ_X;
					posActualY += IncY;				
				}			
		}
		try {
			IncX=(SUBZONA_BOT_DER_X-SUBZONA_TOP_IZ_X)/(sizeObj-i);		
		} catch (Exception e) {
		}
			
		for(;i<sizeObj;++i){
			botonSuelo[i].setBounds(posActualX,posActualY,IncX,IncY);
			botonSuelo[i].setVisible(true);
			contentPane.add(botonSuelo[i]);
			posActualX+=IncX;
		}	
		
		for(BotonDeCoger a : botonSuelo){
			a.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					boleanDameAccion = rudolf.dameAccion(a); 
					ocultarTodo();
				}
			});
			a.setVisible(true);
		}	
	}

	private void irASeleccionLugar(){
			//Data Para que funcione
		ocultarBotonesAccion(); //se ocultan los botones de las acciones para mostrar estos en su lugar 
		setLugares();		//setea los nuevos valores del metodo 
		botonVolver.setVisible(true);
		System.out.println("Tama�o sizeLugar"+ sizeLugar);
			
		int maxPorFila= 3, numFilas=(sizeLugar/maxPorFila)+1;
		int posActualX=SUBZONA_TOP_IZ_X;	//variables temporales para almecenar las X/Y de cada boton
		int posActualY=SUBZONA_TOP_IZ_Y;
		int IncX,IncY;
		IncY=(SUBZONA_BOT_DER_Y-SUBZONA_TOP_IZ_Y)/(numFilas); //Calculamos el NumDeFilas y lo usamo como denominador para dividir la altura maxima
		IncX = (SUBZONA_BOT_DER_X-SUBZONA_TOP_IZ_X)/maxPorFila;
			
		botonVolver.setVisible(true);
		
			
		int i=0; 		
		for(i=0;i<((sizeObj/maxPorFila)*maxPorFila);++i){	//desde 0 hasta la ultima columna fila todos son iguales	
			botonesLugares[i].setBounds(posActualX,posActualY,IncX,IncY);
			botonesLugares[i].setVisible(true);
			contentPane.add(botonesLugares[i]);
			posActualX+=IncX;
				
				if(((i+1)%maxPorFila==0)&&(i!=0)){		//si el boton es multiplo de 3 y por ende es nueva fila, se incrementa la Y
					posActualX =  SUBZONA_TOP_IZ_X;	//la X inicial
					posActualY += IncY;				//se incrementa la Y(baja una fila)
				}			
		}
		try {
			IncX=(SUBZONA_BOT_DER_X-SUBZONA_TOP_IZ_X)/(sizeObj-i);		
			} catch (Exception e) {
		}
			
		for(;i<sizeLugar;++i){
			botonesLugares[i].setBounds(posActualX,posActualY,IncX,IncY);
			botonesLugares[i].setVisible(true);
			contentPane.add(botonesLugares[i]);
			posActualX+=IncX;
		}	
		
		for(BotonDeMoverse a : botonesLugares){
			a.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					boleanDameAccion = rudolf.dameAccion(a); 
					ocultarTodo();//AL pulsar un boton se acaba la ronda y se ocultan los demas botones hasta el inicio de la siguiente ronda(GM mande se�al)
					
				}
			});
			a.setVisible(true);
		}	
		
	}
	
	private void volverASeleccionAccion() {	//Este sirve para ocultar ambos, 
		
		ocultarPersonas();	//volver invisibles y elimina los botones qeu haya creado antes
		ocultarObjetos();	
		ocultarLugares();	
		mostrarBotonesAccion();//mostrar los botones de accion 
	}
	
	//Elimina las personas de la pantalla
	private void ocultarPersonas(){	
		if(botonPer!=null){		//nos aseguramos de que hay algo que recorrer antes de ocultarlo
			for(BotonDePersona a : botonPer){
				a.setVisible(false);
				contentPane.remove(a);
			}
		}
	}
	private void ocultarObjetos(){	
		if(botonSuelo!=null){
			for(BotonDeCoger a : botonSuelo){
				a.setVisible(false);
				contentPane.remove(a);
			}
		}
	}
	private void ocultarLugares(){	
		if(botonesLugares!=null){
			for(BotonDeMoverse a : botonesLugares){
				a.setVisible(false);
				contentPane.remove(a);
			}
		}
	}
	private void mostrarBotonesAccion(){

		if(rudolf.getObjeto()!=null){	//Si tiene objeto
			botonDejar.setVisible(true);
		}else{						//Si no tiene objeto
			botonRecoger.setVisible(true);
			botonPedir.setVisible(true);
		}
		botonPasarTurno.setVisible(true);
		botonVolver.setVisible(false); 	
		botonLugar.setVisible(true);
		}
	private void ocultarBotonesAccion(){
		botonRecoger.setVisible(false);
		botonDejar.setVisible(false);
		botonPasarTurno.setVisible(false);
		botonPedir.setVisible(false);
		botonLugar.setVisible(false);
	}

	private void setPersonasAdyacentes(){
		sizePer=0; //actualizamos a 0
		int i=0;	
		for (Agente a :  ru.getAgentes() ){ //contador 
			if(a==null){
				continue;
			}
			++sizePer;
			System.out.println("SizePer"+sizePer);
		}
		sizePer--;	//no me quiero contar a mi mismo
		if(sizePer!=0){
				botonPer = new BotonDePersona[sizePer]; //al haber empezado en 0 no me hace falta el sizePer-1 para almacenar uno menos									
			
			for( Agente a : ru.getAgentes()){
				if(a.getNombre() != rudolf.getNombre()){			//Como la funcion tmb me devuelve mi propio jugador
					botonPer[i]=new BotonDePersona(a);		//si lo ve, lo ignora y guarda al resto
					botonPer[i].setVisible(true);
					++i;
				}
			}
		}
	}
	private void setObjetosSuelo(){
		sizeObj=0;
		int i=0;	
		for ( Objeto a : ru.getObjeto()){ //contador de tamanio
			++sizeObj;
		}
		botonSuelo = new BotonDeCoger[sizeObj]; 		
		for( Objeto a : ru.getObjeto()){
						
				botonSuelo[i]=new BotonDeCoger(a);	
				botonSuelo[i].setVisible(true);	
				++i;
		}	
	}
	private void setLugares(){
		System.out.println(ru.getObjeto());
		System.out.println(ru.getLugares());
		sizeLugar =0;
		int i=0;	
		for (@SuppressWarnings("unused") Lugar a : ru.getLugares()){ 	//Solo es un contador
			++sizeLugar;
		}
		botonesLugares = new BotonDeMoverse[sizeLugar]; 		
		for( Lugar a : ru.getLugares()){
						
				botonesLugares[i] = new BotonDeMoverse(a);		
				botonesLugares[i].setVisible(true);	
				++i;
		}	
		
		
	}
	
	private void ocultarTodo(){	//Al pulsar cualquier boton debemos esconder todo menos las peticiones
		
		ocultarBotonesAccion();	//oculto todos los botones de acciones
		
		if(sizePer !=0  &&  botonPer != null){
			for(BotonDePersona a : botonPer){
				a.setVisible(false);
			}
			botonPer=null;
		}
		if(sizeLugar!=0 && botonesLugares!=null){
			for (BotonDeMoverse l : botonesLugares) {
				l.setVisible(false);
			}
			botonesLugares=null;
		}
		if(sizeObj!=0 && botonSuelo!=null){
			for(BotonDeCoger a : botonSuelo){
				a.setVisible(false);
			}
			botonSuelo=null;
		}
		//la lista con el objetivo del jugador
		txtLiObjetivo.setVisible(false);
		
			//mostar texto con la ronda
		txtpnTurnoJorge.setText("Turno "+ ronda);	//Actualizada cada ronda
		txtpnTurnoJorge.setVisible(false);
		botonDar.setVisible(false);
		botonVolver.setVisible(false);
		
		reanudarRonda();			//Devuelve el control al main
	}
	
	//con esto se muestra todo tras un cambio de turno
	private void mostrarTodo(){
			//los botones de acciones posibles
		mostrarBotonesAccion();	
		botonVolver.setVisible(false);
			//la lista con el objetivo del jugador
		txtLiObjetivo.setVisible(true);
			//mostar texto con la ronda
		txtpnTurnoJorge.setText("Turno "+ ronda);	//Actualizada cada ronda
		txtpnTurnoJorge.setVisible(true);
	}

	private void comprobarPeticion(){
		botonDar.setVisible(rudolf.getPeticion()!=null);
	}
	private void informarEstado(){ //La pesta�a informativa
		JOptionPane.showMessageDialog(null, "Tu Objeto:"+((rudolf.getObjeto()!=null)?(rudolf.getObjeto().getNombre()):("Ninguno"))+"\n Tu habitacion:"+(rudolf.getLugar()!=null?rudolf.getLugar().getNombre(): "Error con la habitacion"  ),"Informe situacion:", JOptionPane.ERROR_MESSAGE);             
	}
	public boolean getBoleanDameAccion() {
		return this.boleanDameAccion;
	}
	
	public void setRonda(){
		ronda++;				//EL contaodr de las rondas que pasan
		rudolf=(Jugador)GameManager.getJugador();
		ru=rudolf.getLugar();
		System.out.println("ESTA DSPIERTA");
		comprobarPeticion();	//Revisa si hay una peticion a mi jugador y si la hay muestra el boton de dar
		mostrarTodo();		//pongo en visible a las cosas
		informarEstado();	//JPane con la ubicacion y el objeto
		
	}
	
	private void reanudarRonda() {		//Se llama en ocultar todo
	    ((GameManager)GameManager.getGMins()).ronda();
	}
}
