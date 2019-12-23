import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Servidor {
	public static void main(String[] args) {
		try (ServerSocket server = new ServerSocket(8080);){
			while(true) {
				try (Socket cliente = server.accept();
						//BufferedReader br = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
						DataInputStream dis = new DataInputStream(cliente.getInputStream());
					DataOutputStream dos= new DataOutputStream(cliente.getOutputStream());
						//Writer w = new OutputStreamWriter(cliente.getOutputStream());
						){
					System.out.println("SE HA CONECTADO UN CLIENTE");

					String usuario;
					String contrasena;
					dos.writeUTF("Introduce usuario: ");
					
					usuario=dis.readUTF();
					dos.writeUTF("Introduce contrasena: ");
					contrasena=dis.readUTF();
					
					Usuario u = buscarUsuario(usuario,contrasena);
					if(u != null) {
//						dos.writeUTF("Bienvenido "+ u.getUser());
						System.out.println("SE HA CONECTADO:"+  u.getUser());
						int elegir;
						do {
						dos.writeUTF("Introduce una opcion  \r\n "
								+ "1.Devolver entrenamientos: \r\n"
								+ "2.Devolver salud:  \r\n"
								+ "3.Devolver grafica entrenamientos:  \r\n"
								+ "4.Devolver grafica salud:  \r\n"
								+ "5.Introducir entrenamiento:  \r\n"
								+ "6.Introducir salud: \r\n"
								+ "7.Borrar entrenamiento:  \r\n"
								+ "8.Borrar salud:  ");
						elegir=Integer.parseInt(dis.readUTF());
						switch (elegir){
						case 1:
							Date fechaIni;
							Date fechaFin;
							SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
							dos.writeUTF("Introduce fecha inicio (dd-MM-yyyy): ");
							
							fechaIni=sdf.parse(dis.readUTF());
							dos.writeUTF("Introduce fecha fin (dd-MM-yyyy): ");
							fechaFin=sdf.parse(dis.readUTF());
							ArrayList<Entrenamiento> entrenos = buscarEntrenamiento(u.getId(),fechaIni,fechaFin);
							if(entrenos.size()==0) {
								dos.writeUTF("No hay Entrenamientos para las fechas introducidas \r\n");
							}
							else {
							for(Entrenamiento e : entrenos) {
								dos.writeUTF(e.toString());
							}
							}
							break;
						case 2:							
							Date fechaIni2;
							Date fechaFin2;
							SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy");
							dos.writeUTF("Introduce fecha inicio (dd-MM-yyyy): ");
							
							fechaIni2=sdf2.parse(dis.readUTF());
							dos.writeUTF("Introduce fecha fin (dd-MM-yyyy): ");
							fechaFin2=sdf2.parse(dis.readUTF());
							ArrayList<Salud> salud = buscarSalud(u.getId(),fechaIni2,fechaFin2);
							if(salud.size()==0) {
								dos.writeUTF("No hay datos de salud para las fechas introducidas \r\n");
							}
							else {
							for(Salud s : salud) {
								dos.writeUTF(s.toString());
							}
							}
							break;
						case 3:
							Date fechaIni3;
							Date fechaFin3;
							String nombreej;
							SimpleDateFormat sdf3 = new SimpleDateFormat("dd-MM-yyyy");
							dos.writeUTF("Introduce fecha inicio (dd-MM-yyyy): ");
							
							fechaIni3=sdf3.parse(dis.readUTF());
							dos.writeUTF("Introduce fecha fin (dd-MM-yyyy): ");
							fechaFin3=sdf3.parse(dis.readUTF());
							dos.writeUTF("Introduce nombre ejercicio: ");
							nombreej=dis.readUTF();
							ArrayList<Entrenamiento> entrenos3 = buscarEntrenamientoNombre(u.getId(),fechaIni3,fechaFin3,nombreej);
							if(entrenos3.size()==0) {
								dos.writeUTF("No hay Entrenamientos para las fechas introducidas \r\n");
							}
							else {
							dos.writeUTF("Te envio los datos para las graficas de entrenamiento: ");
							for(Entrenamiento e : entrenos3) {
								dos.writeUTF(e.toString());
							}
							dos.writeUTF("Listo");
							}
							break;
						case 4:
							break;
						case 5:
							Date fecha;
							SimpleDateFormat sdf5 = new SimpleDateFormat("dd-MM-yyyy");
							String nombre;
							ArrayList<Integer> lrep = new ArrayList<>();
							int series;
							ArrayList<Double> lpesos = new ArrayList<>();
							dos.writeUTF("Introduce fecha: ");
							fecha=sdf5.parse(dis.readUTF());
							dos.writeUTF("Introduce nombre: ");
							nombre=dis.readUTF();
							dos.writeUTF("Introduce series: ");
							series=Integer.parseInt(dis.readUTF());
							for(int i=1;i<=series;i++) {
								dos.writeUTF("Introduce repeticiones de la " + i + " serie: ");
								lrep.add(Integer.parseInt(dis.readUTF()));
								dos.writeUTF("Introduce pesos(kg) de la " + i + " serie: ");
								lpesos.add(Double.parseDouble(dis.readUTF()));
							}
							Entrenamiento e=new Entrenamiento(fecha,nombre,lrep,series,lpesos);
							boolean existeEntrenamiento = buscarEntrenamientoExiste(u.getId(), e.getFecha(), e.getNombre());
							if (existeEntrenamiento) {
								dos.writeUTF("EL ENTRENAMIENTO YA EXISTE ");
							}
							else {
							if(anadirEntrenamiento(u.getId(), e)){
								dos.writeUTF("EL ENTRENAMIENTO SE HA ANADIDO ");
							}
							else {
								dos.writeUTF("EL ENTRENAMIENTO NO SE HA ANADIDO");
							}
							}
							break;
						case 6:
							Date fecha6;
							SimpleDateFormat sdf6 = new SimpleDateFormat("dd-MM-yyyy");
							double peso;
							int pulsaciones;
							double altura;
							double imc;
							dos.writeUTF("Introduce fecha: ");
							fecha6=sdf6.parse(dis.readUTF());
							dos.writeUTF("Introduce peso: ");
							peso=Double.parseDouble(dis.readUTF());
							dos.writeUTF("Introduce pulsaciones: ");
							pulsaciones=Integer.parseInt(dis.readUTF());
							dos.writeUTF("Introduce altura: ");
							altura=Double.parseDouble(dis.readUTF());								
							imc=peso/(altura*altura);
							Salud s=new Salud(fecha6,peso,pulsaciones,altura,imc);
							boolean existeSalud = buscarSaludExiste(u.getId(), s.getFecha());
							if (existeSalud) {
								dos.writeUTF("LOS DATOS DE SALUD YA EXISTE ");
							}
							else {
							if(anadirSalud(u.getId(), s)){
								dos.writeUTF("LOS DATOS DE SALUD SE HAN ANADIDO ");
							}
							else {
								dos.writeUTF("LOS DATOS DE SALUD NO SE HAN ANADIDO");
							}
							}
							break;
						case 7:
							Date fecha7;
							SimpleDateFormat sdf7 = new SimpleDateFormat("dd-MM-yyyy");
							String nombre7;
							dos.writeUTF("Introduce fecha: ");
							fecha7=sdf7.parse(dis.readUTF());
							dos.writeUTF("Introduce nombre: ");
							nombre7=dis.readUTF();
							boolean existeEntrenamiento7 = buscarEntrenamientoExiste(u.getId(), fecha7, nombre7);
							if (!existeEntrenamiento7) {
								dos.writeUTF("EL ENTRENAMIENTO NO EXISTE ");
							}
							else{
								if(borrarEntrenamiento(u.getId(), fecha7, nombre7)) {
									dos.writeUTF("EL ENTRENAMIENTO SE HA BORRADO");
								}
								else {
									dos.writeUTF("EL ENTRENAMIENTO NO SE HA BORRADO");//POR SI SALTA EXCEPCION
								}
							}
							break;
						case 8:
							Date fecha8;
							SimpleDateFormat sdf8 = new SimpleDateFormat("dd-MM-yyyy");
							dos.writeUTF("Introduce fecha: ");
							fecha8=sdf8.parse(dis.readUTF());
							boolean existeSalud8 = buscarSaludExiste(u.getId(), fecha8);
							if (!existeSalud8) {
								dos.writeUTF("EL DATO DE SALUD NO EXISTE ");
							}
							else{
								if(borrarSalud(u.getId(), fecha8)) {
									dos.writeUTF("EL DATO DE SALUD SE HA BORRADO");
								}
								else {
									dos.writeUTF("EL DATO DE SALUD NO SE HA BORRADO");//POR SI SALTA EXCEPCION
								}
							}
							break;
						}
						}while(elegir!=0);
						dos.writeUTF("close");
					}
					else if(u == null) {
						dos.writeUTF("close");
						System.out.println("SE HA DESCONECTADO:" + u.getUser());
					}
					dos.writeUTF("close");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}	
	}
	
	public static Usuario buscarUsuario(String usuario,String password) {
		BufferedReader br = null;
		Usuario u = null;
		try {
			br =new BufferedReader(new FileReader("bd/usuarios.csv"));
			String line = br.readLine(); // Nos la saltamos son las cabeceras
			line = br.readLine();
			while (line!=null) {
				 String [] partes = line.split(",");				 
				 if(partes[1].equals(usuario) && partes[2].equals(password)) {

					  u = new Usuario(usuario,password,Integer.parseInt(partes[0]));
					 }
				 line = br.readLine();
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return u;
	}
	public static ArrayList<Entrenamiento> buscarEntrenamiento(int id, Date fechaIni, Date fechaFin){
		BufferedReader br = null;
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		ArrayList<Entrenamiento> l= new ArrayList<>();
		try {
			br =new BufferedReader(new FileReader("bd/entrenamientos.csv"));
			String line = br.readLine();//para saltarte cabeceera
			line = br.readLine();
			while (null!=line) {
				 String [] partes = line.split(",");
				 Date fecha =sdf.parse(partes[1]);
				 if(Integer.parseInt(partes[0]) == id) {
					 if(fechaIni.compareTo(fecha)<=0 && fechaFin.compareTo(fecha)>=0) {
						 String nombre = partes[2];
						 String[] repeticiones = partes[3].split("-");
						 ArrayList<Integer> lrep = new ArrayList<>();
						 for(String r : repeticiones) {
							 lrep.add(Integer.parseInt(r));
						 }
						 int series = Integer.parseInt(partes[4]);
						 String[] pesos = partes[5].split("-");
						 ArrayList<Double> lpesos = new ArrayList<>();
						 for(String p : pesos) {
							 lpesos.add(Double.parseDouble(p));
						 }
						 l.add(new Entrenamiento(fecha,nombre,lrep,series,lpesos));
					 }
				 }
			
					line=br.readLine();
				
			}
		} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			if(br!=null) {
				try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
			
		}
		return l;
	}
	public static ArrayList<Entrenamiento> buscarEntrenamientoNombre(int id, Date fechaIni, Date fechaFin,String nombreEjercicio){
		BufferedReader br = null;
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		ArrayList<Entrenamiento> l= new ArrayList<>();
		try {
			br =new BufferedReader(new FileReader("bd/entrenamientos.csv"));
			String line = br.readLine();//para saltarte cabeceera
			line = br.readLine();
			while (null!=line) {
				 String [] partes = line.split(",");
				 Date fecha =sdf.parse(partes[1]);
				 if(Integer.parseInt(partes[0]) == id) {
					 String nombre = partes[2];
					 if(fechaIni.compareTo(fecha)<=0 && fechaFin.compareTo(fecha)>=0 && nombre.equals(nombreEjercicio)) {
						
						 String[] repeticiones = partes[3].split("-");
						 ArrayList<Integer> lrep = new ArrayList<>();
						 for(String r : repeticiones) {
							 lrep.add(Integer.parseInt(r));
						 }
						 int series = Integer.parseInt(partes[4]);
						 String[] pesos = partes[5].split("-");
						 ArrayList<Double> lpesos = new ArrayList<>();
						 for(String p : pesos) {
							 lpesos.add(Double.parseDouble(p));
						 }
						 l.add(new Entrenamiento(fecha,nombre,lrep,series,lpesos));
					 }
				 }
			
					line=br.readLine();
				
			}
		} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			if(br!=null) {
				try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
			
		}
		return l;
	}
	public static boolean buscarEntrenamientoExiste(int id, Date fechaEntrenamiento, String nombre){
		BufferedReader br = null;
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		
		try {
			br =new BufferedReader(new FileReader("bd/entrenamientos.csv"));
			String line = br.readLine();//para saltarte cabeceera
			line = br.readLine();
			while (null!=line) {
				 String [] partes = line.split(",");
				 Date fecha =sdf.parse(partes[1]);
				 if(Integer.parseInt(partes[0]) == id) {
					 if(fechaEntrenamiento.compareTo(fecha)==0 && nombre.equals(partes[2])) {
						return true;
					 }
				 }
			
					line=br.readLine();
				
			}
		} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			if(br!=null) {
				try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
		}
		return false;
	}
	//fecha y nombre son unicos
	public static boolean anadirEntrenamiento(int id, Entrenamiento e) {
		BufferedWriter br = null;
		try {
			br =new BufferedWriter(new FileWriter("bd/entrenamientos.csv",true));
			br.write("\r\n"+id + "," + e.toString());
			
		    return true;
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		finally {
			if(br!=null) {
				try {
				br.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			}
		}
		return false;

	}
	public static ArrayList<Salud> buscarSalud(int id, Date fechaIni2, Date fechaFin2){
		BufferedReader br = null;
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		ArrayList<Salud> l= new ArrayList<>();
		try {
			br =new BufferedReader(new FileReader("bd/salud.csv"));
			String line = br.readLine();//para saltarte cabeceera
			line = br.readLine();
			while (null!=line) {
				 String [] partes = line.split(",");
				 Date fecha =sdf.parse(partes[1]);
				 if(Integer.parseInt(partes[0]) == id) {
					 if(fechaIni2.compareTo(fecha)<=0 && fechaFin2.compareTo(fecha)>=0) {
						 double peso = Double.parseDouble(partes[2]);
						 int pulsaciones = Integer.parseInt(partes[3]);
						 double altura = Double.parseDouble(partes[4]);
						 double imc = Double.parseDouble(partes[5]);
						
						 l.add(new Salud(fecha,peso,pulsaciones,altura,imc));
					 }
				 }
			
					line=br.readLine();
				
			}
		} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			if(br!=null) {
				try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
			
		}
		return l;
	}
	public static boolean buscarSaludExiste(int id, Date fechaSalud){
		BufferedReader br = null;
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		
		try {
			br =new BufferedReader(new FileReader("bd/salud.csv"));
			String line = br.readLine();//para saltarte cabeceera
			line = br.readLine();
			while (null!=line) {
				 String [] partes = line.split(",");
				 Date fecha =sdf.parse(partes[1]);
				 if(Integer.parseInt(partes[0]) == id) {
					 if(fechaSalud.compareTo(fecha)==0 ) {
						return true;
					 }
				 }
			
					line=br.readLine();
				
			}
		} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			if(br!=null) {
				try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
		}
		return false;
	}
	//fecha es unico
	public static boolean anadirSalud(int id, Salud s) {
		BufferedWriter br = null;
		try {
			br =new BufferedWriter(new FileWriter("bd/salud.csv",true));
			br.write("\r\n"+id + "," + s.toString());
		    return true;
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		finally {
			if(br!=null) {
				try {
				br.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			}
		}
		return false;

	}
	public static boolean borrarEntrenamiento(int id, Date fecha, String nombreEjercicio){
		BufferedReader br = null;
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		File file=null;
		BufferedWriter bw=null;
		boolean borrar =  false;
		try {

			bw =new BufferedWriter(new FileWriter("bd/copiaEntrenamientos.csv",true));
			br =new BufferedReader(new FileReader("bd/entrenamientos.csv"));
			String line = br.readLine();//para saltarte cabeceera
			System.out.println(line);
			bw.write(line);
			bw.flush();
			line = br.readLine();
			while (null!=line) {
				 String [] partes = line.split(",");
				 Date f =sdf.parse(partes[1]);
				 if(!(Integer.parseInt(partes[0]) == id && fecha.compareTo(f)==0 && nombreEjercicio.equals(partes[2]))) {
					 bw.write("\r\n"+line);
					 bw.flush();
				}else
				{
					borrar = true;
				}
					line=br.readLine();
				
			}
		} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			if(br!=null) {
				try {
				br.close();
				bw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
		}
		file=new File("bd/entrenamientos.csv"); //abro el entrenamientos
		file.delete();//borro fichero		
		file=new File("bd/copiaEntrenamientos.csv");
		file.renameTo(new File("bd/entrenamientos.csv"));//lo renombro
		return borrar;
	}
	public static boolean borrarSalud(int id, Date fecha){
		BufferedReader br = null;
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		File file=null;
		BufferedWriter bw=null;
		boolean borrar =  false;
		try {

			bw =new BufferedWriter(new FileWriter("bd/copiaSalud.csv",true));
			br =new BufferedReader(new FileReader("bd/salud.csv"));
			String line = br.readLine();//para saltarte cabeceera
			System.out.println(line);
			bw.write(line);
			bw.flush();
			line = br.readLine();
			while (null!=line) {
				 String [] partes = line.split(",");
				 Date f =sdf.parse(partes[1]);
				 if(!(Integer.parseInt(partes[0]) == id && fecha.compareTo(f)==0 )) {
					 bw.write("\r\n"+line);
					 bw.flush();
				}else
				{
					borrar = true;
				}
					line=br.readLine();
				
			}
		} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			if(br!=null) {
				try {
				br.close();
				bw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
		}
		file=new File("bd/salud.csv"); //abro el entrenamientos
		file.delete();//borro fichero		
		file=new File("bd/copiaSalud.csv");
		file.renameTo(new File("bd/salud.csv"));//lo renombro
		return borrar;
	}
}
