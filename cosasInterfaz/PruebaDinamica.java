package teoria;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
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
	
	private final int MARCO_X=100,MARCO_Y=100,ANCHO_MARCO=700,ALTO_MARCO=550;	//EL MARCO
	
	private final int anchoBoton=97,altoBoton=40;
		//Como los botones de accion van a ser sustituidos por las personas al pulsar el boton DAR
	private final int SUBZONA_TOP_IZ_X = 307, SUBZONA_TOP_IZ_Y = 123;	
	
	private final int SUBZONA_BOT_DER_X = 525+anchoBoton, SUBZONA_BOT_DER_Y = 326+altoBoton;
	
	private JPanel contentPane;
		//BOTONES
	JButton botonRecoger = new JButton("Recoger");
	JButton botonPasarTurno = new JButton("Pasar");
	JButton botonDar = new JButton("Dar");
	JButton botonPedir = new JButton("Pedir");
	
	//persona
	//private Agente jugador; 
	
	//Botones de personas
	JButton[] botonPer;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PruebaDinamica frame = new PruebaDinamica();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public PruebaDinamica(/*Agente jugador*/) {
		//this.jugador=jugador;
		setTitle("Ekaitz, Raùl, Gerardo, Daniel");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(MARCO_X, MARCO_Y, ANCHO_MARCO,ALTO_MARCO);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//BOTON RECOGER	
		
		botonRecoger.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Hola");
				
			}
		});
		botonRecoger.setBounds(307, 123, 97, 40);
		contentPane.add(botonRecoger);
		
		//boton Dar
		
		botonDar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		botonDar.setBounds(525, 326, 97, 40);
		contentPane.add(botonDar);
		
		//BOTON PEDIR
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
		botonPedir.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				System.out.println("Adios");
				//cambio de frame
				irASeleccionJugador();

				//ocultarBotonesAccion();
				////
				
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
				//botonPedir.setVisible(false);
				
			}
		});
		botonPedir.setBounds(525, 123, 97, 40);
		contentPane.add(botonPedir);
		
		//texto del turno
		int i=0;
		JTextPane txtpnTurnoJorge = new JTextPane();
		txtpnTurnoJorge.setText("Turno "+i);
		txtpnTurnoJorge.setBounds(525, 13, 97, 22);
		contentPane.add(txtpnTurnoJorge);
		
		//BOTON VOLVER
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
		JButton botonVolver = new JButton("Volver");
		botonPedir.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				volverASeleccionAccion();
			}
		});
		botonVolver.setBounds(431, 451, 74, 25);
		contentPane.add(botonVolver);
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		JButton botonSalon = new JButton("Salon");
		botonSalon.setBounds(12, 34, 97, 40);
		contentPane.add(botonSalon);
		
		JButton botonPiscina = new JButton("Piscina");
		
		botonPiscina.setBounds(161, 196, 97, 40);
		contentPane.add(botonPiscina);
		
		//Boton Habitacion
		JButton botonHabitacion = new JButton("Habitacion");
		botonHabitacion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		botonHabitacion.setBounds(12, 326, 97, 40);
		contentPane.add(botonHabitacion);
		
		//Boton W
		JButton botonW = new JButton("W");
		botonW.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				botonW.setBackground(Color.RED);
			}
		});
		botonW.setForeground(Color.BLACK);
		botonW.setBackground(Color.WHITE);
		botonW.setBounds(548, 451, 74, 25);
		contentPane.add(botonW);
		
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
		
		
		botonPasarTurno.setBounds(307, 326, 97, 40);
		contentPane.add(botonPasarTurno);
	}
	
	private void irASeleccionJugador(){
		
	
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			//esto se debe cargar con el nombre del jugador
		String nombreProta="PEPE";
		String[] adyacentes=jugadoresAdyacentes(nombreProta); //esto debería devolver un String[] con el nombre/ID de los jugaodres adyacentes/de la misma habitacion
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		int maxPorFila= 3,numJugadores=adyacentes.length,numFilas=(numJugadores/maxPorFila)+1;
		int posActualX=SUBZONA_TOP_IZ_X;	//variables temporales para almecenar las X/Y de cada boton
		int posActualY=SUBZONA_TOP_IZ_Y;
		int IncX,IncY;
		IncY=(SUBZONA_BOT_DER_Y-SUBZONA_TOP_IZ_Y)/(numFilas); //Calculamos el NumDeFilas y lo usamo como denominador para dividir la altura maxima
		IncX = (SUBZONA_BOT_DER_X-SUBZONA_TOP_IZ_X)/maxPorFila;
		//preparacion
		//Calcular cuantos botones hay que mostrar y por ende cuanto espacio tiene cada botón
		
		//botones
		botonPer=new JButton[numJugadores];
		
		ocultarBotonesAccion(); //se ocultan los botones de las acciones para mostrar estos en su lugar 
		
		int i=0; 		
		for(i=0;i<((numJugadores/maxPorFila)*maxPorFila);++i){	//desde 0 hasta la ultima columna fila todos son iguales
			
			
			botonPer[i] = botonCrear(adyacentes[i],posActualX,posActualY,IncX,IncY);
			botonPer[i].setVisible(true);
			contentPane.add(botonPer[i]);
			posActualX+=IncX;
			
			
				if(((i+1)%maxPorFila==0)&&(i!=0)){								//si el boton es multiplo de 3 y por ende es nueva fila, se incrementa la Y
					posActualX =  SUBZONA_TOP_IZ_X;//la X inicial
					posActualY += IncY;				//se incrementa la Y(baja una fila)
				}
			
			
		}
		//Se podrían juntar ambos bucles con un if, pero como el momento en el que
		//se cumple ese if es constante pues es mejor separarlos en 2 contiguos
		try {
			IncX=(SUBZONA_BOT_DER_X-SUBZONA_TOP_IZ_X)/(numJugadores-i);		//me siento sucio sí, al ser multiplo de 3 numJug e i se igualarían
		} catch (Exception e) {
			
		}
			
		for(;i<numJugadores;++i){
			JButton boton = botonCrear(adyacentes[i],posActualX,posActualY,IncX,IncY);
			boton.setVisible(true);
			contentPane.add(boton);
			posActualX+=IncX;
		}	
		
		//luego se ve cuantos jugadores hay y cuales son y crear un boton por cada uno
		
	}
	
	//solo para probar que funciona, luego habrá qeu sustituirlo con una funcion del GameManager que devuelva quien está en la misma sala que tu
	private String[] jugadoresAdyacentes(String nombre){
		String[] pepe = {"Manuel","Rodrigo","jorge","maria"};
		return pepe;
		
	}
	private void volverASeleccionAccion() {
		ocultarPersonas();//volver invisibles a lso jugadores y sus botones 
		//mostrarBotonesAccion();//mostrar los botones de accion 
	}
	private void ocultarBotonesAccion(){
		botonRecoger.setVisible(false);
		botonDar.setVisible(false);
		botonPasarTurno.setVisible(false);
		botonPedir.setVisible(false);
	}
	private void ocultarPersonas(){	//ERROR DE NULLPOINTER EXCEPTION
		for (int i = 1;i< (botonPer.length-1);++i){
			botonPer[i].setVisible(false);
		}
		//for(JButton a : botonPer){
			//a.setVisible(false);
		//}
	}
	private JButton botonCrear(String nombre,int x, int y,int ancho, int alto){
		JButton boton = new JButton(nombre);
		boton.setBounds(x, y, ancho, alto);
		return boton;
	}
	private void mostrarBotonesAccion(){
		botonRecoger.setVisible(true);
		botonDar.setVisible(true);
		botonPasarTurno.setVisible(true);
		botonPedir.setVisible(true);
	}
	
}