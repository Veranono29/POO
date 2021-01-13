package gestionJuego;

import elementosNarrativos.Agente;
import elementosNarrativos.Jugador;
import elementosNarrativos.Lugar;
import elementosNarrativos.Objeto;

import datos.Objetivo;

import javax.swing.JFrame;
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
	private final int MARCO_X=100,MARCO_Y=100,ANCHO_MARCO=700,ALTO_MARCO=550;	//EL MARCO
	
	private final int anchoBoton=97,altoBoton=40;
		//Como los botones de accion van a ser sustituidos por las personas al pulsar el boton DAR
	private final int SUBZONA_TOP_IZ_X = 307, SUBZONA_TOP_IZ_Y = 123;	
	private final int SUBZONA_BOT_DER_X = 525+anchoBoton, SUBZONA_BOT_DER_Y = 326+altoBoton;
	
	private JPanel contentPane;
		//BOTONES ACCIONES
	private JButton botonRecoger = new JButton("Recoger");	//Hay que implementarlo
	private JButton botonPasarTurno = new JButton("Pasar");	//dameAccion(false)
	private JButton botonDar = new JButton("Dar");
	private JButton botonPedir = new JButton("Pedir");
	private BotonDeCoger botonDejar = new BotonDeCoger();
	private JButton botonVolver;
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
	 private BotonDeMoverse[] botonesLugares = new BotonDeMoverse[3];
	 	//Numero de ronda
	 JTextPane txtpnTurnoJorge;
	 private int ronda;
	 	//Objetivos
	 JTextField txtLiObjetivo;
	 
	public PruebaDinamica(Agente pepe) {
		
		rudolf= (Jugador) pepe;	//debo tratarlo como jugador porque si no no puedo invocar los metodos propios del jugador
		ru =rudolf.getLugar(); 
		
		//inicializar cosas
		botonesLugares=new BotonDeMoverse[3];
		
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
				if(rudolf.getObjeto()!=null){
					boleanDameAccion= rudolf.dameAccion(botonDejar);	// ->dameAccion(null) es 
					ocultarTodo();//AL haber pusado un botï¿½n se ocultan los demï¿½s y se acaba la ronda
					reanudarRonda();	//Devolver control al main
				}	
			}
		});
		botonDejar.setBounds(525, 326, 97, 40);
		contentPane.add(botonDejar);
		if(rudolf.getObjeto()==null)botonRecoger.setVisible(false);	//si no tiene objeto no puede dejar nada
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
		//boton ACCEPTAR PETION ->3
		botonDar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Dar");
				boleanDameAccion = rudolf.dameAccion(true); //-> esto da el objeto a quien se lo estï¿½ pidiendo
				botonDar.setVisible(false);	//AL pulsarlo significa que le estï¿½s dando el objeto a alguien
				ocultarTodo();//AL haber pusado un botï¿½n se ocultan los demï¿½s y se acaba la ronda
				reanudarRonda();
			}
		});
		botonDar.setBounds(525, 326, 97, 40);
		contentPane.add(botonDar);
		botonDar.setVisible(false);
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		//BOTON PEDIR OBJETO	->4
		botonPedir.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				System.out.println("Adios");
				
				irASeleccionJugador();		//Se imprimen todos los posibles y luego seleccionas
				
			}
		});
		botonPedir.setBounds(525, 123, 97, 40);
		contentPane.add(botonPedir);		//El boton add ya lo hace visible al crearlo
		if(ru.getObjeto()==null){
			botonPedir.setVisible(false);
		}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		//BOTON PASAR TURNO
			botonPasarTurno.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					boleanDameAccion=rudolf.dameAccion(false);	//	
					ocultarTodo();//AL haber pusado un botï¿½n se ocultan los demï¿½s y se acaba la ronda
					reanudarRonda();
				}
			});	
			botonPasarTurno.setBounds(307, 326, 97, 40);	
			contentPane.add(botonPasarTurno);					
			
			//Turnos	
		//texto del turno
		
		JTextPane txtpnTurnoJorge = new JTextPane();
		txtpnTurnoJorge.setText("Turno "+ronda);
		txtpnTurnoJorge.setBounds(525, 13, 97, 22);
		contentPane.add(txtpnTurnoJorge);
		
	
		//BOTON VOLVER
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
		botonVolver = new JButton("Volver");
		botonVolver.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				volverASeleccionAccion();
				}
			});
		botonVolver.setBounds(431, 451, 74, 25);
		contentPane.add(botonVolver);
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		//HABITACIONES
			//SALA ARRIBA
		botonesLugares[0]=new BotonDeMoverse(null);
		botonesLugares[0].addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				boleanDameAccion=rudolf.dameAccion(botonesLugares[0]);
				System.out.println("Sala Arriba 1");
				ocultarTodo();//AL haber pusado un boton se ocultan los demas y se acaba la ronda
				reanudarRonda();
				}
			});
		botonesLugares[0].setBounds(12, 34, 97, 40);
		contentPane.add(botonesLugares[0]);
		botonesLugares[0].setVisible(false);
			
			//SALA DERECHA
		botonesLugares[1]=new BotonDeMoverse(null);
		botonesLugares[1].addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				boleanDameAccion = rudolf.dameAccion(botonesLugares[1]);
				System.out.println("Sala Derecha 2");
				ocultarTodo();//AL haber pusado un boton se ocultan los demas y se acaba la ronda
				reanudarRonda();
				}
			});
		botonesLugares[1].setBounds(161, 196, 97, 40);
		contentPane.add(botonesLugares[1]);
		botonesLugares[1].setVisible(false);
		
			//SALA ABAJO
		botonesLugares[2]=new BotonDeMoverse(null);
		botonesLugares[2].addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				boleanDameAccion=rudolf.dameAccion(botonesLugares[2]);
				System.out.println("Habitacion");
				ocultarTodo();//AL haber pusado un boton se ocultan los demas y se acaba la ronda
				reanudarRonda();
				}
			});
		botonesLugares[2].setBounds(12, 326, 97, 40);
		contentPane.add(botonesLugares[2]);
		botonesLugares[2].setVisible(false);	//Estan desactivados hasta que diga lo contrario
		
		
			//LISTA DE Objetivos
		txtLiObjetivo = new JTextField( ((Objetivo)rudolf.getObjetivo()).toString() );	
		txtLiObjetivo.setBounds(12, 123, 135, 165);
		contentPane.add(txtLiObjetivo);
		
	}
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private void irASeleccionJugador(){	//oculta bootnes y muestra bootnes para seleccoinar a que persona le pides el objeto 
		
		int maxPorFila= 3, numFilas=(sizePer/maxPorFila)+1;
		int posActualX=SUBZONA_TOP_IZ_X;	//variables temporales para almecenar las X/Y de cada boton
		int posActualY=SUBZONA_TOP_IZ_Y;
		int IncX,IncY;
		IncY=(SUBZONA_BOT_DER_Y-SUBZONA_TOP_IZ_Y)/(numFilas); //Calculamos el NumDeFilas y lo usamo como denominador para dividir la altura maxima
		IncX = (SUBZONA_BOT_DER_X-SUBZONA_TOP_IZ_X)/maxPorFila;
		//preparacion
		//Boton volver para poder volver a la seleccion de accion
		botonVolver.setVisible(true);
		//botones
		botonPer=new BotonDePersona[sizePer];
		
		ocultarBotonesAccion(); //se ocultan los botones de las acciones para mostrar estos en su lugar 
		setPersonasAdyacentes();	//inicializa los botnes de persona ycarga el tamaï¿½o de persona
		int i=0; 		
		for(i=0;i<((sizePer/maxPorFila)*maxPorFila);++i){	//desde 0 hasta la ultima columna fila todos son iguales
			
			
			botonPer[i].setBounds(posActualX,posActualY,IncX,IncY);
			contentPane.add(botonPer[i]);
			posActualX+=IncX;
			
			
				if(((i+1)%maxPorFila==0)&&(i!=0)){								//si el boton es multiplo de 3 y por ende es nueva fila, se incrementa la Y
					posActualX =  SUBZONA_TOP_IZ_X;//la X inicial
					posActualY += IncY;				//se incrementa la Y(baja una fila)
				}
			
		}
		//Se podrÃ­an juntar ambos bucles con un if, pero como el momento en el que
		//se cumple ese if es constante pues es mejor separarlos en 2 contiguos
		try {
			IncX=(SUBZONA_BOT_DER_X-SUBZONA_TOP_IZ_X)/(sizePer-i);		//me siento sucio, al ser multiplo de 3 numJug e i se igualaran creando /0
		} catch (Exception e) {
			
		}
		//La segunda parte del weonado	
		for(;i<sizePer;++i){
			botonPer[i].setBounds(posActualX,posActualY,IncX,IncY);
			contentPane.add(botonPer[i]);
			posActualX+=IncX;
			}	
		//Le aniado las acciones a realizar y los muestro (yo lo habria metido en los otros bucles pero no le gustaba a java)
		for(BotonDePersona a : botonPer){
			a.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					boleanDameAccion = rudolf.dameAccion(a); //-> esto da el objeto a quien se lo estï¿½ pidiendo
					ocultarTodo();//AL haber pusado un botï¿½n se ocultan los demï¿½s y se acaba la ronda
					reanudarRonda();
				}
			});
			a.setVisible(true);
		}	
	}
	
	private void irASeleccionObjeto(){	//oculta bootnes y muestra bootnes para seleccoinar a que objeto recoger del suelo
			//Data Para que funcione
		int maxPorFila= 3, numFilas=(sizeObj/maxPorFila)+1;
		int posActualX=SUBZONA_TOP_IZ_X;	//variables temporales para almecenar las X/Y de cada boton
		int posActualY=SUBZONA_TOP_IZ_Y;
		int IncX,IncY;
		IncY=(SUBZONA_BOT_DER_Y-SUBZONA_TOP_IZ_Y)/(numFilas); //Calculamos el NumDeFilas y lo usamo como denominador para dividir la altura maxima
		IncX = (SUBZONA_BOT_DER_X-SUBZONA_TOP_IZ_X)/maxPorFila;
		
		//PAra poder vovler
		botonVolver.setVisible(true);
		
		botonSuelo=new BotonDeCoger[sizeObj];
		
		ocultarBotonesAccion(); //se ocultan los botones de las acciones para mostrar estos en su lugar 
		setObjetosSuelo();		//setea los nuevos valores del metodo 
		int i=0; 		
		for(i=0;i<((sizeObj/maxPorFila)*maxPorFila);++i){	//desde 0 hasta la ultima columna fila todos son iguales
			
			botonSuelo[i].setBounds(posActualX,posActualY,IncX,IncY);
			botonSuelo[i].setVisible(true);
			contentPane.add(botonSuelo[i]);
			posActualX+=IncX;
			
				if(((i+1)%maxPorFila==0)&&(i!=0)){								//si el boton es multiplo de 3 y por ende es nueva fila, se incrementa la Y
					posActualX =  SUBZONA_TOP_IZ_X;//la X inicial
					posActualY += IncY;				//se incrementa la Y(baja una fila)
				}			
		}
		try {
			IncX=(SUBZONA_BOT_DER_X-SUBZONA_TOP_IZ_X)/(sizeObj-i);		//me siento sucio sÃ­, al ser multiplo de 3 numJug e i se igualarÃ­an
		} catch (Exception e) {
		}
			
		for(;i<sizeObj;++i){
			botonSuelo[i].setBounds(posActualX,posActualY,IncX,IncY);
			botonSuelo[i].setVisible(true);
			contentPane.add(botonSuelo[i]);
			posActualX+=IncX;
		}	
		//Le aï¿½ado las acciones a realizar y los muestro
		for(BotonDeCoger a : botonSuelo){
			a.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					boleanDameAccion = rudolf.dameAccion(a); //-> esto da el objeto a quien se lo estï¿½ pidiendo
					ocultarTodo();//AL haber pusado un botï¿½n se ocultan los demï¿½s y se acaba la ronda
					reanudarRonda();
				}
			});
			a.setVisible(true);
		}	
}

	private void volverASeleccionAccion() {	//Este sirve para ocultar ambos, 
		ocultarPersonas();//volver invisibles a lso jugadores y sus botones 
		ocultarObjetos();	//vuelve invisibles los objetos de la sala si los hay
		mostrarBotonesAccion();//mostrar los botones de accion 
	}
	
	//Elimina las personas de la pantalla
	private void ocultarPersonas(){	//ERROR DE NULLPOINTER EXCEPTION
		if(botonPer!=null){		//nos aseguramos de que hay algo que recorrer antes de ocultarlo
			for(JButton a : botonPer){
				a.setVisible(false);
				contentPane.remove(a);
			}
		}
	}
	private void ocultarObjetos(){	//ERROR DE NULLPOINTER EXCEPTION
		if(botonSuelo!=null){
			for(JButton a : botonSuelo){
				a.setVisible(false);
				contentPane.remove(a);
			}
		}
	}
	private void mostrarBotonesAccion(){

		if(ru.getObjeto()!=null){	//Si tiene objeto
			botonDejar.setVisible(true);
		}else{						//Si no tiene objeto
			botonRecoger.setVisible(true);
			botonPedir.setVisible(true);
		}
		botonPasarTurno.setVisible(true);
		botonVolver.setVisible(false); 		
		}
	private void ocultarBotonesAccion(){
		botonRecoger.setVisible(false);
		botonDejar.setVisible(false);
		botonPasarTurno.setVisible(false);
		botonPedir.setVisible(false);
	}
	//funcion invocada desde el GM para informarnos de que le han hecho una peticion (del objeto)
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private void setPersonasAdyacentes(){
		sizePer=0; //actualizamos a 0
		int i=0;	//podrï¿½amos inicializarla mas abajo pero pereza be like
		for (@SuppressWarnings("unused") Agente a : ru.getAgentes()){ //contador de tamaï¿½o
			++sizePer;
		}
		botonPer = new BotonDePersona[sizePer-1]; //al haber empezado en 0 no me hace falta el sizePer-1 para almacenar uno menos									
		for( Agente a : ru.getAgentes()){
			if(a.getNombre() != rudolf.getNombre()){			//Como la funcion tmb me devuelve mi propio jugador
				botonPer[i++]=new BotonDePersona(a);		//si lo ve, lo ignora y guarda al resto
				botonesLugares[i].setVisible(true);
			}
		}	
	}
	private void setObjetosSuelo(){
		sizeObj=0;
		int i=0;	//podrï¿½amos inicializarla mas abajo pero pereza be like
		for (@SuppressWarnings("unused") Objeto a : ru.getObjeto()){ //contador de tamaï¿½o
			++sizeObj;
		}
		botonSuelo = new BotonDeCoger[sizeObj]; //al haber empezado en 0 no me hace falta el sizePer-1 para almacenar uno menos				
		for( Objeto a : ru.getObjeto()){
						//Como la funcion tmb me devuelve mi propio jugador
				botonSuelo[i++]=new BotonDeCoger(a);		//si lo ve, lo ignora y guarda al resto
				botonSuelo[i].setVisible(true);	
		}	
	}
	private void setLugares(){
		int i=0;
		for(Lugar lugar: ru.getLugares()){					//Para cada lugar adyacente le asigno un boton
			botonesLugares[i].setLugar(lugar);				//Enlazo un nuevo lugar 
			botonesLugares[i].setName(lugar.getNombre());	//Actualizo el nombre
			botonesLugares[i].setVisible(true);
			++i;
		}
	}
	
	private void ocultarTodo(){	//Al pulsar cualquier boton debemos esconder todo menos las peticiones
		
		ocultarBotonesAccion();	//oculto todos los botones de acciones
		for(BotonDeMoverse a : botonesLugares){	//oculto los 3 botones de lugares
			a.setVisible(false);
		}
		//la lista con el objetivo del jugador
		txtLiObjetivo.setVisible(true);
			//mostar texto con la ronda
		txtpnTurnoJorge.setText("Turno "+ ronda);	//Actualizada cada ronda
		txtpnTurnoJorge.setVisible(true);
		
		botonVolver.setVisible(false);
	}
///////////////////////////////////////////////////////////////////////////////////////////////////////
	//con esto se muestra todo tras un cambio de turno
	private void mostrarTodo(){
			//los botones de acciones posibles
		mostrarBotonesAccion();	
		botonVolver.setVisible(false);
			//los botones de lugar solo los que puedas irte
		setLugares();	
			//la lista con el objetivo del jugador
		txtLiObjetivo.setVisible(true);
			//mostar texto con la ronda
		txtpnTurnoJorge.setText("Turno "+ ronda);	//Actualizada cada ronda
		txtpnTurnoJorge.setVisible(true);
	}
///////////////////////////////////////////////////////////////////////////////////////////////////////
	private void comprobarPeticion(){
		botonDar.setVisible(rudolf.getPeticion()!=null);
	}

	public boolean getBoleanDameAccion() {
		return this.boleanDameAccion;
	}
	
	public void setRonda(int ronda){
		this.ronda=ronda;
		comprobarPeticion();	//Revisa si hay una peticion a mi jugador
		mostrarTodo();
	}
	private void reanudarRonda() {
	    ((GameManager)GameManager.getGMins()).ronda();
	}
}
