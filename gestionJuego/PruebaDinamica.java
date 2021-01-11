package gestionJuego;

import datos.Peticion;
import elementosNarrativos.Agente;
import elementosNarrativos.Jugador;
import elementosNarrativos.Lugar;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.omg.CORBA.PUBLIC_MEMBER;

import javax.swing.JSlider;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import java.awt.CardLayout;
import java.awt.GridBagLayout;
import javax.swing.JTextPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.JList;
import javax.swing.AbstractListModel;

public class PruebaDinamica extends JFrame {
	//private boolean boleanoDelVotonDeDar=false;
	
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
	
	//persona
	private Jugador rudolf; 
	private Lugar ru ;
	
	//private String[] jugadoresAdyacentes;
	private BotonDePersona[] botonPer;
	private int sizePer;
	//lugares
	 //private String[] nombreLugares;
	 private BotonDeMoverse[] botonesLugares = new BotonDeMoverse[3];
	
	 //Numero de ronda
	 private int ronda;
	/**
	 * Launch the application.
	 
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Agente rudolf= new Jugador("Gerard") {
					
					}; 
					PruebaDinamica frame = new PruebaDinamica(rudolf);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
*/
	/**
	 * Create the frame.
	 */
	public PruebaDinamica() {
		
		rudolf= (Jugador) GameManager.getJugador();	//debo tratarlo como jugador porque si no no puedo invocar los metodos propios del jugador
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
				System.out.println("Hola");
				recogerObj();	//crea e imprime la lista de objetos disponibles en la sala
				//rudolf.dameAccion(botonDeCoger);
			}
		});
		botonRecoger.setBounds(307, 123, 97, 40);
		contentPane.add(botonRecoger);
		
		//BOTON DEJAR EN LA SALA ->2	//Terminado
		BotonDeCoger botDejar = new BotonDeCoger();
		botDejar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(rudolf.getObjeto()!=null)
					rudolf.dameAccion(botDejar);	// ->dameAccion(null) es 
			}
		});
		botDejar.setBounds(525, 326, 97, 40);
		contentPane.add(botDejar);
		rudolf.dameAccion();
		
		//boton ACCEPTAR PETION ->3
		botonDar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Dar");
				rudolf.dameAccion(true); //-> esto da el objeto a quien se lo estÈ pidiendo
				botonDar.setVisible(false);	//AL pulsarlo significa que le est·s dando el objeto a alguien
				
			}
		});
		botonDar.setBounds(525, 326, 97, 40);
		contentPane.add(botonDar);
		botonDar.setVisible(false);
		
		//BOTON PEDIR OBJETO	->4
		botonPedir.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				System.out.println("Adios");
				
				irASeleccionJugador();
			}
		});
		botonPedir.setBounds(525, 123, 97, 40);
		contentPane.add(botonPedir);
		
		//BOTON PASAR TURNO
			botonPasarTurno.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
				rudolf.dameAccion(null);	//dameAccion(null) -> 		
				}
			});	
			botonPasarTurno.setBounds(307, 326, 97, 40);	
			contentPane.add(botonPasarTurno);					
			
			//Turnos	
		//texto del turno
		int i=0;
		JTextPane txtpnTurnoJorge = new JTextPane();
		txtpnTurnoJorge.setText("Turno "+i);
		txtpnTurnoJorge.setBounds(525, 13, 97, 22);
		contentPane.add(txtpnTurnoJorge);
		
		//BOTON VOLVER
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
		JButton botonVolver = new JButton("Volver");
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
		botonesLugares[0].setBounds(12, 34, 97, 40);
		contentPane.add(botonesLugares[0]);
		botonesLugares[0].setVisible(false);
			//SALA DERECHA
		botonesLugares[1]=new BotonDeMoverse(null);
		botonesLugares[1].setBounds(161, 196, 97, 40);
		contentPane.add(botonesLugares[1]);
		botonesLugares[1].setVisible(false);
		
		
			//SALA ABAJO
		botonesLugares[2]=new BotonDeMoverse(null);
		
		botonesLugares[2].addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				rudolf.dameAccion(botonesLugares[2]);
				System.out.println("Habitacion");
				//rudolf.getNombre();
				}
			});
		
		botonesLugares[2].setBounds(12, 326, 97, 40);
		contentPane.add(botonesLugares[2]);
		botonesLugares[2].setVisible(false);
		
		//BOTON W
		
		JButton botonW = new JButton("W");
		botonW.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				botonW.setBackground(Color.RED);
				setPeticion();
				
			}
		});
		botonW.setForeground(Color.BLACK);
		botonW.setBackground(Color.WHITE);
		botonW.setBounds(548, 451, 74, 25);
		contentPane.add(botonW);
			
			//LISTA DE CREENCIAS
		JList lista = new JList();
		lista.setModel(new AbstractListModel() {
			String[] values = new String[] {"1", "2", "3", "4"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		lista.setBounds(12, 123, 135, 165);
		contentPane.add(lista);
		
	}
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private void irASeleccionJugador(){
		
		int maxPorFila= 3, numFilas=(sizePer/maxPorFila)+1;
		int posActualX=SUBZONA_TOP_IZ_X;	//variables temporales para almecenar las X/Y de cada boton
		int posActualY=SUBZONA_TOP_IZ_Y;
		int IncX,IncY;
		IncY=(SUBZONA_BOT_DER_Y-SUBZONA_TOP_IZ_Y)/(numFilas); //Calculamos el NumDeFilas y lo usamo como denominador para dividir la altura maxima
		IncX = (SUBZONA_BOT_DER_X-SUBZONA_TOP_IZ_X)/maxPorFila;
		//preparacion
		//Calcular cuantos botones hay que mostrar y por ende cuanto espacio tiene cada bot√≥n
		
		//botones
		botonPer=new BotonDePersona[sizePer];
		
		ocultarBotonesAccion(); //se ocultan los botones de las acciones para mostrar estos en su lugar 
		
		int i=0; 		
		for(i=0;i<((sizePer/maxPorFila)*maxPorFila);++i){	//desde 0 hasta la ultima columna fila todos son iguales
			
			
			botonPer[i].setBounds(posActualX,posActualY,IncX,IncY);
			botonPer[i].setVisible(true);
			contentPane.add(botonPer[i]);
			posActualX+=IncX;
			
			
				if(((i+1)%maxPorFila==0)&&(i!=0)){								//si el boton es multiplo de 3 y por ende es nueva fila, se incrementa la Y
					posActualX =  SUBZONA_TOP_IZ_X;//la X inicial
					posActualY += IncY;				//se incrementa la Y(baja una fila)
				}
			
		}
		//Se podr√≠an juntar ambos bucles con un if, pero como el momento en el que
		//se cumple ese if es constante pues es mejor separarlos en 2 contiguos
		try {
			IncX=(SUBZONA_BOT_DER_X-SUBZONA_TOP_IZ_X)/(sizePer-i);		//me siento sucio s√≠, al ser multiplo de 3 numJug e i se igualar√≠an
		} catch (Exception e) {
			
		}
			
		for(;i<sizePer;++i){
			botonPer[i].setBounds(posActualX,posActualY,IncX,IncY);
			botonPer[i].setVisible(true);
			contentPane.add(botonPer[i]);
			posActualX+=IncX;
		}	
		
		//luego se ve cuantos jugadores hay y cuales son y crear un boton por cada uno
		
	}
	
	//solo para probar que funciona, luego habra que sustituirlo con una funcion del GameManager que devuelva quien esta en la misma sala que tu
	
	private void volverASeleccionAccion() {
		if()ocultarPersonas();//volver invisibles a lso jugadores y sus botones 
		ocultarObjetos();	//vuelve invisibles los objetos de la sala si los hay
		mostrarBotonesAccion();//mostrar los botones de accion 
	}
	
	private void ocultarBotonesAccion(){
		botonRecoger.setVisible(false);
		botonDar.setVisible(false);
		botonPasarTurno.setVisible(false);
		botonPedir.setVisible(false);
	}
	
	private void ocultarPersonas(){	//ERROR DE NULLPOINTER EXCEPTION
		for(JButton a : botonPer){
			a.setVisible(false);
			contentPane.remove(a);
		}
	}
	
	
	
	private void mostrarBotonesAccion(){
		botonRecoger.setVisible(true);
		botonDar.setVisible(true);
		botonPasarTurno.setVisible(true);
		botonPedir.setVisible(true);
	}
	
	//funcion invocada desde el GM para informarnos de que le han hecho una peticion (del objeto)
	public void setPeticion(){
		botonDar.setVisible(true);
	}
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private void setJugadoresAdyacentes(){
		sizePer=0; //actualizamos a 0
		int i=0;	//podrÌamos inicializarla mas abajo pero pereza be like
		
		for (@SuppressWarnings("unused") Agente a : ru.getAgentes()){ //contador de tamaÒo
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
	
	private void setLugares(){
		int i=0;
		for(Lugar lugar: ru.getLugares()){
			botonesLugares[i]= new BotonDeMoverse(lugar);
			botonesLugares[i].setVisible(true);
			++i;
		}
	}
	
	ocultarTodo()	//Al pulsar cualquier boton debemos esconder los dem·s
	mostrarTodo()	//Al empezar nueva ronda debemos mostrar los botones iniciales;
	
	public boolean setRonda(int ronda){
		this.ronda=ronda;
		mostrarTodo();
		return ;
	}
	
}
