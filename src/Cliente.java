import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

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
					else {
						System.out.println(servidor);		
						outSocket.writeUTF(entrada.next());
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
