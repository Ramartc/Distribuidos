import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
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
}
