import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import org.jfree.ui.RefineryUtilities;

public class Cliente {
	public static void main(String[] args) {
		try (Socket c = new Socket("localhost", 8080);
				//	BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()));
				DataInputStream inSocket = new DataInputStream(c.getInputStream());
//					DataInputStream infich = new DataInputStream(new FileInputStream(nombre));
					DataOutputStream outSocket = new DataOutputStream(c.getOutputStream());
					//Writer w = new OutputStreamWriter(c.getOutputStream());
					)
			{
			boolean conexion=true;
			String servidor;
			Scanner entrada= new Scanner (System.in);
			while(conexion) {
				servidor=inSocket.readUTF();
				if(servidor.equals("close")) {
					conexion=false;					
				}
				else if(servidor.contains("Introduce")) {
					System.out.println(servidor);	
					outSocket.writeUTF(entrada.next());
				}
				else if(servidor.contains("gráficas de entrenamiento")) {
					SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
					ArrayList<Entrenamiento> l= new ArrayList<>();
					servidor=inSocket.readUTF();
						while (!servidor.equals("Listo")) {
							 String [] partes = servidor.split(",");
							 Date fecha;
							try {
								fecha = sdf.parse(partes[0]);
							
							 String nombre = partes[1];
							 String[] repeticiones = partes[2].split("-");
							 ArrayList<Integer> lrep = new ArrayList<>();
									 for(String r : repeticiones) {
										 lrep.add(Integer.parseInt(r));
									 }
									 int series = Integer.parseInt(partes[3]);
									 String[] pesos = partes[4].split("-");
									 ArrayList<Double> lpesos = new ArrayList<>();
									 for(String p : pesos) {
										 lpesos.add(Double.parseDouble(p));
									 }
									 l.add(new Entrenamiento(fecha,nombre,lrep,series,lpesos));
									 servidor=inSocket.readUTF();
									 } catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
				    	   graficaEntrenamiento grafica = new graficaEntrenamiento("Grafica Entrenamiento",l);
				    	   grafica.pack();
				           RefineryUtilities.centerFrameOnScreen(grafica);
				           grafica.setVisible(true);
					}
					
				else {
					System.out.println(servidor);							
				}			
			}
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
}
