import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;

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
//					Scanner entrada=new Scanner(System.in);
//					String usu=entrada.next();
//					System.out.println("CONTRASEÑA: ");
//					Scanner entrada2=new Scanner(System.in);
//					String contr=entrada.next();
				
					String usuario;
					String contraseña;
					dos.writeUTF("USUARIO: ");
					
					usuario=dis.readUTF();
					dos.writeUTF("CONTRASEÑA: ");
					contraseña=dis.readUTF();
					
					Usuario u = buscarUsuario(usuario,contraseña);
					if(u != null) {
						dos.writeUTF("Bienvenido "+ u.getUser());
						System.out.println("SE HA CONECTADO:"+  u.getUser());
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
}
